package lesson09.xmlbeanfactory.aop;

/**
 * @author Webb Dong
 * @description: 方法调用接口
 * @date 2021-02-18 22:22
 */
public interface MethodInvocation {

    /**
     * 执行调用方法
     * @param args 方法可变参数
     * @return 返回值
     */
    Object invoke(Object... args);

}
