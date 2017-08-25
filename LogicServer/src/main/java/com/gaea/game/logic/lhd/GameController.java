package com.gaea.game.logic.lhd;

import com.gaea.game.base.timer.TimerCenter;
import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.room.RoomController;

/**
 * Created on 2017/8/25.
 *
 * @author Alan
 * @since 1.0
 */
public abstract class GameController {
    /* 该游戏控制器所属的房间*/
    protected RoomController roomController;
    protected TimerCenter timerCenter;

    public abstract void start();

    /**
     * 当消息到达
     *
     * @param playerController
     * @param msg
     */
    public abstract void onMessage(PlayerController playerController, Object msg);

    /**
     * 获取游戏信息
     *
     * @return
     */
    public abstract GameInfo getGameInfo();

    /**
     * 广播消息
     *
     * @param msg
     */
    public void broadcast(Object msg) {
        roomController.broadcast(msg);
    }

    /**
     * 向指定玩家发送消息
     *
     * @param playerController
     * @param msg
     */
    public void sendMessage(PlayerController playerController, Object msg) {
        roomController.sendMessage(playerController, msg);
    }

    /**
     * 向指定玩家发送消息
     *
     * @param playerId
     * @param msg
     */
    public void sendMessage(long playerId, Object msg) {
        roomController.sendMessage(playerId, msg);
    }

    public RoomController getRoomController() {
        return roomController;
    }

    public void setRoomController(RoomController roomController) {
        this.roomController = roomController;
    }

    public TimerCenter getTimerCenter() {
        return timerCenter;
    }

    public void setTimerCenter(TimerCenter timerCenter) {
        this.timerCenter = timerCenter;
    }
}
