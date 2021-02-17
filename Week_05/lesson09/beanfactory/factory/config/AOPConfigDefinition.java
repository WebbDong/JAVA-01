package lesson09.beanfactory.factory.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Webb Dong
 * @description: AOP config 配置定义
 * @date 2021-02-16 21:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AOPConfigDefinition {

    public static final String PROXY_TARGET_CLASS_ATTR_NAME = "proxy-target-class";

    public static final String POINTCUT_ELEMENT_NAME = "pointcut";

    public static final String ASPECT_ELEMENT_NAME = "aspect";

    private boolean proxyTargetClass;

//    private List<AOPPointcutDefinition> pointcutDefList;

//    private List<AOPAspectDefinition> aspectDefList;

}
