package com.gaea.game.dataserver.manager;

import com.alibaba.fastjson.JSON;
import com.gaea.game.base.data.PlatformConfig;
import org.alan.utils.FileHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/27
 */
@Component
public class DataConfigManager {
    public String platformConfigFile = "config/platformConfig.json";

    @Bean
    public PlatformConfig logicConfig() {
        String context = FileHelper.readFile(platformConfigFile);
        return JSON.parseObject(context, PlatformConfig.class);
    }
}
