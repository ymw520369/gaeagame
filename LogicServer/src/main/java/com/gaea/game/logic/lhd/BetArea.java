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
    public BetAreaType betAreaType;
    /* 押注用户列表*/
    public transient Map<Long, AtomicLong> playersBet = new ConcurrentHashMap<>();
    /* 赔率*/
    public float odds;
    /* 押注总金额*/
    public transient AtomicLong totalBet = new AtomicLong();

    public BetArea(BetAreaType betAreaType, float odds) {
        this.betAreaType = betAreaType;
        this.odds = odds;
    }

    public void clear() {
        playersBet.clear();
        totalBet.set(0);
    }

    public void bet(long playerId, int money) {
        AtomicLong atomicLong = playersBet.computeIfAbsent(playerId, id -> new AtomicLong(0));
        atomicLong.addAndGet(money);
        totalBet.addAndGet(money);
    }
}
