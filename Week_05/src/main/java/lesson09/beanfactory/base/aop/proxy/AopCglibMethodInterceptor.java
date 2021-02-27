package lesson09.beanfactory.base.aop.proxy;

import lesson09.beanfactory.base.beanfactory.config.AopAdviceDefinition;
import lesson09.beanfactory.base.beanfactory.config.enums.AopAdviceTypeEnum;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: AopCglibMethodInterceptor
 * @date 2021-02-15 14:14
 */
public class AopCglibMethodInterceptor extends AbstractAopAdviceMethodHandler implements MethodInterceptor {

    public AopCglibMethodInterceptor(Object target, Object aspect,
                                     Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        super(target, aspect, adviceDefMap);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return invokeAdvice(obj, method, args);
    }

}
