package com.webbdong.shardingsphere.dynamicdatasource.aspect;

import com.webbdong.shardingsphere.dynamicdatasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切面
 * @author: Webb Dong
 * @date 2021-03-07 11:45 PM
 */
@Aspect
// 保证在@Transactional之前执行
@Order(-1)
@Component
@Slf4j
public class DynamicDataSourceAspect {

    /**
     * 改变数据源
     * @param ds
     */
    @Before("@annotation(ds)")
    public void changeDataSource(DS ds) {
        String dbName = ds.value();
        HintManager.getInstance().setDatabaseShardingValue(dbName);
        log.info("使用数据源 {}", dbName);
    }

    @After("@annotation(ds)")
    public void clearDataSource(DS ds) {
        HintManager.clear();
        log.info("清除数据源 {}", ds.value());
    }

}
