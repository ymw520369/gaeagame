package com.gaea.game.base.ws;

import com.gaea.game.base.protobuf.ProtostuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
public class GameSession {
    static Logger log = LoggerFactory.getLogger(GameSession.class);
    protected WebSocketSession session;
    protected Object reference;
    protected GameSessionListener sessionListener;

    public static Map<Class<?>, WSMessage> responseMap;

    public GameSession(WebSocketSession session) {
        this.session = session;
    }

    public InetSocketAddress getAddress() {
        return session.getRemoteAddress();
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    public GameSessionListener getSessionListener() {
        return sessionListener;
    }

    public void setSessionListener(GameSessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }

    public void close() {
        try {
            session.close(CloseStatus.NORMAL);
        } catch (Exception e) {
            log.warn("会话关闭异常", e);
        }
    }

    public void onSessionClose() {
        if (sessionListener!=null) {
            sessionListener.onSessionClose(this);
        }
    }

    public void send(Object msg) {
        WSMessage responseMessage = responseMap.get(msg.getClass());
        if (responseMessage == null) {
            log.warn("消息发送失败，该消息结构没有被ResponseMessage注解，msg-class={}", msg.getClass());
            return;
        }
        byte[] data = ProtostuffUtil.serialize(msg);
        GameMessage marsMessage = new GameMessage(responseMessage.messageType(), responseMessage.cmd(), data);
        byte[] message = ProtostuffUtil.serialize(marsMessage);
        BinaryMessage binaryMessage = new BinaryMessage(message);
        try {
            session.sendMessage(binaryMessage);
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("send message error.", e);
        }
    }
}
