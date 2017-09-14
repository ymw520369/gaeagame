package com.gaea.game.logic.room;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.dao.uid.UidDao;
import com.gaea.game.core.dao.uid.UidTypeEnum;
import com.gaea.game.core.data.LogicRoomInfo;
import com.gaea.game.core.dubbo.ILogicService;
import com.gaea.game.core.timer.TimerCenter;
import com.gaea.game.logic.config.LogicConfig;
import com.gaea.game.logic.constant.GameResultEnum;
import com.gaea.game.logic.data.GameAnn;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.game.GameController;
import com.gaea.game.logic.sample.game.GameConfigInfo;
import org.alan.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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

    public GameResultEnum createRoom(PlayerController playerController, int gameSid) {
        long uid = uidDao.getAndUpdateUid(UidTypeEnum.ROOM_UID, 1);
        GameConfigInfo gameConfigInfo = GameConfigInfo.getGameConfigInfo(gameSid);
        RoomController roomController = new RoomController(playerController, gameConfigInfo, uid);
        rooms.put(1L, roomController);
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
            return GameResultEnum.SUCCESS;
        } catch (Exception e) {
            log.warn("房间生成失败", e);
        }
        return GameResultEnum.CREATE_ROOM_FAIL;
    }

    public GameResultEnum joinRoom(PlayerController playerController, long roomId) {
        RoomController roomController = rooms.get(roomId);
        if (roomController != null) {
            return roomController.joinRoom(playerController);
        }
        return GameResultEnum.JOIN_ROOM_FAIL;
    }


    @Override
    public void run(String... strings) throws Exception {
        Set<Class<GameController>> classes = ClassUtils.getAllClassByClass("com.gaea", GameController.class);
        if (classes != null && !classes.isEmpty()) {
            classes.forEach(clazz -> {
                GameAnn gameAnn = clazz.getAnnotation(GameAnn.class);
                if (gameAnn != null) {
                    gameTypeClassMap.put(gameAnn.value().ordinal() + 1, clazz);
                }
            });
        }

        //前期系统默认创建一个房间
        if (logicConfig.debug) {
            createRoom(null, 1);
        }

    }
}
