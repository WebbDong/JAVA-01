package lesson10.singleton;

/**
 * @author Webb Dong
 * @description: 单例写法三
 * @date 2021-02-22 01:29
 */
public class Singleton3 {

    private static Singleton3 INSTANCE;

    public static synchronized Singleton3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }

    private Singleton3() {}

}
