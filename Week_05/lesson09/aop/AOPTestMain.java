package lesson09.aop;

import lesson09.aop.cglibdp.AOPMethodInterceptor;
import lesson09.aop.cglibdp.Car;
import lesson09.aop.cglibdp.CarAOP;
import lesson09.aop.jdkdp.AOPInvocationHandler;
import lesson09.aop.jdkdp.Person;
import lesson09.aop.jdkdp.Student;
import lesson09.aop.jdkdp.StudentAOP;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @author Webb Dong
 * @description: 使用 Java 里的动态代理，实现一个简单的 AOP
 * @date 2021-02-14 15:59
 */
public class AOPTestMain {

    /**
     * JDK 动态代理方式实现简单 AOP
     */
    private static void aopWithJavaDynamicProxy() {
        Student target = new Student();
        Person p = (Person) Proxy.newProxyInstance(AOPTestMain.class.getClassLoader(),
                new Class[]{Person.class}, new AOPInvocationHandler(target, new StudentAOP()));
        p.sayHello();
    }

    /**
     * cglib 动态代理方式实现简单 AOP
     */
    private static void aopWithCglibDynamicProxy() {
        Car target = new Car();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new AOPMethodInterceptor(target, new CarAOP()));
        Car c = (Car) enhancer.create();
        c.drive();
    }

    public static void main(String[] args) {
        aopWithJavaDynamicProxy();
        System.out.println("--------------------------");
        aopWithCglibDynamicProxy();
    }

}
