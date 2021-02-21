package lesson09.beanfactory.xmlbeanfactory.aop.methodinvocation;

import lombok.experimental.SuperBuilder;

/**
 * @author Webb Dong
 * @description: 普通通知方法调用器
 * @date 2021-02-19 18:25
 */
@SuperBuilder
public class NormalAdviceMethodInvocation extends AbstractAdviceMethodInvocation {

    @Override
    public Object invoke(Object... args) throws Throwable {
        return aspect.getClass().getMethod(invokeMethodName).invoke(aspect);
    }

}
