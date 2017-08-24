package com.gaea.game.logic.room;

import com.gaea.game.base.data.PlayerController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 房间管理器
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class RoomManager {
    private Map<Long, RoomController> rooms = new ConcurrentHashMap<>();

    public void createRoom(PlayerController playerController) {

    }
}
