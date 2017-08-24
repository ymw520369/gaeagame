package com.gaea.game.base.data;


import com.gaea.game.base.ws.WSMessage;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@WSMessage
public class Role {
    public String userId;
    public long roleUid;
    public String name;
    public long money;
}
