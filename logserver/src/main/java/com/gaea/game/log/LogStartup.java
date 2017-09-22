package com.gaea.game.log;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2017/9/7.
 *
 * @author Alan
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("com.gaea")
public class LogStartup implements CommandLineRunner {

    FileAlterationMonitor monitor;

    public static void main(String[] args) {
        SpringApplication.run(LogStartup.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        monitor.start();
    }

    @Bean
    public FileAlterationMonitor createFileMonitor() {
        return monitor = new FileAlterationMonitor();
    }
}
