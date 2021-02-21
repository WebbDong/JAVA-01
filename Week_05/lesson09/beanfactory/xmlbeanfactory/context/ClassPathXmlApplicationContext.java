package lesson09.beanfactory.xmlbeanfactory.context;

import lesson09.beanfactory.base.ApplicationContext;
import lesson09.beanfactory.base.BeanFactory;
import lesson09.beanfactory.xmlbeanfactory.factory.XmlBeanFactory;

/**
 * @author Webb Dong
 * @description: ClassPathXmlApplicationContext
 * @date 2021-02-16 18:29
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private final BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String configLocation) {
        beanFactory = new XmlBeanFactory(configLocation);
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

}
