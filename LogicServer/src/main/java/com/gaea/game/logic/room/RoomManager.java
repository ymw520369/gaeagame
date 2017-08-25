package com.gaea.game.logic.room;

import com.gaea.game.base.timer.TimerCenter;
import com.gaea.game.logic.data.GameAnn;
import com.gaea.game.logic.data.GameType;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.lhd.GameController;
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

    private Map<GameType, Class<GameController>> gameTypeClassMap = new HashMap<>();

    private Map<Long, RoomController> rooms = new ConcurrentHashMap<>();

    @Autowired
    private TimerCenter timerCenter;

    @Autowired
    RedisTemplate<String, Long> redisTemplate;

    public RoomController createRoom(PlayerController playerController, GameType gameType) {
        long uid = redisTemplate.opsForValue().increment(ROOM_UID_KEY, 1);
        RoomController roomController = new RoomController(playerController, gameType, uid);
        Class<GameController> clazz = gameTypeClassMap.get(gameType);
        try {
            GameController gameController = clazz.newInstance();
            roomController.gameController = gameController;
            gameController.setRoomController(roomController);
            gameController.setTimerCenter(timerCenter);
            gameController.start();
            roomController.joinRoom(playerController);
            playerController.roomController = roomController;
            return roomController;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    }
}
