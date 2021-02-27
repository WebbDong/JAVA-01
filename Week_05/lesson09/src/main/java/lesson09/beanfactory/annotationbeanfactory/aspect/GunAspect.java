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
 * @description: GunAspect
 * @date 2021-02-21 22:36
 */
@Aspect
public class GunAspect {

    @Pointcut(id = "gunAspectPointcut", ref = "gun")
    public void pointcut() {

    }

    /**
     * 前置通知
     */
    @Before
    public void beforeAdvice() {
        System.out.println("GunAspect.beforeAdvice()");
    }

    /**
     * 后置通知
     */
    @After
    public void afterAdvice() {
        System.out.println("GunAspect.afterAdvice()");
    }

    /**
     * 后置返回通知
     */
    @AfterReturning
    public void afterReturning() {
        System.out.println("GunAspect.afterReturning()");
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing
    public void afterThrowing() {
        System.out.println("GunAspect.afterThrowing()");
    }

    /**
     * 环绕通知
     */
    @Around
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("GunAspect.around() start");
        System.out.println("GunAspect.around() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("GunAspect.around() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("GunAspect.around() proxy class name: " + joinPoint.getThis().getClass().getName());
//        Object ret = joinPoint.proceed();
        Object ret = joinPoint.proceed(5000);
        System.out.println("GunAspect.around() end");
        return ret;
//        return null;
    }

}
