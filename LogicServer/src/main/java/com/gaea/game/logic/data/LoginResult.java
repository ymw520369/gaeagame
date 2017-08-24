package com.gaea.game.logic.data;

import com.gaea.game.base.constant.GameResultEnum;
import com.gaea.game.base.constant.MessageConst;
import com.gaea.game.base.data.Player;
import com.gaea.game.base.ws.WSMessage;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.Login.TYPE, cmd = MessageConst.Login.RESP_VERTIFY)
public class LoginResult {
    public GameResultEnum result;
    public Player player;

    public LoginResult(GameResultEnum result, Player player) {
        this.result = result;
        this.player = player;
    }
}
