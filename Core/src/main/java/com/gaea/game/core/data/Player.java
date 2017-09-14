package com.gaea.game.core.data;


/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
public class Player {
    public Role role;
    public UserInfo userInfo;

    public Player(Role role, UserInfo userInfo) {
        this.role = role;
        this.userInfo = userInfo;
    }
}
