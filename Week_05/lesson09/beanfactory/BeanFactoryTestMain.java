package lesson09.beanfactory;

import lesson09.beanfactory.bean.School;
import lesson09.beanfactory.context.ApplicationContext;
import lesson09.beanfactory.context.ClassPathXmlApplicationContext;

/**
 * @author Webb Dong
 * @description: 写代码实现 Spring Bean 的装配
 * @date 2021-02-15 15:11
 */
public class BeanFactoryTestMain {

    public static void main(String[] args) {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("lesson09/beanfactory/beans.xml");
//        School school = (School) ac.getBeans("school");
        System.out.println(Boolean.valueOf(null));
    }

}
