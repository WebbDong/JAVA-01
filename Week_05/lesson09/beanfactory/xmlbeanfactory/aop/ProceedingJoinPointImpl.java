package lesson09.beanfactory.xmlbeanfactory.aop;

import lesson09.beanfactory.xmlbeanfactory.aop.methodinvocation.MethodInvocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @description: ProceedingJoinPointImpl
 * @date 2021-02-14 21:20
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProceedingJoinPointImpl implements ProceedingJoinPoint {

    /**
     * 参数
     */
    private Object[] args;

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 代理对象
     */
    private Object proxy;

    /**
     * 方法调用
     */
    private MethodInvocation methodInvocation;

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public Object getThis() {
        return proxy;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public Object proceed() throws Throwable {
        return methodInvocation.invoke(args);
    }

    @Override
    public Object proceed(Object... args) throws Throwable {
        return methodInvocation.invoke(args);
    }

}
