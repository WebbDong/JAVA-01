package lesson09.xmlbeanfactory.aop;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: 环绕通知方法调用
 * @date 2021-02-18 22:33
 */
@Data
public class AroundAdviceMethodInvocation implements MethodInvocation {

    /**
     * 原始参数
     */
    private Object[] args;

    /**
     * 调用对象，当切面只有一个 around 时，调用对象是目标对象，当切面有多个 around 时，调用对象是切面对象
     */
    private Object invokeObj;

    /**
     * 目标方法
     */
    private Method targetMethod;

    @Override
    public Object invoke(Object... args) {
        return null;
    }

}
