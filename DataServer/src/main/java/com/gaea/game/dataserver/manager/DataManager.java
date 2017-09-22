package com.gaea.game.dataserver.manager;

import com.gaea.game.core.constant.RedisKey;
import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.dao.uid.UidDao;
import com.gaea.game.core.dao.uid.UidTypeEnum;
import com.gaea.game.core.data.*;
import org.alan.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource(name = "redisRoleDao")
    private RoleDao roleDao;
    @Autowired
    private UidDao uidDao;
    @Autowired
    DSLogger dsLogger;

    public Credential vertifyAccount(VertifyInfo vertifyInfo) {
        PlatformUserInfo platformUserInfo = null;
        if ("1001".equals(vertifyInfo.uuid)) {
            platformUserInfo = createPlatformUserInfo(vertifyInfo);
        } else {
            //从平台去认证用户信息
            platformUserInfo = platformManager.vertify(vertifyInfo);
        }
        if (platformUserInfo == null) {
            return null;
        }
        //认证通过，在本地加载玩家信息，初次进入没有玩家信息直接创建一个
        Role role = roleDao.findByUserId(platformUserInfo.uid);
        HashOperations<String, Long, UserInfo> hashOperations = redisTemplate.opsForHash();
        if (role == null) {
            role = createRole(platformUserInfo);
        }
        //构造玩家结构
        String certifyToken = UUID.randomUUID().toString();
        long roleUid = role.roleUid;
        Credential credential = new Credential(certifyToken, roleUid);
        UserInfo userInfo = new UserInfo();
        userInfo.credential = credential;
        userInfo.playerId = roleUid;
        userInfo.platformUserInfo = platformUserInfo;
        userInfo.online = true;

        //将在线用户数据放入redis中管理
        hashOperations.put(RedisKey.ONLINE_PLAYER, roleUid, userInfo);
        return credential;
    }


    /**
     * 生成平台用户信息，测试时使用
     *
     * @param vertifyInfo
     * @return
     */
    public PlatformUserInfo createPlatformUserInfo(VertifyInfo vertifyInfo) {
        PlatformUserInfo platformUserInfo = new PlatformUserInfo();
        //int id = idcreate.getAndDecrement();
        String id = RandomUtils.getRandomString(8);
        platformUserInfo.uid = "id" + id;
        platformUserInfo.userName = "user" + id;
        platformUserInfo.sex = 1;
        platformUserInfo.token = vertifyInfo.token;

        return platformUserInfo;
    }

    public Role createRole(PlatformUserInfo platformUserInfo) {
        long roleUid = uidDao.getAndUpdateUid(UidTypeEnum.ROLE_UID, 1);
        Role role = new Role();
        role.name = platformUserInfo.userName;
        role.roleUid = roleUid;
        role.userId = platformUserInfo.uid;
        role.money = 10000L;
        roleDao.save(role);
        dsLogger.logCreateRole(role);
        return role;
    }
}
