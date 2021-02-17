package lesson09.aop.cglibdp;

import lesson09.aop.Aspect;
import lesson09.aop.ProceedingJoinPoint;
import lesson09.aop.ProceedingJoinPointImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: AopMethodInterceptor
 * @date 2021-02-15 14:14
 */
@Data
@AllArgsConstructor
public class AopMethodInterceptor implements MethodInterceptor {

    /**
     * 目标对象
     */
    private Object target;

    private Aspect aspect;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, method, aspect);
        Object ret = aspect.around(joinPoint);
        return ret;
    }

}
