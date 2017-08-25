package com.gaea.game.base.data;


import com.gaea.game.base.ws.WSMessage;

import java.util.concurrent.atomic.AtomicLong;

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
    public volatile AtomicLong money;
}
