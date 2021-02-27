package lesson09.beanfactory.base.aop.methodinvocation;

import lombok.experimental.SuperBuilder;

/**
 * @author Webb Dong
 * @description: 抽象通知方法调用器
 * @date 2021-02-19 15:24
 */
@SuperBuilder
public abstract class AbstractAdviceMethodInvocation implements MethodInvocation {

    /**
     * 切面对象
     */
    protected Object aspect;

    /**
     * 调用的方法名
     */
    protected String invokeMethodName;

}
