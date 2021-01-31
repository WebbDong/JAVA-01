package lesson06;

/**
 * @author Webb Dong
 * @description: 生产者消费者模式
 * @date 2021-01-31 12:21
 */
public class ProducerAndConsumer {

    private static final int MAX_PRODUCE_COUNT = 50;

    private static int count;

    public static void main(String[] args) {
        final Object lockObj = new Object();
        new Thread(() -> {
            while (true) {
                synchronized (lockObj) {
                    if (count <= MAX_PRODUCE_COUNT) {
                        System.out.println("生产商品" + count++);
                        lockObj.notifyAll();
                    } else {
                        System.out.println("商品生产完毕");
                        try {
                            lockObj.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, "ProducerThread").start();

        new Thread(() -> {
            while (true) {
                synchronized (lockObj) {
                    if (count > 0) {
                        System.out.println("消费商品" + --count);
                        lockObj.notifyAll();
                    } else {
                        System.out.println("商品消费完");
                        try {
                            lockObj.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, "ConsumerThread").start();
    }

}
