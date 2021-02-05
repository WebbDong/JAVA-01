package lesson06;

/**
 * @author Webb Dong
 * @description: Join
 * @date 2021-01-30 23:21
 */
public class ThreadJoin3 {

    public static void main(String[] args) {
        Object oo = new Object();

        MyThread thread1 = new MyThread("thread1 -- ");
        //oo = thread1;
        thread1.setOo(oo);
        thread1.start();

        // 锁是 thread1 对象
        synchronized (thread1) {
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        // 谁先抢到锁谁先执行，另一个线程就被阻塞，当 main 打印到19后，join 方法让出了 CPU 执行时间片，
                        // thread1 执行完后，main 会继续执行，main 执行完毕后，结束 JVM 进程。
                        // join() 内部调用的就是 wait(0)，当 thread1 线程执行完毕后，JVM 会自动调用 notifyAll
//                        thread1.join();

                        // 如果锁对象是线程对象，那么效果等于 thread1.join()，当 thread1 执行完毕后，JVM 会自动调用
                        // notifyAll 来唤醒其他正在等待的线程。
                        thread1.wait(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }

    private static class MyThread extends Thread {

        private String name;

        private Object oo;

        public void setOo(Object oo) {
            this.oo = oo;
        }

        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Thread1.run");
            // 锁也是 thread1 对象
            synchronized (this) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(name + i);
                }
            }
        }

    }

}
