package com.gaea.game.logic.lhd;

import com.gaea.game.core.data.Role;
import com.gaea.game.core.timer.TimerEvent;
import com.gaea.game.core.timer.TimerListener;
import com.gaea.game.logic.data.GameAnn;
import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.data.GameType;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.game.GameController;
import com.gaea.game.logic.game.PokerHelper;
import com.gaea.game.logic.sample.game.LHDConfigInfo;
import com.gaea.game.logic.sample.poker.Poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 龙虎斗游戏管理器
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@GameAnn(GameType.LHD)
public class LHDGameController extends GameController<LHDConfigInfo> implements TimerListener {
    /* 当前是第几局*/
    protected int round = 0;
    /* 当前状态*/
    protected LHDStatus currentStatus = LHDStatus.IDEL;
    /* 龙牌*/
    protected Poker longCard;
    /* 虎牌*/
    protected Poker huCard;

    protected int betBeginTime;

    /* 押注区*/
    protected BetArea[] betAreas = new BetArea[3];

    @Override
    public void start() {
        currentStatus = LHDStatus.IDEL;
        idel();
    }

    @Override
    public void onTimer(TimerEvent e) {
        if (LHDStatus.IDEL == e.getParameter()) {
            idel();
        } else if (LHDStatus.READY == e.getParameter()) {
            readyBet();
        } else if (LHDStatus.BET == e.getParameter()) {
            beginBet();
        } else if (LHDStatus.BILL == e.getParameter()) {
            endBet();
        }
    }

    public void idel() {
        timerCenter.add(new TimerEvent<>(this, gameConfigInfo.idelTime, LHDStatus.READY).withTimeUnit(TimeUnit.SECONDS));
        currentStatus = LHDStatus.IDEL;
        round++;
        betAreas[0] = new BetArea(BetAreaType.LONG, gameConfigInfo.longOdds);
        betAreas[1] = new BetArea(BetAreaType.HE, gameConfigInfo.heOdds);
        betAreas[2] = new BetArea(BetAreaType.HU, gameConfigInfo.huOdds);
        broadcastStatus();
    }

    public void readyBet() {
        currentStatus = LHDStatus.READY;
        timerCenter.add(new TimerEvent<>(this, gameConfigInfo.idelTime, LHDStatus.BET).withTimeUnit(TimeUnit.SECONDS));
        broadcastStatus();
    }

    public void beginBet() {
        currentStatus = LHDStatus.BET;
        timerCenter.add(new TimerEvent<>(this, gameConfigInfo.betTime, LHDStatus.BILL).withTimeUnit(TimeUnit.SECONDS));
        betBeginTime = (int) (System.currentTimeMillis() / 1000);
        //broadcastBetData();
        broadcastStatus();
    }

    public void endBet() {
        currentStatus = LHDStatus.BILL;
        timerCenter.add(new TimerEvent<>(this, gameConfigInfo.idelTime, LHDStatus.IDEL).withTimeUnit(TimeUnit.SECONDS));
        List<Poker> cards = PokerHelper.randomCard(2, false);
        longCard = cards.get(0);
        huCard = cards.get(1);
        //胜利玩家获得的金币
        Map<Long, Long> calculateMap = new HashMap<>();
        //玩家押注金币信息
        Map<Long, Long> betMap = new HashMap<>();
        //总押注值
        int totalBet = 0;
        for (BetArea betArea : betAreas) {
            totalBet += betArea.totalBet.longValue();
            betArea.playersBet.entrySet().forEach(e -> {
                long playerId = e.getKey();
                long betMoney = e.getValue().get();
                long money = betMap.computeIfAbsent(playerId, k -> 0l);
                betMap.put(playerId, betMoney + money);
            });
        }
        int index;
        //龙赢
        if (longCard.value > huCard.value) {
            index = 0;
        } else if (longCard.value < huCard.value) {//虎赢
            index = 1;
        } else {//和
            index = 2;
        }
        BetArea betArea = betAreas[index];
        float odds = betArea.odds;
        betArea.playersBet.entrySet().forEach(e -> {
            long playerId = e.getKey();
            long betMoney = e.getValue().get();
            long getMoney = (long) (betMoney * odds);
            //给用户增加金币
            roleDao.addMoney(playerId, getMoney);
            calculateMap.put(playerId, getMoney);
        });

        long totalPay = calculateMap.values().stream().mapToLong(Long::longValue).sum();
        Map<Long, LHDBillStatusData> lhdGameBillMap = new HashMap<>();
        LHDBillStatusData oterGameBill = new LHDBillStatusData();
        oterGameBill.lhdStatus = currentStatus;
        oterGameBill.huCard = huCard;
        oterGameBill.longCard = longCard;
        oterGameBill.totalBet = totalBet;
        oterGameBill.totalWinMoney = totalBet - totalPay;
        for (Map.Entry<Long, Long> e : betMap.entrySet()) {
            long playerId = e.getKey();
            LHDBillStatusData lhdGameBill = new LHDBillStatusData();
            lhdGameBill.lhdStatus = currentStatus;
            lhdGameBill.huCard = huCard;
            lhdGameBill.longCard = longCard;
            lhdGameBill.totalBet = totalBet;
            lhdGameBill.totalWinMoney = totalBet - totalPay;
            lhdGameBill.playerBet = e.getValue();
            lhdGameBill.playerWinMoney = calculateMap.getOrDefault(playerId, 0L);
            lhdGameBillMap.put(playerId, lhdGameBill);
        }
        sendLHDGameBill(lhdGameBillMap, oterGameBill);

    }

    public void sendLHDGameBill(Map<Long, LHDBillStatusData> lhdGameBillMap, LHDBillStatusData oterGameBill) {
        roomController.roomPlayers.values().forEach(pc -> {
                    long playerId = pc.playerId;
                    LHDBillStatusData lb = lhdGameBillMap.get(playerId);
                    if (lb != null) {
                        lb.playerMoney = roleDao.findOne(playerId).money;
                        sendMessage(pc, lb);
                    } else {
                        sendMessage(pc, oterGameBill);
                    }
                }
        );
    }

    /**
     * 用户押注方法
     *
     * @param playerId 押注用户
     * @param betMoney 押注金额
     * @param areaType 押注区
     */
    public void bet(long playerId, int areaType, int betMoney) {
        //如果当前是押注时间
        if (currentStatus == LHDStatus.BET) {
            Role role = roleDao.findOne(playerId);
            long cm = role.money;
            //如果当前有足够的金币，并且扣除成功
            if (cm >= betMoney && roleDao.reduceMoney(playerId, betMoney) >= 0) {
                BetArea betArea = betAreas[areaType];
                betArea.bet(playerId, betMoney);
                broadcastBetData();
            }
        }
    }


    @Override
    public void onMessage(PlayerController playerController, Object msg) {
        if (msg instanceof BetMessage) {
            BetMessage betMessage = (BetMessage) msg;
            long playerId = playerController.playerId();
            bet(playerId, betMessage.betArea, betMessage.betMoney);
        } else if (msg instanceof StatusMessage) {
            LHDStatusData lhdStatusData = getLhdStatusData();
            sendMessage(playerController, lhdStatusData);
        }
    }

    @Override
    public GameInfo getGameInfo() {
        LHDGameInfo gameInfo = new LHDGameInfo();
        gameInfo.ownerId = roomController.owner;
        gameInfo.roomUid = roomController.uid;
        gameInfo.round = round;
        gameInfo.currentStatus = currentStatus;
        gameInfo.lhdConfigInfo = gameConfigInfo;
        return gameInfo;
    }

    public LHDStatusData getLhdStatusData() {
        LHDStatusData lhdStatusData = new LHDStatusData();
        lhdStatusData.lhdStatus = currentStatus;
        if (currentStatus == LHDStatus.BET) {
            lhdStatusData.countdown = betCountDown();
        }
        return lhdStatusData;
    }

    public void broadcastStatus() {
        LHDStatusData lhdStatusData = getLhdStatusData();
        broadcast(lhdStatusData);
    }

    public void broadcastBetData() {
        roomController.roomPlayers.values().forEach(pc -> {
            int countdown = betCountDown();
            sendBetData(pc, countdown);
        });
    }

    public int betCountDown() {
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        int use = currentTime - betBeginTime;
        return gameConfigInfo.betTime - use;
    }

    public void sendBetData(PlayerController playerController, int countdown) {
        long playerId = playerController.playerId;
        LHDBetStatusData lhdBetStatusData = new LHDBetStatusData();
        lhdBetStatusData.lhdStatus = currentStatus;
        lhdBetStatusData.countDown = countdown;
        List<BetAreaDto> betAreaDtos = new ArrayList<>();
        for (BetArea betArea : betAreas) {
            BetAreaDto betAreaDto = new BetAreaDto();
            betAreaDto.betAreaType = betArea.betAreaType;
            betAreaDto.totalBet = betArea.totalBet.get();
            betAreaDto.selfBet = betArea.playersBet.getOrDefault(playerId, new AtomicLong(0)).get();
            betAreaDtos.add(betAreaDto);
        }
        lhdBetStatusData.betAreaDtos = betAreaDtos;
        sendMessage(playerController, lhdBetStatusData);
    }
}
