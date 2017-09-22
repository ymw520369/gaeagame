package com.gaea.game.log.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class LongColumn extends Column {
	public static final String MYSQL_TYPE = "bigint";

	public String toString() {
		return name + "=" + value;
	}

	@Override
	public String getSqlColumnType() {
		return MYSQL_TYPE + "(" + capacity + ") DEFAULT " + defValue;
	}

	@Override
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
		if (value == null) {
			preparedStatement.setNull(index, Types.BIGINT);
		} else if (value instanceof Long) {
			preparedStatement.setLong(index, (Long) value);
		} else {
			preparedStatement.setLong(index, Long.parseLong(value.toString()));
		}
	}
	
}
