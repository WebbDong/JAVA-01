package lesson09.aop.cglibdp;

import lesson09.aop.Aspect;
import lesson09.aop.ProceedingJoinPoint;

/**
 * @author Webb Dong
 * @description: Car Aspect
 * @date 2021-02-15 14:16
 */
public class CarAspect implements Aspect {

    /**
     * 前置通知
     */
    @Override
    public void beforeAdvice() {
        System.out.println("CarAspect.beforeAdvice()");
    }

    /**
     * 后置通知
     */
    @Override
    public void afterAdvice() {
        System.out.println("CarAspect.afterAdvice()");
    }

    /**
     * 后置返回通知
     */
    @Override
    public void afterReturning() {
        System.out.println("CarAspect.afterReturning()");
    }

    /**
     * 后置异常通知
     */
    @Override
    public void afterThrowing() {
        System.out.println("CarAspect.afterThrowing()");
    }

    /**
     * 环绕通知
     */
    @Override
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("CarAspect.around() start");
        // 调用目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("CarAspect.around() end");
        return proceed;
    }

}
