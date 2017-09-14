package com.gaea.game.logic.dto;

import com.gaea.game.core.data.Player;
import com.gaea.game.core.data.Role;

/**
 * Created on 2017/8/28.
 *
 * @author Alan
 * @since 1.0
 */
public class DTOUtil {
    public static PlayerDTO toPlayer(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.role = toRole(player.role);
        return playerDTO;
    }

    public static RoleDTO toRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.name = role.name;
        roleDTO.roleUid = role.roleUid;
        roleDTO.userId = role.userId;
        roleDTO.money = role.money;
        return roleDTO;
    }
}
