package lesson09.aop;

import java.lang.reflect.Proxy;

/**
 * @author Webb Dong
 * @description: 使用 Java 里的动态代理，实现一个简单的 AOP
 * @date 2021-02-14 15:59
 */
public class AOPTestMain {

    public static void main(String[] args) {
        Student target = new Student();
        Person p = (Person) Proxy.newProxyInstance(AOPTestMain.class.getClassLoader(),
                new Class[]{Person.class}, new AOPInvocationHandler(target, new StudentAOP()));
        p.sayHello();
    }

}
