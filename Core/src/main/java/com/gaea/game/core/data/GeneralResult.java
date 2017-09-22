/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年2月22日
 */
package com.gaea.game.core.data;

import java.io.Serializable;

/**
 *
 * 本服务器设计用于通用消息返回格式
 *
 *
 * @scene 1.0
 *
 * @author Alan
 *
 */
public class GeneralResult implements Serializable {
    public static final GeneralResult SUCCESS = new GeneralResult(1, "ok");
    public static final GeneralResult USER_NOT_FOUND = new GeneralResult(2, "找不到用户");
    public static final GeneralResult SIGN_ERROR = new GeneralResult(3, "签名验证失败");
    public static final GeneralResult FAIL = new GeneralResult(0, "服务器错误");
    /**
     * 服务器响应码
     */
    private int code;
    /**
     * 相应数据
     */
    private Object data;
    /**
     * 相应描述信息
     */
    private String dec;

    public GeneralResult(int code) {
        super();
        this.code = code;
    }

    public GeneralResult(int code, String dec) {
        super();
        this.code = code;
        this.dec = dec;
    }

    public GeneralResult(int code, Object data, String dec) {
        super();
        this.code = code;
        this.data = data;
        this.dec = dec;
    }

    public int getCode() {
        return code;
    }

    public GeneralResult setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public GeneralResult setData(Object data) {
        this.data = data;
        return this;
    }

    public String getDec() {
        return dec;
    }

    public GeneralResult setDec(String dec) {
        this.dec = dec;
        return this;
    }

}
