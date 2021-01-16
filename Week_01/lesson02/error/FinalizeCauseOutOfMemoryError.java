package lesson02.error;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Webb Dong
 * @description: 重写 finalize 方法导致的 OutOfMemoryError
 * @date 2021-01-14 22:09
 */
public class FinalizeCauseOutOfMemoryError {

    /**
     * 创建的对象数量计数器
     */
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

    /**
     * 看似 instance 没有被引用，应该会很快被 GC 回收。但是如果重写了 finalize 方法的对象，执行完 GC 之后，GC 会将这些对象 (Finalizer 对象)
     * 放入到 java.lang.ref.Finalizer.ReferenceQueue 队列中。同时有一个 Finalizer 守护线程负责从 java.lang.ref.Finalizer.ReferenceQueue 队列中
     * 取出 Finalizer 对象并且调用它们的 finalize 方法，然后从队列中删除引用。由于 Finalizer 守护线程的优先级低于主线程，所以 CPU 执行它的时间片
     * 会更少，这就导致了 Finalizer 守护线程调用 Finalizer 对象的 finalize 方法然后删除队列中的引用的速度比主线程创建对象的速度慢，来不及
     * 回收对象导致 OutOfMemoryError
     * @param args
     * @throws Exception
     */
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
