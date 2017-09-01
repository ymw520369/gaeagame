package com.gaea.game.logic.manager;

import com.gaea.game.base.constant.RedisKey;
import com.gaea.game.base.dao.PlayerDao;
import com.gaea.game.base.data.Player;
import com.gaea.game.base.ws.GameSession;
import com.gaea.game.base.ws.GameSessionListener;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.room.RoomController;
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
public class LogicManager implements GameSessionListener {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    PlayerDao playerDao;

    Map<Long, PlayerController> onlinePlayers = new HashMap<>();

    public void playerOnline(PlayerController playerController) {
        onlinePlayers.put(playerController.playerId(), playerController);
    }

    public int getPlayerNum() {
        return onlinePlayers.size();
    }

    @Override
    public void onSessionClose(GameSession session) {
        PlayerController playerController = (PlayerController) session.getReference();
        if (playerController != null) {
            //玩家退出后，将玩家数据清除并存入mongo
            long roleUid = playerController.playerId();
            HashOperations<String, Long, Player> hashOperations = redisTemplate.opsForHash();
            hashOperations.delete(RedisKey.ONLINE_PLAYER, roleUid);
            onlinePlayers.remove(playerController.playerId());
            RoomController roomController = playerController.roomController;
            if (roomController != null) {
                roomController.exitRoom(playerController);
            }
        }
    }
}
