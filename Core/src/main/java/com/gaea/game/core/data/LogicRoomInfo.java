package com.gaea.game.core.data;

import java.io.Serializable;

/**
 * 游戏房间数据结构
 * <p>
 * Created on 2017/9/2.
 *
 * @author Alan
 * @since 1.0
 */
public class LogicRoomInfo implements Serializable {
    //房间ID
    public long roomId;
    //房主ID
    public long ownerId;
    //websocket地址
    public String wsAddress;
    //房间当前人数
    public int currentNum;
    //房间游戏Id;
    public int gameSid;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getWsAddress() {
        return wsAddress;
    }

    public void setWsAddress(String wsAddress) {
        this.wsAddress = wsAddress;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public int getGameSid() {
        return gameSid;
    }

    public void setGameSid(int gameSid) {
        this.gameSid = gameSid;
    }
}
