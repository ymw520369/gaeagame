package com.gaea.game.core.ws;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
public interface GameSessionListener {
    /**
     * 当回话被关闭
     *
     * @param session
     */
    void onSessionClose(GameSession session);
}
