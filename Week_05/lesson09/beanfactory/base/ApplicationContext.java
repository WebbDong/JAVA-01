package lesson09.beanfactory.base;

/**
 * @author Webb Dong
 * @description: ApplicationContext
 * @date 2021-02-16 16:59
 */
public interface ApplicationContext {

    /**
     * 获取 bean
     * @param name bean 的 id 或 name
     * @return 返回实例
     */
    Object getBean(String name);

}
