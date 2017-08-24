package com.gaea.game.logic.game;

import java.util.Arrays;
import java.util.List;

import static com.gaea.game.logic.game.PokerHelper.*;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public enum PokerCard {
    HONGTAO_A(1, HONGTAO, 1),
    HONGTAO_2(2, HONGTAO, 2),
    HONGTAO_3(3, HONGTAO, 3),
    HONGTAO_4(4, HONGTAO, 4),
    HONGTAO_5(5, HONGTAO, 5),
    HONGTAO_6(6, HONGTAO, 6),
    HONGTAO_7(7, HONGTAO, 7),
    HONGTAO_8(8, HONGTAO, 8),
    HONGTAO_9(9, HONGTAO, 9),
    HONGTAO_10(10, HONGTAO, 10),
    HONGTAO_J(11, HONGTAO, 11),
    HONGTAO_Q(12, HONGTAO, 12),
    HONGTAO_K(13, HONGTAO, 13),
    HEITAO_A(14, HEITAO, 1),
    HEITAO_2(15, HEITAO, 2),
    HEITAO_3(16, HEITAO, 3),
    HEITAO_4(17, HEITAO, 4),
    HEITAO_5(18, HEITAO, 5),
    HEITAO_6(19, HEITAO, 6),
    HEITAO_7(20, HEITAO, 7),
    HEITAO_8(21, HEITAO, 8),
    HEITAO_9(22, HEITAO, 9),
    HEITAO_10(23, HEITAO, 10),
    HEITAO_J(24, HEITAO, 11),
    HEITAO_Q(25, HEITAO, 12),
    HEITAO_K(26, HEITAO, 13),
    FANGPIAN_A(27, FANGPIAN, 1),
    FANGPIAN_2(28, FANGPIAN, 2),
    FANGPIAN_3(29, FANGPIAN, 3),
    FANGPIAN_4(30, FANGPIAN, 4),
    FANGPIAN_5(31, FANGPIAN, 5),
    FANGPIAN_6(32, FANGPIAN, 6),
    FANGPIAN_7(33, FANGPIAN, 7),
    FANGPIAN_8(34, FANGPIAN, 8),
    FANGPIAN_9(35, FANGPIAN, 9),
    FANGPIAN_10(36, FANGPIAN, 10),
    FANGPIAN_J(37, FANGPIAN, 11),
    FANGPIAN_Q(38, FANGPIAN, 12),
    FANGPIAN_K(39, FANGPIAN, 13),
    MEIHUA_A(40, MEIHUA, 1),
    MEIHUA_2(41, MEIHUA, 2),
    MEIHUA_3(42, MEIHUA, 3),
    MEIHUA_4(43, MEIHUA, 4),
    MEIHUA_5(44, MEIHUA, 5),
    MEIHUA_6(45, MEIHUA, 6),
    MEIHUA_7(46, MEIHUA, 7),
    MEIHUA_8(47, MEIHUA, 8),
    MEIHUA_9(48, MEIHUA, 9),
    MEIHUA_10(49, MEIHUA, 10),
    MEIHUA_J(50, MEIHUA, 11),
    MEIHUA_Q(51, MEIHUA, 12),
    MEIHUA_K(52, MEIHUA, 13),
    BLACK_JOKER(53, JOKER, 53),
    RED_JOKER(54, JOKER, 54);

    /* 扑克花色*/
    public byte id;
    /* 扑克花色*/
    public byte color;
    /* 扑克点数*/
    public byte number;

    PokerCard(int id, int color, int number) {
        this.id = (byte) id;
        this.color = (byte) color;
        this.number = (byte) number;
    }

    public static final List<PokerCard> cardWithoutJoker;

    static {
        cardWithoutJoker = Arrays.asList(values());
        cardWithoutJoker.remove(BLACK_JOKER);
        cardWithoutJoker.remove(RED_JOKER);
    }
}
