package com.gaea.game.core.ws;

import com.gaea.game.core.protobuf.ProtostuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * Created on 2017/8/22.
 *
 * @author Alan
 * @since 1.0
 */
public class WSMessageHandler extends BinaryWebSocketHandler {

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
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage binaryMessage) throws Exception {
        log.debug("消息到达，{}", session.getRemoteAddress());
        try {
            byte[] msg = binaryMessage.getPayload().array();
            GameSession gameSession = (GameSession) session.getAttributes().get("session");
            if (gameSession == null) {
                log.warn("未被正常绑定的session,断开连接,session={}", session);
                session.close();
                return;
            }
            if (msg != null && msg.length > 0) {
                GameMessage gameMessage = ProtostuffUtil.deserialize(msg, GameMessage.class);
                //GameMessage gameMessage = JSON.parseObject(msg, GameMessage.class);
                messageDispatcher.messageDispatch(gameSession, gameMessage);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            log.warn("消息处理异常", t);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
        e.printStackTrace();
        log.debug("异常，" + session.getRemoteAddress(), e);
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
