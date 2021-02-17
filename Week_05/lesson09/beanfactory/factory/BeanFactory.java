package lesson09.beanfactory.factory;

import lesson09.beanfactory.factory.config.AopAdviceDefinition;
import lesson09.beanfactory.factory.config.AopAspectDefinition;
import lesson09.beanfactory.factory.config.AopPointcutDefinition;
import lesson09.beanfactory.factory.config.BeanConstructorArgDefinition;
import lesson09.beanfactory.factory.config.BeanDefinition;
import lesson09.beanfactory.factory.config.BeanPropertyDefinition;
import lesson09.beanfactory.factory.config.enums.AopAdviceTypeEnum;
import lesson09.beanfactory.factory.config.enums.BeanScopeTypeEnum;
import lesson09.beanfactory.util.AopUtils;
import lesson09.beanfactory.util.BeanInitUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
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
 * @description: Bean 工厂
 * @date 2021-02-16 12:00
 */
@Getter
public class BeanFactory {

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
    private final Map<String, BeanDefinition> beanDefContainer = new HashMap<>();

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

    public BeanFactory(String configLocation) {
        loadXml(configLocation);
    }

    /**
     * 获取 bean
     * @param name
     * @return
     */
    public Object getBean(String name) {
        return beanCaching.get(name);
    }

    /**
     * 加载 xml
     * @param configLocation
     */
    private void loadXml(String configLocation) {
        try (InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream(configLocation)) {
            SAXReader saxReader = new SAXReader();
            Document doc;
            try {
                doc = saxReader.read(in);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
            Element rootElement = doc.getRootElement();
            init(rootElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化
     * @param rootElement
     */
    private void init(Element rootElement) {
        CompletableFuture<Void> initBeanDefinitionsFuture = CompletableFuture.runAsync(
                () -> initBeanDefinitions(rootElement.elements(BeanDefinition.BEAN_ELEMENT_NAME)));
        CompletableFuture<Void> initAopConfigFuture = CompletableFuture.runAsync(
                () -> initAopConfig(rootElement.elements(BeanDefinition.CONFIG_ELEMENT_NAME)));
        CompletableFuture<Void> runAfterBoth = initBeanDefinitionsFuture.runAfterBoth(initAopConfigFuture,
                () -> {
                    initBeans();
                    initAopProxyBeans();
                });
        runAfterBoth.join();
    }

    // ---------------------------- Bean 初始化相关 -----------------------------

    /**
     * 初始化所有单例模式的 bean
     */
    private void initBeans() {
        beanDefContainer.values().stream()
                .filter(b -> BeanScopeTypeEnum.SINGLETON == b.getScopeType())
                .collect(Collectors.toList())
                .forEach(b -> initBean(b));
    }

    /**
     * 初始化所有单例模式的 bean 对应的 AOP 代理
     */
    private void initAopProxyBeans() {
        aspectDefMap.values().stream()
                .forEach((ad) -> {
                    Object target = beanCaching.get(ad.getTargetRef());
                    if (target == null) {
                        throw new RuntimeException("target bean not found");
                    }
                    Object aspect = beanCaching.get(ad.getRef());
                    if (aspect == null) {
                        throw new RuntimeException("aspect bean not found");
                    }
                    Object aopProxy;
                    if (ad.isProxyTargetClass() || target.getClass().getInterfaces().length == 0) {
                        aopProxy = AopUtils.createAOPProxyWithCglib(target, aspect,
                                target.getClass().getSuperclass(),
                                aspectDefMap.get(ad.getTargetRef()).getAdviceDefMap());
                    } else {
                        aopProxy = AopUtils.createAOPProxyWithJDK(target, aspect,
                                target.getClass().getInterfaces(),
                                aspectDefMap.get(ad.getTargetRef()).getAdviceDefMap());
                    }
                    aopProxyCaching.put(ad.getTargetRef(), aopProxy);
                });
    }

    /**
     * 初始化 bean
     * @param b
     */
    @SneakyThrows
    private void initBean(BeanDefinition b) {
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
        beanCaching.put(b.getId(), bean);
    }

    /**
     * 根据属性初始化 bean
     * @param clazz
     * @param propertyDefList
     * @return
     */
    @SneakyThrows
    private Object initBeanByProperty(Class<?> clazz, List<BeanPropertyDefinition> propertyDefList) {
        Object bean = clazz.newInstance();
        for (int i = 0, size = propertyDefList.size(); i < size; i++) {
            BeanPropertyDefinition bpd = propertyDefList.get(i);
            Field field = clazz.getDeclaredField(bpd.getName());
            field.setAccessible(true);
            field.set(bean, BeanInitUtils.getRealTypeValue(field.getType(), bpd.getValue()));
        }
        return bean;
    }

    /**
     * 根据构造函数初始化 bean
     * @param clazz
     * @param constructorArgDefList
     */
    @SneakyThrows
    private Object initBeanByConstructor(Class<?> clazz, List<BeanConstructorArgDefinition> constructorArgDefList) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors == null || constructors.length == 0) {
            throw new RuntimeException("no constructors");
        }
        Constructor<?> constructor = null;
        for (int i = 0, size = constructorArgDefList.size(); i < constructors.length; i++) {
            constructor = constructors[i];
            if (constructor.getParameterCount() == size) {
                break;
            }
        }
        if (constructor == null) {
            throw new RuntimeException("no constructor match");
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
     * @param beanElementList
     */
    private void initBeanDefinitions(List<Element> beanElementList) {
        if (beanElementList == null) {
            return;
        }
        for (int i = 0, size = beanElementList.size(); i < size; i++) {
            Element beanElement = beanElementList.get(i);
            BeanDefinition beanDefinition = new BeanDefinition();
            String idVal = beanElement.attributeValue(ID_ATTRIBUTE_NAME);
            String nameVal = beanElement.attributeValue(BeanPropertyDefinition.NAME_ATTRIBUTE_NAME);
            String scopeVal = beanElement.attributeValue(BeanDefinition.SCOPE_ATTRIBUTE_NAME);
            beanDefinition.setClassName(beanElement.attributeValue(CLASS_ATTRIBUTE_NAME));

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

            List<Element> constructorArgElementList = beanElement.elements(BeanDefinition.CONSTRUCTOR_ARG_ELEMENT_NAME);
            if (constructorArgElementList != null && constructorArgElementList.size() != 0) {
                initBeanConstructorArgElements(beanDefinition, constructorArgElementList);
            }
            List<Element> propertyElementList = beanElement.elements(BeanDefinition.PROPERTY_ELEMENT_NAME);
            if (propertyElementList != null && propertyElementList.size() != 0) {
                initBeanPropertyElements(beanDefinition, propertyElementList);
            }

            beanDefContainer.put(beanDefinition.getId(), beanDefinition);
        }
    }

    /**
     * 初始化 bean 标签的 constructor-arg 子标签定义
     * @param beanDefinition
     * @param elementList
     */
    private void initBeanConstructorArgElements(BeanDefinition beanDefinition, List<Element> elementList) {
        List<BeanConstructorArgDefinition> definitionList = new ArrayList<>(elementList.size());
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
            String indexAttrVal = e.attributeValue(BeanConstructorArgDefinition.INDEX_ATTRIBUTE_NAME);
            String valueAttrVal = e.attributeValue(VALUE_ATTRIBUTE_NAME);
            definitionList.add(BeanConstructorArgDefinition.builder()
                    .index(indexAttrVal != null ? Integer.parseInt(indexAttrVal) : null)
                    .value(valueAttrVal)
                    .build());
        }
        beanDefinition.setConstructorArgDefList(definitionList);
    }

    /**
     * 初始化 bean 标签的 property 子标签定义
     * @param beanDefinition
     * @param elementList
     */
    private void initBeanPropertyElements(BeanDefinition beanDefinition, List<Element> elementList) {
        List<BeanPropertyDefinition> definitionList = new ArrayList<>(elementList.size());
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
            String nameAttrVal = e.attributeValue(BeanPropertyDefinition.NAME_ATTRIBUTE_NAME);
            String valueAttrVal = e.attributeValue(VALUE_ATTRIBUTE_NAME);
            definitionList.add(BeanPropertyDefinition.builder()
                    .name(nameAttrVal)
                    .value(valueAttrVal)
                    .build());
        }
        beanDefinition.setPropertyDefList(definitionList);
    }

    // ---------------------------- AOP 配置相关 -----------------------------

    /**
     * 初始化 AOP 配置
     * @param aopConfigElementList
     */
    private void initAopConfig(List<Element> aopConfigElementList) {
        if (aopConfigElementList == null) {
            return;
        }
        for (int i = 0, size = aopConfigElementList.size(); i < size; i++) {
            Element e = aopConfigElementList.get(i);
            boolean proxyTargetClass = Boolean.valueOf(e.attributeValue(PROXY_TARGET_CLASS_ATTR_NAME));
            List<Element> pointcutElementList = e.elements(POINTCUT_ELEMENT_NAME);
            if (pointcutElementList != null && pointcutElementList.size() != 0) {
                initPointcutDefinitions(pointcutElementList);
            }
            List<Element> aspectElementList = e.elements(ASPECT_ELEMENT_NAME);
            if (aspectElementList != null && aspectElementList.size() != 0) {
                initAspectDefinitions(proxyTargetClass, aspectElementList);
            }
        }
    }

    /**
     * 初始化切点配置定义
     * @return
     * @param elementList
     */
    private void initPointcutDefinitions(List<Element> elementList) {
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
            String id = e.attributeValue(ID_ATTRIBUTE_NAME);
            pointcutDefMap.put(id, AopPointcutDefinition.builder()
                    .id(id)
                    .ref(e.attributeValue(AopAspectDefinition.REF_ATTRIBUTE_NAME))
                    .build());
        }
    }

    /**
     * 初始化切面配置定义
     * @param proxyTargetClass
     * @param elementList
     */
    private void initAspectDefinitions(boolean proxyTargetClass, List<Element> elementList) {
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
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
        }
    }

    /**
     * 初始化通知配置定义
     * @param elementList
     * @return
     */
    private Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> initAdviceDefinitions(List<Element> elementList) {
        Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap = new HashMap<>();
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
            AopAdviceDefinition adviceDef = AopAdviceDefinition.builder()
                    .method(e.attributeValue(AopAdviceDefinition.METHOD_ATTRIBUTE_NAME))
                    .adviceType(AopAdviceTypeEnum.getAdviceTypeEnumByName(e.getName()))
                    .build();
            List<AopAdviceDefinition> adviceDefList = adviceDefMap.get(adviceDef.getAdviceType());
            if (adviceDefList == null) {
                adviceDefList = new ArrayList<>(2);
                adviceDefMap.put(adviceDef.getAdviceType(), adviceDefList);
            }
            adviceDefList.add(adviceDef);
        }
        return adviceDefMap;
    }

}
