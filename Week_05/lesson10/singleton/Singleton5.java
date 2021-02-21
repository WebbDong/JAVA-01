package lesson10.singleton;

/**
 * @author Webb Dong
 * @description: 单例写法五
 * @date 2021-02-22 01:34
 */
public class Singleton5 {

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

    private Singleton5() {}

}
