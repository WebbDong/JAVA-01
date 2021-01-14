package lesson01.classloader;

import lesson01.util.ByteCodeUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Webb Dong
 * @description: 作业第二题，自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内
 *      容是一个 Hello.xlass 文件所有字节（x=255-x）处理后的文件。
 * @date 2021-01-07 11:01
 */
public class HelloClassLoader extends BaseNoParentDelegationClassLoader {

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
            bytes = ByteCodeUtils.readLocalClassAsBytes(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteCodeUtils.xlassDecode(bytes);
        // 将文件名去掉后缀不可以有文件后缀名，否则会报 NoClassDefFoundError: wrong name
        return defineClass(nameSplits[0], bytes, 0, bytes.length);
    }

    public static void main(String[] args) throws Exception {
        // 加载 Hello.xlass
        Class<?> clazz1 = new HelloClassLoader().findClass("Hello.xlass");
        Object obj1 = clazz1.getDeclaredConstructor().newInstance();
        clazz1.getDeclaredMethod("hello").invoke(obj1);

        // 加载 Hello.class，调用 Class.forName 方法加载类，会遵循双亲委派机制，因为使用的字节码文件放在 classpath 中
        // 所以父类加载器会先加载到。这种情况使用此方式需要打破双亲委派机制。
        Class<?> clazz2 = Class.forName("Hello", false, new HelloClassLoader());
        Object obj2 = clazz2.getDeclaredConstructor().newInstance();
        clazz2.getDeclaredMethod("hello").invoke(obj2);
    }

}
