package com.gaea.game.logic.lhd;

import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.game.PokerCard;

/**
 * Created on 2017/8/25.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.LHD.TYPE, cmd = MessageConst.LHD.RESP_GAME_INFO)
public class LHDGameInfo extends GameInfo {
    /* 当前是第几局*/
    protected int round = 0;
    /* 当前状态*/
    protected int currentStatus;
    /* 龙牌*/
    protected PokerCard longCard;
    /* 虎牌*/
    protected PokerCard huCard;
    /* 押注区信息*/
    protected BetArea[] betAreas;
}
