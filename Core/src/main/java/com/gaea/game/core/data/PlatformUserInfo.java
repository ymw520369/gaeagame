package com.gaea.game.core.data;

/**
 * 平台用户信息
 * <p>
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
public class PlatformUserInfo {
    public String uid;
    public String userName;
    public boolean sex;
    public int diamone;
    public String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getDiamone() {
        return diamone;
    }

    public void setDiamone(int diamone) {
        this.diamone = diamone;
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
