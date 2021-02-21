package lesson10.singleton;

/**
 * @author Webb Dong
 * @description: 单例写法七
 * @date 2021-02-22 01:38
 */
public class Singleton7 {

    private static class Inner {

        private static final Singleton7 INSTANCE = new Singleton7();

    }

    public static Singleton7 getInstance() {
        return Inner.INSTANCE;
    }

    private Singleton7() {}

}
