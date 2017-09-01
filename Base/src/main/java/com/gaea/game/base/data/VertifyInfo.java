package com.gaea.game.base.data;


import com.gaea.game.base.ws.WSMessage;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class VertifyInfo {
    public String token;
    public String uuid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
