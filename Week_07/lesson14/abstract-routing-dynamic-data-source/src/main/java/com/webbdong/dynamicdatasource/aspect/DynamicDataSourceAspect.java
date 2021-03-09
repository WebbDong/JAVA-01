package com.webbdong.dynamicdatasource.aspect;

import com.webbdong.dynamicdatasource.annotation.TargetDataSource;
import com.webbdong.dynamicdatasource.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 改变数据源
     * @param joinPoint
     * @param targetDataSource
     */
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        String dbid = targetDataSource.name();
        if (DynamicDataSourceContextHolder.isContainsDataSource(dbid)) {
            logger.debug("使用数据源：{}", dbid);
            DynamicDataSourceContextHolder.setDataSourceType(dbid);
        } else {
            // joinPoint.getSignature() ：获取连接点的方法签名对象
            logger.error("数据源:{} 不存在使用默认的数据源 -> {}", dbid, joinPoint.getSignature());
        }
    }

    @After("@annotation(targetDataSource)")
    public void clearDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        logger.debug("清除数据源 " + targetDataSource.name() + " !");
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

}
