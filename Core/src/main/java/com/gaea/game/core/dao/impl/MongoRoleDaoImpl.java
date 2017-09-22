/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年3月2日
 */
package com.gaea.game.core.dao.impl;

import com.gaea.game.core.dao.MongoBaseDao;
import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.data.Role;
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author alan
 * @since 1.0
 */
//@Repository
public class MongoRoleDaoImpl extends MongoBaseDao<Role, Long> implements RoleDao {

    Logger log = LoggerFactory.getLogger(getClass());

    public MongoRoleDaoImpl(@Autowired MongoTemplate mongoTemplate) {
        super(Role.class, mongoTemplate);
    }

    @Override
    public long getRoleUidByUserId(String userId) {
        Role role = findByUserId(userId);
        if (role != null) {
            return role.roleUid;
        }
        return 0;
    }

    @Override
    public boolean existRoleByUser(String userId) {
        return mongoTemplate.exists(Query.query(Criteria.where("userUid").is(userId)), Role.class);
    }

    @Override
    public Role save(Role role) {
        long beginTime = System.currentTimeMillis();
//        log.info("开始保存玩家数据,roleUid={}", role.getRoleUid());
        Role r = super.save(role);
        long endTime = System.currentTimeMillis();
        log.info("玩家数据保存完成,roleUid={},useTime={}(ms)", role.roleUid, endTime - beginTime);
        return r;
    }


    @Override
    public Role findByUserId(String userId) {
        log.debug("find role, userid is {}", userId);
        return mongoTemplate.findOne(
                Query.query(Criteria.where("userUid").is(userId)), Role.class);
    }

    @Override
    public boolean existRole(long roleUid) {
        log.debug("exist role by role uid, roleUid is {}", roleUid);
        return mongoTemplate.exists(
                Query.query(Criteria.where("roleUid").is(roleUid)), Role.class);
    }

    @Override
    public boolean existRoleName(String roleName) {
        log.debug("check role name exist, role name is  {}", roleName);
        Criteria criteria = Criteria.where("roleName").is(roleName);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, Role.class);
    }

    @Override
    public long addMoney(long roleUid, long money,String addType) {
        Query query = Query.query(Criteria.where("roleUid").is(roleUid));
        Role role = mongoTemplate.findOne(query, Role.class);
        if (role != null) {
            long newMoney = role.money + money;
            Update update = new Update();
            update.inc("money", money);
            WriteResult writeResult = mongoTemplate.updateFirst(query, update, Role.class);
            boolean result = writeResult.isUpdateOfExisting();
            log.info("增加金币结果,roleUid={},money={},oldMoney={},newMoney={},result={}", roleUid, money, role.money, newMoney, result);
            return newMoney;
        }
        log.warn("找不到指定玩家,roleUid={},money={}", role, money);
        return -1;
    }

    @Override
    public long reduceMoney(long roleUid, long money,String reduceType) {
        Query query = Query.query(Criteria.where("roleUid").is(roleUid));
        Role role = mongoTemplate.findOne(query, Role.class);
        if (role != null) {
            long newMoney = role.money - money;
            if (newMoney < 0) {
                log.warn("没有足够的金币,roleUid={},money={},hasMoney={}", role, money, role.money);
                return -1;
            }
            Update update = new Update();
            update.inc("money", -money);
            WriteResult writeResult = mongoTemplate.updateFirst(query, update, Role.class);
            boolean result = writeResult.isUpdateOfExisting();
            log.info("减少金币结果,roleUid={},money={},oldMoney={},newMoney={},result={}", roleUid, money, role.money, newMoney, result);
            return newMoney;
        }
        log.warn("找不到指定玩家,roleUid={},money={}", role, money);
        return -1;
    }
}
