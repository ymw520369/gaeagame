package com.gaea.game.log;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.gaea.game.core.log.ILogService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

/**
 * Created on 2017/9/8.
 *
 * @author Alan
 * @since 1.0
 */
@Service(version = "1.0.0")
public class LogService implements ILogService {
    Logger log = LoggerFactory.getLogger(LogService.class);

    //@Autowired
    //RecordManager recordManager;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean log(Map<String, Object> map) {
        log.info("收到日志消息，map=" + JSON.toJSONString(map));
        if (map == null || map.isEmpty()) {
            log.warn("消息数据为空");
            return true;
        }
        String dbName = map.get("name").toString();
        DBCollection collection = mongoTemplate.getCollection(dbName);
        BasicDBObject dbObject = new BasicDBObject(map);
        collection.insert(dbObject);
        return true;
    }
}
