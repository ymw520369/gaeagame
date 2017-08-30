package com.gaea.game.logic.lhd;

import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.sample.poker.Poker;

import java.util.List;

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
    protected Poker longCard;
    /* 虎牌*/
    protected Poker huCard;
    /* 押注区信息*/
    protected List<BetArea> betAreas;
}
