package lesson01.classloader;

import java.util.ArrayList;

/**
 * @author Webb Dong
 * @description: 类加载器基本知识
 * @date 2021-01-15 16:54
 */
public class ClassLoaderBasicKnowledge {

    /**
     * 获取 AppClassLoader 的方式
     */
    private static void getAppClassLoaderMeans() {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoaderBasicKnowledge.class.getClassLoader());
        System.out.println("--------------------------------------------------");
    }

    /**
     * 获取 ExtClassLoader 的方式
     */
    private static void getExtClassLoaderMeans() {
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        System.out.println(ClassLoaderBasicKnowledge.class.getClassLoader().getParent());
        System.out.println("--------------------------------------------------");
    }

    /**
     * 无法直接获取 BootstrapClassLoader
     */
    private static void canNotGetBootstrapClassLoader() {
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
        System.out.println(ClassLoaderBasicKnowledge.class.getClassLoader().getParent().getParent());
        // JDK 中的类都在 jre/lib/rt.jar 中，都是由 BootstrapClassLoader 加载，所以无法获取类加载器
        System.out.println(ArrayList.class.getClassLoader());
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        getAppClassLoaderMeans();
        getExtClassLoaderMeans();
        canNotGetBootstrapClassLoader();
    }

}
