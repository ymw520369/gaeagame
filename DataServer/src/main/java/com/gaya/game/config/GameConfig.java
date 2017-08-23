/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaya.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created on 2017/4/17.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
//@ConfigurationProperties(prefix = "game")
@PropertySource("file:config/logicConfig.json")
public class GameConfig {
}
