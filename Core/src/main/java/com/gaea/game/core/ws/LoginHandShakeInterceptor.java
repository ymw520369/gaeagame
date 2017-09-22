package com.gaea.game.core.ws;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class LoginHandShakeInterceptor implements HandshakeInterceptor {
    /**
     * 握手之前，若返回false，则不建立链接
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        System.out.println("握手之前...");
        request.getHeaders().keySet().forEach(key ->
                System.out.println(key + "->" +
                        Arrays.toString(request.getHeaders().getOrDefault(key, new ArrayList<String>()).toArray())));
        return true;
    }

    /**
     * 握手之后
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        // TODO Auto-generated method stub

    }
}
