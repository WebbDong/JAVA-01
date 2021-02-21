package lesson10.singleton;

/**
 * @author Webb Dong
 * @description: 单例写法二
 * @date 2021-02-22 01:27
 */
public class Singleton2 {

    private static Singleton2 INSTANCE;

    public static Singleton2 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton2();
        }
        return INSTANCE;
    }

    private Singleton2() {}

}
