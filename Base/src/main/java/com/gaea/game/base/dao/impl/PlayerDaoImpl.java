package com.gaea.game.base.dao.impl;

import com.gaea.game.base.dao.PlayerDao;
import com.gaea.game.base.data.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@Component
public class PlayerDaoImpl extends PlayerDao {
    Logger log = LoggerFactory.getLogger(getClass());

    public PlayerDaoImpl(@Autowired MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public long findPlayerId(String userId) {
        return 0;
    }

    @Override
    public Player findPlayerByUserId(String userId) {
        log.debug("find role by user id, userid is {}", userId);
        Query query = Query.query(Criteria.where("userUid").is(userId));
        return mongoTemplate.findOne(query
                , Player.class);
    }

    @Override
    public boolean addMoney(long playerId, long money) {
        return false;
    }

    @Override
    public boolean reduceMoney(long playerId, long money) {
        return false;
    }

}
