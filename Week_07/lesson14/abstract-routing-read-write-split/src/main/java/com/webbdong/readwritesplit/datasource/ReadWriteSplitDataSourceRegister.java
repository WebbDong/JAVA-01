package com.webbdong.readwritesplit.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ReadWriteSplitDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    /**
     * 主库数据源
     */
    private DataSource masterDataSource;

    /**
     * 从库数据源列表
     */
    private Map<String, DataSource> slaveDataSources;

    @Override
    public void setEnvironment(Environment environment) {
        initMasterDataSource(environment);
        initSlaveDataSources(environment);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry beanDefinitionRegistry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ReadWriteSplitDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", masterDataSource);
        mpv.addPropertyValue("targetDataSources", slaveDataSources);
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);

        log.info("Dynamic DataSource Registry");
    }

    /**
     * 初始化从库
     * @param environment
     */
    private void initSlaveDataSources(Environment environment) {
        // 读取配置文件获取更多数据源
        String slaveDataSourceNames = environment.getProperty("spring.masterslave.slave-data-source-names");
        if (slaveDataSourceNames != null) {
            String[] slaveNames = slaveDataSourceNames.split(",");
            slaveDataSources = new HashMap<>(slaveNames.length);
            for (String slaveName : slaveNames) {
                DataSource ds = buildDataSource(environment, "spring.datasource." + slaveName);
                slaveDataSources.put(slaveName, ds);
                ReadWriteSplitDataSourceContextHolder.addSlaveName(slaveName);
            }
        }
    }

    /**
     * 初始化主库
     * @param environment
     */
    private void initMasterDataSource(Environment environment) {
        String masterDataSourceName = environment.getProperty("spring.masterslave.master-data-source-name");
        if (masterDataSourceName != null) {
            masterDataSource = buildDataSource(environment, "spring.datasource." + masterDataSourceName);
        }
    }

    /**
     * 构建数据源
     * @param environment
     * @param prefix
     * @return
     */
    public DataSource buildDataSource(Environment environment, String prefix) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(environment.getProperty(prefix + ".driver-class-name"));
        hikariDataSource.setJdbcUrl(environment.getProperty(prefix + ".url"));
        hikariDataSource.setUsername(environment.getProperty(prefix + ".username"));
        hikariDataSource.setPassword(environment.getProperty(prefix + ".password"));
        String connectionTimeout = environment.getProperty(prefix + ".connection-timeout");
        if (connectionTimeout != null) {
            hikariDataSource.setMaximumPoolSize(Integer.valueOf(connectionTimeout));
        }
        String maximumPoolSize = environment.getProperty(prefix + ".maximum-pool-size");
        if (maximumPoolSize != null) {
            hikariDataSource.setMinimumIdle(Integer.valueOf(maximumPoolSize));
        }
        String minimumIdle = environment.getProperty(prefix + ".minimum-idle");
        if (minimumIdle != null) {
            hikariDataSource.setMinimumIdle(Integer.valueOf(minimumIdle));
        }
        hikariDataSource.setConnectionTestQuery(environment.getProperty(prefix + ".connection-test-query"));
        return hikariDataSource;
    }

}
