package com.gaea.game.core.dubbo;

import com.gaea.game.core.data.GeneralResult;
import com.gaea.game.core.data.LogicRoomInfo;
import com.gaea.game.core.data.LogicServerInfo;

/**
 * Created on 2017/9/5.
 *
 * @author Alan
 * @since 1.0
 */
public interface ILogicService {
    GeneralResult register(LogicServerInfo logicServerInfo);

    GeneralResult unregister(int serverId);

    GeneralResult addRoom(LogicRoomInfo logicRoomInfo);

    GeneralResult removeRoom(long roomId);

    GeneralResult playerEnter(long playerId, int serverId);

    GeneralResult playerExit(long playerId, int serverId);
}
