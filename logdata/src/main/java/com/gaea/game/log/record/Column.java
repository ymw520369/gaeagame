package com.gaea.game.log.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Column implements Cloneable {
    /**
     * 字段名
     */
    public String name;
    /**
     * 类型
     */
    public int type;
    /* fields */
    /**
     * 字段容量
     */
    public int capacity;

    /**
     * 字段精度
     */
    public int precision;
    /**
     * 是否自增长
     */
    public boolean isAutoincrement;
    /**
     * 是否是空值
     */
    public boolean isNull = true;

    public String defValue = " NULL";

    /**
     * 字段值
     */
    public Object value;

    /**
     * 获得sql字段的描述
     */
    public String getDescription() {
        return new StringBuilder("`").append(name).append("` ")
                .append(getSqlColumnType()).toString();
    }

    public abstract String getSqlColumnType();

    public abstract String getColumnType();

    public abstract String getValue();

    public abstract void setValueByPrepared(int index,
                                            PreparedStatement preparedStatement) throws SQLException;

    public boolean isSameType(String type) {
        if (type != null && !type.isEmpty()) {
            String[] ss = type.split("\\(", -1);
            if (getColumnType().equalsIgnoreCase(ss[0])) {
                if (ss.length > 1) {
                    String v = ss[1].split("\\)", -1)[0];
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
    protected Column clone() {
        try {
            return (Column) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
