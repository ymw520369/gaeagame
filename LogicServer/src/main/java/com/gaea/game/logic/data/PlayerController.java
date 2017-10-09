package com.gaea.game.logic.data;


import com.gaea.game.core.data.Player;
import com.gaea.game.core.data.UserInfo;
import com.gaea.game.core.ws.GameSession;
import com.gaea.game.logic.room.RoomController;

/**
 * Created on 2017/8/10.
 *
 * @author Alan
 * @since 1.0
 */
public class PlayerController {
    public GameSession session;
    public RoomController roomController;
    public UserInfo userInfo;
    public long playerId;
    public String playerName;
    public Player player;

    public PlayerController(Player player) {
        this.playerId = player.role.roleUid;
        this.playerName = player.role.name;
        this.player = player;
    }

    public long playerId() {
        return playerId;
    }

    public String playerName() {
        return playerName;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void sendToClient(Object msg) {
        session.send(msg);
    }
}
