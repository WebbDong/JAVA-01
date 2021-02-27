package lesson06;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
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

    private static ThreadPoolExecutor createProducerPool() {
        // 生产者线程池
        final ThreadPoolExecutor producerPool = new ThreadPoolExecutor(
                5,
                16,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());
        producerPool.setThreadFactory(new ThreadFactory() {

            private int count;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, new StringBuilder("producer-pool-thread-").append(count++).toString());
            }

        });
        return producerPool;
    }

    private static ThreadPoolExecutor createConsumerPool() {
        // 消费者线程池
        final ThreadPoolExecutor consumerPool = new ThreadPoolExecutor(
                5,
                16,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());
        consumerPool.setThreadFactory(new ThreadFactory() {

            private int count;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, new StringBuilder("consumer-pool-thread-").append(count++).toString());
            }

        });
        return consumerPool;
    }

    public static void main(String[] args) {
        final ThreadPoolExecutor producerPool = createProducerPool();
        final ThreadPoolExecutor consumerPool = createConsumerPool();

        /*
        for (int i = 0; i < MAX_PRODUCTION_COUNT; i++) {
            producerPool.execute(() -> {
                longAdder.increment();
                try {
                    String prod = "商品" + longAdder.intValue();
                    blockingQueue.put(prod);
                    System.out.println(Thread.currentThread().getName() + ": 生产" + prod + "成功");
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
                System.out.println(Thread.currentThread().getName() + ": 消费" + prod + "成功");
            });
        }
         */

        producerPool.execute(() -> {
            for (int i = 0; i < MAX_PRODUCTION_COUNT; i++) {
                longAdder.increment();
                try {
                    String prod = "商品" + longAdder.intValue();
                    blockingQueue.put(prod);
                    System.out.println(Thread.currentThread().getName() + ": 生产" + prod + "成功");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        consumerPool.execute(() -> {
            for (int i = 0; i < MAX_PRODUCTION_COUNT; i++) {
                String prod;
                try {
                    prod = blockingQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": 消费" + prod + "成功");
            }
        });

        producerPool.shutdown();
        consumerPool.shutdown();
    }

}
