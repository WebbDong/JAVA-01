package lesson09.beanfactory.annotationbeanfactory.bean;

import lesson09.beanfactory.annotationbeanfactory.annotation.context.Component;

/**
 * @author Webb Dong
 * @description: Gun
 * @date 2021-02-21 22:35
 */
@Component("gun")
public class Gun {

    public int getNumberOfBullets(int numberOfBullets) {
        System.out.println("Target method getNumberOfBullets()");
        return numberOfBullets;
    }

}
