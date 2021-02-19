package lesson09.xmlbeanfactory.aop.proxy;

import lesson09.xmlbeanfactory.factory.config.AopAdviceDefinition;
import lesson09.xmlbeanfactory.factory.config.enums.AopAdviceTypeEnum;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: AopInvocationHandler
 * @date 2021-02-14 21:43
 */
public class AopInvocationHandler extends AbstractAopAdviceMethodHandler implements InvocationHandler {

    public AopInvocationHandler(Object target, Object aspect,
                                Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        super(target, aspect, adviceDefMap);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invokeAdvice(proxy, method, args);
    }

}
