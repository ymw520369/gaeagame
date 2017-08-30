package com.gaea.game.logic;

import com.gaea.game.base.timer.TimerCenter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
public class LogicServer implements ApplicationRunner {
    /**
     * 定时中心
     */
    private TimerCenter timerCenter;
    /**
     * 文件监视器
     */
    private FileAlterationMonitor monitor;

    public static void main(String[] args) {
        SpringApplication.run(LogicServer.class, args);
    }

    @Bean
    public TimerCenter timerCenter() {
        return timerCenter = new TimerCenter("timer-center");
    }

    @Bean
    public FileAlterationMonitor createFileMonitor() {
        return monitor = new FileAlterationMonitor();
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        timerCenter.start();
        monitor.start();
    }
}
