package lesson06;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Webb Dong
 * @description: 生产者消费者模式、引入阻塞任务队列和线程池
 * @date 2021-01-31 15:34
 */
public class ProducerAndConsumer2 {

    private static final int MAX_PRODUCTION_COUNT = 40;

    private static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(MAX_PRODUCTION_COUNT);

    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) {
        // 生产者线程池
        final ExecutorService producerPool = new ThreadPoolExecutor(
                5,
                16,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());

        // 消费者线程池
        final ExecutorService consumerPool = new ThreadPoolExecutor(
                5,
                16,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < MAX_PRODUCTION_COUNT; i++) {
            producerPool.execute(() -> {
                longAdder.increment();
                try {
                    String prod = "商品" + longAdder.intValue();
                    blockingQueue.put(prod);
                    System.out.println("生产" + prod + "成功");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            consumerPool.execute(() -> {
                String prod;
                try {
                    prod = blockingQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("消费" + prod + "成功");
            });
        }

        producerPool.shutdown();
        consumerPool.shutdown();
    }

}
