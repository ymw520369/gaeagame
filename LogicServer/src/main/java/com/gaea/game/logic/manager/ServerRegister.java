package com.gaea.game.logic.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gaea.game.core.data.GeneralResult;
import com.gaea.game.core.data.LogicServerInfo;
import com.gaea.game.core.dubbo.ILogicService;
import com.gaea.game.core.server.ServerListener;
import com.gaea.game.core.timer.TimerCenter;
import com.gaea.game.core.timer.TimerEvent;
import com.gaea.game.core.timer.TimerListener;
import com.gaea.game.logic.config.LogicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class ServerRegister implements CommandLineRunner, TimerListener, ServerListener {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    TimerCenter timerCenter;
    @Autowired
    LogicConfig logicConfig;
    @Autowired
    LogicServer logicManager;
    @Reference(version = "1.0.0")
    ILogicService registerService;

    @Override
    public void run(String... strings) throws Exception {
        timerCenter.add(new TimerEvent<>(this, "Register", 180).withTimeUnit(TimeUnit.SECONDS));
    }

    @Override
    public void onTimer(TimerEvent e) {
        LogicServerInfo logicServerInfo = new LogicServerInfo();
        logicServerInfo.serverId = logicConfig.serverId;
        logicServerInfo.serverName = logicConfig.serverName;
        logicServerInfo.serverAddress = logicConfig.wsAddress;
        logicServerInfo.currentNum = logicManager.getPlayerNum();

        String jsonBody = JSON.toJSONString(logicServerInfo);
        log.info("向中心服务器发起注册，{}", jsonBody);
        try {
            //HttpUtils.HttpResponse httpResponse = HttpUtils.doPost(logicConfig.centerRegisterUrl, params, null, null);
            GeneralResult generalResult = registerService.register(logicServerInfo);
            log.info("注册结果信息，{}", generalResult.getCode());
        } catch (Exception e1) {
            log.warn("向中心服务器发起注册失败.", e1);
        }
    }

    @Override
    public void onServerClose() {
        registerService.unregister(logicConfig.serverId);
    }
}
