package com.gaea.game.base.dao;

import com.gaea.game.base.data.Player;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created on 2017/8/2.
 *
 * @author Alan
 * @since 1.0
 */
public abstract class PlayerDao extends MongoBaseDao<Player, Long> {
    public PlayerDao(MongoTemplate mongoTemplate) {
        super(Player.class, mongoTemplate);
    }

    public abstract long findPlayerId(String userId);

    public abstract Player findPlayerByUserId(String userId);

    public abstract boolean addMoney(long playerId, long money);

    public abstract boolean reduceMoney(long playerId, long money);
}
