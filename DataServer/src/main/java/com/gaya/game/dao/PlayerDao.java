package com.gaya.game.dao;

import com.gaya.game.data.Player;
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

    public abstract Player findPlayerByUserId(int zoneId, long userId);

    public abstract Player createPlayer(int zoneId, long userId);
}
