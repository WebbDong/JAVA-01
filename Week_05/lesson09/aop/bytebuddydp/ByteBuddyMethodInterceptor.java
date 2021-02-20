package lesson09.aop.bytebuddydp;

import java.lang.reflect.Method;

/**
 * @author Webb Dong
 * @description: Byte Buddy 方法拦截器接口
 * @date 2021-02-20 11:20
 */
public interface ByteBuddyMethodInterceptor {

    /**
     * 执行目标方法拦截
     * @param proxy 代理对象
     * @param method 目标方法
     * @param args 参数
     * @return 目标方法返回值
     * @throws Throwable 抛出所有异常
     */
    Object intercept(Object proxy, Method method, Object[] args) throws Throwable;

}
