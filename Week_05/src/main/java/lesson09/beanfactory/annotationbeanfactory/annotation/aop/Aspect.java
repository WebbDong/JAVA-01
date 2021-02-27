package lesson09.beanfactory.annotationbeanfactory.annotation.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Webb Dong
 * @description: AOP 切面 注解
 * @date 2021-02-14 15:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Aspect {

    /**
     * AOP 切面 bean id
     */
    String ref() default "";

}
