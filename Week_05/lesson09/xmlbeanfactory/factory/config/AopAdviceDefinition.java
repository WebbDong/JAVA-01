package lesson09.xmlbeanfactory.factory.config;

import lesson09.xmlbeanfactory.factory.config.enums.AopAdviceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Webb Dong
 * @description: AOP 通知配置定义
 * @date 2021-02-16 21:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AopAdviceDefinition implements Comparable<String> {

    public static final String METHOD_ATTRIBUTE_NAME = "method";

    /**
     * 通知类型
     */
    private AopAdviceTypeEnum adviceType;

    private String method;

    @Override
    public int compareTo(@NotNull String o) {
        return this.method.compareTo(o);
    }

}
