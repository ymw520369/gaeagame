package com.gaea.game.core.ws;

/**
 * Created on 2017/9/22.
 *
 * @author Alan
 * @since 1.0
 */
@MessageType(1)
public class PingMessageHandler {
    @WSMessage(resp = true, messageType = 1, cmd = 2)
    public static class Pong {
        long time;

        public Pong(long time) {
            this.time = time;
        }
    }

    @Command(1)
    public void ping(GameSession gameSession) {
        gameSession.send(new Pong(System.currentTimeMillis()));
    }
}
