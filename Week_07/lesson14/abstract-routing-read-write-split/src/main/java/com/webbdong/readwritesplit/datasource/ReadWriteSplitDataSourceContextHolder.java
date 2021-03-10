package com.webbdong.readwritesplit.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 读写分离数据源上下文
 * @author Webb Dong
 * @date 2021-03-10 2:12 PM
 */
public class ReadWriteSplitDataSourceContextHolder {

    /**
     * 存放当前线程使用的数据源类型信息
     */
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    private static final List<String> slaveNameList = new ArrayList<>();

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 设置当前数据源
     * @param dataSourceName
     */
    public static void setCurrentTargetDataSource(String dataSourceName) {
        HOLDER.set(dataSourceName);
    }

    public static void setCurrentTargetDataSourceByAlgorithm() {
        HOLDER.set(getSlaveName());
    }

    /**
     * 获取当前数据源
     */
    public static String getCurrentTargetDataSource() {
        return HOLDER.get();
    }

    /**
     * 清除当前数据源
     */
    public static void clearCurrentTargetDataSource() {
        HOLDER.remove();
    }

    public static void addSlaveName(String slaveName) {
        slaveNameList.add(slaveName);
    }

    public static String getSlaveName() {
        // 轮询算法获取从库
        if (atomicInteger.get() >= slaveNameList.size()) {
            atomicInteger.set(0);
        }
        return slaveNameList.get(atomicInteger.getAndIncrement());
    }

}
