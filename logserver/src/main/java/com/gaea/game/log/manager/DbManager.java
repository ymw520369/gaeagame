/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2016年6月21日
 */
package com.gaea.game.log.manager;

import com.gaea.game.log.config.LogConfig;
import com.gaea.game.log.record.Column;
import com.gaea.game.log.record.Record;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.alan.utils.xml.ClassNameXmlContext;
import org.alan.utils.xml.XmlContext;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库管理器
 *
 * @author Alan
 * @scene 1.0
 */
//@Controller
public class DbManager extends FileAlterationListenerAdaptor implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 数据源
     */
    @Resource(name = "dataSource")
    private ComboPooledDataSource dataSource;

    @Autowired
    private LogConfig logConfig;
    /**
     * 文件监视器
     */
    @Autowired
    private FileAlterationMonitor monitor;

    @Override
    public void run(String... strings) throws Exception {
        String samplePath = logConfig.sampleFile;
        log.info("begin to load sample files...");
        if (samplePath == null) {
            log.warn("注意: 无法加载log sample file , samplePath 为空");
            return;
        }
        loadSampleFile(samplePath);
        FileAlterationObserver observer = new FileAlterationObserver(samplePath);
        observer.addListener(this);
        observer.initialize();
        monitor.addObserver(observer);
    }

    @Override
    public void onFileChange(File file) {
        log.info("on file change filename = {}", file.getName());
        loadSampleFile(file.getName());
    }

    @Override
    public void onFileCreate(File file) {
        log.info("on file create filename = {}", file.getName());
        loadSampleFile(file.getName());
    }

    public void loadSampleFile(String logSampleFile) {
        XmlContext context = new ClassNameXmlContext(null);
        context.setClassLoader(getClass().getClassLoader());
        context.set("context", context);
        context.parse(logSampleFile);
        Record.allRecords().forEach(this::checkTable);
    }

    public void checkTable(Record record) {
        Connection conn = null;
        Statement stmt = null;
        String sql = null;
        ResultSet rs = null;
        String tableName = record.getDest();
        log.info("检查表结构[" + tableName + "]");
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            // ComboPooledDataSource cpDataSource = (ComboPooledDataSource) dataSource;
            String[] ss = dataSource.getJdbcUrl().split("/");
            String databaseName = ss[ss.length - 1];
            // 判断表是否存在
            sql = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='"
                    + tableName + "' AND TABLE_SCHEMA='" + databaseName + "'";
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                // 建立分区表
                if (record.getCreatePartition() > 0) {
                    sql = record.getCreatePartitionSql();
                    log.info("表[" + tableName + "]不存在，准备创建分区表, SQL=" + sql);
                } else {
                    sql = record.getCreateSql();
                    log.info("表[" + tableName + "]不存在，准备创建表, SQL=" + sql);
                }
                stmt.execute(sql);
            } else {
                sql = "SHOW COLUMNS FROM " + tableName + "";
                rs.close();
                rs = stmt.executeQuery(sql);
                Map<String, String> columns = new HashMap<>();
                while (rs.next()) {
                    columns.put(rs.getString("field"), rs.getString("type"));
                }
                for (Column e : record.getColumnMap().values()) {
                    String type = columns.remove(e.name);
                    if (type == null) {

                        // 如果列不存在，添加列
                        sql = "ALTER TABLE `" + tableName + "` ADD `" + e.name
                                + "` " + e.getSqlColumnType();
                        log.info("找不到列[" + e.name + "],准备添加,SQL=" + sql);
                        stmt.addBatch(sql);
                    } else {
                        // 如果type类型不一致，更新字段
                        if (logConfig.updateTable && !e.isSameType(type)) {
                            sql = "ALTER TABLE `" + tableName + "` CHANGE `"
                                    + e.name + "` `" + e.name + "` "
                                    + e.getSqlColumnType();
                            log.info("列类型不一致[" + e.name + "],准备修改列类型,SQL="
                                    + sql);
                            stmt.addBatch(sql);
                        }
                    }
                }
                // 删除已经被废弃的字段
                if (!columns.isEmpty()) {
                    for (String e : columns.keySet()) {
                        sql = "ALTER TABLE `" + tableName + "` DROP `" + e
                                + "`";
                        log.info("无效的列[" + e + "],准备删除,SQL=" + sql);
                        stmt.addBatch(sql);
                    }
                }
                int[] ii = stmt.executeBatch();
                log.info("检查完毕[" + tableName + "]，执行结果=" + Arrays.toString(ii));
                conn.commit();
            }
        } catch (Exception e) {
            log.warn("检查数据表错误.", e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                log.warn("数据库回滚失败.", e1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
                stmt.close();
                rs.close();
            } catch (Exception e) {
                log.warn("关闭数据库连接错误.", e);
            }
        }
    }

    public void insert(Record record) {
        Connection conn = null;
        Statement stmt = null;
        String sql = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            sql = record.getInsertSql();
            log.info(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            log.warn("数据插入失败，回滚数据.", e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                log.warn("数据插入回滚失败.", e1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
                stmt.close();
            } catch (Exception e) {
                log.warn("关闭数据库连接错误.", e);
            }
        }
    }

    /**
     * 预编译式插入
     */
    public void insertByPrepared(Record record) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        String prepareSql = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            prepareSql = record.getInsertPrepareSql();
            sql = record.getInsertSql();
            stmt = conn.prepareStatement(prepareSql);
            record.setInsertPrepareValue(stmt);
            log.info(sql);
            stmt.execute();
        } catch (Exception e) {
            log.warn("数据插入失败，回滚数据.", e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                log.warn("数据插入回滚失败.", e1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
                stmt.close();
            } catch (Exception e) {
                log.warn("关闭数据库连接错误.", e);
            }
        }
    }
}
