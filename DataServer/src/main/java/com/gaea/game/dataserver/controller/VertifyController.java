package com.gaea.game.dataserver.controller;

import com.alibaba.fastjson.JSON;
import com.gaea.game.core.constant.ResultEnum;
import com.gaea.game.core.data.*;
import com.gaea.game.dataserver.data.VertifyResult;
import com.gaea.game.dataserver.manager.DataManager;
import com.gaea.game.dataserver.manager.LogicServerManager;
import org.alan.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@RestController
@RequestMapping("/vertify")
public class VertifyController {
    @Autowired
    DataManager dataManager;
    @Autowired
    LogicServerManager logicServerManager;

    Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/credential")
    public VertifyResult vertify(VertifyInfo vertifyInfo,HttpServletResponse response) {
//        log.info(json);
//        VertifyInfo vertifyInfo = JSON.parseObject(json, VertifyInfo.class);
        log.info(JSON.toJSONString(vertifyInfo));
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        VertifyResult vertifyResult;
        Credential credential = dataManager.vertifyAccount(vertifyInfo);
        if (credential != null) {
            LogicServerInfo logicServerInfo = logicServerManager.load();
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
        //int id = idcreate.getAndDecrement();
        String id = RandomUtils.getRandomString(8);
        platformUserInfo.uid = "id" + id;
        platformUserInfo.userName = "user" + id;
        platformUserInfo.sex = 1;
        platformUserInfo.token = checkToken.token;

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", platformUserInfo);
        return result;
    }
}
