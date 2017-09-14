package com.gaea.game.dataserver.manager;

import com.gaea.game.core.data.LogicServerInfo;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 逻辑服务器管理器
 * <p>
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class LogicServerManager {

    public Map<Integer, LogicServerInfo> logicServerInfoMap = new HashMap<>();

    public Map<Long, Integer> onlinePlayer = new ConcurrentHashMap<>();

    /**
     * 逻辑服务器注册
     *
     * @param logicServerInfo
     */
    public void register(LogicServerInfo logicServerInfo) {
        logicServerInfoMap.put(logicServerInfo.serverId, logicServerInfo);
    }

    /**
     * 逻辑服务器移除
     *
     * @param serverId
     */
    public void unregister(int serverId) {
        logicServerInfoMap.remove(serverId);
    }

    /**
     * 负载均衡
     *
     * @return
     */
    public LogicServerInfo load() {
        if (logicServerInfoMap.isEmpty()) {
            return null;
        }
        //获取当前人数最少的逻辑服务器
        return logicServerInfoMap.values().stream().
                min(Comparator.comparing(LogicServerInfo::getCurrentNum)).orElse(null);
    }
}
