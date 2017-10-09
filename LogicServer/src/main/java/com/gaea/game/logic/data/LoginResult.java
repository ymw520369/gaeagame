package com.gaea.game.logic.data;

import com.gaea.game.core.constant.ResultEnum;
import com.gaea.game.core.data.Player;
import com.gaea.game.core.ws.WSMessage;
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
    public ResultEnum result;
    public PlayerDTO player;

    public LoginResult(ResultEnum result, Player player) {
        this.result = result;
        if(player!=null) {
            this.player = DTOUtil.toPlayer(player);
        }
    }
}
