package lesson09.xmlbeanfactory;

import lesson09.xmlbeanfactory.bean.School;
import lesson09.xmlbeanfactory.bean.Student;
import lesson09.xmlbeanfactory.context.ApplicationContext;
import lesson09.xmlbeanfactory.context.ClassPathXmlApplicationContext;
import lombok.SneakyThrows;

/**
 * @author Webb Dong
 * @description: XML 方式实现 Spring Bean 的装配与简单的 AOP
 * @date 2021-02-15 15:11
 */
public class XmlBeanFactoryTestMain {

    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("lesson09/xmlbeanfactory/beans.xml");
        School school = (School) ac.getBeans("school");
        System.out.println(school);

        Student student = (Student) ac.getBeans("student");
        student.study();
    }

}
