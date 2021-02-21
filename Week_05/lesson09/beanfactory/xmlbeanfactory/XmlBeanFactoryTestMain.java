package lesson09.beanfactory.xmlbeanfactory;

import lesson09.beanfactory.xmlbeanfactory.bean.Klass;
import lesson09.beanfactory.xmlbeanfactory.bean.School;
import lesson09.beanfactory.xmlbeanfactory.bean.Student;
import lesson09.beanfactory.xmlbeanfactory.bean.Vehicle;
import lesson09.beanfactory.xmlbeanfactory.context.ApplicationContext;
import lesson09.beanfactory.xmlbeanfactory.context.ClassPathXmlApplicationContext;
import lombok.SneakyThrows;

/**
 * @author Webb Dong
 * @description: XML 方式实现 Spring Bean 的装配与简单的 AOP
 * @date 2021-02-15 15:11
 */
public class XmlBeanFactoryTestMain {

    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("lesson09/beanfactory/xmlbeanfactory/beans.xml");
        School school = (School) ac.getBeans("school");
//        school.introduce();
        Object ret = school.testMethod(3.45, 4.55);
        System.out.println("XmlBeanFactoryTestMain ret = " + ret);

        System.out.println("----------------------------------------------");

        Student student = (Student) ac.getBeans("student");
//        student.study();
        ret = student.testMethod(50, 80, "Hello World");
        System.out.println("XmlBeanFactoryTestMain ret = " + ret);

        System.out.println("----------------------------------------------");

        Vehicle car = (Vehicle) ac.getBeans("car");
        car.drive();

        System.out.println("----------------------------------------------");

        Klass klass1 = (Klass) ac.getBeans("klass");
        klass1.testMethod();
        Klass klass2 = (Klass) ac.getBeans("klass");
        System.out.println(klass1 == klass2);

        System.out.println("----------------------------------------------");

        Vehicle truck1 = (Vehicle) ac.getBeans("truck");
        truck1.drive();
        Vehicle truck2 = (Vehicle) ac.getBeans("truck");
        truck2.drive();
        System.out.println("(truck1 == truck2) = " + (truck1 == truck2));
    }

}
