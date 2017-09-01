package com.gaea.game.base.data;

import com.gaea.game.base.ws.WSMessage;

/**
 * 逻辑服务器信息
 * <p>
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class LogicServerInfo {
    public int serverId;
    public String serverName;
    public String serverAddress;
    public int currentNum;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }
}
