package lesson09.aop;

import lombok.experimental.SuperBuilder;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: 抽象 AOP 方法拦截器
 * @date 2021-02-20 16:08
 */
@SuperBuilder
public abstract class AbstractAopMethodInterceptor {

    /**
     * 目标对象
     */
    protected Object target;

    protected Aspect aspect;

    protected Object invokeAdvice(Object proxy, Method method, Object[] args) throws Throwable {
        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, proxy, method, aspect);
        return aspect.around(joinPoint);
    }

}
