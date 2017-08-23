package com.gaya.game.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.net.InetSocketAddress;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
public class GameSession {
    Logger log = LoggerFactory.getLogger(GameSession.class);
    protected WebSocketSession session;
    protected Object reference;
    protected GameSessionListener sessionListener;

    public GameSession(WebSocketSession session) {
        this.session = session;
    }

    public InetSocketAddress getAddress() {
        return session.getRemoteAddress();
    }

    public <C> C getReference(Class<C> clazz) {
        if (reference != null && reference.getClass().isAssignableFrom(clazz)) {
            return clazz.cast(reference);
        }
        return null;
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
        sessionListener.onSessionClose(this);
    }
}
