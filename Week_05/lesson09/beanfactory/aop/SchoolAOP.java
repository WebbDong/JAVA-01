package lesson09.beanfactory.aop;

import lesson09.aop.ProceedingJoinPoint;

/**
 * @author Webb Dong
 * @description: SchoolAOP
 * @date 2021-02-16 15:46
 */
public class SchoolAOP {

    /**
     * 前置通知
     */
    public void beforeAdvice() {

    }

    /**
     * 后置通知
     */
    public void afterAdvice() {

    }

    /**
     * 后置返回通知
     */
    public void afterReturning() {

    }

    /**
     * 后置异常通知
     */
    public void afterThrowing() {

    }

    /**
     * 环绕通知
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return null;
    }

}
