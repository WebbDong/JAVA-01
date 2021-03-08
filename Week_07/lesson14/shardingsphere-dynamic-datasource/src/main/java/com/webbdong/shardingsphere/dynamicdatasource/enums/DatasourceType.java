package com.webbdong.shardingsphere.dynamicdatasource.enums;

import lombok.Getter;

/**
 * @author Webb Dong
 * @date 2021-03-08 12:18 PM
 */
@Getter
public enum DatasourceType {

    DB1("db1"),

    DB2("db2");

    DatasourceType(String dbName) {
        this.dbName = dbName;
    }

    private String dbName;

}
