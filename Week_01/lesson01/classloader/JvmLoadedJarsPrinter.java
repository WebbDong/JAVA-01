package lesson01.classloader;

import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * @author Webb Dong
 * @description: 打印显示当前 ClassLoader 加载了哪些 Jar
 * @date 2021-01-08 13:03
 */
public class JvmLoadedJarsPrinter {

    public static void main(String[] args) throws Exception {
        // 启动类加载器
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器（BootstrapClassLoader）：");
        for (URL url : urLs) {
            System.out.println(" ==> " + url.toExternalForm());
        }
        System.out.println();

        // 扩展类加载器
        printClassLoader("扩展类加载器", JvmLoadedJarsPrinter.class.getClassLoader().getParent());
        System.out.println();

        // 应用类加载器
        printClassLoader("应用类加载器", JvmLoadedJarsPrinter.class.getClassLoader());
    }

    private static void printClassLoader(String name, ClassLoader cl) throws Exception {
        if (cl != null) {
            System.out.println(name + " ClassLoader ==> " + cl.toString());
            printURLForClassLoader(cl);
        } else {
            System.out.println(name + " ClassLoader ==> null");
        }
    }

    private static void printURLForClassLoader(ClassLoader cl) throws Exception {
        Object ucp = insightField(cl, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList ps = (ArrayList) path;
        for (Object p : ps) {
            System.out.println(" ==> " + p.toString());
        }
    }

    private static Object insightField(Object obj, String fName) throws Exception {
        Field f;
        if (obj instanceof URLClassLoader) {
            f = URLClassLoader.class.getDeclaredField(fName);
        } else {
            f = obj.getClass().getDeclaredField(fName);
        }
        f.setAccessible(true);
        return f.get(obj);
    }

}
