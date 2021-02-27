package lesson09.aop.bytebuddydp;

import lesson09.aop.AbstractAopMethodInterceptor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: Byte Buddy 方法拦截器
 * @date 2021-02-20 11:55
 */
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AopByteBuddyMethodInterceptor extends AbstractAopMethodInterceptor implements ByteBuddyMethodInterceptor {

    @RuntimeType
    @Override
    public Object intercept(@This Object proxy, @Origin Method method, @AllArguments Object[] args) throws Throwable {
        return invokeAdvice(proxy, method, args);
    }

}
