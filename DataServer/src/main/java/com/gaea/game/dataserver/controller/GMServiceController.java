package com.gaea.game.dataserver.controller;

import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.data.GeneralResult;
import com.gaea.game.core.data.PlatformConfig;
import com.gaea.game.core.data.PlatformUserInfo;
import com.gaea.game.core.data.Role;
import com.gaea.game.dataserver.manager.DataManager;
import org.alan.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * GM 服务控制器
 * <p>
 * Created on 2017/8/29.
 *
 * @author Alan
 * @since 1.0
 */
@RestController
@RequestMapping("gm")
public class GMServiceController {
    @Resource(name = "redisRoleDao")
    private RoleDao roleDao;

    @Autowired
    private PlatformConfig platformConfig;
    @Autowired
    private DataManager dataManager;

    Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "getMoney")
    public GeneralResult getMoney(String uid, String sign) {
        String source = uid + "&" + platformConfig.secretKey;
        try {
            String mySign = MD5Utils.encoderByMd5(source);
            if (StringUtils.isEmpty(sign) || !sign.equalsIgnoreCase(mySign)) {
                return GeneralResult.SIGN_ERROR;
            }
            log.info("收到获取用户金币数量请求，uid=" + uid);
            Role role = getOrCreateRole(uid);
            if (role != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("uid", role.userId);
                data.put("money", role.money);
                return GeneralResult.SUCCESS.setData(data);
            } else {
                return GeneralResult.USER_NOT_FOUND;
            }
        } catch (Exception e) {
            log.warn("", e);
            return GeneralResult.FAIL.setDec("Exception:" + e.getMessage());
        }
    }

    @RequestMapping(value = "addMoney")
    public GeneralResult addMoney(String uid, int addMoney, String sign) {
        String source = uid + "&" + addMoney + platformConfig.secretKey;
        try {
            String mySign = MD5Utils.encoderByMd5(source);
            if (StringUtils.isEmpty(sign) || !sign.equalsIgnoreCase(mySign)) {
                return GeneralResult.SIGN_ERROR;
            }
            log.info("收到增加用户金币数量请求，uid=" + uid + ",addMoney=" + addMoney);
            Role role = getOrCreateRole(uid);
            if (role != null) {
                long currentMoney = roleDao.addMoney(role.roleUid, addMoney,"charge_money");
                Map<String, Object> data = new HashMap<>();
                data.put("uid", uid);
                data.put("money", currentMoney);
                return GeneralResult.SUCCESS.setData(data);
            } else {
                return GeneralResult.USER_NOT_FOUND;
            }
        } catch (Exception e) {
            log.warn("", e);
            return GeneralResult.FAIL.setDec("Exception:" + e.getMessage());
        }
    }

    public Role getOrCreateRole(String uid) {
        Role role = roleDao.findByUserId(uid);
        if (role == null) {
            log.warn("没有找到用户信息，创建映射关系，uid={}", uid);
            PlatformUserInfo platformUserInfo = new PlatformUserInfo();
            platformUserInfo.uid = uid;
            platformUserInfo.userName = "";
            role = dataManager.createRole(platformUserInfo);
        }
        return role;
    }
}
