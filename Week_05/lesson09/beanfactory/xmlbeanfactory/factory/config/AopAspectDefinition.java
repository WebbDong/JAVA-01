package lesson09.beanfactory.xmlbeanfactory.factory.config;

import lesson09.beanfactory.xmlbeanfactory.factory.config.enums.AopAdviceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: AOP 切面配置定义
 * @date 2021-02-16 21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AopAspectDefinition {

    public static final String REF_ATTRIBUTE_NAME = "ref";

    public static final String POINTCUT_REF_ATTRIBUTE_NAME = "pointcut-ref";

    private String id;

    /**
     * AOP 切面 bean id
     */
    private String ref;

    /**
     * 目标 bean id
     */
    private String targetRef;

    private String pointcutRef;

    private boolean proxyTargetClass;

    /**
     * 通知方法 map
     * key: 通知方法类型, value: 对应类型的通知方法定义集合
     */
    private Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap;

}
