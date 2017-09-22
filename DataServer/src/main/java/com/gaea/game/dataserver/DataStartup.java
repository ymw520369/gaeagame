package com.gaea.game.dataserver;

import com.gaea.game.core.dao.RoleDao;
import com.gaea.game.core.dao.impl.RedisRoleDaoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2017/8/22.
 *
 * @author Alan
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("com.gaea")
public class DataStartup {
    public static void main(String[] args) {
        SpringApplication.run(DataStartup.class, args);
    }

    @Bean("redisRoleDao")
    public RoleDao roleDao() {
        return new RedisRoleDaoImpl();
    }
}
