package lesson10.singleton;

import java.io.Serializable;

/**
 * 单例写法五、懒汉式、双重检查 + volatile
 * 此写法，支持延迟加载，线程安全，锁粒度减小并发性能有一定提升。添加 volatile 防止指令重排
 * 同时也存在单例被反序列化破坏、克隆破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 01:34
 */
public class Singleton5 implements Singleton, Serializable, Cloneable {

    private volatile static Singleton5 INSTANCE;

    public static Singleton5 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton5.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton5();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton5() {}

}
