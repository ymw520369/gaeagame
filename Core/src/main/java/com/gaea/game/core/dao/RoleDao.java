package com.gaea.game.core.dao;

import com.gaea.game.core.data.Role;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
public interface RoleDao {

    long getRoleUidByUserId(String userId);

    boolean existRoleByUser(String userId);

    Role findByUserId(String userId);

    boolean existRole(long roleUid);

    boolean existRoleName(String roleName);

    long addMoney(long roleUid, long money,String addType);

    long reduceMoney(long roleUid, long money,String reduceType);

    Role save(Role role);

    Role findOne(Long roleUid);
}
