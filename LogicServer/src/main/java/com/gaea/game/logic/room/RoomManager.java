package com.gaea.game.logic.room;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.dao.uid.UidDao;
import com.gaea.game.core.dao.uid.UidTypeEnum;
import com.gaea.game.core.data.LogicRoomInfo;
import com.gaea.game.core.data.PlatformConfig;
import com.gaea.game.core.dubbo.ILogicService;
import com.gaea.game.core.timer.TimerCenter;
import com.gaea.game.logic.config.LogicConfig;
import com.gaea.game.logic.constant.GameResultEnum;
import com.gaea.game.logic.data.GameAnn;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.game.GameController;
import com.gaea.game.logic.manager.LogicLogger;
import com.gaea.game.logic.sample.game.GameConfigInfo;
import org.alan.utils.ClassUtils;
import org.alan.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 房间管理器
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class RoomManager implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(getClass());
    private Map<Integer, Class<GameController>> gameTypeClassMap = new HashMap<>();
    private Map<Long, RoomController> rooms = new ConcurrentHashMap<>();
    @Autowired
    private TimerCenter timerCenter;
    @Autowired
    private LogicConfig logicConfig;
    @Autowired
    UidDao uidDao;
    @Autowired
    RoleDao roleDao;
    @Reference(version = "1.0.0")
    ILogicService logicService;
    @Autowired
    PlatformConfig platformConfig;
    @Autowired
    LogicLogger logicLogger;

    public GameResultEnum createRoom(PlayerController playerController, int gameSid, long liveRoomId) {
        long uid = 1;
        //如果没有直播房间号，默认是创建测试房间
        if (playerController == null) {
            uid = 1;
            log.info("系统创建测试房间，gameSid={},liveRoomId={}", gameSid, liveRoomId);
        } else {
            uid = uidDao.getAndUpdateUid(UidTypeEnum.ROOM_UID, 1);
            log.info("创建房间，roleUid={},gameSid={},liveRoomId={}", playerController.playerId, gameSid, liveRoomId);
        }
        GameConfigInfo gameConfigInfo = GameConfigInfo.getGameConfigInfo(gameSid);
        RoomController roomController = new RoomController(playerController, gameConfigInfo, uid);
        roomController.setLiveRoomUid(liveRoomId);
        rooms.put(uid, roomController);
        Class<GameController> clazz = gameTypeClassMap.get(gameConfigInfo.gameType);
        try {
            GameController gameController = clazz.newInstance();
            roomController.gameController = gameController;
            gameController.setRoomController(roomController);
            gameController.setTimerCenter(timerCenter);
            gameController.setRoleDao(roleDao);
            gameController.setGameConfigInfo(gameConfigInfo);
            gameController.start();
            if (playerController != null) {
                roomController.joinRoom(playerController);
                playerController.roomController = roomController;
            }

            LogicRoomInfo logicRoomInfo = roomController.getLogicRoomInfo();
            logicRoomInfo.wsAddress = logicConfig.wsAddress;
            logicService.addRoom(logicRoomInfo);
            if (playerController != null && liveRoomId > 0) {
                uploadCreateRoom(playerController, roomController);
                logicLogger.createRoom(playerController, gameSid, liveRoomId, uid);
            }
            return GameResultEnum.SUCCESS;
        } catch (Exception e) {
            log.warn("房间生成失败", e);
        }
        return GameResultEnum.CREATE_ROOM_FAIL;
    }

    public void uploadCreateRoom(PlayerController playerController, RoomController roomController) {
        if (!StringUtils.isEmpty(platformConfig.createRoomUpUrl)) {
            Map<String, String> head = new HashMap<>();
            head.put("userId", playerController.getUserInfo().platformUserInfo.uid);
            Map<String, String> params = new HashMap<>();
            params.put("head", JSON.toJSONString(head));
            params.put("avRoomId", "" + roomController.getLiveRoomUid());
            params.put("gameRoomId", "" + roomController.uid);
            params.put("gameName", roomController.gameController.getGameConfigInfo().getName());
            try {
                HttpUtils.HttpResponse httpResponse = HttpUtils.doPost(platformConfig.createRoomUpUrl, params);
                if (httpResponse.isOk()) {
                    if (httpResponse.getIntValue("resultCode") == 1) {
                        log.debug("上传房间数据成功，roomInfo={}", JSON.toJSONString(params));
                    } else {
                        log.warn("上传房间数据失败，des={}，roomInfo={}", httpResponse.getValueByKey("resultMessage"), JSON.toJSONString(params));
                    }
                } else {
                    log.warn("上传房间数据失败，stateCode={},url={}", httpResponse.getStatusCode(), platformConfig.createRoomUpUrl);
                }
            } catch (Exception e) {
                log.warn("", e);
            }
        }
    }

    public GameResultEnum joinRoom(PlayerController playerController, long roomId) {
        log.info("玩家加入房间,roleUid={},roomId={}", playerController.playerId, roomId);
        RoomController roomController = rooms.get(roomId);
        GameResultEnum resultEnum = null;
        if (roomController != null) {
            resultEnum = roomController.joinRoom(playerController);
        } else {
            resultEnum = GameResultEnum.JOIN_ROOM_FAIL;
        }
        logicLogger.joinRoom(playerController, roomId, resultEnum);
        return resultEnum;
    }


    @Override
    public void run(String... strings) throws Exception {
        Set<Class<GameController>> classes = ClassUtils.getAllClassByClass("com.gaea", GameController.class);
        if (classes != null && !classes.isEmpty()) {
            classes.forEach(clazz -> {
                GameAnn gameAnn = clazz.getAnnotation(GameAnn.class);
                if (gameAnn != null) {
                    gameTypeClassMap.put(gameAnn.value(), clazz);
                }
            });
        }

        //前期系统默认创建一个房间
        if (logicConfig.debug) {
            createRoom(null, 1, -1);
        }

    }
}
