package lesson10.singleton;

/**
 * @author Webb Dong
 * @description: 单例写法一
 * @date 2021-02-22 00:46
 */
public class Singleton1 {

    private static final Singleton1 INSTANCE = new Singleton1();

    public static Singleton1 getInstance() {
        return INSTANCE;
    }

    private Singleton1() {}

}
