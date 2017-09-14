package com.gaea.game.dataserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2017/8/22.
 *
 * @author Alan
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("com.gaea")
public class DataStartup {
    public static void main(String[] args) {
        SpringApplication.run(DataStartup.class, args);
    }
}
