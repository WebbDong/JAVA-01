package lesson09.beanfactory.xmlbeanfactory.aop.proxy;

import lesson09.beanfactory.xmlbeanfactory.aop.ProceedingJoinPointImpl;
import lesson09.beanfactory.xmlbeanfactory.aop.methodinvocation.AroundAdviceMethodInvocation;
import lesson09.beanfactory.xmlbeanfactory.aop.methodinvocation.MethodInvocation;
import lesson09.beanfactory.xmlbeanfactory.aop.methodinvocation.NormalAdviceMethodInvocation;
import lesson09.beanfactory.xmlbeanfactory.aop.methodinvocation.TargetMethodInvocation;
import lesson09.beanfactory.xmlbeanfactory.factory.config.AopAdviceDefinition;
import lesson09.beanfactory.xmlbeanfactory.factory.config.enums.AopAdviceTypeEnum;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Webb Dong
 * @description: 抽象 AOP 通知方法处理类
 * @date 2021-02-17 20:25
 */
@Data
public abstract class AbstractAopAdviceMethodHandler {

    /**
     * 目标对象
     */
    protected Object target;

    /**
     * 切面对象
     */
    protected Object aspect;

    /**
     * 通知方法定义
     */
    protected Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap;

    /**
     * 构造器
     * @param target 目标对象
     * @param aspect 切面对象
     * @param adviceDefMap 通知方法定义
     */
    public AbstractAopAdviceMethodHandler(Object target, Object aspect,
                                          Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        this.target = target;
        this.aspect = aspect;
        this.adviceDefMap = adviceDefMap;
    }

    /**
     * 执行通知方法
     * @param proxy 代理对象
     * @param method 目标方法
     * @param args 参数
     * @return 目标方法返回值
     * @throws Throwable 抛出所有异常
     */
    protected Object invokeAdvice(Object proxy, Method method, Object[] args) throws Throwable {
        if (adviceDefMap == null || adviceDefMap.size() == 0) {
            return method.invoke(target, args);
        }

        List<AopAdviceDefinition> aroundDefAdviceList = adviceDefMap.get(AopAdviceTypeEnum.AROUND);
        Object ret;
        if (adviceDefMap == null || aroundDefAdviceList == null || aroundDefAdviceList.size() == 0) {
            ret = createTargetMethodInvocation(method).invoke(args);
        } else {
            MethodInvocation aroundMethodInvocation = createAroundAdviceMethodInvocationCallChain(
                    aroundDefAdviceList, proxy, method, args);
            ret = aroundMethodInvocation.invoke(args);
        }
        return ret;
    }

    /**
     * 创建环绕通知方法链
     * @param aroundDefAdviceList 环绕通知方法定义集合
     * @param proxy 代理对象
     * @param targetMethod 目标方法
     * @param args 参数
     * @return 返回第一个环绕通知方法调用器
     */
    private MethodInvocation createAroundAdviceMethodInvocationCallChain(
            List<AopAdviceDefinition> aroundDefAdviceList, Object proxy, Method targetMethod, Object[] args) {
        AroundAdviceMethodInvocation firstAroundAdvice = null;
        AroundAdviceMethodInvocation prevAroundAdvice = null;
        for (AopAdviceDefinition aopAdviceDefinition : aroundDefAdviceList) {
            AroundAdviceMethodInvocation newAroundAdvice = AroundAdviceMethodInvocation.builder()
                    .aspect(aspect)
                    .invokeMethodName(aopAdviceDefinition.getMethod())
                    .build();
            if (firstAroundAdvice == null) {
                firstAroundAdvice = newAroundAdvice;
            }
            if (prevAroundAdvice != null) {
                prevAroundAdvice.setNextJoinPoint(ProceedingJoinPointImpl.builder()
                        .args(args)
                        .proxy(proxy)
                        .target(target)
                        .methodInvocation(newAroundAdvice)
                        .build());
            }
            prevAroundAdvice = newAroundAdvice;
        }
        prevAroundAdvice.setNextJoinPoint(ProceedingJoinPointImpl.builder()
                .args(args)
                .proxy(proxy)
                .target(target)
                .methodInvocation(createTargetMethodInvocation(targetMethod))
                .build());
        return firstAroundAdvice;
    }

    /**
     * 创建目标方法调用器
     * @param targetMethod 目标方法
     * @return 返回目标方法调用器
     */
    private MethodInvocation createTargetMethodInvocation(Method targetMethod) {
        return TargetMethodInvocation.builder()
                .beforeAdviceMethodList(createNormalAdviceMethodInvocationList(AopAdviceTypeEnum.BEFORE))
                .afterReturningMethodList(createNormalAdviceMethodInvocationList(AopAdviceTypeEnum.AFTER_RETURNING))
                .afterThrowingMethodList(createNormalAdviceMethodInvocationList(AopAdviceTypeEnum.AFTER_THROWING))
                .afterAdviceMethodList(createNormalAdviceMethodInvocationList(AopAdviceTypeEnum.AFTER))
                .targetMethod(targetMethod)
                .target(target)
                .aspect(aspect)
                .build();
    }

    /**
     * 创建普通通知方法（包含所有前置通知和所有后置通知）调用器集合
     * @param adviceType 通知类型
     * @return 返回普通通知方法的调用器集合
     */
    private List<MethodInvocation> createNormalAdviceMethodInvocationList(AopAdviceTypeEnum adviceType) {
        List<AopAdviceDefinition> definitionList = adviceDefMap.get(adviceType);
        if (definitionList == null || definitionList.size() == 0) {
            return null;
        }
        return definitionList.stream()
                .map(def -> NormalAdviceMethodInvocation.builder()
                        .aspect(aspect)
                        .invokeMethodName(def.getMethod())
                        .build())
                .collect(Collectors.toList());
    }

}
