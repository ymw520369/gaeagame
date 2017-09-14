/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaea.game.core.data;

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
    /* 登录证书*/
    public Credential credential;
    /* 平台用户信息*/
    public PlatformUserInfo platformUserInfo;
    /* 当前用户所在服务器*/
    public int serverId;
    /* 用户当前是否在线，避免重复登录*/
    public boolean online;
}
