package lesson09.beanfactory.annotationbeanfactory.annotation.context;

import lesson09.beanfactory.base.beanfactory.config.enums.BeanScopeTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Webb Dong
 * @description: Bean 范围
 * @date 2021-02-21 17:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Scope {

    /**
     * bean scope 类型，默认单例模式
     */
    BeanScopeTypeEnum value() default BeanScopeTypeEnum.SINGLETON;

}
