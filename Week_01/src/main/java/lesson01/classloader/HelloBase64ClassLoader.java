package lesson01.classloader;

import lesson01.util.Base64Utils;
import lesson01.util.ByteCodeUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Webb Dong
 * @description: Hello.class Base64字符串 类加载
 * @date 2021-01-08 10:53
 */
public class HelloBase64ClassLoader extends BaseNoParentDelegationClassLoader {

    @Override
    protected Class<?> findClass(String name) {
        String base64 = "NQFFQf///8v/4/X/+f/x9v/w/+/3/+71/+3/7Pj/6/j/6v7/+cOWkZaLwf7//NfWqf7/+7yQm5r+" +
                "//CzlpGasYqSnZqNq56dk5r+//qXmpOTkP7/9ayQio2cmrmWk5r+//W3mpOTkNGVnome8//4//f4/+nz/+j/5/" +
                "7/7Leak5OQ09+ck56MjLOQnpuajd74/+bz/+X/5P7/+reak5OQ/v/vlZ6JntCTnpGY0LCdlZqci/7/75WeiZ7Qk" +
                "56RmNCshoyLmpL+//yQiov+/+qzlZ6JntCWkNCvjZaRi6yLjZqeksT+/+yVnome0JaQ0K+NlpGLrIuNmp6S/v/" +
                "4j42WkYuTkf7/6tezlZ6JntCTnpGY0KyLjZaRmMTWqf/e//r/+f///////f/+//j/9//+//b////i//7//v////" +
                "rVSP/+Tv////7/9f////n//v////7//v/0//f//v/2////2v/9//7////2Tf/97fxJ//tO/////v/1////9f/9" +
                "////+//3//r//v/z/////f/y";
        byte[] plaintextBytes = Base64Utils.decode(base64.getBytes());
        ByteCodeUtils.xlassDecode(plaintextBytes);
        return defineClass(name, plaintextBytes, 0, plaintextBytes.length);
    }

    public static void main(String[] args) throws Exception {
//        printBase64HelloClass();
        HelloBase64ClassLoader helloBase64ClassLoader = new HelloBase64ClassLoader();
        Class<?> clazz1 = helloBase64ClassLoader.findClass("Hello");
        Object obj1 = clazz1.getDeclaredConstructor().newInstance();
        clazz1.getDeclaredMethod("hello").invoke(obj1);

        Class<?> clazz2 = Class.forName("Hello", false, helloBase64ClassLoader);
        Object obj2 = clazz2.getDeclaredConstructor().newInstance();
        clazz2.getDeclaredMethod("hello").invoke(obj2);
    }

    /**
     * 打印 base64 之后的 Hello.class 字节码文件
     */
    private static void printBase64HelloClass() {
        byte[] bytes;
        try (InputStream in = ClassLoader.getSystemResourceAsStream("Hello.class")) {
            bytes = ByteCodeUtils.readLocalClassAsBytes(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new String(Base64Utils.encode(bytes)));
    }

}
