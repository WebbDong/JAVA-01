package lesson09.aop.bytebuddydp;

import lesson09.aop.Aspect;
import lesson09.aop.ProceedingJoinPoint;

/**
 * @author Webb Dong
 * @description:
 * @date 2021-02-20 02:22
 */
public class CatAspect implements Aspect {

    /**
     * 前置通知
     */
    @Override
    public void beforeAdvice() {
        System.out.println("CatAspect.beforeAdvice()");
    }

    /**
     * 后置通知
     */
    @Override
    public void afterAdvice() {
        System.out.println("CatAspect.afterAdvice()");
    }

    /**
     * 后置返回通知
     */
    @Override
    public void afterReturning() {
        System.out.println("CatAspect.afterReturning()");
    }

    /**
     * 后置异常通知
     */
    @Override
    public void afterThrowing() {
        System.out.println("CatAspect.afterThrowing()");
    }

    /**
     * 环绕通知
     */
    @Override
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("CatAspect.around() start");
        // 调用目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("CatAspect.around() end");
        return proceed;
    }

}
