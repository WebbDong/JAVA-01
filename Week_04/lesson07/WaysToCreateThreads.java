package lesson07;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Phaser;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author Webb Dong
 * @description: 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
 * @date 2021-02-03 12:32
 */
public class WaysToCreateThreads {

    private static int sum() {
        return IntStream.range(1, 20).sum();
    }

    // ------------------ 方法一 -------------------

    private static volatile Integer res1;

    private static void method1() {
        new Thread(() -> res1 = sum()).start();

        while (res1 == null)
            ;
        System.out.println("res = " + res1);
    }

    // ------------------ 方法二 -------------------

    private static void method2() throws Exception {
        FutureTask<Integer> task = new FutureTask<>(() -> sum());
        new Thread(task).start();
        Integer res = task.get();
        System.out.println("res = " + res);
    }

    // ------------------ 方法三 -------------------

    private static volatile Integer res2;

    private static void method3() throws Exception {
        final CountDownLatch cdl = new CountDownLatch(1);
        new Thread(() -> {
            res2 = sum();
            cdl.countDown();
        }).start();

        cdl.await();
        System.out.println("res = " + res2);
    }

    // ------------------ 方法四 -------------------

    private static volatile int res3;

    private static void method4() throws InterruptedException {
        Thread t1 = new Thread(() -> res3 = sum());
        t1.start();
        t1.join();
        System.out.println("res = " + res3);
    }

    // ------------------ 方法五 -------------------

    private static volatile int res4;

    private static void method5() throws InterruptedException {
        final Object lockObj = new Object();
        new Thread(() -> {
            synchronized (lockObj) {
                res4 = sum();
                lockObj.notifyAll();
            }
        }).start();

        synchronized (lockObj) {
            lockObj.wait();
            System.out.println("res = " + res4);
        }
    }

    // ------------------ 方法六 -------------------

    private static volatile int res5;

    private static void method6() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final Condition c = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                res5 = sum();
                c.signalAll();
            } finally {
                lock.unlock();
            }
        }).start();

        try {
            lock.lock();
            c.await();
            System.out.println("res = " + res5);
        } finally {
            lock.unlock();
        }
    }

    // ------------------ 方法七 -------------------

    private static void method7() throws ExecutionException, InterruptedException {
        final CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> sum());
        Integer res = cf.get();
        System.out.println("res = " + res);
    }

    // ------------------ 方法八 -------------------

    private static void method8() throws ExecutionException, InterruptedException {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 5, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.AbortPolicy());
        Integer res = executor.submit(() -> sum()).get();
        System.out.println("res = " + res);
        executor.shutdown();
    }

    // ------------------ 方法九 -------------------

    private static volatile int res6;

    private static void method9() throws InterruptedException {
        final Object lockObj = new Object();
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 5, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.AbortPolicy());
        executor.execute(() -> {
            synchronized (lockObj) {
                res6 = sum();
                lockObj.notifyAll();
            }
        });

        synchronized (lockObj) {
            lockObj.wait();
            System.out.println("res = " + res6);
        }

        executor.shutdown();
    }

    // ------------------ 方法十 -------------------

    private static volatile int res7;

    private static void method10() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final Condition c = lock.newCondition();
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 5, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.AbortPolicy());
        executor.execute(() -> {
            try {
                lock.lock();
                res7 = sum();
                c.signalAll();
            } finally {
                lock.unlock();
            }
        });

        try {
            lock.lock();
            c.await();
            System.out.println("res = " + res7);
        } finally {
            lock.unlock();
        }

        executor.shutdown();
    }

    // ------------------ 方法十一 -------------------

    private static void method11() throws InterruptedException {
        final ForkJoinPool fjp = new ForkJoinPool(1);
        final RecursiveTask<Integer> task = new RecursiveTask<Integer>() {

            @Override
            protected Integer compute() {
                return sum();
            }

        };
        Integer res = fjp.invoke(task);
        System.out.println("res = " + res);
    }

    // ------------------ 方法十二 -------------------

    private static void method12() throws ExecutionException, InterruptedException {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                5,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadPoolExecutor.AbortPolicy());

        final CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        Integer res = cs.submit(() -> sum()).get();
        System.out.println("res = " + res);
        executor.shutdown();
    }

    // ------------------ 方法十三 -------------------

    private static void method13() throws InterruptedException {
        final BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(1);
        new Thread(() -> {
            try {
                bq.put(sum());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Integer res = bq.take();
        System.out.println("res = " + res);
    }

    // ------------------ 方法十四 -------------------

    private static volatile int res8;

    private static void method14() throws InterruptedException {
        final Semaphore s = new Semaphore(1);
        new Thread(() -> {
            try {
                s.acquire();
                res8 = sum();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                s.release();
            }
        }).start();

        // main 线程 sleep 一下，确保子线程先拿到信号
        TimeUnit.MILLISECONDS.sleep(20);
        s.acquire();
        System.out.println("res = " + res8);
        s.release();
    }

    // ------------------ 方法十五 -------------------

    private static volatile int res9;

    private static void method15() {
        final Phaser phaser = new Phaser(2);

        new Thread(() -> {
            res9 = sum();
            phaser.arriveAndAwaitAdvance();
        }).start();

        phaser.arriveAndAwaitAdvance();
        System.out.println("res = " + res9);
    }

    // ------------------ 方法十六 -------------------

    private static void method16() throws InterruptedException {
        final Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                exchanger.exchange(sum());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        int res = exchanger.exchange(0);
        System.out.println("res = " + res);
    }

    public static void main(String[] args) throws Exception {
//        method1();
//        method2();
//        method3();
//        method4();
//        method5();
//        method6();
//        method7();
//        method8();
//        method9();
//        method10();
//        method11();
//        method12();
//        method13();
//        method14();
//        method15();
        method16();
    }

}
