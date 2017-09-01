package com.gaea.game.dataserver.manager;

import com.gaea.game.base.data.LogicServerInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class NodeManager {

    public Map<Integer, LogicServerInfo> logicServerInfoMap = new HashMap<>();

    public void register(LogicServerInfo logicServerInfo) {
        logicServerInfoMap.put(logicServerInfo.serverId, logicServerInfo);
    }

    public LogicServerInfo dynamicLoad() {
        if (logicServerInfoMap.isEmpty()) {
            return null;
        }
        return logicServerInfoMap.values().stream().sorted().findFirst().get();
    }
}
