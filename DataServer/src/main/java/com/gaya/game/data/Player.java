package com.gaya.game.data;

import com.gaya.game.ws.WSMessage;

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
}
