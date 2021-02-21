package lesson09.beanfactory.xmlbeanfactory.factory.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @description: Bean 构造器定义
 * @date 2021-02-16 18:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeanConstructorArgDefinition {

    public static final String INDEX_ATTRIBUTE_NAME = "index";

    private Integer index;

    private String value;

}
