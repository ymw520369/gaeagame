package com.gaea.game.logic.dto;

import com.gaea.game.core.ws.WSMessage;

/**
 * Created on 2017/8/28.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class PlayerDTO {
    public RoleDTO role;
    public int sceneId = 1;
}
