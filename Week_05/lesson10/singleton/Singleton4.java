package lesson10.singleton;

/**
 * @author Webb Dong
 * @description: 单例写法四
 * @date 2021-02-22 01:31
 */
public class Singleton4 {

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

    private Singleton4() {}

}
