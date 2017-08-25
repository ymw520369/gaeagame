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
    public Player player;
    public RoomController roomController;

    public PlayerController(Player player) {
        this.player = player;
    }

    public long playerId() {
        return player.role.roleUid;
    }

    public String playerName() {
        return player.role.name;
    }

    public int sceneId() {
        return player.sceneId;
    }

    public void setSceneId(int sceneId) {
        player.sceneId = sceneId;
    }

    public void sendToClient(Object msg) {
    }
}
