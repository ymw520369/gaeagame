package com.gaea.game.logic.lhd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 押注区数据结构
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class BetArea {
    /* 押注用户列表*/
    public Map<Long, AtomicLong> playersBet = new ConcurrentHashMap<>();
    /* 赔率*/
    public float odds;
    /* 押注总金额*/
    public AtomicLong totalBet = new AtomicLong();

    public BetArea(float odds) {
        this.odds = odds;
    }

    public void clear() {
        playersBet.clear();
        totalBet.set(0);
    }
}
