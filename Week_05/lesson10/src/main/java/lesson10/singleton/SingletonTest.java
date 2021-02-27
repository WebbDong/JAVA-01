package lesson10.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 单例测试
 * @author Webb Dong
 * @date 2021-02-22 10:30
 */
public class SingletonTest {

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10,
            20,
            5,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(20),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {
        concurrentTest(Singleton1.class);
        singletonTest(Singleton1.getInstance());

        concurrentTest(Singleton2.class);
        singletonTest(Singleton2.getInstance());

        concurrentTest(Singleton3.class);
        singletonTest(Singleton3.getInstance());

        concurrentTest(Singleton4.class);
        singletonTest(Singleton4.getInstance());

        concurrentTest(Singleton5.class);
        singletonTest(Singleton5.getInstance());

        concurrentTest(Singleton7.class);
        singletonTest(Singleton7.getInstance());

        concurrentTest(Singleton8.class);
        singletonTest(Singleton8.getInstance());

        threadPoolExecutor.shutdown();

        enumSingletonTest();
    }

    /**
     * 测试枚举单例
     */
    private static void enumSingletonTest() throws Exception {
        System.out.println("----------------------- " + Singleton6.class.getName() + " -----------------------");
        // 枚举不允许反射创建实例
        Constructor<?>[] declaredConstructors = Singleton6.class.getDeclaredConstructors();
        declaredConstructors[0].setAccessible(true);
        try {
            declaredConstructors[0].newInstance("test", 5);
        } catch (IllegalArgumentException e) {
            System.out.println(Singleton6.class.getName() + " 的单例没有被反射破坏");
        }

        // 枚举的 clone 方法是 protected 访问权限，同时也是 final 方法，无法被重写，所以在外部无法调用枚举的 clone
//        Singleton6.INSTANCE.clone();
        System.out.println("枚举的 clone 方法是 protected 访问权限，同时也是 final 方法，无法被重写，所以在外部无法调用枚举的 clone");

        File objFile = new File(SingletonTest.class.getResource("").getPath(), "obj.data");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(objFile))) {
            out.writeObject(Singleton6.INSTANCE);
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(objFile))) {
            Singleton6 deserializationInstance = (Singleton6) in.readObject();
            if (Singleton6.INSTANCE == deserializationInstance) {
                System.out.println(Singleton6.class.getName() + " 的单例没有被反序列化破坏");
            } else {
                System.out.println(Singleton6.class.getName() + " 的单例已被反序列化破坏");
            }
        }
        objFile.delete();
    }

    /**
     * 测试单例是否存在克隆破坏、反射破坏和反序列化破坏
     * @param instance
     * @param <T>
     * @throws CloneNotSupportedException
     */
    private static <T extends Singleton> void singletonTest(T instance) throws Exception {
        // 测试克隆破坏
        T cloneInstance = (T) instance.clone();
        if (instance == cloneInstance) {
            System.out.println(instance.getClass().getName() + " 的单例没有被克隆破坏");
        } else {
            System.out.println(instance.getClass().getName() + " 的单例已被克隆破坏");
        }

        // 测试反射破坏
        Constructor<?> constructor = Class.forName(instance.getClass().getName()).getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            T newInstance = (T) constructor.newInstance();
            if (instance == newInstance) {
                System.out.println(instance.getClass().getName() + " 的单例没有被反射破坏");
            } else {
                System.out.println(instance.getClass().getName() + " 的单例已被反射破坏");
            }
        } catch (InvocationTargetException e) {
            System.out.println(instance.getClass().getName() + " 的单例没有被反射破坏");
        }

        // 测试反序列化破坏
        File objFile = new File(SingletonTest.class.getResource("").getPath(), "obj.data");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(objFile))) {
            out.writeObject(instance);
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(objFile))) {
            T deserializationInstance = (T) in.readObject();
            if (instance == deserializationInstance) {
                System.out.println(instance.getClass().getName() + " 的单例没有被反序列化破坏");
            } else {
                System.out.println(instance.getClass().getName() + " 的单例已被反序列化破坏");
            }
        }
        objFile.delete();
    }

    /**
     * 测试单例多线程安全
     * @param aClass
     * @param <T>
     * @throws Exception
     */
    private static <T extends Singleton> void concurrentTest(Class<?> aClass) throws Exception {
        System.out.println("----------------------- " + aClass.getName() + " -----------------------");
        CountDownLatch cdl = new CountDownLatch(20);
        Object[] instanceArr = new Object[20];
        IntStream.range(0, 20).forEach(i ->
                threadPoolExecutor.execute(() -> {
                    try {
                        Method getInstanceMethod = aClass.getDeclaredMethod("getInstance");
                        instanceArr[i] = getInstanceMethod.invoke(aClass);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    cdl.countDown();
                }));
        cdl.await();
        boolean isConcurrentSafety = true;
        Object ins = instanceArr[0];
        for (int i = 1; i < instanceArr.length; i++) {
            if (ins != instanceArr[i]) {
                System.out.println(aClass.getName() + " 多线程不安全");
                isConcurrentSafety = false;
                break;
            }
        }
        if (isConcurrentSafety) {
            System.out.println(aClass.getName() + " 多线程安全");
        }
    }

}
