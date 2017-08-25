package com.gaea.game.logic.data;

import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;

/**
 * Created on 2017/8/25.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.ROOM.TYPE, cmd = MessageConst.ROOM.RESP_EXIT_ROOM)
public class ExitRoomResult {
    public boolean result;
}
