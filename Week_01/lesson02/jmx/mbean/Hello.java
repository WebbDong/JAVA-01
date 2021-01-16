package lesson02.jmx.mbean;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Webb Dong
 * @description: MBean 实现类，MBean 接口和 Mbean 实现类必须在同一个包
 * @date 2021-01-16 10:40
 */
public class Hello implements HelloMBean {

    private String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String print() {
        return "Hello " + name;
    }

    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("lesson02.jmx.mbean:type=Hello");
        Hello hello = new Hello();
        hello.setName("MBean 1");
        // hello 对象注册到 MBeanServer
        mbs.registerMBean(hello, objectName);
        // 属性名首字母必须大写
        mbs.setAttribute(objectName, new Attribute("Name", hello.getName()));
        // 第三个参数为 Object[] 为传入的参数值，第四个参数为 String[] 为指明参数类型
        mbs.invoke(objectName, "print", null, null);

        // 创建一个线程，每隔一秒修改 hello 的 name 属性
        // 在支持 MBeans 的图形化工具中，可以查看属性的变化和调用定义的 print 操作方法
        new Thread(() -> IntStream.range(2, Integer.MAX_VALUE).boxed().forEach(i -> {
            hello.setName("MBean " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }), "SetHelloNameThread-1").start();

        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

}
