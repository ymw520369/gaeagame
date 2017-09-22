package com.gaea.game.log.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class IntColumn extends Column {

	public static final String MYSQL_TYPE = "int";

	public String toString() {
		return name + "=" + value;
	}

	@Override
	public String getSqlColumnType() {
		return MYSQL_TYPE + "(" + capacity + ") DEFAULT " + defValue;
	}

	public String getColumnType() {
		return MYSQL_TYPE;
	}


	@Override
	public String getValue() {
		return "" + value;
	}

	@Override
	public void setValueByPrepared(int index,
			PreparedStatement preparedStatement) throws SQLException {
		if(value == null) {
			preparedStatement.setNull(index, Types.INTEGER);
		}else if (value instanceof Integer) {
			preparedStatement.setInt(index, (Integer) value);
		} else {
			preparedStatement.setInt(index, Integer.parseInt(value.toString()));
		}
	}
	
}
