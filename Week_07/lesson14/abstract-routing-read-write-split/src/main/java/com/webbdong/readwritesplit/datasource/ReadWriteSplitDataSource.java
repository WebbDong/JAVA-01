package com.webbdong.readwritesplit.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 读写分离数据源
 * @author Webb Dong
 * @date 2021-03-10 1:55 PM
 */
public class ReadWriteSplitDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ReadWriteSplitDataSourceContextHolder.getCurrentTargetDataSource();
    }

}
