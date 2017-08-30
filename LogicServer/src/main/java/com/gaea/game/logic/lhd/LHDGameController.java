package com.gaea.game.logic.lhd;

import com.gaea.game.base.data.Role;
import com.gaea.game.base.timer.TimerEvent;
import com.gaea.game.base.timer.TimerListener;
import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.game.PokerHelper;
import com.gaea.game.logic.sample.poker.Poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 龙虎斗游戏管理器
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class LHDGameController extends GameController implements TimerListener {
    public static final Integer LHD_STATUS_BET = 1, LHD_STATUS_END = 2;
    /* 当前是第几局*/
    protected int round = 0;
    /* 当前状态*/
    protected int currentStatus;
    /* 龙牌*/
    protected Poker longCard;
    /* 虎牌*/
    protected Poker huCard;

    /* 押注区*/
    protected BetArea[] betAreas = {new BetArea(LHDConfig.longDdds), new BetArea(LHDConfig.heDdds), new BetArea(LHDConfig.huDdds)};

    @Override
    public void start() {
        timerCenter.add(new TimerEvent<>(this, LHDConfig.intervalTime, LHD_STATUS_BET));
    }

    @Override
    public void onTimer(TimerEvent e) {
        if (LHD_STATUS_BET == e.getParameter()) {
            beginBet();
        } else if (LHD_STATUS_END == e.getParameter()) {
            endBet();
        }
    }

    public void beginBet() {
        round++;
        for (BetArea betArea : betAreas) {
            betArea.clear();
        }
        currentStatus = LHD_STATUS_BET;
        timerCenter.add(new TimerEvent<>(this, LHDConfig.betTime, LHD_STATUS_END));
    }

    public void endBet() {
        List<Poker> cards = PokerHelper.randomCard(2, false);
        longCard = cards.get(0);
        huCard = cards.get(1);

        //龙赢
        if (longCard.value > huCard.value) {
            //betLong.playersBet
        } else if (longCard.value < huCard.value) {//虎赢

        } else {//和

        }
        currentStatus = LHD_STATUS_END;
        timerCenter.add(new TimerEvent<>(this, LHDConfig.intervalTime, LHD_STATUS_BET));
    }

    /**
     * 用户押注方法
     *
     * @param playerController 押注用户
     * @param betMoney         押注金额
     * @param areaType         押注区
     */
    public void bet(PlayerController playerController, int areaType, int betMoney) {
        //如果当前是押注时间
        if (currentStatus == LHD_STATUS_BET) {
            Role role = playerController.player.role;
            long cm = role.money.get();
            //如果当前有足够的金币，并且扣除成功
            if (cm > betMoney && role.money.compareAndSet(cm, cm - betMoney)) {
                BetArea betArea = betAreas[areaType];
                betArea.bet(playerController.playerId(), betMoney);
            }
        }
    }


    @Override
    public void onMessage(PlayerController playerController, Object msg) {
        if (msg instanceof BetMessage) {
            BetMessage betMessage = (BetMessage) msg;
            bet(playerController, betMessage.betArea, betMessage.betMoney);
        }
    }

    @Override
    public GameInfo getGameInfo() {
        LHDGameInfo gameInfo = new LHDGameInfo();
        gameInfo.ownerId = roomController.owner;
        gameInfo.roomUid = roomController.uid;
        gameInfo.round = round;
        gameInfo.currentStatus = currentStatus;
        gameInfo.longCard = longCard;
        gameInfo.huCard = huCard;
        gameInfo.betAreas = new ArrayList<>(Arrays.asList(betAreas));
        return gameInfo;
    }
}
