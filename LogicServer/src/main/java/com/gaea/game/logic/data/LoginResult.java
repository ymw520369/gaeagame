package com.gaea.game.logic.data;

import com.gaea.game.base.constant.GameResultEnum;
import com.gaea.game.base.data.Player;
import com.gaea.game.base.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.dto.DTOUtil;
import com.gaea.game.logic.dto.PlayerDTO;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.Login.TYPE, cmd = MessageConst.Login.RESP_VERTIFY)
public class LoginResult {
    public GameResultEnum result;
    public PlayerDTO player;

    public LoginResult(GameResultEnum result, Player player) {
        this.result = result;
        this.player = DTOUtil.toPlayer(player);
    }
}
