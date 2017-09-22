/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2016年6月23日
 */
package com.gaea.game.log.manager;

import com.gaea.game.log.config.LogConfig;
import com.gaea.game.log.record.Record;
import org.alan.utils.FileHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 文件系统记录管理器
 *
 * @author Alan
 * @scene 1.0
 */
//@Controller
public class RecordManager {

    Logger log = Logger.getLogger(getClass());

    @Autowired
    LogConfig logConfig;
    /**
     * 数据库管理器
     */
    @Autowired
    private DbManager dbManager;

    public void appendToFile(Record record, String sql) {
        String date = record.getColumn("timeDate").value.toString();
        String fileName = record.getDest();

        String file = logConfig.recordPath + File.separator + date + File.separator
                + fileName + ".sql";
        // 记录文件时默认在sql后面加上分号
        FileHelper.saveFile(file, sql + ";\n", true);
    }

    public void addRecord(Record record) {
        dbManager.insertByPrepared(record);
    }
}
