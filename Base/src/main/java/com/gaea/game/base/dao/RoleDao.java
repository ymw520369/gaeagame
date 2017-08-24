package com.gaea.game.base.dao;

import com.gaea.game.base.data.Role;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
public abstract class RoleDao extends MongoBaseDao<Role, Long> {

    public RoleDao(MongoTemplate mongoTemplate) {
        super(Role.class, mongoTemplate);
    }

    public abstract Role findByUserId(String userId);

    public abstract boolean existRole(long roleUid);

    public abstract boolean existRoleName(String roleName);
}
