package com.gaea.game.base.data;

/**
 * 平台用户信息
 * <p>
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
public class PlatformUserInfo {
    public String userId;
    public String userName;
    public boolean sex;
    public String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
