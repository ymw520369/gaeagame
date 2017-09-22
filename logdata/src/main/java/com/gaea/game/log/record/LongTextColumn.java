package com.gaea.game.log.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 
 * 字符串类型列，longtext
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class LongTextColumn extends Column {
	public static final String MYSQL_TYPE_TEXT = "longtext";

	public String toString() {
		return name + "=" + value;
	}

	@Override
	public String getSqlColumnType() {
		return MYSQL_TYPE_TEXT + " DEFAULT " + defValue;
	}

	@Override
	public String getColumnType() {
		return MYSQL_TYPE_TEXT;
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
