package lesson09.beanfactory.xmlbeanfactory.factory.config;

import lesson09.beanfactory.xmlbeanfactory.factory.config.enums.BeanScopeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Webb Dong
 * @description: Bean 定义
 * @date 2021-02-16 16:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeanDefinition {

    public static final String BEAN_ELEMENT_NAME = "bean";

    public static final String CONSTRUCTOR_ARG_ELEMENT_NAME = "constructor-arg";

    public static final String CONFIG_ELEMENT_NAME = "config";

    public static final String PROPERTY_ELEMENT_NAME = "property";

    public static final String SCOPE_ATTRIBUTE_NAME = "scope";

    private String id;

    private String name;

    private String className;

    private BeanScopeTypeEnum scopeType;

    private List<BeanConstructorArgDefinition> constructorArgDefList;

    private List<BeanPropertyDefinition> propertyDefList;

}
