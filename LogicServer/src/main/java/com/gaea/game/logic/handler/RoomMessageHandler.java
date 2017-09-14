package com.gaea.game.logic.handler;

import com.gaea.game.core.ws.Command;
import com.gaea.game.core.ws.MessageType;
import com.gaea.game.core.ws.WSMessage;
import com.gaea.game.logic.constant.GameResultEnum;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.message.MessageToClient;
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
        public int gameSid;
    }

    @WSMessage
    public static class JoinRoom {
        public int roomId;
    }

    @WSMessage
    public static class ExitRoom {
        public int roomId;
    }

    @WSMessage(resp = true, messageType = MessageConst.ROOM.TYPE, cmd = MessageConst.ROOM.RESP_EXIT_ROOM)
    public static class ExitRoomResult {
        public GameResultEnum gameResultEnum;
    }

    @Autowired
    RoomManager roomManager;

    @Command(MessageConst.ROOM.REQ_CREATE_ROOM)
    public void createRoom(PlayerController playerController, CreateGame createRoom) {
        GameResultEnum gameResultEnum = roomManager.createRoom(playerController, createRoom.gameSid);
        if (gameResultEnum != GameResultEnum.SUCCESS) {
            MessageToClient.sendTimerGameTips(playerController.session, gameResultEnum);
        }
    }

    @Command(MessageConst.ROOM.REQ_JOIN_ROOM)
    public void joinRoom(PlayerController playerController, JoinRoom joinRoom) {
        GameResultEnum gameResultEnum = roomManager.joinRoom(playerController, joinRoom.roomId);
        if (gameResultEnum != GameResultEnum.SUCCESS) {
            MessageToClient.sendTimerGameTips(playerController.session, gameResultEnum);
        }
    }

    @Command(MessageConst.ROOM.REQ_EXIT_ROOM)
    public void exitRoom(PlayerController playerController, ExitRoom exitRoom) {
        GameResultEnum gameResultEnum = GameResultEnum.ILLEGAL;
        RoomController roomController = playerController.roomController;
        if (roomController != null) {
            gameResultEnum = roomController.exitRoom(playerController);
        }
        ExitRoomResult exitRoomResult = new ExitRoomResult();
        exitRoomResult.gameResultEnum = gameResultEnum;
        playerController.sendToClient(exitRoomResult);
    }
}
