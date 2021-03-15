package com.webbdong.sharding.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Webb Dong
 * @date 2021-03-15 3:09 PM
 */
@Component
@Data
public class DataSourceProperties {

    @Value("${spring.shardingsphere.datasource.orderds0.jdbc-url}")
    private String orderds0JdbcUrl;

    @Value("${spring.shardingsphere.datasource.orderds0.username}")
    private String orderds0Username;

    @Value("${spring.shardingsphere.datasource.orderds0.password}")
    private String orderds0Password;

    @Value("${spring.shardingsphere.datasource.orderds1.jdbc-url}")
    private String orderds1JdbcUrl;

    @Value("${spring.shardingsphere.datasource.orderds1.username}")
    private String orderds1Username;

    @Value("${spring.shardingsphere.datasource.orderds1.password}")
    private String orderds1Password;

    @Value("${spring.shardingsphere.datasource.orderds2.jdbc-url}")
    private String orderds2JdbcUrl;

    @Value("${spring.shardingsphere.datasource.orderds2.username}")
    private String orderds2Username;

    @Value("${spring.shardingsphere.datasource.orderds2.password}")
    private String orderds2Password;

    @Value("${spring.shardingsphere.datasource.orderds3.jdbc-url}")
    private String orderds3JdbcUrl;

    @Value("${spring.shardingsphere.datasource.orderds3.username}")
    private String orderds3Username;

    @Value("${spring.shardingsphere.datasource.orderds3.password}")
    private String orderds3Password;

}
