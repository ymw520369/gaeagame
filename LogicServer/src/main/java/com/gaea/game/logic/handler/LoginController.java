package com.gaea.game.logic.handler;

import com.gaea.game.base.constant.ResultEnum;
import com.gaea.game.base.constant.RedisKey;
import com.gaea.game.base.dao.PlayerDao;
import com.gaea.game.base.data.Credential;
import com.gaea.game.base.data.Player;
import com.gaea.game.base.data.UserInfo;
import com.gaea.game.base.ws.Command;
import com.gaea.game.base.ws.GameSession;
import com.gaea.game.base.ws.MessageType;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.LoginResult;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.manager.LogicManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.Login.TYPE)
public class LoginController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LogicManager logicManager;
    @Autowired
    PlayerDao playerDao;

    @Command(MessageConst.Login.REQ_VERTIFY)
    public void login(GameSession session, Credential credential) {
        HashOperations<String, Long, UserInfo> hashOperations = redisTemplate.opsForHash();
        UserInfo userInfo = hashOperations.get(RedisKey.ONLINE_PLAYER, credential.playerId);
        if (userInfo == null) {
            sendLoginResult(session, ResultEnum.FAILURE, null);
        }
        String certifyToken = userInfo.credential.certifyToken;
        if (credential.certifyToken.equals(certifyToken)) {
            Player player = playerDao.findOne(userInfo.playerId);
            PlayerController playerController = new PlayerController(player);
            playerController.session = session;
            session.setReference(playerController);
            session.setSessionListener(logicManager);
            sendLoginResult(session, ResultEnum.SUCCESS, player);
            logicManager.playerOnline(playerController);
        } else {
            sendLoginResult(session, ResultEnum.FAILURE, null);
        }
    }

    public void sendLoginResult(GameSession session, ResultEnum resultEnum, Player player) {
        LoginResult loginResult = new LoginResult(resultEnum, player);
        session.send(loginResult);
    }
}
