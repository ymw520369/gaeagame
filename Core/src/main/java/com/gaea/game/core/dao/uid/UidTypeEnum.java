/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年2月27日
 */
package com.gaea.game.core.dao.uid;

/**
 * @author Alan
 * @scene 1.0
 */
public enum UidTypeEnum {
    USER_ID(100L, 10000L), ROLE_UID(100L, 10000L),ROOM_UID(1L, 1L);

    UidTypeEnum(long cacheNum, long beginNum) {
        this.cacheNum = cacheNum;
        this.beginNum = beginNum;
    }

    private long cacheNum;
    private long beginNum;

    public long getCacheNum() {
        return cacheNum;
    }

    public long getBeginNum() {
        return beginNum;
    }
}
