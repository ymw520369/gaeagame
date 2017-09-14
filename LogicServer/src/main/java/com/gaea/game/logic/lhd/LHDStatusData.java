package com.gaea.game.logic.lhd;

import com.gaea.game.core.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;

/**
 * Created on 2017/9/1.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.LHD.TYPE, cmd = MessageConst.LHD.RESP_LHD_STATUS_DATA)
public class LHDStatusData {
    public LHDStatus lhdStatus;
    public int countdown;
}
