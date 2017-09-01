package com.gaea.game.logic.data;


import com.gaea.game.base.data.Player;
import com.gaea.game.base.ws.GameSession;
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
    public long playerId;
    public String playerName;

    public PlayerController(Player player) {
        this.playerId = player.role.roleUid;
        this.playerName = player.role.name;
    }

    public long playerId() {
        return playerId;
    }

    public String playerName() {
        return playerName;
    }

    public void sendToClient(Object msg) {
        session.send(msg);
    }
}
