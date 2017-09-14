package com.gaea.game.logic.lhd;

import com.gaea.game.core.ws.WSMessage;

/**
 * Created on 2017/8/25.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class BetMessage {
    public byte betArea;
    public int betMoney;
}
