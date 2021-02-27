package lesson02.jmx.mbean;

/**
 * @author Webb Dong
 * @description: MBean 接口，必须以 MBean 结尾（约定）
 * @date 2021-01-16 10:38
 */
public interface HelloMBean {

    void setName(String name);

    String getName();

    String print();

}
