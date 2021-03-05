package lesson11.guava.reflection;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;

import java.util.List;

/**
 * 反射
 * @author Webb Dong
 * @date 2021-03-05 2:39 PM
 */
public class ReflectionExample {

    private static void typeTokenExample() {
        System.out.println("----------------- typeTokenExample ------------------");
        TypeToken<String> stringToken = TypeToken.of(String.class);
        System.out.println(stringToken.getType().getTypeName());
        System.out.println(stringToken.getRawType().getName());

        TypeToken<List<String>> stringListTypeToken = new TypeToken<List<String>>() {};
        System.out.println(stringListTypeToken.getType());
        System.out.println(stringListTypeToken.getRawType());
        System.out.println(stringListTypeToken.isArray());
    }

    /**
     * Invokable 是对 java.lang.reflect.Method 和 java.lang.reflect.Constructor 的流式包装。它简化了常见的反射代码的使用。
     */
    @SneakyThrows
    private static void invokableExample() {
        System.out.println("----------------- invokableExample ------------------");
        String str = "Hello ";
        Invokable<String, Object> concatInvokable = new TypeToken<String>() {}
                .method(String.class.getMethod("concat", String.class));
        String str2 = (String) concatInvokable.invoke(str, "World");
        System.out.println(str2);
    }

    private interface Animal {

        void run();

    }

    private static class Person implements Animal {

        @Override
        public void run() {
            System.out.println("Person.run()");
        }

    }

    private static void dynamicProxiesExample() {
        System.out.println("----------------- dynamicProxiesExample ------------------");
        Animal animal = new Person();
        Animal proxyObj = Reflection.newProxy(Animal.class, (proxy, method, args) -> {
            System.out.println("InvocationHandler");
            return method.invoke(animal, args);
        });
        proxyObj.run();
    }

    @SneakyThrows
    private static void classPathExample() {
        System.out.println("----------------- classPathExample ------------------");
        ClassPath classPath = ClassPath.from(ReflectionExample.class.getClassLoader());
        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses("java.util")) {
            System.out.println(classInfo);
        }
    }

    public static void main(String[] args) {
        typeTokenExample();
        invokableExample();
        dynamicProxiesExample();
        classPathExample();
    }

}
