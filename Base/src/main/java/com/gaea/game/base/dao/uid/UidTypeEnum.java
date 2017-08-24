/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年2月27日
 */
package com.gaea.game.base.dao.uid;

/**
 * @author Alan
 * @scene 1.0
 */
public enum UidTypeEnum {
    USER_ID(100, 10000), ROLE_UID(100, 10000), PROP_UID(100, 1000), CUP_UID(100, 1000),

    QUEST_UID(100, 1000),
    AWARD_UID(100, 1000),
    SEASON_ROLE(100, 1000),
    SEASON_UID(100, 1000);

    UidTypeEnum(int cacheNum, int beginNum) {
        this.cacheNum = cacheNum;
        this.beginNum = beginNum;
    }

    private int cacheNum;
    private int beginNum;

    public int getCacheNum() {
        return cacheNum;
    }

    public int getBeginNum() {
        return beginNum;
    }
}
