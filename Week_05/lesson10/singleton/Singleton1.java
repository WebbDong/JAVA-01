package lesson10.singleton;

import java.io.Serializable;

/**
 * 单例写法一、饿汉式
 * 此写法，线程安全，但是不支持延迟加载，存在单例被反序列化破坏、克隆破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 00:46
 */
public class Singleton1 implements Singleton, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private static final Singleton1 INSTANCE = new Singleton1();

    public static Singleton1 getInstance() {
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton1() {}

}
