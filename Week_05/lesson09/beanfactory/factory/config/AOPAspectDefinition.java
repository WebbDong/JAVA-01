package lesson09.beanfactory.factory.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Webb Dong
 * @description: AOP 切面配置定义
 * @date 2021-02-16 21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AOPAspectDefinition {

    public static final String REF_ATTRIBUTE_NAME = "ref";

    private String id;

    private String ref;

    private Set<AOPAdviceDefinition> adviceDefSet;

}
