/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaea.game.base.data;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户信息
 * <p>
 * Created on 2017/4/13.
 *
 * @author Alan
 * @since 1.0
 */
@Document
public class UserInfo {
    public static final String USER_INFO = "USER_INFO:";
    /* 用户ID*/
    public long playerId;
    public Credential credential;
    public PlatformUserInfo platformUserInfo;
    /* 用户当前是否在线，避免重复登录*/
    public boolean online;
}
