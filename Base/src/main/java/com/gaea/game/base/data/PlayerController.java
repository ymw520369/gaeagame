package com.gaea.game.base.data;


import com.gaea.game.base.ws.GameSession;

/**
 * Created on 2017/8/10.
 *
 * @author Alan
 * @since 1.0
 */
public class PlayerController {
    public GameSession session;
    public Player player;

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
