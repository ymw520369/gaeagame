/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年2月27日 	
 */
package com.gaya.game.dao.uid;


/**
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public interface UidDao {

	/**
	 * 获得一个long值类型的ID,并自增给定的数量
	 * 
	 * @param key
	 *            id键值
	 * @param num
	 *            获取数量
	 * @return
	 * 
	 * @exception RuntimeException
	 *                当传入的数量不是自然数并且超过Int最大值
	 */
	long getAndUpdateUid(UidTypeEnum key, int num);
	
	/**
	 * 获得一个long值类型的ID,并自增给定的数量
	 * 
	 * @param key
	 *            id键值
	 * @param num
	 *            获取数量
	 * @return
	 * 
	 * @exception RuntimeException
	 *                当传入的数量不是自然数并且超过Int最大值
	 */
//	default long casUid(UidTypeEnum key, int num) {
//		if (num <= 0 || num == Integer.MAX_VALUE) {
//			throw new RuntimeException("参数错误，key=" + key + ",num=" + num);
//		}
//		
//		synchronized (key) {
//			Uid uid = findOne(key);
//			if (uid == null) {
//				uid = new Uid(key, 1000L);
//			}
//			long value = uid.getLastId();
//			uid.setLastId(value + num);
//			save(uid);
//			return value;
//		}
//	}
}
