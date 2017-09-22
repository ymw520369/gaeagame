package com.gaea.game.log.record;

import org.alan.utils.TimeHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库记录，数据结构
 *
 * @author Alan
 * @scene 1.0
 */
public class Record {

    public static Map<Integer, Record> sampleMap = new HashMap<>();

    public static Record newRecord(int sid) {
        return sampleMap.get(sid).clone();
    }

    public static Collection<Record> allRecords() {
        return sampleMap.values();
    }


    /**
     * 样本编号
     */
    protected int sid;
    /**
     * 唯一编号
     */
    protected int uid;
    /**
     * 样本名称
     */
    protected String name;
    /**
     * 表引擎
     */
    protected String destType;
    /* fields */
    /**
     * 记录目标
     */
    protected String dest;

    /**
     * 记录编码
     */
    protected String charset;
    /**
     * 分区
     */
    protected int createPartition;

    /**
     * 保存天数
     */
    protected int saveDay;

    /**
     * 自动添加分区数量
     */
    protected int autoAddPartition;

	/* dynamic fields */
    /**
     * 字段表
     */
    Map<String, Column> columnMap = new HashMap<>();

	/* methods */

    /**
     * 构造方法
     */
    public Record() {
        addDefaultInfo();
    }

    public Record setColumnValue(String key, Object value) {
        Column col = getColumn(key);
        col.value = value;
        return this;
    }

    /**
     * 获得创建表的sql
     */
    public String getCreateSql() {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(" `").append(dest).append("` (").append("\n");
        // 字段结构
        for (Column column : columnMap.values()) {
            if (column != null) {
                sb.append(column.getDescription()).append(",\n");
            }
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append(")").append(" ENGINE=").append(destType)
                .append(" DEFAULT CHARSET=").append(charset).append(";");
        return sb.toString();
    }

    /**
     * 创建分区表
     *
     * @return
     */
    public String getCreatePartitionSql() {
        if (createPartition > 0) {
            StringBuilder sb = new StringBuilder("CREATE TABLE ");
            sb.append(" `").append(dest).append("` (").append("\n");
            // 字段结构
            for (Column column : columnMap.values()) {
                if (column != null) {
                    sb.append(column.getDescription()).append(",\n");
                }
            }
            sb.deleteCharAt(sb.length() - 2);
            sb.append(")").append(" ENGINE=").append(destType)
                    .append(" DEFAULT CHARSET=").append(charset).append("\n PARTITION BY RANGE ( to_days(timeDate) )(");

            // 循环添加分区
            for (int i = 0; i < createPartition; i++) {
                if (i > 0) {
                    sb.append(",\n PARTITION ");
                } else {
                    sb.append("\n PARTITION ");
                }
                sb.append("p" + i);
                sb.append(" VALUES LESS THAN (to_days('");
                sb.append(TimeHelper.getDate(TimeHelper.getDaysTime(i)).split(" ")[0]);
                sb.append("'))");
            }
            sb.append("\n);");
            return sb.toString();
        }
        return null;
    }

    /**
     * 获得插入日志的sql
     */
    public String getInsertSql() {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        StringBuilder value = new StringBuilder(" VALUES(");
        sb.append(" `").append(dest).append("` (");

        for (Column column : columnMap.values()) {
            if (column != null) {
                sb.append("`" + column.name + "`,");
                value.append(column.getValue() + ",");
            }
        }
        sb.deleteCharAt(sb.length() - 1).append(")")
                .append(value.deleteCharAt(value.length() - 1).append(")"));
        return sb.toString();
    }

    /**
     * 获取插入预编译sql
     */
    public String getInsertPrepareSql() {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        StringBuilder value = new StringBuilder(" VALUES(");
        sb.append(" `").append(dest).append("` (");

        for (Column column : columnMap.values()) {
            if (column != null) {
                sb.append("`" + column.name + "`,");
                value.append("?,");
            }
        }
        sb.deleteCharAt(sb.length() - 1).append(")")
                .append(value.deleteCharAt(value.length() - 1).append(")"));
        return sb.toString();
    }

    /**
     * 设置插入预编译值
     */
    public void setInsertPrepareValue(PreparedStatement preparedStatement) throws SQLException {
        int index = 1;
        for (Column column : columnMap.values()) {
            if (column != null) {
                column.setValueByPrepared(index, preparedStatement);
                index++;
            }
        }
    }

    /**
     * 设置玩家角色默认字段(为了方便的特殊写法)
     */
    public void addDefaultInfo() {
        Column column = new IntColumn();// 服务器大区号
        column.name = "serverArea";
        column.capacity = 11;
        setColumn(column);
        column = new IntColumn();// 服务器 线路号
        column.name = "serverIndex";
        column.capacity = 11;
        setColumn(column);
        column = new StringColumn();// 服务器 大区名
        column.name = "serverName";
        column.capacity = 50;
        setColumn(column);
        column = new StringColumn();// 时间
        column.name = "eventTime";
        column.capacity = 19;
        setColumn(column);
        column = new IntColumn();// 时间
        column.name = "secondTime";
        column.capacity = 11;
        setColumn(column);
        column = new DateColumn();// 时间
        column.name = "timeDate";
        column.capacity = 50;
        setColumn(column);
    }

    public void setColumn(Column column) {
        columnMap.put(column.name, column);
    }

    /**
     * 取指定名称的字段
     */
    public Column getColumn(String key) {
        return columnMap.get(key);
    }

    public String getDestType() {
        return destType;
    }

    public void setDestType(String destType) {
        this.destType = destType;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Map<String, Column> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Column> columnMap) {
        this.columnMap = columnMap;
    }

    public int getCreatePartition() {
        return createPartition;
    }

    public void setCreatePartition(int createPartition) {
        this.createPartition = createPartition;
    }

    public int getSaveDay() {
        return saveDay;
    }

    public void setSaveDay(int saveDay) {
        this.saveDay = saveDay;
    }

    public int getAutoAddPartition() {
        return autoAddPartition;
    }

    public void setAutoAddPartition(int autoAddPartition) {
        this.autoAddPartition = autoAddPartition;
    }

    @Override
    protected Record clone() {
        try {
            Record record = (Record) super.clone();
            record.columnMap = new HashMap<>();
            this.columnMap.entrySet().forEach(e -> record.columnMap.put(e.getKey(), e.getValue().clone()));
            return record;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
