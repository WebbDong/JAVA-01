package lesson10.singleton;

import java.io.Serializable;

/**
 * 单例写法三、懒汉式、getInstance 方法加锁
 * 此写法，支持延迟加载，线程安全，但是直接在 getInstance 加锁，锁粒度过大并发性能不好。同时也存在单例被反序列化破坏、克隆破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 01:29
 */
public class Singleton3 implements Singleton, Serializable, Cloneable {

    private static Singleton3 INSTANCE;

    public static synchronized Singleton3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton3() {}

}
