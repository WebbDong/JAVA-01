package lesson06;

/**
 * @author Webb Dong
 * @description: 获取当前线程组
 * @date 2021-01-30 22:21
 */
public class GetCurrentThreadGroup {

    /**
     * 面试题：Java 启动一个 main 程序时，有多少个线程？
     * 答：Java 启动一个 main 程序后，有两个线程组，一个是 system，另一个是 main，system 线程组是 main 线程组的父线程组。
     *      system 线程组有4个线程，main 线程组里有2个线程
     * @param args
     */
    public static void main(String[] args) {
        ThreadGroup currentThreadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("currentThreadGroup.activeCount() = " + currentThreadGroup.activeCount());
        System.out.println("currentThreadGroup.getName() = " + currentThreadGroup.getName());
        System.out.println("currentThreadGroup.getMaxPriority() = " + currentThreadGroup.getMaxPriority());
        currentThreadGroup.list();

        System.out.println("---------------------------------------------");

        ThreadGroup systemThreadGroup = currentThreadGroup.getParent();
        System.out.println("systemThreadGroup.getParent() = " + systemThreadGroup.getParent());
        System.out.println("systemThreadGroup.activeCount() = " + systemThreadGroup.activeCount());
        System.out.println("systemThreadGroup.getName() = " + systemThreadGroup.getName());
        System.out.println("systemThreadGroup.getMaxPriority() = " + systemThreadGroup.getMaxPriority());
        systemThreadGroup.list();
    }

}
