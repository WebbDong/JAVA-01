package lesson09.xmlbeanfactory.factory.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @description: Bean 属性定义
 * @date 2021-02-16 18:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeanPropertyDefinition {

    public static final String NAME_ATTRIBUTE_NAME = "name";

    public static final String REF_ATTRIBUTE_NAME = "ref";

    private String name;

    private String value;

    private String ref;

}
