package com.gaea.game.log.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * Created on 2017/9/21.
 *
 * @author Alan
 * @since 1.0
 */
//@Configuration
@PropertySource(value = "file:config/dbconfig.properties")
public class DataSourceConfig {
    ComboPooledDataSource datasource;
    @Bean(name = "dataSource")
    @Qualifier(value = "dataSource")
    @Primary
    public ComboPooledDataSource dataSource() throws IOException {
        return datasource;
    }
}
