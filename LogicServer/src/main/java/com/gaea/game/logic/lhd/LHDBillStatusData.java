package com.gaea.game.logic.lhd;

import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.sample.poker.Poker;

/**
 * Created on 2017/8/31.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.LHD.TYPE, cmd = MessageConst.LHD.RESP_LHD_BILL_DATA)
public class LHDBillStatusData extends LHDStatusData {
    public long playerBet;
    public long playerWinMoney;
    public long playerMoney;
    public long totalBet;
    public long totalWinMoney;
    /* 龙牌*/
    public Poker longCard;
    /* 虎牌*/
    public Poker huCard;

}
