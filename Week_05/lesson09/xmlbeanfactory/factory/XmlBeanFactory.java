package lesson09.xmlbeanfactory.factory;

import lesson09.xmlbeanfactory.factory.config.AopAdviceDefinition;
import lesson09.xmlbeanfactory.factory.config.AopAspectDefinition;
import lesson09.xmlbeanfactory.factory.config.AopPointcutDefinition;
import lesson09.xmlbeanfactory.factory.config.BeanConstructorArgDefinition;
import lesson09.xmlbeanfactory.factory.config.BeanDefinition;
import lesson09.xmlbeanfactory.factory.config.BeanPropertyDefinition;
import lesson09.xmlbeanfactory.factory.config.enums.AopAdviceTypeEnum;
import lesson09.xmlbeanfactory.factory.config.enums.BeanScopeTypeEnum;
import lesson09.xmlbeanfactory.util.AopUtils;
import lesson09.xmlbeanfactory.util.BeanInitUtils;
import lesson09.xmlbeanfactory.util.ListUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Webb Dong
 * @description: XML Bean 工厂
 * @date 2021-02-16 12:00
 */
@Getter
public class XmlBeanFactory {

    public static final String PROXY_TARGET_CLASS_ATTR_NAME = "proxy-target-class";

    public static final String POINTCUT_ELEMENT_NAME = "pointcut";

    public static final String ASPECT_ELEMENT_NAME = "aspect";

    private static final String ID_ATTRIBUTE_NAME = "id";

    private static final String VALUE_ATTRIBUTE_NAME = "value";

    private static final String CLASS_ATTRIBUTE_NAME = "class";

    /**
     * bean map
     * key: bean id, value: bean 实例
     */
    private final Map<String, Object> beanCaching = new HashMap<>();

    /**
     * aop 代理 bean map
     * key: aop bean id, value: aop bean 实例
     */
    private final Map<String, Object> aopProxyCaching = new HashMap<>();

    /**
     * bean 配置定义的 map
     * key: bean id, value: BeanDefinition
     */
    private final Map<String, BeanDefinition> beanDefCaching = new HashMap<>();

    /**
     * 切点配置定义 map
     * key: pointcut id, value: AOPPointcutDefinition
     */
    private final Map<String, AopPointcutDefinition> pointcutDefMap = new HashMap<>();

    /**
     * aop 切面配置定义 map
     * key: 目标 bean id, value: AOPAspectDefinition
     */
    private final Map<String, AopAspectDefinition> aspectDefMap = new HashMap<>();

    public XmlBeanFactory(String configLocation) {
        loadXml(configLocation);
    }

    /**
     * 获取 bean
     * @param name bean 的 id
     * @return 返回实例
     */
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

    /**
     * 加载 xml
     * @param configLocation xml 配置文件路径
     */
    @SneakyThrows
    private void loadXml(String configLocation) {
        try (InputStream in = XmlBeanFactory.class.getClassLoader().getResourceAsStream(configLocation)) {
            SAXReader saxReader = new SAXReader();
            Document doc;
            try {
                doc = saxReader.read(in);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
            Element rootElement = doc.getRootElement();
            init(rootElement);
        }
    }

    /**
     * 初始化
     * @param rootElement xml 根元素
     */
    private void init(Element rootElement) {
        CompletableFuture<Void> initBeanDefinitionsFuture = CompletableFuture.runAsync(
                () -> initBeanDefinitions(rootElement.elements(BeanDefinition.BEAN_ELEMENT_NAME)));
        CompletableFuture<Void> initAopConfigFuture = CompletableFuture.runAsync(
                () -> initAopConfig(rootElement.elements(BeanDefinition.CONFIG_ELEMENT_NAME)));
        CompletableFuture<Void> runAfterBoth = initBeanDefinitionsFuture.runAfterBoth(initAopConfigFuture,
                () -> {
                    initAllSingletonBeans();
                    initAllSingletonBeansAopProxyBeans();
                });
        runAfterBoth.join();
    }

    // ---------------------------- Bean 初始化相关 -----------------------------

    /**
     * 初始化所有单例模式的 bean
     */
    private void initAllSingletonBeans() {
        beanDefCaching.values().stream()
                .filter(b -> BeanScopeTypeEnum.SINGLETON == b.getScopeType())
                .collect(Collectors.toList())
                .forEach(b -> beanCaching.put(b.getId(), createAndInitBean(b)));
    }

    /**
     * 初始化所有单例模式的 bean 对应的 AOP 代理的 bean
     */
    private void initAllSingletonBeansAopProxyBeans() {
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
    private Object createAndInitSingletonBeanAopProxyBean(AopAspectDefinition ad) {
        Object target = beanCaching.get(ad.getTargetRef());
        if (target == null) {
            throw new RuntimeException("target bean not found");
        }
        Object aspect = beanCaching.get(ad.getRef());
        if (aspect == null) {
            throw new RuntimeException("aspect bean not found");
        }
        return createAopProxyBean(ad, target, aspect);
    }

    /**
     * 创建 AOP 代理对象
     * @param ad 切面定义
     * @param target 目标对象
     * @param aspect 切面对象
     * @return 返回 AOP 代理对象
     */
    private Object createAopProxyBean(AopAspectDefinition ad, Object target, Object aspect) {
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
     * 创建并初始化 bean
     * @param b bean 配置定义
     */
    @SneakyThrows
    private Object createAndInitBean(BeanDefinition b) {
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

    // ---------------------------- Bean 配置相关 -----------------------------

    /**
     * 初始化 bean 定义
     * @param beanElementList bean 标签集合
     */
    private void initBeanDefinitions(List<Element> beanElementList) {
        if (beanElementList == null) {
            return;
        }
        beanElementList.forEach(e -> {
            BeanDefinition beanDefinition = new BeanDefinition();
            String idVal = e.attributeValue(ID_ATTRIBUTE_NAME);
            String nameVal = e.attributeValue(BeanPropertyDefinition.NAME_ATTRIBUTE_NAME);
            String scopeVal = e.attributeValue(BeanDefinition.SCOPE_ATTRIBUTE_NAME);
            beanDefinition.setClassName(e.attributeValue(CLASS_ATTRIBUTE_NAME));

            if (scopeVal != null) {
                beanDefinition.setScopeType(BeanScopeTypeEnum.valueOfByName(scopeVal));
            } else {
                beanDefinition.setScopeType(BeanScopeTypeEnum.SINGLETON);
            }

            if (idVal != null) {
                beanDefinition.setId(idVal);
                if (nameVal == null) {
                    beanDefinition.setName(beanDefinition.getId());
                }
            }
            if (nameVal != null) {
                beanDefinition.setName(nameVal);
                if (idVal == null) {
                    beanDefinition.setId(beanDefinition.getName());
                }
            }

            List<Element> constructorArgElementList = e.elements(BeanDefinition.CONSTRUCTOR_ARG_ELEMENT_NAME);
            if (constructorArgElementList != null && constructorArgElementList.size() != 0) {
                initBeanConstructorArgElements(beanDefinition, constructorArgElementList);
            }
            List<Element> propertyElementList = e.elements(BeanDefinition.PROPERTY_ELEMENT_NAME);
            if (propertyElementList != null && propertyElementList.size() != 0) {
                initBeanPropertyElements(beanDefinition, propertyElementList);
            }

            beanDefCaching.put(beanDefinition.getId(), beanDefinition);
        });
    }

    /**
     * 初始化 bean 标签的 constructor-arg 子标签定义
     * @param beanDefinition bean 定义
     * @param elementList constructor-arg 标签集合
     */
    private void initBeanConstructorArgElements(BeanDefinition beanDefinition, List<Element> elementList) {
        beanDefinition.setConstructorArgDefList(elementList.stream().map(e -> {
            String indexAttrVal = e.attributeValue(BeanConstructorArgDefinition.INDEX_ATTRIBUTE_NAME);
            String valueAttrVal = e.attributeValue(VALUE_ATTRIBUTE_NAME);
            return BeanConstructorArgDefinition.builder()
                        .index(indexAttrVal != null ? Integer.parseInt(indexAttrVal) : null)
                        .value(valueAttrVal)
                        .build();
            })
            .collect(Collectors.toList()));
    }

    /**
     * 初始化 bean 标签的 property 子标签定义
     * @param beanDefinition bean 定义
     * @param elementList property 标签集合
     */
    private void initBeanPropertyElements(BeanDefinition beanDefinition, List<Element> elementList) {
        beanDefinition.setPropertyDefList(elementList.stream().map(e -> {
            String nameAttrVal = e.attributeValue(BeanPropertyDefinition.NAME_ATTRIBUTE_NAME);
            String valueAttrVal = e.attributeValue(VALUE_ATTRIBUTE_NAME);
            return BeanPropertyDefinition.builder()
                    .name(nameAttrVal)
                    .value(valueAttrVal)
                    .build();
        })
        .collect(Collectors.toList()));
    }

    // ---------------------------- AOP 配置相关 -----------------------------

    /**
     * 初始化 AOP 配置
     * @param aopConfigElementList aop config 标签集合
     */
    private void initAopConfig(List<Element> aopConfigElementList) {
        if (aopConfigElementList == null) {
            return;
        }
        aopConfigElementList.forEach(e -> {
            boolean proxyTargetClass = Boolean.parseBoolean(e.attributeValue(PROXY_TARGET_CLASS_ATTR_NAME));
            List<Element> pointcutElementList = e.elements(POINTCUT_ELEMENT_NAME);
            if (pointcutElementList != null && pointcutElementList.size() != 0) {
                initPointcutDefinitions(pointcutElementList);
            }
            List<Element> aspectElementList = e.elements(ASPECT_ELEMENT_NAME);
            if (aspectElementList != null && aspectElementList.size() != 0) {
                initAspectDefinitions(proxyTargetClass, aspectElementList);
            }
        });
    }

    /**
     * 初始化切点配置定义
     * @param elementList aop pointcut 标签集合
     */
    private void initPointcutDefinitions(List<Element> elementList) {
        elementList.forEach(e -> {
            String id = e.attributeValue(ID_ATTRIBUTE_NAME);
            pointcutDefMap.put(id, AopPointcutDefinition.builder()
                    .id(id)
                    .ref(e.attributeValue(AopAspectDefinition.REF_ATTRIBUTE_NAME))
                    .build());
        });
    }

    /**
     * 初始化切面配置定义
     * @param proxyTargetClass proxyTargetClass 属性
     * @param elementList aop aspect 标签集合
     */
    private void initAspectDefinitions(boolean proxyTargetClass, List<Element> elementList) {
        elementList.forEach(e -> {
            String pointcutRef = e.attributeValue(AopAspectDefinition.POINTCUT_REF_ATTRIBUTE_NAME);
            AopAspectDefinition aspectDef = AopAspectDefinition.builder()
                    .id(e.attributeValue(ID_ATTRIBUTE_NAME))
                    .ref(e.attributeValue(AopAspectDefinition.REF_ATTRIBUTE_NAME))
                    .targetRef(pointcutDefMap.get(pointcutRef).getRef())
                    .pointcutRef(pointcutRef)
                    .proxyTargetClass(proxyTargetClass)
                    .build();
            List<Element> adviceDefElementList = e.elements();
            if (adviceDefElementList != null && adviceDefElementList.size() != 0) {
                aspectDef.setAdviceDefMap(initAdviceDefinitions(adviceDefElementList));
            }
            aspectDefMap.put(aspectDef.getTargetRef(), aspectDef);
        });
    }

    /**
     * 初始化通知配置定义
     * @param elementList 通知标签集合
     * @return 返回 Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> 实例
     */
    private Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> initAdviceDefinitions(List<Element> elementList) {
        Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap = new HashMap<>();
        elementList.forEach(e -> {
            AopAdviceDefinition adviceDef = AopAdviceDefinition.builder()
                    .method(e.attributeValue(AopAdviceDefinition.METHOD_ATTRIBUTE_NAME))
                    .adviceType(AopAdviceTypeEnum.getAdviceTypeEnumByName(e.getName()))
                    .build();
            List<AopAdviceDefinition> adviceDefList = adviceDefMap.computeIfAbsent(
                    adviceDef.getAdviceType(), k -> new ArrayList<>(2));
            ListUtils.orderedAdd(adviceDefList, adviceDef,
                    AopAdviceTypeEnum.isAfterTypeAdvice(adviceDef.getAdviceType()));
        });
        return adviceDefMap;
    }

}
