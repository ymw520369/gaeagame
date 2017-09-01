package com.gaea.game.logic.message;

import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.GameResultEnum;
import com.gaea.game.logic.constant.MessageConst;

/**
 * Created on 2017/8/30.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.Tips.TYPE, cmd = MessageConst.Tips.RESP_GAME_TIPS)
public class GameTips {
    public static final byte TIMER_TIPS = 1, CLOSED_TIPS = 2;
    public int tipsType;
    public int resultCode;
    public String resultDes;

    public GameTips(int tipsType, GameResultEnum gameResultEnum) {
        this.tipsType = tipsType;
        this.resultCode = gameResultEnum.code;
        resultDes = gameResultEnum.message;
    }
}
