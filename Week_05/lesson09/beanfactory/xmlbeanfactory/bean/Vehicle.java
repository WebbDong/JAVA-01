package lesson09.beanfactory.xmlbeanfactory.bean;

/**
 * @author Webb Dong
 * @description: 载具接口
 * @date 2021-02-19 22:56
 */
public abstract class Vehicle {

    private String brand;

    private Integer power;

    private Integer torque;

    public void drive() {
        System.out.println("brand = " + brand + ", power = " + power + ", torque = " + torque);
    }

}
