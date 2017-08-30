package com.gaea.game.logic.lhd;

import com.gaea.game.base.ws.WSMessage;

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
@WSMessage
public class BetArea {
    public interface Type {
        int LONG_AREA = 1, HE_AREA = 2, HU_AREA = 3;
    }

    /* 押注用户列表*/
    public transient Map<Long, AtomicLong> playersBet = new ConcurrentHashMap<>();
    /* 赔率*/
    public float odds;
    /* 押注总金额*/
    public transient AtomicLong totalBet = new AtomicLong();
    /* 押注总金额*/
    private long totalBetMoney;
    /* 该用户押注金额，该字段仅用于数据传输*/
    public long playerBet;

    public BetArea(float odds) {
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
