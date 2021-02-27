package lesson09.beanfactory.annotationbeanfactory.annotation.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Webb Dong
 * @description: 后置异常通知
 * @date 2021-02-14 16:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AfterThrowing {

}
