package com.gaea.game.logic.dto;

import com.gaea.game.core.ws.WSMessage;
import com.gaea.game.logic.constant.MessageConst;

/**
 * Created on 2017/8/28.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage(resp = true, messageType = MessageConst.Role.TYPE, cmd = MessageConst.Role.RESP_ROLE_INFO)
public class RoleDTO {
    public String userId;
    public long roleUid;
    public String name;
    public long money;
}
