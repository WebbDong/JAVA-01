package lesson09.beanfactory.xmlbeanfactory.factory.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Webb Dong
 * @description: Bean scope 类型
 * @date 2021-02-17 16:18
 */
@Getter
@AllArgsConstructor
public enum BeanScopeTypeEnum {

    /**
     * 单例
     */
    SINGLETON(0, "singleton"),

    /**
     * 原型
     */
    PROTOTYPE(1, "prototype");

    private final int type;

    private final String name;

    public static BeanScopeTypeEnum valueOfByName(String name) {
        if (SINGLETON.name.equals(name)) {
            return SINGLETON;
        } else if (PROTOTYPE.name.equals(name)) {
            return PROTOTYPE;
        } else {
            throw new RuntimeException("Unknown scope type enum name");
        }
    }

}
