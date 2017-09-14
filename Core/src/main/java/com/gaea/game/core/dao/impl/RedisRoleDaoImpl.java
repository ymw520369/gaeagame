package com.gaea.game.core.dao.impl;

import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.data.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import static com.gaea.game.core.constant.RedisKey.*;

/**
 * Created on 2017/9/7.
 *
 * @author Alan
 * @since 1.0
 */
@Repository("redisRoleDao")
public class RedisRoleDaoImpl implements RoleDao {

    @Autowired
    RedisTemplate redisTemplate;

    public long getRoleUidByUserId(String userId) {
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        Long roleUid = hashOperations.get(USER_ROLE_MAPPING_TABLE, userId);
        if (roleUid != null) {
            return roleUid;
        }
        return 0L;
    }

    @Override
    public boolean existRoleByUser(String userId) {
        return getRoleUidByUserId(userId) > 0;
    }

    @Override
    public Role findByUserId(String userId) {
        long roleUid = getRoleUidByUserId(userId);
        HashOperations<String, Long, Role> hashOperations = redisTemplate.opsForHash();
        Role role = hashOperations.get(ROLE_TABLE, roleUid);
        return role;
    }

    @Override
    public boolean existRole(long roleUid) {
        HashOperations<String, Long, Role> hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(ROLE_TABLE, roleUid);
    }

    @Override
    public boolean existRoleName(String roleName) {
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        Long roleUid = hashOperations.get(ROLE_NAME_MAPPING_TABLE, roleName);
        return roleUid != null;
    }

    @Override
    public long addMoney(long roleUid, long money) {
        Role role = findOne(roleUid);
        role.money += money;
        save(role);
        return role.money;
    }

    @Override
    public long reduceMoney(long roleUid, long money) {
        Role role = findOne(roleUid);
        if (role.money - money < 0) {
            return -1;
        }
        role.money -= money;
        save(role);
        return role.money;
    }

    @Override
    public Role save(Role role) {
        HashOperations<String, Long, Role> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(ROLE_TABLE, role.roleUid, role);

        HashOperations<String, String, Long> userHashOperations = redisTemplate.opsForHash();
        userHashOperations.put(USER_ROLE_MAPPING_TABLE, role.userId, role.roleUid);

        HashOperations<String, String, Long> nameHashOperations = redisTemplate.opsForHash();
        nameHashOperations.put(ROLE_NAME_MAPPING_TABLE, role.name, role.roleUid);
        return role;
    }

    @Override
    public Role findOne(Long roleUid) {
        HashOperations<String, Long, Role> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(ROLE_TABLE, roleUid);
    }
}
