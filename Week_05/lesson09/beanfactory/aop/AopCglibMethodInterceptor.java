package lesson09.beanfactory.aop;

import lesson09.beanfactory.factory.config.AopAdviceDefinition;
import lesson09.beanfactory.factory.config.enums.AopAdviceTypeEnum;
import lombok.Data;
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
@Data
public class AopCglibMethodInterceptor extends AbstractAOPAdviceMethodHandler implements MethodInterceptor {

    public AopCglibMethodInterceptor(Object target, Object aspect,
                                     Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        super(target, aspect, adviceDefMap);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, method, aspect);
        List<AopAdviceDefinition> aroundAdviceList = adviceDefMap.get(AopAdviceTypeEnum.AROUND);
//        if () {
//
//        }
//        Object ret = aspect.around(joinPoint);
//        return ret;
        return null;
    }

}
