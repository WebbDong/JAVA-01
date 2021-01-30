package lesson05;

/**
 * @author Webb Dong
 * @description: Join
 * @date 2021-01-30 23:21
 */
public class ThreadJoin2 {

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
                        // 两边用的锁对象不同，所以互不干扰，并发执行。当 main 打印到19后，由于 main 的 synchronized
                        // 锁对象与调用 wait 方法的 oo 并不是同一个锁对象，所以此处会抛出 IllegalMonitorStateException
                        // main 方法中止， 而 thread1 不会受到影响执行完后，结束 JVM 进程。
//                        oo.wait(0);

                        // 一开始并行执行，当 main 打印到19后，join 方法让出了 CPU 执行时间片，thread1 执行完后，main 会继续执行，
                        // main 执行完毕后，结束 JVM 进程。
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
            // 锁是 oo 对象
            synchronized (oo) {
                for (int i = 0; i < 100; i++) {
                    System.out.println(name + i);
                }
            }
        }

    }

}
