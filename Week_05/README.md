# 目录介绍
- lesson09: 第五周第九课
  - aop: AOP 相关
    - bytebuddydp: byte-buddy 实现简单 AOP
    - cglibdp: cglib 实现简单 AOP
    - jdkdp: jdk 动态代理实现简单 AOP
  - beanfactory: 实现 Spring Bean 装配与 AOP 相关
    - annotationbeanfactory: 注解方式实现 Spring Bean 的装配与简单的 AOP
    - base: 公共类与抽象类
    - util: 工具类
    - xmlbeanfactory: XML 方式实现 Spring Bean 的装配与简单的 AOP
  - bytebuddyagent: byte-buddy 的 java agent
- lesson10: 第五周第十课
  - singleton: 单例相关
  - jdbc: JDBC 相关
  - hikaricp: HikariCP 数据库连接池相关
  - spring-boot-autoconfigure-starter-demo: 自动配置 starter 相关

# 第五周课程内容
## 第九课
> Spring Framework、Spring AOP、Spring Bean 原理、Spring XML 配置原理、Spring Messaging
## 第十课
> Spring Boot 原理、Spring Boot Starter、JDBC 与数据库连接池、Hibernate 与 MyBatis、
> Spring 集成 ORM/JPA、Spring Boot 集成 ORM/JPA

# 第五周作业
## 第九课:
> 1.使 Java 里的动态代理，实现一个简单的 AOP  
>> [1.1 JDK 动态代理方式实现一个简单的 AOP ](https://github.com/WebbDong/JAVA-01/tree/main/Week_05/lesson09/aop/jdkdp)   
>> [1.2 cglib 方式实现一个简单的 AOP](https://github.com/WebbDong/JAVA-01/tree/main/Week_05/lesson09/aop/cglibdp)  
>   
>> [简单 AOP 测试类](https://github.com/WebbDong/JAVA-01/blob/main/Week_05/lesson09/aop/AopTestMain.java)
> 
> 2.写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）
>> [2.1 XML 方式实现 Spring Bean 的装配与 AOP](https://github.com/WebbDong/JAVA-01/tree/main/Week_05/lesson09/beanfactory/xmlbeanfactory)   
>> [2.2 注解方式实现 Spring Bean 的装配与 AOP](https://github.com/WebbDong/JAVA-01/tree/main/Week_05/lesson09/beanfactory/annotationbeanfactory)   
> 
> [3.实现一个 Spring XML 自定义配置，配置一组 Bean，例如: Student/Klass/School](https://github.com/WebbDong/JAVA-01/blob/main/Week_05/lesson09/beanfactory/xmlbeanfactory/beans.xml)    
> 
> [4.尝试使用 ByteBuddy 实现一个简单的基于类的 AOP](https://github.com/WebbDong/JAVA-01/tree/main/Week_05/lesson09/aop/bytebuddydp)    
> 
> [5.尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入下的 AOP](https://github.com/WebbDong/JAVA-01/blob/main/Week_05/lesson09/bytebuddyagent/ByteBuddyAgentAopTest.java)      

## 第十课:
> 1.总结一下，单例的各种写法，比较它们的优劣。
>> [各种单例写法的对比测试](https://github.com/WebbDong/JAVA-01/blob/main/Week_05/lesson10/singleton/SingletonTest.java)   
>> 
>> 1.1 写法一、饿汉式
```java
public class Singleton1 implements Singleton, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private static final Singleton1 INSTANCE = new Singleton1();

    public static Singleton1 getInstance() {
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton1() {}

}
```
>> 此写法，线程安全，但是不支持延迟加载，存在单例被反序列化破坏、克隆破坏和反射破坏
> 
>> 1.2 写法二、懒汉式
```java
public class Singleton2 implements Singleton, Serializable, Cloneable {

    private static Singleton2 INSTANCE;

    public static Singleton2 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton2();
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton2() {}

}
```
>> 此写法，支持延迟加载，但存在线程安全问题，是错误写法，同时也存在单例被反序列化破坏、克隆破坏和反射破坏
> 
>> 1.3 写法三、懒汉式 + getInstance 方法锁
```java
public class Singleton3 implements Singleton, Serializable, Cloneable {

    private static Singleton3 INSTANCE;

    public static synchronized Singleton3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton3() {}

}
```
>> 此写法，支持延迟加载，线程安全，但是直接在 getInstance 加锁，锁粒度过大并发性能不好。同时也存在单例被反序列化破坏、克隆破坏和反射破坏
> 
>> 1.4 写法四、懒汉式 + 双重检查
```java
public class Singleton4 implements Singleton, Serializable, Cloneable {

    private static Singleton4 INSTANCE;

    public static Singleton4 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton4.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton4();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton4() {}

}
```
>> 此写法，支持延迟加载，线程安全、锁粒度减小并发性能有一定提升。此方式可能存在指令重排问题，要解决这个问题，我们需要给 instance 成员变量加上 volatile 关键字。
>> 可能此问题只会出现在低版本的 JDK 中，高版本中可能已经解决这个问题，需要考证。
> 
>> 1.5 写法五、懒汉式 + 双重检查 + volatile
```java
public class Singleton5 implements Singleton, Serializable, Cloneable {

    private volatile static Singleton5 INSTANCE;

    public static Singleton5 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton5.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton5();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton5() {}

}
```
>> 此写法，支持延迟加载，线程安全，锁粒度减小并发性能有一定提升。添加 volatile 确保防止指令重排，同时也存在单例被反序列化破坏、克隆破坏和反射破坏
> 
>> 1.6 写法六、枚举方式
```java
public enum Singleton6 {

    INSTANCE;

    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }

}
```
>> 此写法，线程安全，不能延迟加载，枚举本身的特性，防止克隆破坏、反序列化破坏和反射破坏
> 
>> 1.7 写法七、内部类方式
```java
public class Singleton7 implements Singleton, Serializable, Cloneable {

    private static class LazyHolder {

        private static final Singleton7 INSTANCE = new Singleton7();

    }

    public static Singleton7 getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton7() {}

}
``` 
>> 此写法，支持延迟加载，线程安全。同时也存在单例被反序列化破坏、克隆破坏和反射破坏
> 
>> 1.8 法八、懒汉式、双重检查 + volatile，同时防止克隆破坏、反序列化破坏和反射破坏
```java
public class Singleton8 implements Singleton, Serializable, Cloneable {

    private volatile static Singleton8 INSTANCE;

    private static boolean firstCreate = true;

    public static Singleton8 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton8.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton8();
                }
            }
        }
        return INSTANCE;
    }

    // 防止克隆破坏单例
    @Override
    public Object clone() throws CloneNotSupportedException {
        return INSTANCE;
    }

    // 防止反序列化破坏单例
    private Object readResolve() {
        return INSTANCE;
    }

    // 防止反射破坏单例
    private Singleton8() {
        if (firstCreate) {
            synchronized (Singleton8.class) {
                if (firstCreate) {
                    firstCreate = false;
                }
            }
        } else {
            throw new RuntimeException("单例模式无法再次实例化");
        }
    }

}
```
>> 1. 通过重写 Cloneable 接口的 clone 方法直接返回单例实例来防止克隆破坏。
>> 2. 通过定义 readResolve 方法直接返回单例实例来防止反序列化破坏。
>> 3. 通过定义私有无参构造器并定义一个全局布尔变量来防止反射破坏
> 
> 2.[使用 JDBC 原生接口，实现数据库的增删改查操作](https://github.com/WebbDong/JAVA-01/blob/main/Week_05/lesson10/jdbc/JdbcCrud.java)   
> 3.[使用事务，PrepareStatement 方式，批处理方式，改进上述操作](https://github.com/WebbDong/JAVA-01/blob/main/Week_05/lesson10/jdbc/JdbcPrepareStatementTransaction.java)   
> 4.[配置 Hikari 连接池，改进上述操作](https://github.com/WebbDong/JAVA-01/tree/main/Week_05/lesson10/hikaricp)    