package lesson10.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 单例写法六、枚举方式
 * 此写法，线程安全，不能延迟加载，枚举本身的特性，防止克隆破坏、反序列化破坏和反射破坏
 * @author Webb Dong
 * @date 2021-02-22 01:36
 */
public enum Singleton6 {

    INSTANCE;

    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }

}
