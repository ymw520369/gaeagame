package com.gaea.game.logic.manager;

import com.alibaba.fastjson.JSON;
import com.gaea.game.base.data.LogicServerInfo;
import com.gaea.game.base.timer.TimerCenter;
import com.gaea.game.base.timer.TimerEvent;
import com.gaea.game.base.timer.TimerListener;
import com.gaea.game.logic.config.LogicConfig;
import org.alan.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class ServerRegister implements CommandLineRunner, TimerListener {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    TimerCenter timerCenter;
    @Autowired
    LogicConfig logicConfig;
    @Autowired
    LogicManager logicManager;

    @Override
    public void run(String... strings) throws Exception {
        timerCenter.add(new TimerEvent<>(this, "Register", 60).withTimeUnit(TimeUnit.SECONDS));
    }

    @Override
    public void onTimer(TimerEvent e) {
        LogicServerInfo logicServerInfo = new LogicServerInfo();
        Map<String, String> params = new HashMap<>();
        params.put("serverId", "" + logicConfig.serverId);
        params.put("serverName", logicConfig.serverName);
        params.put("serverAddress", logicConfig.wsAddress);
        params.put("currentNum", "" + logicManager.getPlayerNum());
        logicServerInfo.serverId = logicConfig.serverId;
        logicServerInfo.serverName = logicConfig.serverName;
        logicServerInfo.serverAddress = logicConfig.wsAddress;
        logicServerInfo.currentNum = logicManager.getPlayerNum();

        String jsonBody = JSON.toJSONString(logicServerInfo);
        log.info("向中心服务器发起注册，{}", jsonBody);
        try {
            HttpUtils.HttpResponse httpResponse = HttpUtils.doPost(logicConfig.centerRegisterUrl, params, null, null);
            log.info("注册结果信息，{}", httpResponse.toString());
        } catch (Exception e1) {
            log.warn("向中心服务器发起注册失败.", e1);
        }
    }
}
