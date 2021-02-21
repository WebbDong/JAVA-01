package lesson10.singleton;

/**
 * @author Webb Dong
 * @description: 单例写法八
 * @date 2021-02-22 01:40
 */
public class Singleton8 {

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
    protected Object clone() throws CloneNotSupportedException {
        return INSTANCE;
    }

    // 防止序列化破坏单例
    private Object readResolve() {
        return INSTANCE;
    }

    private Singleton8() {
        // 防止反射破坏
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
