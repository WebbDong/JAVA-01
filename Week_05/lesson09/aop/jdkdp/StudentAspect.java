package lesson09.aop.jdkdp;

import lesson09.aop.Aspect;
import lesson09.aop.ProceedingJoinPoint;

/**
 * @author Webb Dong
 * @description: Student Aspect
 * @date 2021-02-14 16:49
 */
public class StudentAspect implements Aspect {

    /**
     * 前置通知
     */
    @Override
    public void beforeAdvice() {
        System.out.println("StudentAspect.beforeAdvice()");
    }

    /**
     * 后置通知
     */
    @Override
    public void afterAdvice() {
        System.out.println("StudentAspect.afterAdvice()");
    }

    /**
     * 后置返回通知
     */
    @Override
    public void afterReturning() {
        System.out.println("StudentAspect.afterReturning()");
    }

    /**
     * 后置异常通知
     */
    @Override
    public void afterThrowing() {
        System.out.println("StudentAspect.afterThrowing()");
    }

    /**
     * 环绕通知
     */
    @Override
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("StudentAspect.around() start");
        // 调用目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("StudentAspect.around() end");
        return proceed;
    }

}
