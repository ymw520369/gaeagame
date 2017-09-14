package com.gaea.game.dataserver.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.gaea.game.core.data.GeneralResult;
import com.gaea.game.core.data.LogicRoomInfo;
import com.gaea.game.core.data.LogicServerInfo;
import com.gaea.game.core.dubbo.ILogicService;
import com.gaea.game.dataserver.manager.LogicRoomManager;
import com.gaea.game.dataserver.manager.LogicServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 2017/9/5.
 *
 * @author Alan
 * @since 1.0
 */
@Service(version = "1.0.0")
public class LogicService implements ILogicService {
    @Autowired
    LogicServerManager logicServerManager;
    @Autowired
    LogicRoomManager logicRoomManager;

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public GeneralResult register(LogicServerInfo logicServerInfo) {
        log.info("收到逻辑节点注册，serverInfo=" + JSON.toJSONString(logicServerInfo));
        logicServerManager.register(logicServerInfo);
        return GeneralResult.SUCCESS;
    }

    @Override
    public GeneralResult unregister(int serverId) {
        log.info("移除逻辑节点，serverId=" + serverId);
        logicServerManager.unregister(serverId);
        return GeneralResult.SUCCESS;
    }

    @Override
    public GeneralResult addRoom(LogicRoomInfo logicRoomInfo) {
        log.info("收到逻辑服务器添加房间消息，logicRoomInfo=" + JSON.toJSONString(logicRoomInfo));
        logicRoomManager.addRoom(logicRoomInfo);
        return GeneralResult.SUCCESS;
    }

    @Override
    public GeneralResult removeRoom(long roomId) {
        log.info("移除房间，roomId=" + roomId);
        logicRoomManager.removeRoom(roomId);
        return GeneralResult.SUCCESS;
    }

    @Override
    public GeneralResult playerEnter(long playerId, int serverId) {
        return null;
    }

    @Override
    public GeneralResult playerExit(long playerId, int serverId) {
        return null;
    }
}
