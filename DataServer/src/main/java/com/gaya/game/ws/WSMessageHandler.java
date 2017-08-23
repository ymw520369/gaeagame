package com.gaya.game.ws;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class WSMessageHandler extends TextWebSocketHandler {

    Logger log = LoggerFactory.getLogger(WSMessageHandler.class);

    @Autowired
    WSMessageDispatcher messageDispatcher;

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
