package lesson09.aop.jdkdp;

import lesson09.aop.AOP;
import lesson09.aop.ProceedingJoinPoint;
import lesson09.aop.ProceedingJoinPointImpl;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: AOPInvocationHandler
 * @date 2021-02-14 21:43
 */
@Data
@AllArgsConstructor
public class AOPInvocationHandler implements InvocationHandler {

    /**
     * 目标对象
     */
    private Object target;

    private AOP aop;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, method, aop);
        Object ret = aop.around(joinPoint);
        return ret;
    }

}
