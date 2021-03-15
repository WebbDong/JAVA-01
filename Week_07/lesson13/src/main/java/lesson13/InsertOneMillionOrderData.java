package lesson13;

import lombok.SneakyThrows;
import lesson13.util.HikaricpUtils;
import lesson13.util.InsertCommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
 *
 * @author Webb Dong
 * @date 2021-03-06 12:39 AM
 */
public class InsertOneMillionOrderData {

    private static final int RECORD_COUNT = 1000000;

    /**
     * 订单表一共 32 个字段
     */
    private static final int ORDER_TABLE_COLUMN_COUNT = 32;

    /**
     * 1、使用多个 values 进行批量插入方式
     *
     * 插入策略: 每 10000 条一个批次，条数到 10000 时，提交并插入一批
     *      rewriteBatchedStatements 关闭情况: 耗时 30561 ms
     *      rewriteBatchedStatements 开启情况: 耗时 34773 ms
     *
     * 插入策略: 每 20000 条一个批次，条数到 20000 时，提交并插入一批
     *      rewriteBatchedStatements 关闭情况: 耗时 30844 ms
     *      rewriteBatchedStatements 开启情况: 耗时 35334 ms
     */
    @SneakyThrows
    private static void way1() {
        // INSERT 一个批次的条数
        final int INSERT_BATCH_COUNT = 20000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        for (int i = 1; i <= RECORD_COUNT; i++) {
            InsertCommonUtils.fillOrderParamList(paramList, i);
            if (i % INSERT_BATCH_COUNT == 0) {
                HikaricpUtils.INSTANCE.executeBatchInsertByValues(
                        InsertCommonUtils.INSERT_INTO_SQL, INSERT_BATCH_COUNT,
                        ORDER_TABLE_COLUMN_COUNT, paramList);
                paramList.clear();
            }
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 2、使用批量插入方式
     *
     * 插入策略: 每 10000 条一个批次，条数到 10000 时，提交并插入一批
     *      rewriteBatchedStatements 关闭情况: 速度过慢，5-8分钟只插入 10 万条左右数据
     *      rewriteBatchedStatements 开启情况: 耗时 34420 ms
     *
     * 插入策略: 每 20000 条一个批次，条数到 20000 时，提交并插入一批
     *      rewriteBatchedStatements 开启情况: 耗时 36422 ms
     */
    @SneakyThrows
    private static void way2() {
        // INSERT 一个批次的条数
        final int INSERT_BATCH_COUNT = 20000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        for (int i = 1; i <= RECORD_COUNT; i++) {
            InsertCommonUtils.fillOrderParamList(paramList, i);
            if (i % INSERT_BATCH_COUNT == 0) {
                HikaricpUtils.INSTANCE.executeBatch(
                        InsertCommonUtils.INSERT_INTO_VALUES_SQL, ORDER_TABLE_COLUMN_COUNT,
                        paramList, INSERT_BATCH_COUNT);
                paramList.clear();
            }
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 3、使用批量插入方式 + 手动控制事务
     *
     * 插入策略: 每 10000 条一个批次，条数到 10000 时，提交并插入一批
     *      rewriteBatchedStatements 开启情况: 耗时 29202 ms
     *
     * 插入策略: 每 20000 条一个批次，条数到 20000 时，提交并插入一批
     *      rewriteBatchedStatements 开启情况: 耗时 29798 ms
     */
    @SneakyThrows
    private static void way3() {
        final int INSERT_BATCH_COUNT = 20000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(InsertCommonUtils.INSERT_INTO_VALUES_SQL);
            for (int i = 1; i <= RECORD_COUNT; i++) {
                InsertCommonUtils.fillOrderParamList(paramList, i);
                if (i % INSERT_BATCH_COUNT == 0) {
                    HikaricpUtils.INSTANCE.executeBatchByManualTransaction(
                            stmt, ORDER_TABLE_COLUMN_COUNT, paramList, INSERT_BATCH_COUNT);
                    paramList.clear();
                }
            }
            conn.commit();
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 4、使用多个 values 进行批量插入方式 + 手动控制事务 + rewriteBatchedStatements=true&max_allowed_packet=10M
     *
     * 插入策略: 每 50000 条一个批次，条数到 50000 时，提交并插入一批，耗时: 28741 ms
     *
     * 插入策略: 每 100000 条一个批次，条数到 100000 时，提交并插入一批，耗时: 31102 ms
     */
    @SneakyThrows
    private static void way4() {
        // INSERT 一个批次的条数
        final int INSERT_BATCH_COUNT = 100000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            conn.setAutoCommit(false);
            String fullSql = HikaricpUtils.INSTANCE.generateFullInsertByValuesSql(
                    InsertCommonUtils.INSERT_INTO_SQL, INSERT_BATCH_COUNT, ORDER_TABLE_COLUMN_COUNT);
            PreparedStatement stmt = conn.prepareStatement(fullSql);
            for (int i = 1; i <= RECORD_COUNT; i++) {
                InsertCommonUtils.fillOrderParamList(paramList, i);
                if (i % INSERT_BATCH_COUNT == 0) {
                    HikaricpUtils.INSTANCE.executeBatchInsertByValuesAndManualTransaction(stmt, paramList);
                    paramList.clear();
                }
            }
            conn.commit();
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 5、使用批量插入方式 + 手动控制事务 + rewriteBatchedStatements=true&max_allowed_packet=10M
     *
     * 插入策略: 每 50000 条一个批次，条数到 50000 时，提交并插入一批，耗时: 29961 ms
     *
     * 插入策略: 每 100000 条一个批次，条数到 100000 时，提交并插入一批，耗时:  ms
     */
    @SneakyThrows
    private static void way5() {
//        final int INSERT_BATCH_COUNT = 50000;
        final int INSERT_BATCH_COUNT = 100000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(InsertCommonUtils.INSERT_INTO_VALUES_SQL);
            for (int i = 1; i <= RECORD_COUNT; i++) {
                InsertCommonUtils.fillOrderParamList(paramList, i);
                if (i % INSERT_BATCH_COUNT == 0) {
                    HikaricpUtils.INSTANCE.executeBatchByManualTransaction(
                            stmt, ORDER_TABLE_COLUMN_COUNT, paramList, INSERT_BATCH_COUNT);
                    paramList.clear();
                }
            }
            conn.commit();
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 6、使用批量插入方式 + 手动控制事务并且分批提交事务 + rewriteBatchedStatements=true&max_allowed_packet=10M
     *
     * 插入策略: 每 50000 条一个批次，条数到 50000 时，插入并提交一批事务，耗时: 29090 ms
     *
     * 插入策略: 每 100000 条一个批次，条数到 100000 时，插入并提交一批事务，耗时: 30546 ms
     */
    @SneakyThrows
    private static void way6() {
//        final int INSERT_BATCH_COUNT = 50000;
        final int INSERT_BATCH_COUNT = 100000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(InsertCommonUtils.INSERT_INTO_VALUES_SQL);
            for (int i = 1; i <= RECORD_COUNT; i++) {
                InsertCommonUtils.fillOrderParamList(paramList, i);
                if (i % INSERT_BATCH_COUNT == 0) {
                    HikaricpUtils.INSTANCE.executeBatchByManualTransaction(
                            stmt, ORDER_TABLE_COLUMN_COUNT, paramList, INSERT_BATCH_COUNT);
                    conn.commit();
                    paramList.clear();
                }
            }
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 7、使用批量插入方式 + 手动控制事务并且分批提交事务 + useServerPrepStmts=false&rewriteBatchedStatements=true&max_allowed_packet=10M
     *
     * 插入策略: 每 100000 条一个批次，条数到 100000 时，插入并提交一批事务，耗时: 30823 ms
     */
    private static void way7() {
        way6();
    }

    /**
     * 8、使用批量插入方式 + 手动控制事务并且分批提交事务 + 多线程
     *
     * 插入策略: 100万数据分成八份，每个线程处理 125000 条数据，每个线程单独一个数据库连接，耗时: 15262 ms
     */
    @SneakyThrows
    private static void way8() {
        final int THREAD_BATCH_COUNT = 8;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                THREAD_BATCH_COUNT,
                THREAD_BATCH_COUNT * 2,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8),
                new ThreadPoolExecutor.AbortPolicy());

        final int THREAD_BATCH_RECORD_COUNT = RECORD_COUNT / THREAD_BATCH_COUNT;
        final int BATCH_COUNT = 50000;
        long start = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_BATCH_COUNT, () -> {
            System.out.println("耗时: " + (System.currentTimeMillis() - start));
            executor.shutdown();
        });
        for (int i = 0; i < THREAD_BATCH_COUNT; i++) {
            final int s = i;
            executor.execute(() -> {
                try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
                    conn.setAutoCommit(false);
                    try (PreparedStatement stmt = conn.prepareStatement(InsertCommonUtils.INSERT_INTO_VALUES_SQL)) {
                        for (int j = THREAD_BATCH_RECORD_COUNT * s; j < ((s + 1) * THREAD_BATCH_RECORD_COUNT); j++) {
                            InsertCommonUtils.setStmt(stmt, j);
                            stmt.addBatch();
                            if (j % BATCH_COUNT == 0) {
                                stmt.executeBatch();
                            }
                        }
                        stmt.executeBatch();
                    }
                    conn.commit();
                    cyclicBarrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void main(String[] args) {
//        way1();
//        way2();
//        way3();
//        way4();
//        way5();
//        way6();
//        way7();
        way8();
    }

}
