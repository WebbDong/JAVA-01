package lesson09.beanfactory.xmlbeanfactory.aspect;

import lesson09.beanfactory.xmlbeanfactory.aop.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * @author Webb Dong
 * @description: Student AOP 切面
 * @date 2021-02-16 15:46
 */
public class StudentAspect {

    /**
     * 前置通知
     */
    public void beforeAdvice1() {
        System.out.println("StudentAspect.beforeAdvice1()");
    }

    public void beforeAdvice2() {
        System.out.println("StudentAspect.beforeAdvice2()");
    }

    /**
     * 后置通知
     */
    public void afterAdvice1() {
        System.out.println("StudentAspect.afterAdvice1()");
    }

    public void afterAdvice2() {
        System.out.println("StudentAspect.afterAdvice2()");
    }

    /**
     * 后置返回通知
     */
    public void afterReturning1() {
        System.out.println("StudentAspect.afterReturning1()");
    }

    public void afterReturning2() {
        System.out.println("StudentAspect.afterReturning2()");
    }

    /**
     * 后置异常通知
     */
    public void afterThrowing1() {
        System.out.println("StudentAspect.afterThrowing1()");
    }

    public void afterThrowing2() {
        System.out.println("StudentAspect.afterThrowing2()");
    }

    /**
     * 环绕通知
     */
    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("StudentAspect.around1() start");
        System.out.println("StudentAspect.around1() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("StudentAspect.around1() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("StudentAspect.around1() proxy class name: " + joinPoint.getThis().getClass().getName());
//        Object ret = joinPoint.proceed();
        Object ret = joinPoint.proceed(100, 200, "Ferrari");
        System.out.println("StudentAspect.around1() end");
        return ret;
//        return null;
    }

    public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("StudentAspect.around2() start");
        System.out.println("StudentAspect.around2() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("StudentAspect.around2() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("StudentAspect.around2() proxy class name: " + joinPoint.getThis().getClass().getName());
//        Object ret = joinPoint.proceed();
        Object ret = joinPoint.proceed(new Object[] {600, 400, "Lamborghini"});
        System.out.println("StudentAspect.around2() end");
        return ret;
//        return null;
    }

}
