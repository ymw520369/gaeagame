package com.gaea.game.base.manager;

import com.gaea.game.base.constant.RedisKey;
import com.gaea.game.base.dao.PlayerDao;
import com.gaea.game.base.dao.uid.UidDao;
import com.gaea.game.base.dao.uid.UidTypeEnum;
import com.gaea.game.base.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@Component
public class DataManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    PlatformManager platformManager;

    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private UidDao uidDao;

    public Credential vertifyAccount(VertifyInfo vertifyInfo) {
        //从平台去认证用户信息
        PlatformUserInfo platformUserInfo = platformManager.vertify(vertifyInfo);
        if (platformUserInfo == null) {
            return null;
        }
        //认证通过，在本地加载玩家信息，初次进入没有玩家信息直接创建一个
        long playerId = playerDao.findPlayerId(platformUserInfo.userId);
        HashOperations<String, Long, UserInfo> hashOperations = redisTemplate.opsForHash();
        if (playerId <= 0) {
            playerId = createPlayer(platformUserInfo);
        }
        //构造玩家结构
        String certifyToken = UUID.randomUUID().toString();
        Credential credential = new Credential(certifyToken, playerId);
        UserInfo userInfo = new UserInfo();
        userInfo.credential = credential;
        userInfo.playerId = playerId;
        userInfo.platformUserInfo = platformUserInfo;
        userInfo.online = true;

        //将在线用户数据放入redis中管理
        hashOperations.put(RedisKey.ONLINE_PLAYER, playerId, userInfo);
        return credential;
    }

    public long createPlayer(PlatformUserInfo platformUserInfo) {
        long roleUid = uidDao.getAndUpdateUid(UidTypeEnum.ROLE_UID, 1);
        Role role = new Role();
        role.name = platformUserInfo.userName;
        role.roleUid = roleUid;
        role.userId = platformUserInfo.userId;
        role.money = 10000L;
        Player player = new Player();
        player.role = role;
        playerDao.save(player);
        return roleUid;
    }
}
