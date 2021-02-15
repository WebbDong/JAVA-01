package lesson09.aop.cglibdp;

import lesson09.aop.AOP;
import lesson09.aop.ProceedingJoinPoint;

/**
 * @author Webb Dong
 * @description: Car AOP
 * @date 2021-02-15 14:16
 */
public class CarAOP implements AOP {

    /**
     * 前置通知
     */
    @Override
    public void beforeAdvice() {
        System.out.println("CarAOP.beforeAdvice()");
    }

    /**
     * 后置通知
     */
    @Override
    public void afterAdvice() {
        System.out.println("CarAOP.afterAdvice()");
    }

    /**
     * 后置返回通知
     */
    @Override
    public void afterReturning() {
        System.out.println("CarAOP.afterReturning()");
    }

    /**
     * 后置异常通知
     */
    @Override
    public void afterThrowing() {
        System.out.println("CarAOP.afterThrowing()");
    }

    /**
     * 环绕通知
     */
    @Override
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("CarAOP.around() start");
        // 调用目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("CarAOP.around() end");
        return proceed;
    }

}
