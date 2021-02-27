package lesson01.error;

import lesson01.classloader.HelloURLClassLoader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Webb Dong
 * @description: 模拟引发 ClassCircularityError 的情况。ClassCircularityError 是当某个类是其自身的（间接）父类，某个接口（间接）
 *               对其自身进行扩展或类似操作时通常不会发生这种情况，因为编译器不允许这种情况。但是使用不同版本的 jar 包就可能出现这种情况。
 * @date 2021-01-13 12:13
 */
public class ClassCircularityErrorCase {

    /**
     * 场景模拟说明：
     *      有两个 jar 包，ClassCircularityErrorCaseJar1.jar 和 ClassCircularityErrorCaseJar2.jar
     *
     *      1、ClassCircularityErrorCaseJar1.jar 制作过程
     *          public class Class1 extends Class3 {
     *          }
     *
     *          public class Class2 extends Class1 {
     *          }
     *
     *          public class Class3 {
     *          }
     *
     *          定义三个类，Class1、Class2、Class3 编译成功后，只将 Class1.class 和 Class2.class 打包成 ClassCircularityErrorCaseJar1.jar
     *
     *      2、ClassCircularityErrorCaseJar2.jar 制作过程
     *          public class Class1 {
     *          }
     *
     *          public class Class2 extends Class1 {
     *          }
     *
     *          public class Class3 extends Class2 {
     *          }
     *
     *          同样定义三个类，Class1、Class2、Class3 编译成功后，只将 Class3.class 打包成 ClassCircularityErrorCaseJar2.jar
     *
     *      3、然后通过同时加载这2个 jar 包，然后加载 Class3
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final String JAR1_PATH = ClassCircularityErrorCase.class.getClassLoader()
                .getResource("lesson01/error/jar/ClassCircularityErrorCaseJar1.jar").toString();
        final String JAR2_PATH = ClassCircularityErrorCase.class.getClassLoader()
                .getResource("lesson01/error/jar/ClassCircularityErrorCaseJar2.jar").toString();
        System.out.println(JAR1_PATH);
        System.out.println(JAR2_PATH);
        URLClassLoader urlClassLoader = (URLClassLoader) HelloURLClassLoader.class.getClassLoader();
        Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURLMethod.setAccessible(true);
        addURLMethod.invoke(urlClassLoader, new URL(JAR1_PATH));
        addURLMethod.invoke(urlClassLoader, new URL(JAR2_PATH));
        Class.forName("lesson01.error.Class3");
    }

}
