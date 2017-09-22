package com.gaea.game.logic.lhd;

import com.gaea.game.core.ws.Command;
import com.gaea.game.core.ws.MessageType;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.manager.LogicLogger;
import com.gaea.game.logic.room.RoomController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    LogicLogger logicLogger;

    Logger log = LoggerFactory.getLogger(getClass());

    @Command(MessageConst.LHD.REQ_BET)
    public void bet(PlayerController playerController, BetMessage betMessage) {
        log.debug("收到玩家押注信息，roleUid={},roleName={}", playerController.playerId, playerController.playerName);
        logicLogger.logBet(playerController, betMessage);
        RoomController roomController = playerController.roomController;
        if (roomController != null && roomController.gameController != null) {
            roomController.gameController.onMessage(playerController, betMessage);
        }
    }

    @Command(MessageConst.LHD.REQ_LHD_STATUS_COUNTDOWN)
    public void syncCountdown(PlayerController playerController, StatusMessage statusMessage) {
        RoomController roomController = playerController.roomController;
        if (roomController != null && roomController.gameController != null) {
            roomController.gameController.onMessage(playerController, statusMessage);
        }
    }
}
