package com.gaea.game.logic.lhd;

import com.gaea.game.core.constant.ResultEnum;
import com.gaea.game.core.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;

/**
 * Created on 2017/9/27.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.LHD.TYPE, cmd = MessageConst.LHD.RESP_LHD_BET_RESULT)
public class BetResult {
    public ResultEnum result;
    public long remainMoney;

    public BetResult(ResultEnum result, long remainMoney) {
        this.result = result;
        this.remainMoney = remainMoney;
    }
}
