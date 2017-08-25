package com.gaea.game.base.ws;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created on 2017/8/22.
 *
 * @author Alan
 * @since 1.0
 */
public class WSMessageHandler extends TextWebSocketHandler {

    Logger log = LoggerFactory.getLogger(WSMessageHandler.class);

    WSMessageDispatcher messageDispatcher;

    @Bean
    public WSMessageDispatcher messageDispatcher() {
        return messageDispatcher = new WSMessageDispatcher();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("连接建立，{}", session.getRemoteAddress());
        GameSession gameSession = new GameSession(session);
        session.getAttributes().put("session", gameSession);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        log.debug("消息到达，{}", session.getRemoteAddress());
        String msg = textMessage.getPayload();
        GameSession gameSession = (GameSession) session.getAttributes().get("session");
        if (gameSession == null) {
            log.warn("未被正常绑定的session,断开连接,session={}", session);
            session.close();
            return;
        }
        if (msg != null && !msg.isEmpty()) {
            GameMessage gameMessage = JSON.parseObject(msg, GameMessage.class);
            messageDispatcher.messageDispatch(gameSession, gameMessage);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable var2) throws Exception {
        log.debug("连接建立，" + session.getRemoteAddress(), var2);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus var2) throws Exception {
        log.debug("连接关闭，{}", session.getRemoteAddress());
        GameSession gameSession = (GameSession) session.getAttributes().get("session");
        if (gameSession == null) {
            log.warn("未被正常绑定的session,断开连接,session={}", session);
            session.close();
            return;
        }
        gameSession.onSessionClose();
    }
}
