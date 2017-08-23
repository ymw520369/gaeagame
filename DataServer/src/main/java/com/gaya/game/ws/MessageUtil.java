package com.gaya.game.ws;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.alan.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2017/7/28.
 *
 * @author Alan
 * @since 1.0
 */
public class MessageUtil {

    static Logger log = LoggerFactory.getLogger(MessageUtil.class);

    public static Map<Integer, MessageController> load(ApplicationContext context) {
        Map<Integer, MessageController> messageControllers = new HashMap<>();
        Class<MessageType> clazz = MessageType.class;
        log.debug("开始初始化 {} 消息分发器", clazz);
        Map<String, Object> beans = context.getBeansWithAnnotation(clazz);
        beans.values().forEach(o -> {
            MessageType messageType = o.getClass().getAnnotation(clazz);
            MessageController messageController = new MessageController(o);
            messageControllers.put(messageType.value(), messageController);
        });
        return messageControllers;
    }

    public static Map<Integer, MethodInfo> load(MethodAccess methodAccess, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        Map<Integer, MethodInfo> MethodInfos = new HashMap<>();
        if (methods != null && methods.length >= 0) {
            for (Method method : methods) {
                Class<Command> clz = Command.class;
                Command command = method.getAnnotation(clz);
                if (command != null) {
                    String name = method.getName();
                    Class[] types = method.getParameterTypes();
                    Type returnType = method.getReturnType();
                    int index = methodAccess.getIndex(name, types);
                    MethodInfo methodInfo = new MethodInfo(index, name, types, returnType);
                    MethodInfos.put(command.value(), methodInfo);
                }

            }
        }
        return MethodInfos;
    }

    public static Map<Class<?>, WSMessage> loadResponseMessage(String... pkgs) {
        Map<Class<?>, WSMessage> MethodInfos = new HashMap<>();
        Set<Class<?>> clazzes = new HashSet<>();
        for (String pkg : pkgs) {
            clazzes.addAll(ClassUtils.getAllClassByAnnotation(pkg, WSMessage.class));
        }
        if (!clazzes.isEmpty()) {
            clazzes.forEach(clazz -> {
                WSMessage responseMessage = clazz.getAnnotation(WSMessage.class);
                if (responseMessage.resp()) {
                    MethodInfos.put(clazz, responseMessage);
                }
            });
        }
        return MethodInfos;
    }
}
