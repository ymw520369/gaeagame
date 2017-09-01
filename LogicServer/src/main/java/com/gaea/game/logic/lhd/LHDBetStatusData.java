package com.gaea.game.logic.lhd;

import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;

import java.util.List;

/**
 * Created on 2017/9/1.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.LHD.TYPE, cmd = MessageConst.LHD.RESP_LHD_BET_DATA)
public class LHDBetStatusData extends LHDStatusData {
    public LHDStatus lhdStatus;
    public List<BetAreaDto> betAreaDtos;
    public int countDown;
}
