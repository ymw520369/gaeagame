package com.gaea.game.dataserver.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gaea.game.core.log.GeneralLogger;
import com.gaea.game.core.log.ILogService;
import com.gaea.game.dataserver.config.DSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/9/21.
 *
 * @author Alan
 * @since 1.0
 */
@Component("logger")
public class DSLogger extends GeneralLogger implements CommandLineRunner {

    @Reference(version = "1.0.0")
    ILogService logService;
    @Autowired
    DSConfig dsConfig;

    @Override
    public void run(String... strings) throws Exception {
        setLogService(logService);
        setServerConfig(dsConfig);
    }
}
