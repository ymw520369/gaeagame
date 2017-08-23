package com.gaya.game.manager;

import com.gaya.game.constant.GameResultEnum;
import com.gaya.game.constant.RedisKey;
import com.gaya.game.dao.RoleDao;
import com.gaya.game.dao.uid.UidDao;
import com.gaya.game.dao.uid.UidTypeEnum;
import com.gaya.game.data.*;
import com.gaya.game.ws.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@Component
public class DataManager implements PlayerExitListener {

    @Resource(name = "centerRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    PlatformManager platformManager;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UidDao uidDao;

    public GameResultEnum vertifyAccount(GameSession session, VertifyInfo vertifyInfo) {
        //从平台去认证用户信息
        PlatformUserInfo platformUserInfo = platformManager.vertify(vertifyInfo);
        if (platformUserInfo == null) {
            return GameResultEnum.FAILURE;
        }
        //认证通过，在本地加载玩家信息，初次进入没有玩家信息直接创建一个
        Role role = roleDao.findByUserId(platformUserInfo.userId);
        if (role == null) {
            role = createRole(platformUserInfo);
        }
        //构造玩家结构
        Player player = new Player();
        player.role = role;
        player.userInfo = platformUserInfo;
        PlayerController playerController = new PlayerController(session, player);
        session.setReference(playerController);

        //将在线用户数据放入redis中管理
        HashOperations<String, Long, Player> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(RedisKey.ONLINE_PLAYER, player.role.roleUid, player);
        return GameResultEnum.SUCCESS;
    }

    public Role createRole(PlatformUserInfo platformUserInfo) {
        long roleUid = uidDao.getAndUpdateUid(UidTypeEnum.ROLE_UID, 1);
        Role role = new Role();
        role.name = platformUserInfo.userName;
        role.roleUid = roleUid;
        role.userId = platformUserInfo.userId;
        role.money = 10000;
        roleDao.save(role);
        return role;
    }

    @Override
    public void playerExit(PlayerController playerController) {

    }
}
