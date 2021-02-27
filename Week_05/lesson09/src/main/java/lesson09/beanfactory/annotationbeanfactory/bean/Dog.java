package lesson09.beanfactory.annotationbeanfactory.bean;

import lesson09.beanfactory.annotationbeanfactory.annotation.context.Component;

/**
 * @author Webb Dong
 * @description: Dog
 * @date 2021-02-21 15:38
 */
@Component
public class Dog {

    public void run() {
        System.out.println("Target method Dog.run()");
    }

}
