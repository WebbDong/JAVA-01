package lesson09.beanfactory.factory.config.enums;

import lombok.AllArgsConstructor;

/**
 * @author Webb Dong
 * @description: AOP 通知类型枚举
 * @date 2021-02-16 22:21
 */
@AllArgsConstructor
public enum AOPAdviceTypeEnum {

    /**
     * 前置通知
     */
    BEFORE(0),

    /**
     * 后置通知
     */
    AFTER(1),

    /**
     * 后置返回通知
     */
    AFTER_RETURNING(2),

    /**
     * 后置异常通知
     */
    AFTER_THROWING(3),

    /**
     * 环绕通知
     */
    AROUND(4);

    private int type;

}
