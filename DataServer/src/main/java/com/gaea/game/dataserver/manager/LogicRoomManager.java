package com.gaea.game.dataserver.manager;

import com.gaea.game.core.data.LogicRoomInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/9/2.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class LogicRoomManager {
    Map<Long, LogicRoomInfo> logicRoomInfoMap = new HashMap<>();

    public void addRoom(LogicRoomInfo logicRoomInfo) {
        logicRoomInfoMap.put(logicRoomInfo.roomId, logicRoomInfo);
    }

    public void removeRoom(long roomId) {
        logicRoomInfoMap.remove(roomId);
    }
}
