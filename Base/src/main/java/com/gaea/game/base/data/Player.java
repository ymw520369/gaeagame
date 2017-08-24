package com.gaea.game.base.data;


import com.gaea.game.base.ws.WSMessage;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
@WSMessage
public class Player {
    public Role role;
    public transient PlatformUserInfo userInfo;
    public int sceneId;
    public String certifyToken;
}
