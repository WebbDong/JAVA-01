package lesson09.aop;

import lesson09.aop.bytebuddydp.AopByteBuddyMethodInterceptor;
import lesson09.aop.bytebuddydp.ByteBuddyProxy;
import lesson09.aop.bytebuddydp.Cat;
import lesson09.aop.bytebuddydp.CatAspect;
import lesson09.aop.cglibdp.AopMethodInterceptor;
import lesson09.aop.cglibdp.Car;
import lesson09.aop.cglibdp.CarAspect;
import lesson09.aop.jdkdp.AopInvocationHandler;
import lesson09.aop.jdkdp.Person;
import lesson09.aop.jdkdp.Student;
import lesson09.aop.jdkdp.StudentAspect;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @author Webb Dong
 * @description: 使用 Java 里的动态代理，实现一个简单的 AOP
 * @date 2021-02-14 15:59
 */
public class AopTestMain {

    /**
     * JDK 动态代理方式实现简单 AOP
     */
    private static void aopWithJavaDynamicProxy() {
        Student target = new Student();
        Person p = (Person) Proxy.newProxyInstance(AopTestMain.class.getClassLoader(),
                new Class[]{Person.class},
                AopInvocationHandler.builder()
                        .target(target)
                        .aspect(new StudentAspect())
                        .build());
        p.sayHello();
    }

    /**
     * cglib 动态代理方式实现简单 AOP
     */
    private static void aopWithCglibDynamicProxy() {
        Car target = new Car();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(AopMethodInterceptor.builder()
                .target(target)
                .aspect(new CarAspect())
                .build());
        Car c = (Car) enhancer.create();
        c.drive();
    }

    /**
     * Byte Buddy 方式实现简单 AOP
     */
    private static void aopWithByteBuddy() {
        Cat target = new Cat();
        Cat proxy = ByteBuddyProxy.newProxyInstance(Cat.class, AopByteBuddyMethodInterceptor.builder()
                .target(target)
                .aspect(new CatAspect())
                .build());
        proxy.eat();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("------------- JDK 动态代理实现 AOP -------------");
        aopWithJavaDynamicProxy();
        System.out.println("------------ CGLIB 动态代理实现 AOP ------------");
        aopWithCglibDynamicProxy();
        System.out.println("------------ Byte Buddy 实现 AOP ------------");
        aopWithByteBuddy();
    }

}
