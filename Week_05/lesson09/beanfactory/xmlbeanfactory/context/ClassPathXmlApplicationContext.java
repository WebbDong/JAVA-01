package lesson09.beanfactory.xmlbeanfactory.context;

import lesson09.beanfactory.xmlbeanfactory.factory.XmlBeanFactory;

/**
 * @author Webb Dong
 * @description: ClassPathXmlApplicationContext
 * @date 2021-02-16 18:29
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private final XmlBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String configLocation) {
        beanFactory = new XmlBeanFactory(configLocation);
    }

    @Override
    public Object getBeans(String name) {
        return beanFactory.getBean(name);
    }

}
