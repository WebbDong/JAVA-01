package com.webbdong.shardingsphere.dynamicdatasource.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置
 * @author: Webb Dong
 * @date 2021-03-07 11:45 PM
 */
@Configuration
@MapperScan("com.webbdong.shardingsphere.dynamicdatasource.dao")
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 设置驼峰命名规则
            configuration.setMapUnderscoreToCamelCase(true);
        };
    }

}
