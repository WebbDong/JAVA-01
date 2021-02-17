package lesson09.aop.jdkdp;

import lesson09.aop.Aspect;
import lesson09.aop.ProceedingJoinPoint;
import lesson09.aop.ProceedingJoinPointImpl;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: AopInvocationHandler
 * @date 2021-02-14 21:43
 */
@Data
@AllArgsConstructor
public class AopInvocationHandler implements InvocationHandler {

    /**
     * 目标对象
     */
    private Object target;

    private Aspect aspect;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, method, aspect);
        Object ret = aspect.around(joinPoint);
        return ret;
    }

}
