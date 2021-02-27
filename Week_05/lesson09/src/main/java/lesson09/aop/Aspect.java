package lesson09.aop;

/**
 * @author Webb Dong
 * @description: 切面接口
 * @date 2021-02-14 22:47
 */
public interface Aspect {

    /**
     * 前置通知
     */
    void beforeAdvice();

    /**
     * 后置通知
     */
    void afterAdvice();

    /**
     * 后置返回通知
     */
    void afterReturning();

    /**
     * 后置异常通知
     */
    void afterThrowing();

    /**
     * 环绕通知
     */
    Object around(ProceedingJoinPoint joinPoint) throws Throwable;

}
