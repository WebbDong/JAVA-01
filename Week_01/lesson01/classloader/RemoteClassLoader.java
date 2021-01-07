package lesson01.classloader;

import lesson01.util.ClassCodeUtils;

import java.io.IOException;

/**
 * @author Webb Dong
 * @description: 远程类加载器，加载网络上的字节码文件
 * @date 2021-01-07 21:25
 */
public class RemoteClassLoader extends BaseNoParentDelegationClassLoader {

    /**
     * 远程字节码文件的url地址
     */
    private String url;

    public RemoteClassLoader(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name == null || name == "") {
            throw new ClassNotFoundException();
        }

        byte[] bytes;
        try {
            bytes = ClassCodeUtils.getRemoteClassFileAsBytes(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ClassCodeUtils.xlassDecode(bytes);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public static void main(String[] args) throws Exception {
        RemoteClassLoader remoteClassLoader = new RemoteClassLoader("http://192.168.238.150:36000/Hello.xlass");
        Class<?> clazz1 = remoteClassLoader.findClass("Hello");
        Object obj1 = clazz1.getDeclaredConstructor().newInstance();
        clazz1.getDeclaredMethod("hello").invoke(obj1);

        Class<?> clazz2 = Class.forName("Hello", false, remoteClassLoader);
        Object obj3 = clazz2.getDeclaredConstructor().newInstance();
        clazz2.getDeclaredMethod("hello").invoke(obj3);

        Class<?> clazz3 = Class.forName("Hello", false,
                new RemoteClassLoader("http://192.168.238.150:36000/Hello.class"));
        Object obj2 = clazz3.getDeclaredConstructor().newInstance();
        clazz3.getDeclaredMethod("hello").invoke(obj2);
    }

}
