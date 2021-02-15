package lesson09.aop.jdkdp;

import lesson09.aop.AOP;
import lesson09.aop.ProceedingJoinPoint;

/**
 * @author Webb Dong
 * @description: Student AOP
 * @date 2021-02-14 16:49
 */
public class StudentAOP implements AOP {

    /**
     * 前置通知
     */
    @Override
    public void beforeAdvice() {
        System.out.println("StudentAOP.beforeAdvice()");
    }

    /**
     * 后置通知
     */
    @Override
    public void afterAdvice() {
        System.out.println("StudentAOP.afterAdvice()");
    }

    /**
     * 后置返回通知
     */
    @Override
    public void afterReturning() {
        System.out.println("StudentAOP.afterReturning()");
    }

    /**
     * 后置异常通知
     */
    @Override
    public void afterThrowing() {
        System.out.println("StudentAOP.afterThrowing()");
    }

    /**
     * 环绕通知
     */
    @Override
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("StudentAOP.around() start");
        // 调用目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("StudentAOP.around() end");
        return proceed;
    }

}
