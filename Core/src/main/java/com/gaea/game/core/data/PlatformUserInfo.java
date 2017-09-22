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
    public byte sex;
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

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
