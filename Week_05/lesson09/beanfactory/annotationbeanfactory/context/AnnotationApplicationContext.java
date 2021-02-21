package lesson09.beanfactory.annotationbeanfactory.context;

import lesson09.beanfactory.annotationbeanfactory.factory.AnnotationBeanFactory;
import lesson09.beanfactory.base.ApplicationContext;
import lesson09.beanfactory.base.BeanFactory;

/**
 * @author Webb Dong
 * @description: AnnotationApplicationContext
 * @date 2021-02-21 14:45
 */
public class AnnotationApplicationContext implements ApplicationContext {

    private final BeanFactory beanFactory;

    public AnnotationApplicationContext(Class<?> primarySource) {
        beanFactory = new AnnotationBeanFactory(primarySource);
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

}
