package com.gaea.game.core.ws;

import com.gaea.game.core.protobuf.ProtostuffUtil;
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
        if (sessionListener != null) {
            sessionListener.onSessionClose(this);
        }
    }

    /**
     * 发送消息，该消息应该是被 @WSMessage 注解的，并且提供了消息类型与命令字
     *
     * @param msg
     */
    public void send(Object msg) {
        WSMessage responseMessage = responseMap.get(msg.getClass());
        if (responseMessage == null) {
            log.warn("消息发送失败，该消息结构没有被ResponseMessage注解，msg-class={}", msg.getClass());
            return;
        }
        send(responseMessage.messageType(), responseMessage.cmd(), msg);
    }

    /**
     * 以指定的消息类型与命令字发送消息
     *
     * @param messageType
     * @param cmd
     * @param msg
     */
    public void send(int messageType, int cmd, Object msg) {
        byte[] data = ProtostuffUtil.serialize(msg);
        GameMessage marsMessage = new GameMessage(messageType, cmd, data);
        byte[] message = ProtostuffUtil.serialize(marsMessage);
        BinaryMessage binaryMessage = new BinaryMessage(message);
        try {
            session.sendMessage(binaryMessage);
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("send message error.", e);
        }
    }

    @Override
    public String toString() {
        return session.toString();
    }
}
