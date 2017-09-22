package com.gaea.game.log.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * 字符串类型列，如果给定容量大于200，使用text
 *
 * 否则使用varchar
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class StringColumn extends Column {
	public static final String MYSQL_TYPE_VARCHAR = "varchar";

	public String toString() {
		return name + "=" + value;
	}

	@Override
	public String getSqlColumnType() {
		return MYSQL_TYPE_VARCHAR + "(" + capacity + ") DEFAULT " + defValue;
	}

	@Override
	public String getColumnType() {
		return MYSQL_TYPE_VARCHAR;
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
		preparedStatement.setString(index, value.toString());
	}

}
