package com.gaea.game.logic.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gaea.game.core.log.GeneralLogger;
import com.gaea.game.core.log.ILogService;
import com.gaea.game.logic.config.LogicConfig;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.lhd.BetMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/9/21.
 *
 * @author Alan
 * @since 1.0
 */
@Component("logger")
public class LogicLogger extends GeneralLogger implements CommandLineRunner{

    @Reference(version = "1.0.0")
    ILogService logService;
    @Autowired
    LogicConfig logicConfig;

    public void logBet(PlayerController playerController, BetMessage betMessage) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "bet");
        map.put("betArea", betMessage.betArea);
        map.put("betMoney", betMessage.betMoney);
        addPlayerDefault(playerController.player.role, map);
        logService.log(map);
    }

    @Override
    public void run(String... strings) throws Exception {
        setLogService(logService);
        setServerConfig(logicConfig);
    }
}
