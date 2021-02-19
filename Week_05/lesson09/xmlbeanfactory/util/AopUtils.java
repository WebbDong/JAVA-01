package lesson09.xmlbeanfactory.util;

import lesson09.xmlbeanfactory.aop.AopCglibMethodInterceptor;
import lesson09.xmlbeanfactory.aop.AopInvocationHandler;
import lesson09.xmlbeanfactory.factory.config.AopAdviceDefinition;
import lesson09.xmlbeanfactory.factory.config.enums.AopAdviceTypeEnum;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: AOP 工具类
 * @date 2021-02-17 19:36
 */
public class AopUtils {

    /**
     * 使用 JDK 动态代理创建 AOP 代理对象
     * @param target 目标对象
     * @param aspect AOP 切面对象
     * @param interfaces 需要实现的接口数组
     * @param adviceDefMap 通知方法定义
     * @return 返回代理对象
     */
    public static Object createAOPProxyWithJDK(Object target, Object aspect, Class<?>[] interfaces,
                                               Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        return Proxy.newProxyInstance(AopUtils.class.getClassLoader(),
                interfaces, new AopInvocationHandler(target, aspect, adviceDefMap));
    }

    /**
     * 使用 cglib 动态代理创建 AOP 代理对象
     * @param target 目标对象
     * @param aspect AOP 切面对象
     * @param superClass 父类 class
     * @param adviceDefMap 通知方法定义
     * @return 返回代理对象
     */
    public static Object createAOPProxyWithCglib(Object target, Object aspect, Class<?> superClass,
                                                 Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superClass);
        enhancer.setCallback(new AopCglibMethodInterceptor(target, aspect, adviceDefMap));
        return enhancer.create();
    }

    private AopUtils() {}

}
