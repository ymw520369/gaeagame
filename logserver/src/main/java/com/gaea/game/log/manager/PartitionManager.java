package com.gaea.game.log.manager;

import com.gaea.game.log.record.Record;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * 分区管理器
 *
 * @author YXW
 */
//@Controller
public class PartitionManager {

    Logger log = Logger.getLogger(getClass());

    public static final String PARTITION_NAME = "PARTITION_NAME";

    public static final String PARTITION_DESCRIPTION = "PARTITION_DESCRIPTION";

    /**
     * 数据源
     */
    @Resource(name = "dataSource")
    private ComboPooledDataSource dataSource;

    public void execute() {
        log.info("开始执行分区维护任务........");

        Collection<Record> records = Record.allRecords();

        int curDateDay = getDateBaseDay();
        if (curDateDay < 0) {
            log.error("分区维护失败！无法获取数据库当前日期。");
            return;
        }

        log.info("数据库当前天数为:" + curDateDay);

        // 遍历所有建表文件
        for (Record record : records) {
            if (record.getCreatePartition() <= 0 || record.getSaveDay() <= 0
                    || record.getAutoAddPartition() <= 0) {
                continue;
            }

            log.info("开始执行" + record.getDest() + "表分区维护...,createPartition:"
                    + record.getCreatePartition() + ",saveDay:"
                    + record.getSaveDay() + "autoAddPartition:"
                    + record.getAutoAddPartition());

            checkAndDeleteMaxPartitionValue(record.getDest());

            if (record.getAutoAddPartition() > 0) {
                checkAndAddPartition(curDateDay, record.getDest(),
                        record.getAutoAddPartition());
            }

            if (record.getSaveDay() > 0) {
                checkAndDeleteOldPartition(curDateDay, record.getDest(),
                        record.getSaveDay());
            }

            log.info(record.getDest() + "维护完成");
        }
        log.info("分区管理任务完成！");
    }

    /**
     * 检查并添加分区
     */
    public void checkAndAddPartition(int curDateDay, String tableName,
                                     int addPartition) {
        log.info("开始检查并添加" + tableName + "表分区....");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            // 获取最大和最小分区数量信息
            rs = stmt.executeQuery(getMaxAndMinPartitionSql(tableName));

            if (rs.next()) {
                int maxPartitionDescription = rs
                        .getInt("MAX_PARTITION_DESCRIPTION");

                // 判断是否需要增加新分区
                if (maxPartitionDescription - curDateDay < addPartition) {
                    int addDay = addPartition
                            - (maxPartitionDescription - curDateDay);
                    ResultSet rsAdd = stmt
                            .executeQuery(getMaxPartitionSql(tableName));
                    if (rsAdd.next()) {
                        String partitionName = rsAdd.getString(PARTITION_NAME);
                        int partitionDescription = rsAdd
                                .getInt(PARTITION_DESCRIPTION);

                        int pId = Integer.parseInt(partitionName.substring(1));
                        pId++;
                        partitionDescription++;

                        for (int j = 0; j < addDay; j++) {
                            String addSql = getAddPartitionSql(tableName, pId
                                    + j, partitionDescription + j);
                            stmt.addBatch(addSql);
                            log.info("添加新分区name:" + (pId + j) + ",分区值:"
                                    + (partitionDescription + j));
                        }

                        stmt.executeBatch();
                        conn.commit();
                        rsAdd.close();
                        log.info("执行" + tableName + "添加分区成功，添加分区数：" + addDay);
                    } else {
                        rsAdd.close();
                        log.error("检查并添加" + tableName + "表分区失败！添加分区时，没有分区大小信息。");
                    }
                }
            } else {
                log.error("检查并添加" + tableName + "表分区失败！该表不是分区表，没有分区大小信息。");
            }
            log.info("检查并添加" + tableName + "表分区完成！");
        } catch (Exception e) {
            log.error("执行" + tableName + "维护失败！error message:" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
                stmt.close();
                rs.close();
            } catch (Exception e) {
                log.error("关闭数据库连接错误.", e);
            }
        }
    }

    /**
     * 检查并删除老分区
     */
    public void checkAndDeleteOldPartition(int curDateDay, String tableName,
                                           int saveDay) {
        log.info("开始检查并删除表" + tableName + "老分区...");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            // 获取最大和最小分区数量信息
            rs = stmt.executeQuery(getMaxAndMinPartitionSql(tableName));

            if (rs.next()) {
                int minPartitionDescription = rs
                        .getInt("MIN_PARTITION_DESCRIPTION");

                // 判断是否需要删除老分区
                if (curDateDay - minPartitionDescription > saveDay) {
                    ResultSet rsMin = stmt
                            .executeQuery(getMinPartitionSql(tableName));
                    if (rsMin.next()) {
                        String minName = rsMin.getString(PARTITION_NAME);
                        int minDescription = rsMin
                                .getInt(PARTITION_DESCRIPTION);
                        if (minDescription != minPartitionDescription) {
                            rsMin.close();
                            log.error("检查并删除表" + tableName
                                    + "老分区失败！删除分区时，最小分区信息不同。");
                            rsMin.close();
                            return;
                        }

                        int pId = Integer.parseInt(minName.substring(1));

                        int delDay = curDateDay - minPartitionDescription
                                - saveDay;

                        for (int j = 0; j < delDay; j++) {
                            stmt.addBatch(getDelPartitionSql(tableName, "p"
                                    + (pId + j)));
                            log.info("删除分区名称:p" + (pId + j));
                        }
                        stmt.executeBatch();
                        conn.commit();
                        rsMin.close();
                    } else {
                        rsMin.close();
                        log.error("检查并删除表" + tableName
                                + "老分区失败！删除分区时，没有最小分区信息。");
                    }
                }
                log.info("检查并删除表" + tableName + "老分区完成。");
            } else {
                log.error("检查并删除表" + tableName + "老分区失败！该表不是分区表，没有分区大小信息。");
            }
        } catch (Exception e) {
            log.error("检查并删除表" + tableName + "老分区失败！error message:"
                    + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
                stmt.close();
                rs.close();
            } catch (Exception e) {
                log.error("关闭数据库连接错误.", e);
            }
        }
    }

    /**
     * 获取当前数据库天数
     */
    public int getDateBaseDay() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT to_days(CURDATE())");
            int curDateDay;
            if (rs.next()) {
                curDateDay = rs.getInt(1);
                return curDateDay;
            } else {
                log.error("执行分区管理任务失败！获取当前日期时间错误.........");
                return -1;
            }
        } catch (SQLException e) {
            log.error("执行分区管理任务失败！errorMessage=" + e.getMessage());
            return -1;
        } finally {
            try {
                conn.close();
                stmt.close();
                rs.close();
            } catch (Exception e) {
                log.error("getDateBaseDay,关闭数据库连接错误.", e);
            }
        }
    }

    /**
     * 检查并删除最大分区值
     */
    public void checkAndDeleteMaxPartitionValue(String tableName) {
        log.info("开始检查并删除" + tableName + "表最大分区...");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            // 查询当前分区的最大分区和分区ID
            String getMaxSql = getMaxPartitionSql(tableName);
            rs = stmt.executeQuery(getMaxSql);

            if (rs.next()) {
                String partitionName = rs.getString(PARTITION_NAME);
                String partitionDescription = rs
                        .getString(PARTITION_DESCRIPTION);

                log.info("当前最大分区名称:" + partitionName + ",最大分区值:"
                        + partitionDescription);

                // 删除最大分区值
                if (partitionDescription.equals("MAXVALUE")) {
                    stmt.executeUpdate(getDelPartitionSql(tableName,
                            partitionName));
                    log.info("删除" + partitionName + "分区。");
                }
                log.info("检查并删除" + tableName + "表最大分区完成。");
            } else {
                log.error("执行" + tableName + "检查并删除最大分区值失败！该表不是分区表，没有分区信息。");
            }
        } catch (Exception e) {
            log.error("执行" + tableName + "检查并删除最大分区值失败！错误信息:" + e.getMessage());
        } finally {
            try {
                conn.close();
                stmt.close();
                rs.close();
            } catch (Exception e) {
                log.error("checkAndDeleteMaxPartitionValue,关闭数据库连接错误.", e);
            }
        }
    }

    /**
     * 获取最大分区sql
     */
    public static String getMaxPartitionSql(String tableName) {
        StringBuilder sb = new StringBuilder(
                "SELECT PARTITION_NAME,PARTITION_DESCRIPTION  FROM INFORMATION_SCHEMA.PARTITIONS WHERE TABLE_NAME = '");
        sb.append(tableName);
        sb.append("' AND TABLE_SCHEMA = database() ORDER BY PARTITION_ORDINAL_POSITION DESC LIMIT 1");
        return sb.toString();
    }

    /**
     * 获取最小分区sql
     */
    public static String getMinPartitionSql(String tableName) {
        StringBuilder sb = new StringBuilder(
                "SELECT PARTITION_NAME,PARTITION_DESCRIPTION  FROM INFORMATION_SCHEMA.PARTITIONS WHERE TABLE_NAME = '");
        sb.append(tableName);
        sb.append("' AND TABLE_SCHEMA = database() ORDER BY PARTITION_ORDINAL_POSITION LIMIT 1");
        return sb.toString();
    }

    /**
     * 获取增加分区sql
     */
    public static String getAddPartitionSql(String tableName, int pId,
                                            int partitionValue) {
        StringBuilder sb = new StringBuilder("ALTER TABLE ");
        sb.append(tableName);
        sb.append(" ADD PARTITION (PARTITION p");
        sb.append(pId);
        sb.append(" VALUES LESS THAN (");
        sb.append(partitionValue);
        sb.append("))");
        return sb.toString();
    }

    /**
     * 获取删除分区sql
     */
    public static String getDelPartitionSql(String tableName,
                                            String partitionName) {
        StringBuilder sb = new StringBuilder("ALTER TABLE ");
        sb.append(tableName);
        sb.append("  DROP PARTITION " + partitionName);
        return sb.toString();
    }

    /**
     * 获取最大和最小分区信息sql
     */
    public static String getMaxAndMinPartitionSql(String tableName) {
        StringBuilder sb = new StringBuilder(
                "SELECT MAX(PARTITION_DESCRIPTION) MAX_PARTITION_DESCRIPTION,MIN(PARTITION_DESCRIPTION) AS MIN_PARTITION_DESCRIPTION FROM INFORMATION_SCHEMA.PARTITIONS WHERE TABLE_NAME = '");
        sb.append(tableName);
        sb.append("' AND TABLE_SCHEMA = DATABASE()");
        return sb.toString();
    }

}
