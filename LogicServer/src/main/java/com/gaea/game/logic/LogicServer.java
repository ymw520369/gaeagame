package com.gaea.game.logic;

import com.gaea.game.base.timer.TimerCenter;
import com.gaea.game.base.ws.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("com.gaea")
public class LogicServer {
    public static void main(String[] args) {
        SpringApplication.run(LogicServer.class, args);
    }

    @Bean
    public WebSocketConfig webSocketConfig() {
        return new WebSocketConfig();
    }
    @Bean
    public TimerCenter timerCenter() {
        return new TimerCenter("timer-center");
    }
}
