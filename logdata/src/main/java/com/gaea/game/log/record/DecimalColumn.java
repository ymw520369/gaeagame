package com.gaea.game.log.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DecimalColumn extends Column {

	public static final String MYSQL_TYPE = "DOUBLE";

	/**
	 * 小数类型的精度
	 */
	public int d = 0;

	public String toString() {
		return name + "=" + value;
	}

	@Override
	public String getSqlColumnType() {
		return MYSQL_TYPE + "(" + capacity + "," + d + ") DEFAULT " + defValue;
	}

	@Override
	public String getColumnType() {
		return MYSQL_TYPE;
	}

	public boolean isSameType(String type) {
		if (type != null && !type.isEmpty()) {
			String[] ss = type.split("\\(", -1);
			if (getColumnType().equalsIgnoreCase(ss[0])) {
				if (ss.length > 1) {
					String v = ss[1].split("\\,", -1)[0];
					int c = Integer.parseInt(v);
					if (c != capacity) {
						return false;
					}
					return true;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public String getValue() {
		return "" + value;
	}

	@Override
	public void setValueByPrepared(int index,
			PreparedStatement preparedStatement) throws SQLException {
		if (value == null) {
			preparedStatement.setNull(index, Types.DOUBLE);
		} else if (value instanceof Double) {
			preparedStatement.setDouble(index, (Double) value);
		} else {
			preparedStatement.setDouble(index,
					Double.parseDouble(value.toString()));
		}
	}

}
