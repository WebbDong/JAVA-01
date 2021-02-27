package lesson09.beanfactory.annotationbeanfactory.aspect;

import lesson09.beanfactory.annotationbeanfactory.annotation.aop.After;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.AfterReturning;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.AfterThrowing;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Around;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Aspect;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Before;
import lesson09.beanfactory.annotationbeanfactory.annotation.aop.Pointcut;
import lesson09.beanfactory.base.aop.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * @author Webb Dong
 * @description: DogAspect
 * @date 2021-02-21 19:16
 */
@Aspect
public class DogAspect {

    @Pointcut(id = "dogAspectPointcut", ref = "dog")
    public void pointcut() {

    }

    /**
     * 前置通知
     */
    @Before
    public void beforeAdvice1() {
        System.out.println("DogAspect.beforeAdvice1()");
    }

    @Before
    public void beforeAdvice2() {
        System.out.println("DogAspect.beforeAdvice2()");
    }

    /**
     * 后置通知
     */
    @After
    public void afterAdvice1() {
        System.out.println("DogAspect.afterAdvice1()");
    }

    @After
    public void afterAdvice2() {
        System.out.println("DogAspect.afterAdvice2()");
    }

    /**
     * 后置返回通知
     */
    @AfterReturning
    public void afterReturning1() {
        System.out.println("DogAspect.afterReturning1()");
    }

    @AfterReturning
    public void afterReturning2() {
        System.out.println("DogAspect.afterReturning2()");
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing
    public void afterThrowing1() {
        System.out.println("DogAspect.afterThrowing1()");
    }

    @AfterThrowing
    public void afterThrowing2() {
        System.out.println("DogAspect.afterThrowing2()");
    }

    /**
     * 环绕通知
     */
    @Around
    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("DogAspect.around1() start");
        System.out.println("DogAspect.around1() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("DogAspect.around1() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("DogAspect.around1() proxy class name: " + joinPoint.getThis().getClass().getName());
        Object ret = joinPoint.proceed();
//        Object ret = joinPoint.proceed(80.45, 90.55);
        System.out.println("DogAspect.around1() end");
        return ret;
//        return null;
    }

    @Around
    public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("DogAspect.around2() start");
        System.out.println("DogAspect.around2() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("DogAspect.around2() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("DogAspect.around2() proxy class name: " + joinPoint.getThis().getClass().getName());
        Object ret = joinPoint.proceed();
//        Object ret = joinPoint.proceed(80.45, 90.55);
        System.out.println("DogAspect.around2() end");
        return ret;
//        return null;
    }

}
