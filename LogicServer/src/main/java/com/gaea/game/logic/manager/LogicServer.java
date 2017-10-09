package com.gaea.game.logic.manager;

import com.gaea.game.core.constant.RedisKey;
import com.gaea.game.core.data.UserInfo;
import com.gaea.game.core.ws.GameSession;
import com.gaea.game.core.ws.GameSessionListener;
import com.gaea.game.logic.config.LogicConfig;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.room.RoomController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/8/25.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class LogicServer implements GameSessionListener {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LogicConfig logicConfig;

    Map<Long, PlayerController> onlinePlayers = new HashMap<>();
    @Autowired
    LogicLogger logicLogger;

    public LogicConfig logicConfig() {
        return logicConfig;
    }

    public void playerOnline(PlayerController playerController) {
        log.info("玩家上线，playerId={}", playerController.playerId);
        onlinePlayers.put(playerController.playerId(), playerController);
    }

    public int getPlayerNum() {
        return onlinePlayers.size();
    }

    @Override
    public void onSessionClose(GameSession session) {
        PlayerController playerController = (PlayerController) session.getReference();
        log.info("session 断开，sessionId={}", session);
        if (playerController != null) {
            log.info("玩家下线，playerId={}", playerController.playerId);
            //玩家退出后，将玩家数据清除并存入mongo
            long roleUid = playerController.playerId();
            HashOperations<String, Long, UserInfo> hashOperations = redisTemplate.opsForHash();
            UserInfo userInfo = hashOperations.get(RedisKey.ONLINE_PLAYER, roleUid);
            RoomController roomController = playerController.roomController;
            if (roomController != null) {
                userInfo.roomId = playerController.roomController.uid;
                roomController.exitRoom(playerController);
            }
            userInfo.online = false;
            userInfo.lastTime = System.currentTimeMillis();
            hashOperations.put(RedisKey.ONLINE_PLAYER, roleUid, userInfo);
            onlinePlayers.remove(playerController.playerId());
            logicLogger.logLogout(playerController.player.role);
        }
    }
}
