/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2016年6月23日
 */
package com.gaea.game.log.record;

import org.alan.utils.TimeHelper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 *
 * @scene 1.0
 *
 * @author Alan
 *
 */
public class DateColumn extends Column {
    public static final String MYSQL_TYPE = "date";

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
            preparedStatement.setNull(index, Types.DATE);
        } else {
            preparedStatement.setDate(index, new Date(TimeHelper.getTimeMillis(value.toString() + " 00:00:00", TimeHelper.dateFormat)));
        }
    }

}
