package lesson13;

import lesson13.util.HikaricpUtils;
import lesson13.util.InsertCommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 按自己设计的表结构，插入1000万订单模拟数据，测试不同方式的插入效率。
 *
 * @author Webb Dong
 * @date 2021-03-07 10:42 AM
 */
public class InsertTenMillionOrderData {

    private static final int RECORD_COUNT = 10000000;

    /**
     * 每 50000 条一个批次，耗时: 177080 ms
     * 每 100000 条一个批次，耗时: 179366 ms
     */
    private static void way1() {
        final int THREAD_BATCH_COUNT = 8;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                THREAD_BATCH_COUNT,
                THREAD_BATCH_COUNT * 2,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8),
                new ThreadPoolExecutor.AbortPolicy());

        final int THREAD_BATCH_RECORD_COUNT = RECORD_COUNT / THREAD_BATCH_COUNT;
        final int BATCH_COUNT = 100000;
        long start = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_BATCH_COUNT, () -> {
            System.out.println("耗时: " + (System.currentTimeMillis() - start));
            executor.shutdown();
        });
        for (int i = 0; i < 8; i++) {
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
        way1();
    }

}
