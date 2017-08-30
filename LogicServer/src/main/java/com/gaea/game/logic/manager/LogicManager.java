package com.gaea.game.logic.manager;

import com.gaea.game.base.constant.RedisKey;
import com.gaea.game.base.dao.RoleDao;
import com.gaea.game.base.data.Player;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.data.PlayerExitListener;
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
public class LogicManager implements PlayerExitListener {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    RoleDao roleDao;

    Map<Long, PlayerController> onlinePlayers = new HashMap<>();

    @Override
    public void playerExit(PlayerController playerController) {
        //玩家退出后，将玩家数据清除并存入mongo
        long roleUid = playerController.playerId();
        HashOperations<String, Long, Player> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(RedisKey.ONLINE_PLAYER, roleUid);
        roleDao.save(playerController.player.role);
    }

    public void playerOnline(PlayerController playerController) {
        onlinePlayers.put(playerController.playerId(), playerController);
    }

    public int getPlayerNum() {
        return onlinePlayers.size();
    }
}
