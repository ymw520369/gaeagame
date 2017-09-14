package com.gaea.game.log;

import com.alibaba.dubbo.config.annotation.Service;
import com.gaea.game.core.log.ILogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2017/9/8.
 *
 * @author Alan
 * @since 1.0
 */
@Service(version = "1.0.0")
public class LogService implements ILogService {
    Logger log = LoggerFactory.getLogger(LogService.class);

    @Override
    public boolean log(Object logMessage) {
        log.info("收到日志消息，record=" + logMessage);
        return true;
    }
}
