package com.gaea.game.core.ws;

import com.esotericsoftware.reflectasm.MethodAccess;

import java.util.Map;

/**
 * Created on 2017/7/28.
 *
 * @author Alan
 * @since 1.0
 */
public class MessageController {
    public Object been;
    public MethodAccess methodAccess;
    public Map<Integer, MethodInfo> MethodInfos;

    public MessageController(Object been) {
        this.been = been;
        Class<?> clazz = been.getClass();
        methodAccess = MethodAccess.get(clazz);
        MethodInfos = MessageUtil.load(methodAccess, clazz);
    }
}
