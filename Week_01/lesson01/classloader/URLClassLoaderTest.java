package lesson01.classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Webb Dong
 * @description: URLClassLoader 加载当前工程 ClassPath 之外某个目录下的字节码文件
 * @date 2021-01-12 18:54
 */
public class URLClassLoaderTest {

    private static final String BYTE_CODE_FILE_PATH = "file:/D:/";

    private static final String JAR_URL = "http://192.168.238.150:36000/lesson01.jar";

    /**
     * 使用当前类的 URLClassLoader 类加载器加载本地目录中的 class 文件
     * @throws Exception
     */
    private static void test1() throws Exception {
        URLClassLoader urlClassLoader = (URLClassLoader) HelloURLClassLoader.class.getClassLoader();
        Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURLMethod.setAccessible(true);
        URL url = new URL(BYTE_CODE_FILE_PATH);
        addURLMethod.invoke(urlClassLoader, url);
        Class<?> clazz = Class.forName("lesson01.bytecode.Test1");
        clazz.getDeclaredMethod("hello").invoke(clazz.newInstance());
    }

    /**
     * 创建 URLClassLoader 类加载器加载本地目录中的 class 文件
     * @throws Exception
     */
    private static void test2() throws Exception {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {new URL(BYTE_CODE_FILE_PATH)});
        Class<?> clazz = urlClassLoader.loadClass("lesson01.bytecode.Test1");
        clazz.getDeclaredMethod("hello").invoke(clazz.newInstance());
    }

    /**
     * 使用当前类的 URLClassLoader 类加载器加载网络上的 jar 包
     * @throws Exception
     */
    private static void test3() throws Exception {
        URLClassLoader urlClassLoader = (URLClassLoader) HelloURLClassLoader.class.getClassLoader();
        Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURLMethod.setAccessible(true);
        addURLMethod.invoke(urlClassLoader, new URL(JAR_URL));
        Class<?> clazz = Class.forName("lesson01.bytecode.Test1");
        clazz.getDeclaredMethod("hello").invoke(clazz.newInstance());
    }

    /**
     * 创建 URLClassLoader 类加载器加载网络上的 jar 包
     * @throws Exception
     */
    private static void test4() throws Exception {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {new URL(JAR_URL)});
        Class<?> clazz = urlClassLoader.loadClass("lesson01.bytecode.Test1");
        clazz.getDeclaredMethod("hello").invoke(clazz.newInstance());
    }

    public static void main(String[] args) throws Exception {
        test1();
        test2();
        test3();
        test4();
    }

}
