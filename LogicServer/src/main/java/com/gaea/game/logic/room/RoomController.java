package com.gaea.game.logic.room;

import com.gaea.game.core.data.LogicRoomInfo;
import com.gaea.game.logic.constant.GameResultEnum;
import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.game.GameController;
import com.gaea.game.logic.sample.game.GameConfigInfo;

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
    public long owner = -1;
    /* 房间类型*/
    public GameConfigInfo gameStaticInfo;
    /* 房间的唯一ID*/
    public long uid;
    public Map<Long, PlayerController> roomPlayers = new ConcurrentHashMap<>();

    public GameController gameController;

    public RoomController(PlayerController ownerController, GameConfigInfo gameStaticInfo, long uid) {
        if (ownerController != null) {
            this.owner = ownerController.playerId();
            roomPlayers.put(owner, ownerController);
        }
        this.gameStaticInfo = gameStaticInfo;
        this.uid = uid;
    }

    /**
     * 玩家加入房间
     *
     * @param playerController
     */
    public GameResultEnum joinRoom(PlayerController playerController) {
        roomPlayers.put(playerController.playerId(), playerController);
        playerController.roomController = this;
        GameInfo gameInfo = gameController.getGameInfo();
        sendMessage(playerController, gameInfo);
        return GameResultEnum.SUCCESS;
    }

    /**
     * 玩家退出房间
     *
     * @param playerController
     */
    public GameResultEnum exitRoom(PlayerController playerController) {
        roomPlayers.remove(playerController.playerId());
        playerController.roomController = null;
        return GameResultEnum.SUCCESS;
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

    public LogicRoomInfo getLogicRoomInfo() {
        LogicRoomInfo logicRoomInfo = new LogicRoomInfo();
        logicRoomInfo.roomId = uid;
        logicRoomInfo.currentNum = roomPlayers.size();
        logicRoomInfo.gameSid = gameController.getGameConfigInfo().sid;
        logicRoomInfo.ownerId=owner;
        return logicRoomInfo;
    }
}
