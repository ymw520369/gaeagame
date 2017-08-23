/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年3月2日 	
 */
package com.gaya.game.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;

/**
 *
 * mongo db 数据访问基类
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public abstract class MongoBaseDao<T, ID extends Serializable> extends
		SimpleMongoRepository<T, ID> {

	protected MongoTemplate mongoTemplate;
	protected Class<T> clazz;

	public MongoBaseDao(Class<T> clazz, MongoTemplate mongoTemplate) {
		super(new MongoRepositoryFactory(mongoTemplate)
				.getEntityInformation(clazz), mongoTemplate);
		this.mongoTemplate = mongoTemplate;
		this.clazz = clazz;
	}
}
