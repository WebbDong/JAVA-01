package lesson10.singleton;

import java.io.Serializable;

/**
 * 单例写法八、懒汉式、双重检查 + volatile，同时防止克隆破坏、反序列化破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 01:40
 */
public class Singleton8 implements Singleton, Serializable, Cloneable {

    private volatile static Singleton8 INSTANCE;

    private static boolean firstCreate = true;

    public static Singleton8 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton8.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton8();
                }
            }
        }
        return INSTANCE;
    }

    // 防止克隆破坏单例
    @Override
    public Object clone() throws CloneNotSupportedException {
        return INSTANCE;
    }

    // 防止反序列化破坏单例
    private Object readResolve() {
        return INSTANCE;
    }

    // 防止反射破坏单例
    private Singleton8() {
        System.out.println("Singleton8");
        if (firstCreate) {
            synchronized (Singleton8.class) {
                if (firstCreate) {
                    firstCreate = false;
                }
            }
        } else {
            throw new RuntimeException("单例模式无法再次实例化");
        }
    }

}
