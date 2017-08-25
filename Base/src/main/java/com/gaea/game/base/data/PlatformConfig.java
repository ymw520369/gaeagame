package com.gaea.game.base.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
//@ConfigurationProperties(prefix = "game")
@PropertySource("file:config/platformConfig.json")
public class PlatformConfig {
    public String secretKey;
    public String vertifyUrl;
    public String consumeUrl;
}
