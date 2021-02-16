package lesson09.beanfactory.factory.config;

import lesson09.beanfactory.factory.config.enums.AOPAdviceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @description: AOP 通知配置定义
 * @date 2021-02-16 21:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AOPAdviceDefinition {

    /**
     * 通知类型
     */
    private AOPAdviceTypeEnum adviceType;

    private String method;

    private String pointcutRef;

}
