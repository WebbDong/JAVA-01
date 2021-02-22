package lesson10.singleton;

import java.io.Serializable;

/**
 * 单例写法四、懒汉式、双重检查
 * 此写法，支持延迟加载，线程安全、锁粒度减小并发性能有一定提升。此方式可能存在指令重排问题，要解决这个问题，我们需要给 instance 成员变量加上 volatile 关键字，
 * 禁止指令重排序才行。实际上，只有很低版本的 Java 才会有这个问题。我们现在用的高版本的 Java 已经在 JDK 内部实现中解决了这个问题
 * （解决的方法很简单，只要把对象 new 操作和初始化操作设计为原子操作，就自然能禁止重排序）。
 * 同时也存在单例被反序列化破坏、克隆破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 01:31
 */
public class Singleton4 implements Singleton, Serializable, Cloneable {

    private static Singleton4 INSTANCE;

    public static Singleton4 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton4.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton4();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton4() {}

}
