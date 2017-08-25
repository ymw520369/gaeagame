package com.gaea.game.base.manager;

import com.gaea.game.base.constant.RedisKey;
import com.gaea.game.base.dao.RoleDao;
import com.gaea.game.base.dao.uid.UidDao;
import com.gaea.game.base.dao.uid.UidTypeEnum;
import com.gaea.game.base.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@Component
public class DataManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    PlatformManager platformManager;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UidDao uidDao;

    public Credential vertifyAccount(VertifyInfo vertifyInfo) {
        //从平台去认证用户信息
        PlatformUserInfo platformUserInfo = platformManager.vertify(vertifyInfo);
        if (platformUserInfo == null) {
            return null;
        }
        //认证通过，在本地加载玩家信息，初次进入没有玩家信息直接创建一个
        Role role = roleDao.findByUserId(platformUserInfo.userId);
        if (role == null) {
            role = createRole(platformUserInfo);
        }
        //构造玩家结构
        String certifyToken = UUID.randomUUID().toString();
        Player player = new Player();
        player.role = role;
        player.userInfo = platformUserInfo;
        player.certifyToken = certifyToken;
        //将在线用户数据放入redis中管理
        HashOperations<String, Long, Player> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(RedisKey.ONLINE_PLAYER, player.role.roleUid, player);

        Credential credential = new Credential(certifyToken, player.role.roleUid);
        return credential;
    }

    public Role createRole(PlatformUserInfo platformUserInfo) {
        long roleUid = uidDao.getAndUpdateUid(UidTypeEnum.ROLE_UID, 1);
        Role role = new Role();
        role.name = platformUserInfo.userName;
        role.roleUid = roleUid;
        role.userId = platformUserInfo.userId;
        role.money = new AtomicLong(10000L);
        roleDao.save(role);
        return role;
    }
}
