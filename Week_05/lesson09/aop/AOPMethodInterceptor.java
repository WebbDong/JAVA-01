package lesson09.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: AOPMethodInterceptor
 * @date 2021-02-15 14:14
 */
@Data
@AllArgsConstructor
public class AOPMethodInterceptor implements MethodInterceptor {

    /**
     * 目标对象
     */
    private Object target;

    private AOP aop;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, method, aop);
        Object ret = aop.around(joinPoint);
        return ret;
    }

}
