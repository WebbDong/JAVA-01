package com.webbdong.readwritesplit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置
 * @author: Webb Dong
 * @date 2021-03-07 11:45 PM
 */
@Configuration
@MapperScan("com.webbdong.readwritesplit.mapper")
public class MyBatisConfig {
}
