package com.gaea.game.logic.message;

import com.gaea.game.core.ws.GameSession;
import com.gaea.game.logic.constant.GameResultEnum;

/**
 * Created on 2017/8/30.
 *
 * @author Alan
 * @since 1.0
 */
public interface MessageToClient {

    /**
     * 发送定时关闭的消息提示
     *
     * @param session
     * @param gameResultEnum
     */
    static void sendTimerGameTips(GameSession session, GameResultEnum gameResultEnum) {
        session.send(new GameTips(GameTips.TIMER_TIPS, gameResultEnum));
    }

    /**
     * 发送手动关闭的消息提示
     *
     * @param session
     * @param gameResultEnum
     */
    static void sendClosedGameTips(GameSession session, GameResultEnum gameResultEnum) {
        session.send(new GameTips(GameTips.CLOSED_TIPS, gameResultEnum));
    }
}
