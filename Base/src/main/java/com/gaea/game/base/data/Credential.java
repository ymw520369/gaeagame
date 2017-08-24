package com.gaea.game.base.data;

/**
 * 用户令牌
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class Credential {
    /* 用户认证通过的令牌*/
    public String certifyToken;
    /* 玩家ID*/
    public long playerId;
    /* 令牌签名*/
    public String sign;

    public Credential(String certifyToken, long playerId) {
        this.certifyToken = certifyToken;
        this.playerId = playerId;
    }
}
