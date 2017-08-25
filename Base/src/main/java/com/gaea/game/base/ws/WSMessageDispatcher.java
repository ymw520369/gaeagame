package com.gaea.game.base.ws;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

/**
 * Created on 2017/8/22.
 *
 * @author Alan
 * @since 1.0
 */
public class WSMessageDispatcher implements ApplicationListener<ContextRefreshedEvent> {
    private Map<Integer, MessageController> messageControllers;
    Logger log = LoggerFactory.getLogger(getClass());

    public void messageDispatch(GameSession session, GameMessage msg) {
        int messageType = msg.messageType;
        int command = msg.cmd;
        //心跳消息不输出
        if (messageType != 1) {
            log.info("收到客户端消息,messageType={},cmd={}", messageType, command);
        }
        //这里做一个特殊处理，如果不是登录消息，并且用户还没有登录，将连接直接断掉
        if (messageType != 1 && messageType != 1000 && session.reference == null) {
            session.close();
            return;
        }
        MessageController messageController = messageControllers.get(messageType);
        if (messageController != null) {
            MethodInfo methodInfo = messageController.MethodInfos.get(command);
            Object bean = messageController.been;
            if (methodInfo.parms != null && methodInfo.parms.length > 0) {
                Object[] args = new Object[methodInfo.parms.length];
                Object reference = session.getReference(Object.class);
                for (int i = 0; i < args.length; i++) {
                    Class<?> clazz = methodInfo.parms[i];
                    if (clazz == GameSession.class) {
                        args[i] = session;
                    } else if (reference != null && clazz == reference.getClass()) {
                        args[i] = reference;
                    } else {
                        WSMessage rm = clazz.getAnnotation(WSMessage.class);
                        if (rm != null && msg.data != null && !msg.data.isEmpty()) {
                            args[i] = JSON.parseObject(msg.data, clazz);
                        }
                    }
                }
                messageController.methodAccess.invoke(bean, methodInfo.index, args);
            } else {
                messageController.methodAccess.invoke(bean, methodInfo.index);
            }
        } else {
            log.warn("未被注册的消息,messageType={},cmd={}", messageType, command);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        messageControllers = MessageUtil.load(event.getApplicationContext());
        GameSession.responseMap = MessageUtil.loadResponseMessage("org.alan");
    }
}
