package com.gaea.game.dataserver.controller;

import com.alibaba.fastjson.JSON;
import com.gaea.game.base.data.GeneralResult;
import com.gaea.game.base.data.LogicServerInfo;
import com.gaea.game.dataserver.manager.NodeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 节点注册
 * <p>
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@RestController
@RequestMapping("node")
public class NodeRegisterController {
    @Autowired
    NodeManager nodeManager;

    Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "register")
    public GeneralResult register(LogicServerInfo logicServerInfo) {
        log.info("收到逻辑节点注册，serverInfo=" + JSON.toJSONString(logicServerInfo));
        nodeManager.register(logicServerInfo);
        return GeneralResult.SUCCESS;
    }
}
