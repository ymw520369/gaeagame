package com.gaea.game.core.log;

import com.gaea.game.core.config.ServerConfig;
import com.gaea.game.core.data.Role;
import org.alan.utils.TimeHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/9/21.
 *
 * @author Alan
 * @since 1.0
 */
public class GeneralLogger {

    public ILogService logService;

    public ServerConfig serverConfig;

    public ILogService getLogService() {
        return logService;
    }

    public void setLogService(ILogService logService) {
        this.logService = logService;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
    public void logCreateRole(Role role) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "create_role");
        addPlayerDefault(role, map);
        logService.log(map);
    }

    public void logLogin(Role role) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "login");
        addPlayerDefault(role, map);
        logService.log(map);
    }

    public void logLogout(Role role) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "logout");
        addPlayerDefault(role, map);
        logService.log(map);
    }

    public void logAddmoney(Role role, String addType, int addMoney) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "money_change");
        map.put("addType", addType);
        map.put("add", true);
        map.put("change_money", addMoney);
        addPlayerDefault(role, map);
        logService.log(map);
    }

    public void logReducemoney(Role role, String addType, int reduceMoney) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "money_change");
        map.put("addType", addType);
        map.put("add", false);
        map.put("change_money", reduceMoney);
        addPlayerDefault(role, map);
        logService.log(map);
    }

    public void addPlayerDefault(Role role, Map<String, Object> map) {
        map.put("roleUid", role.roleUid);
        map.put("roleName", role.name);
        map.put("money", role.money);
        map.put("date", TimeHelper.getDate());
        map.put("dateTime", TimeHelper.getDateTime());
        map.put("eventTime", System.currentTimeMillis());
        map.put("serverId", serverConfig.serverId);
        map.put("serverType", serverConfig.serverType);
        map.put("serverName", serverConfig.serverName);
    }
}
