package lesson09.aop.cglibdp;

import lesson09.aop.AbstractAopMethodInterceptor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: AopMethodInterceptor
 * @date 2021-02-15 14:14
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AopMethodInterceptor extends AbstractAopMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return invokeAdvice(obj, method, args);
    }

}
