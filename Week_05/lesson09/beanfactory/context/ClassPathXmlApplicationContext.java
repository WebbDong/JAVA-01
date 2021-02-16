package lesson09.beanfactory.context;

import lesson09.beanfactory.factory.BeanFactory;

/**
 * @author Webb Dong
 * @description: ClassPathXmlApplicationContext
 * @date 2021-02-16 18:29
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String configLocation) {
        beanFactory = new BeanFactory(configLocation);
    }

    @Override
    public Object getBeans(String name) {
        return beanFactory.getBean(name);
    }

}
