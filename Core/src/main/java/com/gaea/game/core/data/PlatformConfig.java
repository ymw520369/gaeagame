package com.gaea.game.core.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "platform")
public class PlatformConfig {
    public String secretKey;
    public String vertifyUrl;
    public String consumeUrl;
    public String createRoomUpUrl;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getVertifyUrl() {
        return vertifyUrl;
    }

    public void setVertifyUrl(String vertifyUrl) {
        this.vertifyUrl = vertifyUrl;
    }

    public String getConsumeUrl() {
        return consumeUrl;
    }

    public void setConsumeUrl(String consumeUrl) {
        this.consumeUrl = consumeUrl;
    }

    public String getCreateRoomUpUrl() {
        return createRoomUpUrl;
    }

    public void setCreateRoomUpUrl(String createRoomUpUrl) {
        this.createRoomUpUrl = createRoomUpUrl;
    }
}
