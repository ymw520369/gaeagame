package com.gaea.game.logic.controller;

import com.gaea.game.base.constant.GameResultEnum;
import com.gaea.game.base.constant.MessageConst;
import com.gaea.game.base.constant.RedisKey;
import com.gaea.game.base.data.Credential;
import com.gaea.game.base.data.Player;
import com.gaea.game.base.data.PlayerController;
import com.gaea.game.base.ws.Command;
import com.gaea.game.base.ws.GameSession;
import com.gaea.game.base.ws.MessageType;
import com.gaea.game.logic.data.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@MessageType(MessageConst.Login.TYPE)
public class LoginController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Command(MessageConst.Login.REQ_VERTIFY)
    public void login(GameSession session, Credential credential) {
        HashOperations<String, Long, Player> hashOperations = redisTemplate.opsForHash();
        Player player = hashOperations.get(RedisKey.ONLINE_PLAYER, credential.playerId);
        if (player == null || credential == null) {
            sendLoginResult(session, GameResultEnum.FAILURE, null);
        }
        if (credential.certifyToken.equals(player.certifyToken)) {
            PlayerController playerController = new PlayerController(player);
            playerController.session = session;
            session.setReference(playerController);
            sendLoginResult(session, GameResultEnum.SUCCESS, player);
        } else {
            sendLoginResult(session, GameResultEnum.FAILURE, null);
        }
    }

    public void sendLoginResult(GameSession session, GameResultEnum resultEnum, Player player) {
        LoginResult loginResult = new LoginResult(resultEnum, player);
        session.send(loginResult);
    }
}
