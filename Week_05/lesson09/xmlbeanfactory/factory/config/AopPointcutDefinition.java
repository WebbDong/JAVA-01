package lesson09.xmlbeanfactory.factory.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @description: AOP 切点配置定义
 * @date 2021-02-16 21:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AopPointcutDefinition {

    private String id;

    private String ref;

}
