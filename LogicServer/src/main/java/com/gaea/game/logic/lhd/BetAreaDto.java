package com.gaea.game.logic.lhd;

import com.gaea.game.base.ws.WSMessage;

/**
 * Created on 2017/9/1.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class BetAreaDto {
    public BetAreaType betAreaType;
    public long totalBet;
    public long selfBet;
}
