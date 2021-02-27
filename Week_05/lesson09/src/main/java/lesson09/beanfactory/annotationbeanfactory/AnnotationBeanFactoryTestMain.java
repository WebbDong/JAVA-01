package lesson09.beanfactory.annotationbeanfactory;

import lesson09.beanfactory.annotationbeanfactory.bean.Dog;
import lesson09.beanfactory.annotationbeanfactory.bean.Gun;
import lesson09.beanfactory.annotationbeanfactory.context.AnnotationApplicationContext;
import lesson09.beanfactory.base.ApplicationContext;

/**
 * @author Webb Dong
 * @description: 注解方式实现 Spring Bean 的装配与简单的 AOP
 * @date 2021-02-21 15:21
 */
public class AnnotationBeanFactoryTestMain {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationApplicationContext(AnnotationBeanFactoryTestMain.class);
        Dog dog = (Dog) ac.getBean("dog");
        dog.run();

        Dog dog2 = (Dog) ac.getBean("dog");
        System.out.println(dog2 == dog);

        System.out.println("-------------------------------------");

        Gun gun = (Gun) ac.getBean("gun");
        System.out.println("ret = " + gun.getNumberOfBullets(6));
    }

}
