package com.gaea.game.logic.dto;

import com.gaea.game.base.ws.WSMessage;

/**
 * Created on 2017/8/28.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class RoleDTO {
    public String userId;
    public long roleUid;
    public String name;
    private long money = 3;
}
