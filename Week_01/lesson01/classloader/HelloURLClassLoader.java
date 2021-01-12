package lesson01.classloader;

import lesson01.util.ClassCodeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author Webb Dong
 * @description: 自己的 URLClassLoader 加载修改后的 Hello.class
 * @date 2021-01-12 18:04
 */
public class HelloURLClassLoader extends URLClassLoader {

    public HelloURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public HelloURLClassLoader(URL[] urls) {
        super(urls);
    }

    public HelloURLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name == null || name == "") {
            throw new ClassNotFoundException();
        }

        String[] nameSplits = name.split("\\.");
        // 是否有文件后缀名
        boolean isNoFileSuffix = nameSplits.length == 1;
        if (isNoFileSuffix) {
            // 没有文件后缀名，默认使用 .class
            name = name + ".class";
        }

        byte[] bytes;
        try (InputStream in = this.getSystemResourceAsStream(name)) {
            if (in == null) {
                throw new ClassNotFoundException(name);
            }
            bytes = ClassCodeUtils.readLocalClassAsBytes(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ClassCodeUtils.xlassDecode(bytes);
        // 将文件名去掉后缀不可以有文件后缀名，否则会报 NoClassDefFoundError: wrong name
        return defineClass(nameSplits[0], bytes, 0, bytes.length);
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL(HelloURLClassLoader.class.getClassLoader().getResource("").toString());
        HelloURLClassLoader helloURLClassLoader = new HelloURLClassLoader(new URL[] {url});
//        Class<?> clazz = helloURLClassLoader.findClass("Hello.class");
//        Class<?> clazz = helloURLClassLoader.findClass("Hello.xlass");
        Class<?> clazz = helloURLClassLoader.findClass("Hello");
        Object obj = clazz.newInstance();
        clazz.getDeclaredMethod("hello").invoke(obj);
    }

}
