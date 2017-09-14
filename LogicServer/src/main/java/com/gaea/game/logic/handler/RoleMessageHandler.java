package com.gaea.game.logic.handler;

import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.data.Role;
import com.gaea.game.core.ws.Command;
import com.gaea.game.core.ws.MessageType;
import com.gaea.game.logic.constant.MessageConst;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.dto.DTOUtil;
import com.gaea.game.logic.dto.RoleDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.Role.TYPE)
public class RoleMessageHandler {
    @Resource(name = "redisRoleDao")
    RoleDao roleDao;

    @Command(MessageConst.Role.REQ_ROLE_INFO)
    public void getRoleInfo(PlayerController playerController) {
        long playerId = playerController.playerId;
        Role role = roleDao.findOne(playerId);
        RoleDTO roleDTO = DTOUtil.toRole(role);
        playerController.sendToClient(roleDTO);
    }

}
