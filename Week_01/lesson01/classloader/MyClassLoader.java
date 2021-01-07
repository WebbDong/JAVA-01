package lesson01.classloader;

import lesson01.util.ClassCodeUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Webb Dong
 * @description: 作业第二题，自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内
 *      容是一个 Hello.xlass 文件所有字节（x=255-x）处理后的文件。
 * @date 2021-01-07 11:01
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 重写 loadClass 打破双亲委派机制
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // 查看此类是否以及被加载过，如果已经加载过，直接从缓存中获取
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    // 排除 jdk 的包，自己的类才打破双亲委派机制
                    if (!(name.startsWith("java") || name.startsWith("jdk")
                            || name.startsWith("sun"))) {
                        c = this.findClass(name);
                    } else {
                        c = this.getParent().loadClass(name);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String[] nameSplits = name.split("\\.");
        // 是否有文件后缀名
        boolean isNoFileSuffix = nameSplits.length == 1;
        if (isNoFileSuffix) {
            // 没有文件后缀名，默认使用 .class
            name = name + ".class";
        }
        byte[] bytes = readClassAsBytes(name);
        ClassCodeUtils.xlassDecode(bytes);
        // 将文件名去掉后缀，否则会报 NoClassDefFoundError: wrong name
        return defineClass(nameSplits[0], bytes, 0, bytes.length);
    }

    /**
     * 读取包中指定的字节码文件，并且将流转换成 byte 数组
     * @param name
     * @return
     */
    private byte[] readClassAsBytes(String name) {
        InputStream in = this.getSystemResourceAsStream(name);
        byte[] bytes;
        try {
            bytes = new byte[in.available()];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            in.read(bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public static void main(String[] args) throws Exception {
        // 加载 Hello.xlass
        Class<?> clazz1 = new MyClassLoader().findClass("Hello.xlass");
        Object obj1 = clazz1.getDeclaredConstructor().newInstance();
        clazz1.getDeclaredMethod("hello").invoke(obj1);

        // 加载 Hello.class，调用 Class.forName 方法加载类，会遵循双亲委派机制，使用此方式需要打破双亲委派机制
        Class<?> clazz2 = Class.forName("Hello", false, new MyClassLoader());
        Object obj2 = clazz2.getDeclaredConstructor().newInstance();
        clazz2.getDeclaredMethod("hello").invoke(obj2);
    }

}
