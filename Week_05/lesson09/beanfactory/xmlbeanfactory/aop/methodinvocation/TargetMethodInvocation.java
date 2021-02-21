package lesson09.beanfactory.xmlbeanfactory.aop.methodinvocation;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Webb Dong
 * @description: 目标方法调用器，包含前置通知和后置通知
 * @date 2021-02-19 11:20
 */
@Data
@Builder
public class TargetMethodInvocation implements MethodInvocation {

    /**
     * 前置通知方法集合
     */
    private List<MethodInvocation> beforeAdviceMethodList;

    /**
     * 后置返回通知方法集合
     */
    private List<MethodInvocation> afterReturningMethodList;

    /**
     * 后置异常通知方法集合
     */
    private List<MethodInvocation> afterThrowingMethodList;

    /**
     * 后置通知方法集合
     */
    private List<MethodInvocation> afterAdviceMethodList;

    /**
     * 目标方法
     */
    private Method targetMethod;

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 切面对象
     */
    private Object aspect;

    @Override
    public Object invoke(Object[] args) throws Throwable {
        invokeAdvice(beforeAdviceMethodList);
        Object ret;
        try {
            ret = targetMethod.invoke(target, args);
            invokeAdvice(afterReturningMethodList);
        } catch (Throwable e) {
            invokeAdvice(afterThrowingMethodList);
            invokeAdvice(afterAdviceMethodList);
            throw e;
        }
        invokeAdvice(afterAdviceMethodList);
        return ret;
    }

    /**
     * 执行通知方法
     * @param adviceMethodList 通知方法集合
     */
    private void invokeAdvice(List<MethodInvocation> adviceMethodList) {
        if (adviceMethodList == null || adviceMethodList.size() == 0) {
            return;
        }
        adviceMethodList.forEach(m -> {
            try {
                m.invoke(aspect);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

}
