package lesson09.xmlbeanfactory.aop;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @description: ProceedingJoinPointImpl
 * @date 2021-02-14 21:20
 */
@AllArgsConstructor
@NoArgsConstructor
public class ProceedingJoinPointImpl implements ProceedingJoinPoint {

    private Object[] args;

    private Object target;

    private Object proxy;

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
        return proceed0(this.args);
    }

    @Override
    public Object proceed(Object[] args) throws Throwable {
        return proceed0(args);
    }

    private Object proceed0(Object[] args) throws Throwable {
        return null;
    }

}
