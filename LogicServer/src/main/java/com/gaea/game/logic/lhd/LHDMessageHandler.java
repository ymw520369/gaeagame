package com.gaea.game.logic.lhd;

import com.gaea.game.base.ws.Command;
import com.gaea.game.base.ws.MessageType;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.room.RoomController;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/8/30.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.LHD.TYPE)
public class LHDMessageHandler {

    @Command(MessageConst.LHD.REQ_BET)
    public void bet(PlayerController playerController, BetMessage betMessage) {
        RoomController roomController = playerController.roomController;
        if (roomController != null && roomController.gameController != null) {
            roomController.gameController.onMessage(playerController, betMessage);
        }
    }
}
