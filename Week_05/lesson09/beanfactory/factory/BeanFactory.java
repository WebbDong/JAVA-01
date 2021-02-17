package lesson09.beanfactory.factory;

import lesson09.beanfactory.factory.config.AOPAdviceDefinition;
import lesson09.beanfactory.factory.config.AOPAspectDefinition;
import lesson09.beanfactory.factory.config.AOPConfigDefinition;
import lesson09.beanfactory.factory.config.AOPPointcutDefinition;
import lesson09.beanfactory.factory.config.BeanConstructorArgDefinition;
import lesson09.beanfactory.factory.config.BeanDefinition;
import lesson09.beanfactory.factory.config.BeanPropertyDefinition;
import lesson09.beanfactory.factory.config.enums.AOPAdviceTypeEnum;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author Webb Dong
 * @description: Bean 工厂
 * @date 2021-02-16 12:00
 */
public class BeanFactory {

    private static final String ID_ATTRIBUTE_NAME = "id";

    private static final String VALUE_ATTRIBUTE_NAME = "value";

    private static final String CLASS_ATTRIBUTE_NAME = "class";

    /**
     * bean 的 map
     */
    private final Map<String, Object> beanContainer = new HashMap<>();

    /**
     * bean 定义的 map
     */
    private final Map<String, Object> beanDefContainer = new HashMap<>();

    /**
     * aop 配置定义集合
     */
    private final List<AOPConfigDefinition> aopConfigDefContainer = new ArrayList<>();

    /**
     * aop 切面 map
     */
    private final Map<String, AOPAspectDefinition> aspectDefMap = new HashMap<>();

    public BeanFactory(String configLocation) {
        loadXml(configLocation);
    }

    /**
     * 获取 bean
     * @param name
     * @return
     */
    public Object getBean(String name) {
        return beanContainer.get(name);
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
            System.out.println("runAfterBoth");
        });
        runAfterBoth.join();
        System.out.println();
    }

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
            Attribute idAttr = beanElement.attribute(ID_ATTRIBUTE_NAME);
            Attribute nameAttr = beanElement.attribute(BeanPropertyDefinition.NAME_ATTRIBUTE_NAME);
            beanDefinition.setClassName(beanElement.attributeValue(CLASS_ATTRIBUTE_NAME));
            if (idAttr != null) {
                beanDefinition.setId(idAttr.getValue());
                if (nameAttr == null) {
                    beanDefinition.setName(beanDefinition.getId());
                }
            }
            if (nameAttr != null) {
                beanDefinition.setName(nameAttr.getValue());
                if (idAttr == null) {
                    beanDefinition.setId(beanDefinition.getName());
                }
            }
            List<Element> constructorArgElementList = beanElement.elements(BeanDefinition.CONSTRUCTOR_ARG_ELEMENT_NAME);
            List<Element> propertyElementList = beanElement.elements(BeanDefinition.PROPERTY_ELEMENT_NAME);
            if (constructorArgElementList != null) {
                initBeanConstructorArgElements(beanDefinition, constructorArgElementList);
            }
            if (propertyElementList != null) {
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

    /**
     * 初始化 aop 配置
     * @param aopConfigElementList
     */
    private void initAopConfig(List<Element> aopConfigElementList) {
        if (aopConfigElementList == null) {
            return;
        }
        for (int i = 0, size = aopConfigElementList.size(); i < size; i++) {
            Element e = aopConfigElementList.get(i);
            String attrValue = e.attributeValue(AOPConfigDefinition.PROXY_TARGET_CLASS_ATTR_NAME);
            AOPConfigDefinition aopConfig = new AOPConfigDefinition();
            aopConfig.setProxyTargetClass(Boolean.valueOf(attrValue));

            List<Element> pointcutElementList = e.elements(AOPConfigDefinition.POINTCUT_ELEMENT_NAME);
            if (pointcutElementList != null) {
                initPointcutDefinitions(pointcutElementList);
            }
            List<Element> aspectElementList = e.elements(AOPConfigDefinition.ASPECT_ELEMENT_NAME);
            if (aspectElementList != null) {
                initAspectDefinitions(aspectElementList);
            }

            aopConfigDefContainer.add(aopConfig);
        }
    }

    /**
     * 初始化切面配置定义
     * @param elementList
     */
    private void initAspectDefinitions(List<Element> elementList) {
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
            AOPAspectDefinition aspectDef = AOPAspectDefinition.builder()
                    .id(e.attributeValue(ID_ATTRIBUTE_NAME))
                    .ref(e.attributeValue(AOPAspectDefinition.REF_ATTRIBUTE_NAME))
                    .build();

            List<Element> adviceDefElementList = e.elements();
            if (adviceDefElementList != null) {
                aspectDef.setAdviceDefSet(initAdviceDefinitions(adviceDefElementList));
            }

            aspectDefMap.put(aspectDef.getRef(), aspectDef);
        }
    }

    /**
     * 初始化通知配置定义
     * @param elementList
     * @return
     */
    private Set<AOPAdviceDefinition> initAdviceDefinitions(List<Element> elementList) {
        Set<AOPAdviceDefinition> adviceDefSet = new HashSet<>(elementList.size());
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
            AOPAdviceDefinition adviceDef = AOPAdviceDefinition.builder()
                    .method(e.attributeValue(AOPAdviceDefinition.METHOD_ATTRIBUTE_NAME))
                    .pointcutRef(e.attributeValue(AOPAdviceDefinition.POINTCUT_REF_ATTRIBUTE_NAME))
                    .adviceType(AOPAdviceTypeEnum.getAdviceTypeEnumByName(e.getName()))
                    .build();
            adviceDefSet.add(adviceDef);
        }
        return adviceDefSet;
    }

    /**
     * 初始化切点配置定义
     * @return
     * @param elementList
     */
    private List<AOPPointcutDefinition> initPointcutDefinitions(List<Element> elementList) {
        List<AOPPointcutDefinition> pointcutDefList = new ArrayList<>(elementList.size());
        for (int i = 0, size = elementList.size(); i < size; i++) {
            Element e = elementList.get(i);
            pointcutDefList.add(AOPPointcutDefinition.builder()
                    .id(e.attributeValue(ID_ATTRIBUTE_NAME))
                    .ref(e.attributeValue(AOPAspectDefinition.REF_ATTRIBUTE_NAME))
                    .build());
        }
        return pointcutDefList;
    }

}
