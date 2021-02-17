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
public enum AopAdviceTypeEnum {

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

    public static AopAdviceTypeEnum getAdviceTypeEnumByName(String name) {
        AopAdviceTypeEnum adviceTypeEnum;
        if (AopAdviceTypeEnum.BEFORE.getName().equals(name)) {
            adviceTypeEnum = AopAdviceTypeEnum.BEFORE;
        } else if (AopAdviceTypeEnum.AFTER.getName().equals(name)) {
            adviceTypeEnum = AopAdviceTypeEnum.AFTER;
        } else if (AopAdviceTypeEnum.AFTER_RETURNING.getName().equals(name)) {
            adviceTypeEnum = AopAdviceTypeEnum.AFTER_RETURNING;
        } else if (AopAdviceTypeEnum.AFTER_THROWING.getName().equals(name)) {
            adviceTypeEnum = AopAdviceTypeEnum.AFTER_THROWING;
        } else if (AopAdviceTypeEnum.AROUND.getName().equals(name)) {
            adviceTypeEnum = AopAdviceTypeEnum.AROUND;
        } else {
            throw new RuntimeException("Unknown advice type enum : " + name);
        }
        return adviceTypeEnum;
    }

}
