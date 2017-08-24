package com.gaea.game.logic.game;

import java.util.ArrayList;
import java.util.Arrays;
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

    static List<PokerCard> randomCard(int count, boolean needJoker) {
        List<PokerCard> cardList = Arrays.asList(PokerCard.values());
        List<PokerCard> selectCards = new ArrayList<>();
        while (selectCards.size() < count) {
            int index = (int) (cardList.size() * Math.random());
            PokerCard pokerCard = cardList.get(index);
            if (!needJoker && pokerCard.color == JOKER) {
                cardList.remove(pokerCard);
            } else {
                selectCards.add(pokerCard);
            }
        }
        return selectCards;
    }

}
