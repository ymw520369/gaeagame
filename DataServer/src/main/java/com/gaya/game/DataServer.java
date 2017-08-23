package com.gaya.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

/**
 * Created on 2017/8/22.
 *
 * @author Alan
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("com.gaya")
@Order(value = 999)
public class DataServer {
    public static void main(String[] args) {
        SpringApplication.run(DataServer.class, args);
    }
}
