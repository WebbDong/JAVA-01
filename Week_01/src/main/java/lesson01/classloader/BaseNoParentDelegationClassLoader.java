package lesson01.classloader;

/**
 * @author Webb Dong
 * @description: 抽象 ClassLoader，将打破打破双亲委派机制的 loadClass 方法抽象出来
 * @date 2021-01-07 21:32
 */
public abstract class BaseNoParentDelegationClassLoader extends ClassLoader {

    /**
     * 重写 loadClass 打破双亲委派机制
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) {
        synchronized (getClassLoadingLock(name)) {
            // 查看此类是否以及被加载过，如果已经加载过，直接从缓存中获取
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    // 排除 jdk 的包，自己的类才打破双亲委派机制
                    if (!(name.startsWith("java") || name.startsWith("jdk")
                            || name.startsWith("sun"))) {
                        c = this.findClass(name);
                    } else {
                        c = this.getParent().loadClass(name);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

}
