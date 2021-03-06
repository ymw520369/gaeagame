/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaea.game.core.constant;


import com.gaea.game.core.ws.WSMessage;

/**
 * 游戏通用返回值枚举定义
 * <p>
 * Created on 2017/3/27.
 *
 * @author Chow
 * @since 1.0
 */
@WSMessage
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    ILLEGAL(2, "非法操作"),
    ERROR(3, "未知错误"),
    ROLE_CREATED(4, "创建角色"),
    ROLE_REPEAT(5, "角色重复"),
    NOT_ENOUGH_GOLD(6, "金币不足"),
    NOT_ENOUGH_DIAMOND(7, "钻石不足"),
    FINISH_QUEST(8, "完成任务"),
    LENGTH_TOO_LONG(9, "字符串长度过长"),
    NEED_GET_AWARD(10, "奖励未领取"),
    SEASON_NOT_OPENED(11, "赛季未开启"),
    NOT_IN_STATUS_BET(12, "不在押注状态");


    public int code;
    public String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
