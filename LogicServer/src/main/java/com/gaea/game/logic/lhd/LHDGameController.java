package com.gaea.game.logic.lhd;

import com.gaea.game.logic.game.PokerCard;
import com.gaea.game.logic.game.PokerHelper;

import java.util.List;

/**
 * 龙虎斗游戏管理器
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class LHDGameController {
    /* 当前是第几局*/
    protected int round = 0;
    /* 龙牌*/
    protected PokerCard longCard;
    /* 虎牌*/
    protected PokerCard huCard;

    /* 押注区*/
    protected BetArea betLong = new BetArea(LHDConfig.longDdds);
    protected BetArea betHu = new BetArea(LHDConfig.huDdds);
    protected BetArea betHe = new BetArea(LHDConfig.heDdds);


    public void beginBet() {
        round++;
        betLong.clear();
        betHe.clear();
        betHu.clear();
    }

    public void endBet() {
        List<PokerCard> cards = PokerHelper.randomCard(2, false);
        longCard = cards.get(0);
        huCard = cards.get(1);

        //龙赢
        if (longCard.number > huCard.number) {
            //betLong.playersBet
        } else if (longCard.number < huCard.number) {//虎赢

        } else {//和

        }

    }

    public void roundEnd() {

    }

}
