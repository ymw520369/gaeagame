package com.gaea.game.logic.lhd;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gaea.game.core.log.ILogService;
import com.gaea.game.core.ws.Command;
import com.gaea.game.core.ws.MessageType;
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

    @Reference(version = "1.0.0")
    ILogService logService;

    @Command(MessageConst.LHD.REQ_BET)
    public void bet(PlayerController playerController, BetMessage betMessage) {
        logService.log("收到押注消息");
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
