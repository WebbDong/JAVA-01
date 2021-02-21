package lesson09.beanfactory.xmlbeanfactory.aspect;

import lesson09.beanfactory.base.aop.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * @author Webb Dong
 * @description: School AOP 切面
 * @date 2021-02-19 21:34
 */
public class SchoolAspect {

    /**
     * 前置通知
     */
    public void beforeAdvice() {
        System.out.println("SchoolAspect.beforeAdvice()");
    }

    /**
     * 后置通知
     */
    public void afterAdvice() {
        System.out.println("SchoolAspect.afterAdvice()");
    }

    /**
     * 后置返回通知
     */
    public void afterReturning() {
        System.out.println("SchoolAspect.afterReturning()");
    }

    /**
     * 后置异常通知
     */
    public void afterThrowing() {
        System.out.println("SchoolAspect.afterThrowing()");
    }

    /**
     * 环绕通知
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("SchoolAspect.around() start");
        System.out.println("SchoolAspect.around() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("SchoolAspect.around() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("SchoolAspect.around() proxy class name: " + joinPoint.getThis().getClass().getName());
        Object ret = joinPoint.proceed();
//        Object ret = joinPoint.proceed(80.45, 90.55);
        System.out.println("SchoolAspect.around() end");
        return ret;
//        return null;
    }

}
