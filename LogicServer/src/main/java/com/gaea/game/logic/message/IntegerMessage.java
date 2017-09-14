package com.gaea.game.logic.message;

import com.gaea.game.core.ws.WSMessage;

/**
 * 通用单整型参数消息结构定义
 * <p>
 * Created on 2017/9/8.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class IntegerMessage {
    public int value;
}
