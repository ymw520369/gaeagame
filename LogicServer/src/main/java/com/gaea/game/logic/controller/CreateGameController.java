package com.gaea.game.logic.controller;

import com.gaea.game.base.constant.MessageConst;
import com.gaea.game.base.ws.Command;
import com.gaea.game.base.ws.GameSession;
import com.gaea.game.base.ws.MessageType;
import com.gaea.game.base.ws.WSMessage;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.ROOM.TYPE)
public class CreateGameController {
    @WSMessage
    public static class CreateGame {
        int roomType;
    }

    @Command(MessageConst.ROOM.REQ_CREATE_ROOM)
    public void createRoom(GameSession gameSession, CreateGame createRoom) {

    }
}
