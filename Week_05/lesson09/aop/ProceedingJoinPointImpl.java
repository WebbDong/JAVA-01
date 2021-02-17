package lesson09.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: ProceedingJoinPointImpl
 * @date 2021-02-14 21:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProceedingJoinPointImpl implements ProceedingJoinPoint {

    private Object[] args;

    private Object target;

    private Method method;

    private Aspect aspect;

    @Override
    public Object proceed() throws Throwable {
        return proceed0(this.args);
    }

    @Override
    public Object proceed(Object[] args) throws Throwable {
        return proceed0(args);
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    private Object proceed0(Object[] args) throws Throwable {
        aspect.beforeAdvice();
        Object ret;
        try {
            ret = method.invoke(target, args);
            aspect.afterReturning();
            aspect.afterAdvice();
        } catch (Throwable e) {
            aspect.afterThrowing();
            aspect.afterAdvice();
            throw e;
        }
        return ret;
    }

}
