package com.gaea.game.dataserver.controller;

import com.gaea.game.base.constant.ResultEnum;
import com.gaea.game.base.data.*;
import com.gaea.game.base.manager.DataManager;
import com.gaea.game.dataserver.data.VertifyResult;
import com.gaea.game.dataserver.manager.NodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@RestController
@RequestMapping("/vertify")
public class VertifyController {
    AtomicInteger idcreate = new AtomicInteger();
    @Autowired
    DataManager dataManager;
    @Autowired
    NodeManager nodeManager;

    @RequestMapping("/credential")
    public VertifyResult vertify(HttpServletResponse response, VertifyInfo vertifyInfo) {
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        VertifyResult vertifyResult;
        Credential credential = dataManager.vertifyAccount(vertifyInfo);
        if (credential != null) {
            LogicServerInfo logicServerInfo = nodeManager.dynamicLoad();
            String logicUrl = logicServerInfo == null ? "" : logicServerInfo.serverAddress;
            vertifyResult = new VertifyResult(ResultEnum.SUCCESS, credential, logicUrl);
        } else {
            vertifyResult = new VertifyResult(ResultEnum.FAILURE, credential, null);
        }
        return vertifyResult;
    }

    @RequestMapping("/checkToken")
    public Map<String, Object> checkToken(CheckToken checkToken) {
        PlatformUserInfo platformUserInfo = new PlatformUserInfo();
        int id = idcreate.getAndDecrement();
        platformUserInfo.userId = "id" + id;
        platformUserInfo.userName = "user" + id;
        platformUserInfo.sex = false;
        platformUserInfo.token = checkToken.token;

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", platformUserInfo);
        return result;
    }
}
