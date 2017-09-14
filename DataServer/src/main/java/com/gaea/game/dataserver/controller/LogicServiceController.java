package com.gaea.game.dataserver.controller;

import com.alibaba.fastjson.JSON;
import com.gaea.game.core.data.GeneralResult;
import com.gaea.game.core.data.LogicRoomInfo;
import com.gaea.game.core.data.LogicServerInfo;
import com.gaea.game.dataserver.manager.LogicRoomManager;
import com.gaea.game.dataserver.manager.LogicServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 逻辑服务器业务处理类
 * <p>
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@RestController
@RequestMapping("logic")
public class LogicServiceController {
    @Autowired
    LogicServerManager logicServerManager;
    @Autowired
    LogicRoomManager logicRoomManager;

    Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "register")
    public GeneralResult register(LogicServerInfo logicServerInfo) {
        log.info("收到逻辑节点注册，serverInfo=" + JSON.toJSONString(logicServerInfo));
        logicServerManager.register(logicServerInfo);
        return GeneralResult.SUCCESS;
    }

    @RequestMapping(value = "unregister")
    public GeneralResult unregister(LogicServerInfo logicServerInfo) {
        log.info("移除逻辑节点，serverInfo=" + JSON.toJSONString(logicServerInfo));
        logicServerManager.unregister(logicServerInfo.serverId);
        return GeneralResult.SUCCESS;
    }

    @RequestMapping(value = "addRoom")
    public GeneralResult addRoom(LogicRoomInfo logicRoomInfo) {
        log.info("收到逻辑服务器添加房间消息，logicRoomInfo=" + JSON.toJSONString(logicRoomInfo));
        logicRoomManager.addRoom(logicRoomInfo);
        return GeneralResult.SUCCESS;
    }

    @RequestMapping(value = "removeRoom")
    public GeneralResult removeRoom(LogicRoomInfo logicRoomInfo) {
        log.info("移除房间，logicRoomInfo=" + JSON.toJSONString(logicRoomInfo));
        logicRoomManager.removeRoom(logicRoomInfo.roomId);
        return GeneralResult.SUCCESS;
    }
}
