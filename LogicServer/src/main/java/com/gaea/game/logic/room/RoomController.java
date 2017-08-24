package com.gaea.game.logic.room;

import com.gaea.game.base.data.PlayerController;
import com.gaea.game.logic.data.RoomType;

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
public abstract class RoomController {
    /* 房间房主ID*/
    protected long owner;
    /* 房间类型*/
    protected RoomType roomType;
    /* 房间的唯一ID*/
    protected long uid;
    protected Map<Long, PlayerController> roomPlayers = new ConcurrentHashMap<>();

    public RoomController(PlayerController ownerController, RoomType roomType, long uid) {
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
    }

    /**
     * 玩家退出房间
     *
     * @param playerController
     */
    public void exitRoom(PlayerController playerController) {
        roomPlayers.remove(playerController.playerId());
    }

}
