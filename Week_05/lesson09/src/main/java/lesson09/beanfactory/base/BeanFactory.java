package lesson09.beanfactory.base;

/**
 * @author Webb Dong
 * @description: Bean 工厂接口
 * @date 2021-02-21 13:58
 */
public interface BeanFactory {

    /**
     * 获取 bean
     * @param name bean 的 id 或 name
     * @return 返回实例
     */
    Object getBean(String name);

}
