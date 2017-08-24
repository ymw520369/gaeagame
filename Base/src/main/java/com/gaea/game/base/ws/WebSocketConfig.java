package com.gaea.game.base.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Autowired
    WSMessageHandler messageHandler;
    @Autowired
    HandshakeInterceptor interceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler, "/websocket").setAllowedOrigins("*").addInterceptors(interceptor);
        registry.addHandler(messageHandler, "/websocket/sockjs").addInterceptors(interceptor).withSockJS();
    }
}
