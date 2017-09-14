package com.gaea.game.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2017/9/7.
 *
 * @author Alan
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("com.gaea")
public class LogStartup {
    public static void main(String[] args) {
        SpringApplication.run(LogStartup.class, args);
    }
}
