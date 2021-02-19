package lesson09.xmlbeanfactory.context;

/**
 * @author Webb Dong
 * @description: ApplicationContext
 * @date 2021-02-16 16:59
 */
public interface ApplicationContext {

    Object getBeans(String name);

}
