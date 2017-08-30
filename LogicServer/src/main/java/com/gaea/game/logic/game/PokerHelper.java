package com.gaea.game.logic.game;

import com.gaea.game.logic.sample.poker.Poker;

import java.util.ArrayList;
import java.util.List;

/**
 * 扑克帮助者
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public interface PokerHelper {
    int HONGTAO = 1, HEITAO = 2, MEIHUA = 3, FANGPIAN = 4, JOKER = 5;

    static List<Poker> randomCard(int count, boolean needJoker) {
        List<Poker> cardList = new ArrayList<>(Poker.factory.getAllSamples());
        List<Poker> selectCards = new ArrayList<>();
        while (selectCards.size() < count) {
            int index = (int) (cardList.size() * Math.random());
            Poker Poker = cardList.get(index);
            if (!needJoker && Poker.type == JOKER) {
                cardList.remove(Poker);
            } else {
                selectCards.add(Poker);
            }
        }
        return selectCards;
    }

}
