/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2016年6月23日 	
 */
package com.gaea.game.log.record;

import org.alan.utils.TimeHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


/**
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class DateTimeColumn extends Column {
	public static final String MYSQL_TYPE = "datetime";

	@Override
	public String getSqlColumnType() {
		return MYSQL_TYPE + " DEFAULT " + defValue;
	}

	@Override
	public String getColumnType() {
		return MYSQL_TYPE;
	}

	@Override
	public String getValue() {
		if (value == null) {
			return null;
		}
		return "'" + value + "'";
	}

	@Override
	public void setValueByPrepared(int index,
			PreparedStatement preparedStatement) throws SQLException {
		if (value == null) {
			preparedStatement.setNull(index, Types.TIMESTAMP);
		} else {
			preparedStatement.setTimestamp(index, new Timestamp(TimeHelper.getTimeMillisBy(value.toString())));
		}
	}

}
