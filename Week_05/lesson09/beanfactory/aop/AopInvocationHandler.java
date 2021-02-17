package lesson09.beanfactory.aop;

import lesson09.beanfactory.factory.config.AopAdviceDefinition;
import lesson09.beanfactory.factory.config.enums.AopAdviceTypeEnum;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: AopInvocationHandler
 * @date 2021-02-14 21:43
 */
@Data
public class AopInvocationHandler extends AbstractAOPAdviceMethodHandler implements InvocationHandler {

    public AopInvocationHandler(Object target, Object aspect,
                                     Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        super(target, aspect, adviceDefMap);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, method, aspect);
//        Object ret = aop.around(joinPoint);
        return null;
    }

}
