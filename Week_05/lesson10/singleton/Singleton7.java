package lesson10.singleton;

import java.io.Serializable;

/**
 * 单例写法七、内部类方式
 * 此写法，支持延迟加载，线程安全。同时也存在单例被反序列化破坏、克隆破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 01:38
 */
public class Singleton7 implements Singleton, Serializable, Cloneable {

    private static class LazyHolder {

        private static final Singleton7 INSTANCE = new Singleton7();

    }

    public static Singleton7 getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton7() {
        System.out.println("Singleton7");
    }

}
