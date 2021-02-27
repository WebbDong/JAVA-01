package lesson09.beanfactory.base;

import lesson09.beanfactory.base.beanfactory.config.AopAdviceDefinition;
import lesson09.beanfactory.base.beanfactory.config.AopAspectDefinition;
import lesson09.beanfactory.base.beanfactory.config.AopPointcutDefinition;
import lesson09.beanfactory.base.beanfactory.config.BeanConstructorArgDefinition;
import lesson09.beanfactory.base.beanfactory.config.BeanDefinition;
import lesson09.beanfactory.base.beanfactory.config.BeanPropertyDefinition;
import lesson09.beanfactory.base.beanfactory.config.enums.AopAdviceTypeEnum;
import lesson09.beanfactory.base.beanfactory.config.enums.BeanScopeTypeEnum;
import lesson09.beanfactory.util.AopUtils;
import lesson09.beanfactory.util.BeanInitUtils;
import lesson09.beanfactory.util.ListUtils;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: 抽象 Bean 工厂
 * @date 2021-02-21 14:06
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    /**
     * bean map
     * key: bean id, value: bean 实例
     */
    protected final Map<String, Object> beanCaching = new HashMap<>();

    /**
     * aop 代理 bean map
     * key: aop bean id, value: aop bean 实例
     */
    protected final Map<String, Object> aopProxyCaching = new HashMap<>();

    /**
     * bean 配置定义的 map
     * key: bean id, value: BeanDefinition
     */
    protected final Map<String, BeanDefinition> beanDefCaching = new HashMap<>();

    /**
     * 切点配置定义 map
     * key: pointcut id, value: AOPPointcutDefinition
     */
    protected final Map<String, AopPointcutDefinition> pointcutDefMap = new HashMap<>();

    /**
     * aop 切面配置定义 map
     * key: 目标 bean id, value: AOPAspectDefinition
     */
    protected final Map<String, AopAspectDefinition> aspectDefMap = new HashMap<>();

    @Override
    public Object getBean(String name) {
        BeanDefinition beanDef = beanDefCaching.get(name);
        if (beanDef.getScopeType() == BeanScopeTypeEnum.SINGLETON) {
            // 单例模式
            Object bean = aopProxyCaching.get(name);
            if (bean == null) {
                bean = beanCaching.get(name);
            }
            return bean;
        } else {
            // 原型模式，每次获取都创建新对象
            return newBean(name);
        }
    }

    /**
     * 创建并初始化 bean
     * @param b bean 配置定义
     */
    @SneakyThrows
    protected Object createAndInitBean(BeanDefinition b) {
        Class<?> clazz = Class.forName(b.getClassName());
        List<BeanConstructorArgDefinition> constructorArgDefList = b.getConstructorArgDefList();
        List<BeanPropertyDefinition> propertyDefList = b.getPropertyDefList();
        Object bean;
        if (propertyDefList != null && propertyDefList.size() != 0) {
            bean = initBeanByProperty(clazz, propertyDefList);
        } else if (constructorArgDefList != null && constructorArgDefList.size() != 0) {
            bean = initBeanByConstructor(clazz, constructorArgDefList);
        } else {
            bean = clazz.newInstance();
        }
        return bean;
    }

    /**
     * 创建 AOP 代理对象
     * @param ad 切面定义
     * @param target 目标对象
     * @param aspect 切面对象
     * @return 返回 AOP 代理对象
     */
    protected Object createAopProxyBean(AopAspectDefinition ad, Object target, Object aspect) {
        Object aopProxy;
        if (ad.isProxyTargetClass() || target.getClass().getInterfaces().length == 0) {
            aopProxy = AopUtils.createAOPProxyWithCglib(target, aspect,
                    target.getClass(),
                    aspectDefMap.get(ad.getTargetRef()).getAdviceDefMap());
        } else {
            aopProxy = AopUtils.createAOPProxyWithJDK(target, aspect,
                    target.getClass().getInterfaces(),
                    aspectDefMap.get(ad.getTargetRef()).getAdviceDefMap());
        }
        return aopProxy;
    }

    /**
     * 初始化 AdviceDefMap
     * @param adviceDefMap Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> 实例
     * @param methodName 通知方法名
     * @param adviceType 通知类型
     */
    protected void initAdviceDefMap(Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap,
                                    String methodName,
                                    AopAdviceTypeEnum adviceType) {
        AopAdviceDefinition adviceDef = AopAdviceDefinition.builder()
                .method(methodName)
                .adviceType(adviceType)
                .build();
        List<AopAdviceDefinition> adviceDefList = adviceDefMap.computeIfAbsent(
                adviceDef.getAdviceType(), k -> new ArrayList<>(2));
        ListUtils.orderedAdd(adviceDefList, adviceDef,
                AopAdviceTypeEnum.isAfterTypeAdvice(adviceDef.getAdviceType()));
    }

    /**
     * 初始化所有单例模式的 bean 对应的 AOP 代理的 bean
     */
    protected void initAllSingletonBeansAopProxyBeans() {
        aspectDefMap.values()
                .forEach((ad) -> {
                    BeanDefinition bd = beanDefCaching.get(ad.getTargetRef());
                    if (bd == null || bd.getScopeType() != BeanScopeTypeEnum.SINGLETON) {
                        return;
                    }
                    aopProxyCaching.put(ad.getTargetRef(), createAndInitSingletonBeanAopProxyBean(ad));
                });
    }

    /**
     * 创建初始化单例模式的 bean 对应的 AOP 代理的 bean
     * @param ad 切面定义
     * @return 返回 AOP 代理对象
     */
    @SneakyThrows
    private Object createAndInitSingletonBeanAopProxyBean(AopAspectDefinition ad) {
        Object target = beanCaching.get(ad.getTargetRef());
        if (target == null) {
            throw new RuntimeException("target bean not found");
        }
        Object aspect = beanCaching.get(ad.getRef());
        if (aspect == null) {
            BeanDefinition beanDef = beanDefCaching.get(ad.getRef());
            aspect = Class.forName(beanDef.getClassName()).newInstance();
        }
        return createAopProxyBean(ad, target, aspect);
    }

    /**
     * 根据属性初始化 bean
     * @param clazz 字节码 class 对象
     * @param propertyDefList 属性定义集合
     * @return 返回创建的实例
     */
    @SneakyThrows
    private Object initBeanByProperty(final Class<?> clazz, List<BeanPropertyDefinition> propertyDefList) {
        Object bean = clazz.newInstance();
        propertyDefList.forEach(bpd -> {
            Class<?> beanClass = clazz;
            while (true) {
                try {
                    Field field = beanClass.getDeclaredField(bpd.getName());
                    field.setAccessible(true);
                    field.set(bean, BeanInitUtils.getRealTypeValue(field.getType(), bpd.getValue()));
                    break;
                } catch (NoSuchFieldException e) {
                    beanClass = beanClass.getSuperclass();
                    if (beanClass == Object.class) {
                        throw new RuntimeException(e);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return bean;
    }

    /**
     * 根据构造函数初始化 bean
     * @param clazz 字节码 class 对象
     * @param constructorArgDefList 构造器定义集合
     * @return 返回初始化的实例
     */
    @SneakyThrows
    private Object initBeanByConstructor(Class<?> clazz, List<BeanConstructorArgDefinition> constructorArgDefList) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            throw new RuntimeException("no constructors");
        }
        Constructor<?> constructor = null;
        for (int i = 0, size = constructorArgDefList.size(); i < constructors.length; i++) {
            constructor = constructors[i];
            if (constructor.getParameterCount() == size) {
                break;
            }
        }
        final Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] args = constructorArgDefList.stream()
                .sorted(Comparator.comparing(BeanConstructorArgDefinition::getIndex))
                .map(cad -> BeanInitUtils.getRealTypeValue(paramTypes[cad.getIndex()], cad.getValue()))
                .toArray();
        return constructor.newInstance(args);
    }

    /**
     * 创建新的 bean
     * @param name bean 的 id
     * @return 返回新的实例
     */
    private Object newBean(String name) {
        BeanDefinition bd = beanDefCaching.get(name);
        if (bd == null) {
            return null;
        }

        Object bean = createAndInitBean(bd);
        AopAspectDefinition aspectDef = aspectDefMap.get(name);
        if (aspectDef == null) {
            return bean;
        }

        return createAopProxyBean(aspectDef, bean, createAndInitBean(beanDefCaching.get(aspectDef.getRef())));
    }

}
