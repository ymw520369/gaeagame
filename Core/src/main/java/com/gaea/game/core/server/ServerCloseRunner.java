package com.gaea.game.core.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/9/6.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class ServerCloseRunner implements CommandLineRunner, Runnable, ApplicationListener<ContextRefreshedEvent> {

    List<ServerListener> serverListeners;

    @Override
    public void run() {
        if (serverListeners != null) {
            serverListeners.forEach(ServerListener::onServerClose);
        }
    }

    @Override
    public void run(String... strings) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(this));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        Map<String, ServerListener> serverListenerMap = context.getBeansOfType(ServerListener.class);
        serverListeners = new ArrayList<>(serverListenerMap.values());
    }
}
