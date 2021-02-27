package lesson06;

/**
 * @author Webb Dong
 * @description: Join
 * @date 2021-01-30 23:03
 */
public class ThreadJoin1 {

    public static void main(String[] args) {
        Object oo = new Object();

        MyThread thread1 = new MyThread("thread1 -- ");
        //oo = thread1;
        thread1.setOo(oo);
        thread1.start();

        // 锁是 oo 对象
        synchronized (oo) {
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        // wait(0) 等于 wait() 。会一直阻塞下去，当 main 打印完19后，wait 方法会释放持有的锁。
                        // thread1 会执行完。但是 main 线程无法被唤醒，导致 JVM 不会结束进程。
//                        oo.wait(0);

                        // join 方法是使用 wait 方法来实现，所以释放的锁对象是调用 join 方法的对象
                        // 此处也就是 thread1。但是 main 和 MyThread 的使用的锁都是 oo 对象。
                        // 所以依然死锁状态。
                        thread1.join();
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
            // 锁也是 oo 对象，和 main 方法的是同一把锁
            synchronized (oo) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(name + i);
                }
            }
        }

    }

}
