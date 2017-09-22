package com.gaea.game.core.log;

import java.util.Map;

/**
 * 日志存储接口
 * <p>
 * Created on 2017/9/7.
 *
 * @author Alan
 * @since 1.0
 */
public interface ILogService {

    boolean log(Map<String, Object> map);

}
