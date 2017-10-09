package com.gaea.game.logic.handler;

import com.gaea.game.core.constant.RedisKey;
import com.gaea.game.core.constant.ResultEnum;
import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.data.Credential;
import com.gaea.game.core.data.Player;
import com.gaea.game.core.data.Role;
import com.gaea.game.core.data.UserInfo;
import com.gaea.game.core.ws.Command;
import com.gaea.game.core.ws.GameSession;
import com.gaea.game.core.ws.MessageType;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.LoginResult;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.manager.LogicLogger;
import com.gaea.game.logic.manager.LogicServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.Login.TYPE)
public class LoginHandler {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LogicServer logicServer;
    @Resource(name = "redisRoleDao")
    RoleDao roleDao;

    @Autowired
    LogicLogger logicLogger;


    @Command(MessageConst.Login.REQ_VERTIFY)
    public void login(GameSession session, Credential credential) {
        HashOperations<String, Long, UserInfo> hashOperations = redisTemplate.opsForHash();
        UserInfo userInfo = hashOperations.get(RedisKey.ONLINE_PLAYER, credential.playerId);
        if (userInfo == null) {
            sendLoginResult(session, ResultEnum.FAILURE, null);
            return;
        }
        String certifyToken = userInfo.credential.certifyToken;
        if (credential.certifyToken.equals(certifyToken)) {
            Role role = roleDao.findOne(userInfo.playerId);
            Player player = new Player(role, userInfo);
            PlayerController playerController = new PlayerController(player);
            playerController.userInfo = userInfo;
            playerController.session = session;
            session.setReference(playerController);
            session.setSessionListener(logicServer);
            sendLoginResult(session, ResultEnum.SUCCESS, player);
            logicServer.playerOnline(playerController);
            userInfo.serverId = logicServer.logicConfig().serverId;
            userInfo.online = true;
            hashOperations.put(RedisKey.ONLINE_PLAYER, credential.playerId, userInfo);
            logicLogger.logLogin(role);
        } else {
            sendLoginResult(session, ResultEnum.FAILURE, null);
        }
    }

    public void sendLoginResult(GameSession session, ResultEnum resultEnum, Player player) {
        LoginResult loginResult = new LoginResult(resultEnum, player);
        session.send(loginResult);
    }
}
