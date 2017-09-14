/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年2月27日
 */
package com.gaea.game.core.dao.uid.impl;

import com.gaea.game.core.constant.RedisKey;
import com.gaea.game.core.dao.uid.UidDao;
import com.gaea.game.core.dao.uid.UidTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Alan
 * @since 1.0
 */
@Repository
public class RedisUidDaoImpl implements UidDao, CommandLineRunner {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        initUid();
    }

    private void initUid() {
        for (UidTypeEnum key : UidTypeEnum.values()) {
            HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
            hashOperations.putIfAbsent(RedisKey.UID_KEY, key.name(), key.getBeginNum());
        }
    }

    @Override
    public long getAndUpdateUid(UidTypeEnum key, int num) {
        if (num <= 0 || num == Integer.MAX_VALUE) {
            throw new RuntimeException("参数错误，key=" + key + ",num=" + num);
        }
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        return hashOperations.increment(RedisKey.UID_KEY, key.name(), num);
    }
}
