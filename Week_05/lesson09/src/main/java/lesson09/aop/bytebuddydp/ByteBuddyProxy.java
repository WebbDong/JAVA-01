package lesson09.aop.bytebuddydp;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author Webb Dong
 * @description: 基于 byte-buddy 的动态代理工具类
 * @date 2021-02-20 11:24
 */
public class ByteBuddyProxy {

    /**
     * 创建代理对象
     * @param subclass 实现的类或接口
     * @param methodInterceptor 方法拦截器
     * @return 返回代理对象
     */
    @SneakyThrows
    public static <T> T newProxyInstance(Class<T> subclass, ByteBuddyMethodInterceptor methodInterceptor) {
        return new ByteBuddy()
                .subclass(subclass)
                .method(ElementMatchers.anyOf(subclass.getDeclaredMethods()))
                .intercept(MethodDelegation.to(methodInterceptor))
                .make()
                .load(subclass.getClassLoader())
                .getLoaded()
                .getConstructor()
                .newInstance();
    }

    private ByteBuddyProxy() {}

}
