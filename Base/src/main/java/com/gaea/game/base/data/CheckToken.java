package com.gaea.game.base.data;

/**
 * Created on 2017/8/30.
 *
 * @author Alan
 * @since 1.0
 */
public class CheckToken {
    public String uuid;
    public String token;
    public String sign;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
