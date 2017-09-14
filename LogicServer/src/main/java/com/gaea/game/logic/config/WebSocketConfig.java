package com.gaea.game.logic.config;

import com.gaea.game.core.ws.LoginHandShakeInterceptor;
import com.gaea.game.core.ws.WSMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Created on 2017/8/22.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    WSMessageHandler messageHandler;
    @Autowired
    HandshakeInterceptor interceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler, "/ws").setAllowedOrigins("*").addInterceptors(interceptor);
        registry.addHandler(messageHandler, "/ws/sockjs").setAllowedOrigins("*").addInterceptors(interceptor).withSockJS();
    }

    @Bean
    public WSMessageHandler messageHandler() {
        return new WSMessageHandler();
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new LoginHandShakeInterceptor();
    }
}
