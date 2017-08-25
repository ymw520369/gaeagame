package com.gaea.game.logic.room;

import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.data.GameType;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.lhd.GameController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 房间控制器抽象类，不同的游戏房间，应该有不同的实现
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class RoomController {
    /* 房间房主ID*/
    public long owner;
    /* 房间类型*/
    public GameType roomType;
    /* 房间的唯一ID*/
    public long uid;
    public Map<Long, PlayerController> roomPlayers = new ConcurrentHashMap<>();

    GameController gameController;

    public RoomController(PlayerController ownerController, GameType roomType, long uid) {
        this.owner = ownerController.playerId();
        this.roomType = roomType;
        this.uid = uid;
        roomPlayers.put(owner, ownerController);
    }

    /**
     * 玩家加入房间
     *
     * @param playerController
     */
    public void joinRoom(PlayerController playerController) {
        roomPlayers.put(playerController.playerId(), playerController);
        GameInfo gameInfo = gameController.getGameInfo();
        sendMessage(playerController, gameInfo);
    }

    /**
     * 玩家退出房间
     *
     * @param playerController
     */
    public void exitRoom(PlayerController playerController) {
        roomPlayers.remove(playerController.playerId());
        playerController.roomController = null;
    }

    /**
     * 广播消息
     *
     * @param msg
     */
    public void broadcast(Object msg) {
        roomPlayers.values().forEach(pc -> sendMessage(pc, msg));
    }

    /**
     * 向指定玩家发送消息
     *
     * @param playerController
     * @param msg
     */
    public void sendMessage(PlayerController playerController, Object msg) {
        playerController.session.send(msg);
    }

    /**
     * 向指定玩家发送消息
     *
     * @param playerId
     * @param msg
     */
    public void sendMessage(long playerId, Object msg) {
        PlayerController playerController = roomPlayers.get(playerId);
        if (playerController != null) {
            sendMessage(playerController, msg);
        }
    }
}
