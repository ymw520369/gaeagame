package com.gaea.game.logic.handler;

import com.gaea.game.base.ws.Command;
import com.gaea.game.base.ws.MessageType;
import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.GameType;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.room.RoomController;
import com.gaea.game.logic.room.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.ROOM.TYPE)
public class RoomMessageHandler {
    @WSMessage
    public static class CreateGame {
        public GameType gameType;
    }

    @Autowired
    RoomManager roomManager;

    @Command(MessageConst.ROOM.REQ_CREATE_ROOM)
    public void createRoom(PlayerController playerController, CreateGame createRoom) {
        roomManager.createRoom(playerController, createRoom.gameType);
    }

    @Command(MessageConst.ROOM.REQ_EXIT_ROOM)
    public void exitRoom(PlayerController playerController) {
        RoomController roomController = playerController.roomController;
        if (roomController != null) {
            roomController.exitRoom(playerController);
        }
    }
}
