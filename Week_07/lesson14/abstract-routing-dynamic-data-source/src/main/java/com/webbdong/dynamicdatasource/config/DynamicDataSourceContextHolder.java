package com.webbdong.dynamicdatasource.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态数据源上下文
 * @author: Webb Dong
 * @date 2021-03-07 11:45 PM
 */
public class DynamicDataSourceContextHolder {

    /**
     * 存放当前线程使用的数据源类型信息
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 存放数据源id
     */
    private static List<String> dataSourceIds = new ArrayList<>();

    /**
     * 设置数据源
     * @param dataSourceType
     */
    public static void setDataSourceType(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 判断当前数据源是否存在
     * @param dataSourceId
     * @return
     */
    public static boolean isContainsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

    public static void addDataSourceId(String id) {
        dataSourceIds.add(id);
    }

}
