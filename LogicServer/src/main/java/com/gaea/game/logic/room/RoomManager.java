package com.gaea.game.logic.room;

import com.gaea.game.base.dao.PlayerDao;
import com.gaea.game.base.timer.TimerCenter;
import com.gaea.game.logic.config.LogicConfig;
import com.gaea.game.logic.constant.GameResultEnum;
import com.gaea.game.logic.data.GameAnn;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.game.GameController;
import com.gaea.game.logic.sample.game.GameConfigInfo;
import org.alan.utils.ClassUtils;
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
    public static final String ROOM_UID_KEY = "ROOM_UID:";

    private Map<Integer, Class<GameController>> gameTypeClassMap = new HashMap<>();

    private Map<Long, RoomController> rooms = new ConcurrentHashMap<>();
    @Autowired
    private TimerCenter timerCenter;
    @Autowired
    private LogicConfig logicConfig;
    @Autowired
    RedisTemplate<String, Long> redisTemplate;
    @Autowired
    PlayerDao playerDao;

    public GameResultEnum createRoom(PlayerController playerController, int gameSid) {
        long uid = redisTemplate.opsForValue().increment(ROOM_UID_KEY, 1);
        GameConfigInfo gameConfigInfo = GameConfigInfo.getGameConfigInfo(gameSid);
        RoomController roomController = new RoomController(playerController, gameConfigInfo, uid);
        Class<GameController> clazz = gameTypeClassMap.get(gameConfigInfo.gameType);
        try {
            GameController gameController = clazz.newInstance();
            roomController.gameController = gameController;
            gameController.setRoomController(roomController);
            gameController.setTimerCenter(timerCenter);
            gameController.setPlayerDao(playerDao);
            gameController.setGameConfigInfo(gameConfigInfo);
            gameController.start();
            if (playerController != null) {
                roomController.joinRoom(playerController);
                playerController.roomController = roomController;
            }
            return GameResultEnum.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GameResultEnum.CREATE_ROOM_FAIL;
    }

    public GameResultEnum joinRoom(PlayerController playerController, int roomId) {
        RoomController roomController = rooms.get(roomId);
        if (roomController != null) {
            roomController.joinRoom(playerController);
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
