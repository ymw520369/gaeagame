package com.gaea.game.core.dao.impl;

import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.data.Role;
import com.gaea.game.core.log.GeneralLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;

import static com.gaea.game.core.constant.RedisKey.*;

/**
 * Created on 2017/9/7.
 *
 * @author Alan
 * @since 1.0
 */
public class RedisRoleDaoImpl implements RoleDao {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource(name = "logger")
    GeneralLogger generalLogger;

    public long getRoleUidByUserId(String userId) {
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        Object roleUid = hashOperations.get(USER_ROLE_MAPPING_TABLE, userId);
        if (roleUid != null) {
            return Long.parseLong(roleUid.toString());
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
        return findOne(roleUid);
    }

    @Override
    public boolean existRole(long roleUid) {
        HashOperations<String, Long, Role> hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(ROLE_TABLE, roleUid);
    }

    @Override
    public boolean existRoleName(String roleName) {
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        Object roleUid = hashOperations.get(ROLE_NAME_MAPPING_TABLE, roleName);
        return roleUid != null;
    }

    @Override
    public long addMoney(long roleUid, long money, String addType) {
        SessionCallback<Long> sessionCallback = new SessionCallback<Long>() {
            @Override
            public Long execute(RedisOperations operations) throws DataAccessException {
                String key = roleKey(roleUid);
                operations.watch(key);
                Role role = (Role) operations.opsForValue().get(key);
                operations.multi();
                role.money += money;
                operations.opsForValue().set(key, role);
                operations.exec();
                generalLogger.logAddmoney(role, addType, (int) money);
                return role.money;
            }
        };
        return (Long) redisTemplate.execute(sessionCallback);
    }

    @Override
    public long reduceMoney(long roleUid, long money, String reduceType) {
        SessionCallback<Long> sessionCallback = new SessionCallback<Long>() {
            @Override
            public Long execute(RedisOperations operations) throws DataAccessException {
                String key = roleKey(roleUid);
                operations.watch(key);
                Role role = (Role) operations.opsForValue().get(key);
                operations.multi();
                if (role.money - money < 0) {
                    return -1L;
                }
                role.money -= money;
                operations.opsForValue().set(key, role);
                operations.exec();
                generalLogger.logReducemoney(role, reduceType, (int) money);
                return role.money;
            }
        };
        return (Long) redisTemplate.execute(sessionCallback);
    }

    @Override
    public Role save(Role role) {
        ValueOperations<String, Role> valueOperations = redisTemplate.opsForValue();
        String key = roleKey(role.roleUid);
        Role oldRole = valueOperations.get(key);
        //首次插入
        if (oldRole == null) {
            HashOperations<String, String, Long> userHashOperations = redisTemplate.opsForHash();
            userHashOperations.put(USER_ROLE_MAPPING_TABLE, role.userId, role.roleUid);

            HashOperations<String, String, Long> nameHashOperations = redisTemplate.opsForHash();
            nameHashOperations.put(ROLE_NAME_MAPPING_TABLE, role.name, role.roleUid);
        }
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.watch(key);
                operations.multi();
                operations.opsForValue().set(roleKey(role.roleUid), role);
                return operations.exec();
            }
        });
        return role;
    }

    @Override
    public Role findOne(Long roleUid) {
        ValueOperations<String, Role> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(roleKey(roleUid));
    }

    public String roleKey(long roleUid) {
        return ROLE_TABLE + roleUid;
    }
}
