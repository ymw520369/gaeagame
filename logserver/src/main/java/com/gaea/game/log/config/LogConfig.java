package com.gaea.game.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2017/9/21.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "logconfig")
public class LogConfig {
    public String mqName;
    public String recordPath;
    public String sampleFile;
    public boolean updateTable;
    public String hdfsConfig;

    public String getMqName() {
        return mqName;
    }

    public void setMqName(String mqName) {
        this.mqName = mqName;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public String getSampleFile() {
        return sampleFile;
    }

    public void setSampleFile(String sampleFile) {
        this.sampleFile = sampleFile;
    }

    public boolean isUpdateTable() {
        return updateTable;
    }

    public void setUpdateTable(boolean updateTable) {
        this.updateTable = updateTable;
    }

    public String getHdfsConfig() {
        return hdfsConfig;
    }

    public void setHdfsConfig(String hdfsConfig) {
        this.hdfsConfig = hdfsConfig;
    }
}
