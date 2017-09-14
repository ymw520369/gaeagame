package com.gaea.game.dataserver.controller;

import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.data.GeneralResult;
import com.gaea.game.core.data.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "getMoney")
    public GeneralResult register(String uid) {
        log.info("收到获取用户金币数量请求，uid=" + uid);
        Role role = roleDao.findByUserId(uid);
        if (role != null) {
            return GeneralResult.SUCCESS.setData(role.money);
        } else {
            return GeneralResult.FAIL.setDec("找不到用户");
        }
    }
}
