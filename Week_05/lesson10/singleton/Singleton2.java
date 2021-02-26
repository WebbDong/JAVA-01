package lesson10.singleton;

import java.io.Serializable;

/**
 * 单例写法二、懒汉式
 * 此写法，支持延迟加载，但存在线程安全问题，是错误写法，同时也存在单例被反序列化破坏、克隆破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 01:27
 */
public class Singleton2 implements Singleton, Serializable, Cloneable {

    private static Singleton2 INSTANCE;

    public static Singleton2 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton2();
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton2() {
        System.out.println("Singleton2");
    }

}
