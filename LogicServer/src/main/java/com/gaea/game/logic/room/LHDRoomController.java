package com.gaea.game.logic.room;

import com.gaea.game.base.data.PlayerController;
import com.gaea.game.logic.data.RoomType;

/**
 * 龙虎斗房间管理器
 * <p>
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class LHDRoomController extends RoomController {
    public LHDRoomController(PlayerController ownerController, RoomType roomType, long uid) {
        super(ownerController, roomType, uid);
    }
}
