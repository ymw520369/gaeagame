package com.gaea.game.logic.manager;

import com.alibaba.fastjson.JSON;
import com.gaea.game.logic.config.GameConfig;
import org.alan.utils.FileHelper;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/9/8.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class ConfigManager implements CommandLineRunner {

    public String gameConfigFile = "config/gameConfig.json";

    private FileAlterationMonitor monitor;

    @Override
    public void run(String... strings) throws Exception {
        String context = FileHelper.readFile(gameConfigFile);
        GameConfig gameConfig = JSON.parseObject(context, GameConfig.class);
    }
}
