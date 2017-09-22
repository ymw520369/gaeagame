package com.gaea.game.core.data;

import com.gaea.game.core.ws.WSMessage;

/**
 * 用户令牌
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class Credential {
    /* 用户认证通过的令牌*/
    public String certifyToken;
    /* 玩家ID*/
    public long playerId;
    /* 令牌签名*/
    public String sign;
    /* 认证时间*/
    public int certifyTime;

    public Credential() {

    }

    public Credential(String certifyToken, long playerId) {
        this.certifyToken = certifyToken;
        this.playerId = playerId;
    }

    public String getCertifyToken() {
        return certifyToken;
    }

    public void setCertifyToken(String certifyToken) {
        this.certifyToken = certifyToken;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getCertifyTime() {
        return certifyTime;
    }

    public void setCertifyTime(int certifyTime) {
        this.certifyTime = certifyTime;
    }
}
