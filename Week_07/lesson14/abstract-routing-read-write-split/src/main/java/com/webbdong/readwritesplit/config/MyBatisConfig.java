package com.webbdong.readwritesplit.config;

import com.webbdong.readwritesplit.datasource.ReadWriteSplitDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * MyBatis配置
 * @author: Webb Dong
 * @date 2021-03-07 11:45 PM
 */
@Configuration
@Import(ReadWriteSplitDataSourceRegister.class)
@MapperScan("com.webbdong.readwritesplit.mapper")
public class MyBatisConfig {
}
