package com.webbdong.dynamicdatasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册动态数据源
 * @author: Webb Dong
 * @date 2021-03-07 11:45 PM
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    /**
     * 默认数据源
     */
    private DataSource defaultDataSource;

    /**
     * 用户自定义数据源
     */
    private Map<String, DataSource> slaveDataSources = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        initDefaultDataSource(environment);
        initslaveDataSources(environment);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object, Object> targetDataSources = new HashMap<>(6);
        // 添加默认数据源
        targetDataSources.put("dataSource", this.defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        // 添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        for (String key : slaveDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }

        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        // 注册 - BeanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);

        logger.info("Dynamic DataSource Registry");
    }

    /**
     * 初始化从库
     * @param environment
     */
    private void initslaveDataSources(Environment environment) {
        // 读取配置文件获取更多数据源
        String dsPrefixs = environment.getProperty("spring.datasource.slave.names");
        if (dsPrefixs != null) {
            for (String dsPrefix : dsPrefixs.split(",")) {
                // 多个数据源
                Map<String, Object> dsMap = new HashMap<>(4);
                dsMap.put("driver", environment.getProperty("spring.datasource.slave." + dsPrefix + ".driver-class-name"));
                dsMap.put("url", environment.getProperty("spring.datasource.slave." + dsPrefix + ".url"));
                dsMap.put("username", environment.getProperty("spring.datasource.slave." + dsPrefix + ".username"));
                dsMap.put("password", environment.getProperty("spring.datasource.slave." + dsPrefix + ".password"));
                dsMap.put("connectionTimeout", environment.getProperty("spring.datasource.slave." + dsPrefix + ".connection-timeout"));
                dsMap.put("maxPoolSize", environment.getProperty("spring.datasource.slave." + dsPrefix + ".maximum-pool-size"));
                dsMap.put("minIdle", environment.getProperty("spring.datasource.slave." + dsPrefix + ".minimum-idle"));
                dsMap.put("connectionTestQuery", environment.getProperty("spring.datasource.slave." + dsPrefix + ".connection-test-query"));
                DataSource ds = buildDataSource(dsMap);
                slaveDataSources.put(dsPrefix, ds);
            }
        }
    }

    /**
     * 初始化主库
     * @param environment
     */
    private void initDefaultDataSource(Environment environment) {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>(4);
        dsMap.put("driver", environment.getProperty("spring.datasource.driver-class-name"));
        dsMap.put("url", environment.getProperty("spring.datasource.url"));
        dsMap.put("username", environment.getProperty("spring.datasource.username"));
        dsMap.put("password", environment.getProperty("spring.datasource.password"));
        dsMap.put("connectionTimeout", environment.getProperty("spring.datasource.connection-timeout"));
        dsMap.put("maxPoolSize", environment.getProperty("spring.datasource.maximum-pool-size"));
        dsMap.put("minIdle", environment.getProperty("spring.datasource.minimum-idle"));
        dsMap.put("connectionTestQuery", environment.getProperty("spring.datasource.connection-test-query"));
        defaultDataSource = buildDataSource(dsMap);
    }

    /**
     * 构建数据源
     * @param dataSourceMap
     * @return
     */
    public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(dataSourceMap.get("driver").toString());
        hikariDataSource.setJdbcUrl(dataSourceMap.get("url").toString());
        hikariDataSource.setUsername(dataSourceMap.get("username").toString());
        hikariDataSource.setPassword(dataSourceMap.get("password").toString());
        if (dataSourceMap.get("maxPoolSize") != null) {
            hikariDataSource.setMaximumPoolSize(Integer.valueOf(dataSourceMap.get("maxPoolSize").toString()));
        }
        if (dataSourceMap.get("minIdle") != null) {
            hikariDataSource.setMinimumIdle(Integer.valueOf(dataSourceMap.get("minIdle").toString()));
        }
        return hikariDataSource;
    }

}
