package com.nero.demo.configuration;

import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class ShardingDataSourceConfiguration {

    @Bean
    public DataSource dataSource() throws IOException, SQLException {
        return YamlShardingDataSourceFactory.createDataSource(getFile("/ShardingConfig.yaml"));
    }

    private static File getFile(final String fileName) {
        return new File(Thread.currentThread().getClass().getResource(fileName).getFile());
    }
}
