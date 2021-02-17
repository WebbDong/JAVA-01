package lesson09.beanfactory.factory.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Webb Dong
 * @description: AOP 通知类型枚举
 * @date 2021-02-16 22:21
 */
@Getter
@AllArgsConstructor
public enum AOPAdviceTypeEnum {

    /**
     * 前置通知
     */
    BEFORE(0, "before"),

    /**
     * 后置通知
     */
    AFTER(1, "after"),

    /**
     * 后置返回通知
     */
    AFTER_RETURNING(2, "after-returning"),

    /**
     * 后置异常通知
     */
    AFTER_THROWING(3, "after-throwing"),

    /**
     * 环绕通知
     */
    AROUND(4, "around");

    private int type;

    private String name;

    public static AOPAdviceTypeEnum getAdviceTypeEnumByName(String name) {
        AOPAdviceTypeEnum adviceTypeEnum;
        if (AOPAdviceTypeEnum.BEFORE.getName().equals(name)) {
            adviceTypeEnum = AOPAdviceTypeEnum.BEFORE;
        } else if (AOPAdviceTypeEnum.AFTER.getName().equals(name)) {
            adviceTypeEnum = AOPAdviceTypeEnum.AFTER;
        } else if (AOPAdviceTypeEnum.AFTER_RETURNING.getName().equals(name)) {
            adviceTypeEnum = AOPAdviceTypeEnum.AFTER_RETURNING;
        } else if (AOPAdviceTypeEnum.AFTER_THROWING.getName().equals(name)) {
            adviceTypeEnum = AOPAdviceTypeEnum.AFTER_THROWING;
        } else if (AOPAdviceTypeEnum.AROUND.getName().equals(name)) {
            adviceTypeEnum = AOPAdviceTypeEnum.AROUND;
        } else {
            throw new RuntimeException("Unknown advice type enum : " + name);
        }
        return adviceTypeEnum;
    }

}
