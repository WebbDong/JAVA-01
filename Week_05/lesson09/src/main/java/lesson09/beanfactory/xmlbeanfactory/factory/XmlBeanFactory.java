package lesson09.beanfactory.xmlbeanfactory.factory;

import lesson09.beanfactory.base.AbstractBeanFactory;
import lesson09.beanfactory.base.beanfactory.config.AopAdviceDefinition;
import lesson09.beanfactory.base.beanfactory.config.AopAspectDefinition;
import lesson09.beanfactory.base.beanfactory.config.AopPointcutDefinition;
import lesson09.beanfactory.base.beanfactory.config.BeanConstructorArgDefinition;
import lesson09.beanfactory.base.beanfactory.config.BeanDefinition;
import lesson09.beanfactory.base.beanfactory.config.BeanPropertyDefinition;
import lesson09.beanfactory.base.beanfactory.config.enums.AopAdviceTypeEnum;
import lesson09.beanfactory.base.beanfactory.config.enums.BeanScopeTypeEnum;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
public class XmlBeanFactory extends AbstractBeanFactory {

    public static final String PROXY_TARGET_CLASS_ATTR_NAME = "proxy-target-class";

    public static final String POINTCUT_ELEMENT_NAME = "pointcut";

    public static final String ASPECT_ELEMENT_NAME = "aspect";

    private static final String ID_ATTRIBUTE_NAME = "id";

    private static final String VALUE_ATTRIBUTE_NAME = "value";

    private static final String CLASS_ATTRIBUTE_NAME = "class";

    public XmlBeanFactory(String configLocation) {
        loadXml(configLocation);
    }

    /**
     * 加载 xml
     * @param configLocation xml 配置文件路径
     */
    @SneakyThrows
    private void loadXml(String configLocation) {
        try (InputStream in = XmlBeanFactory.class.getClassLoader().getResourceAsStream(configLocation)) {
            if (in == null) {
                throw new FileNotFoundException(configLocation);
            }

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
            String id = e.attributeValue(ID_ATTRIBUTE_NAME);
            String ref = e.attributeValue(AopAspectDefinition.REF_ATTRIBUTE_NAME);
            AopAspectDefinition aspectDef = AopAspectDefinition.builder()
                    .id(id != null ? id : ref)
                    .ref(ref)
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
        elementList.forEach(e -> initAdviceDefMap(adviceDefMap,
                e.attributeValue(AopAdviceDefinition.METHOD_ATTRIBUTE_NAME),
                AopAdviceTypeEnum.getAdviceTypeEnumByName(e.getName())));
        return adviceDefMap;
    }

}
