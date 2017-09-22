/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaea.game.dataserver.config;

import com.gaea.game.core.config.ServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2017/4/17.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "dsconfig")
public class DSConfig extends ServerConfig {
    public String host;
    public boolean debug;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
