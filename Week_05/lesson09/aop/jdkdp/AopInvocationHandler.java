package lesson09.aop.jdkdp;

import lesson09.aop.AbstractAopMethodInterceptor;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: AopInvocationHandler
 * @date 2021-02-14 21:43
 */
@SuperBuilder
public class AopInvocationHandler extends AbstractAopMethodInterceptor implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invokeAdvice(proxy, method, args);
    }

}
