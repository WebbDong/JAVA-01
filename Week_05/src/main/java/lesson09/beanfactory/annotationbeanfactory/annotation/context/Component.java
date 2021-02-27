package lesson09.beanfactory.annotationbeanfactory.annotation.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Webb Dong
 * @description: 组件
 * @date 2021-02-21 14:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    /**
     * bean id
     */
    String value() default "";

}
