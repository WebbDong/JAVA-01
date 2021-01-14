package lesson02.error;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Webb Dong
 * @description: 重写 finalize 方法导致的 OutOfMemoryError
 * @date 2021-01-14 22:09
 */
public class FinalizeCauseOutOfMemoryError {

    private static AtomicInteger aliveCount = new AtomicInteger(0);

    public FinalizeCauseOutOfMemoryError() {
        // 创建对象时计数器加一
        FinalizeCauseOutOfMemoryError.aliveCount.incrementAndGet();
    }

    @Override
    protected void finalize() throws Throwable {
        // 销毁对象时计数器减一
        FinalizeCauseOutOfMemoryError.aliveCount.decrementAndGet();
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; ; i++) {
            FinalizeCauseOutOfMemoryError instance = new FinalizeCauseOutOfMemoryError();
            if ((i % 100_000) == 0) {
                System.out.format("创建了 %d 个对象之后，还存活的对象有 %d 个.%n",
                        i, FinalizeCauseOutOfMemoryError.aliveCount.get());
            }
        }
    }

}
