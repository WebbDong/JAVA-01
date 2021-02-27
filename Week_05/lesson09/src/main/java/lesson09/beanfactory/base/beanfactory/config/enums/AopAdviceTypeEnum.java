package lesson09.beanfactory.base.beanfactory.config.enums;

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

    private final int type;

    private final String name;

    /**
     * 根据标签名获取对应的 AOP 通知类型枚举
     * @param elementName 标签名
     * @return 返回标签名对应的 AOP 通知类型枚举
     */
    public static AopAdviceTypeEnum getAdviceTypeEnumByName(String elementName) {
        AopAdviceTypeEnum typeEnum;
        if (AopAdviceTypeEnum.BEFORE.getName().equals(elementName)) {
            typeEnum = AopAdviceTypeEnum.BEFORE;
        } else if (AopAdviceTypeEnum.AFTER.getName().equals(elementName)) {
            typeEnum = AopAdviceTypeEnum.AFTER;
        } else if (AopAdviceTypeEnum.AFTER_RETURNING.getName().equals(elementName)) {
            typeEnum = AopAdviceTypeEnum.AFTER_RETURNING;
        } else if (AopAdviceTypeEnum.AFTER_THROWING.getName().equals(elementName)) {
            typeEnum = AopAdviceTypeEnum.AFTER_THROWING;
        } else if (AopAdviceTypeEnum.AROUND.getName().equals(elementName)) {
            typeEnum = AopAdviceTypeEnum.AROUND;
        } else {
            throw new RuntimeException("Unknown advice type enum : " + elementName);
        }
        return typeEnum;
    }

    /**
     * 是否是后置类型的通知
     * @param typeEnum AOP 通知类型枚举
     * @return 是后置类型的通知返回 true 否则返回 false
     */
    public static boolean isAfterTypeAdvice(AopAdviceTypeEnum typeEnum) {
        return typeEnum == AFTER
                || typeEnum == AFTER_RETURNING
                || typeEnum == AFTER_THROWING;
    }

}
