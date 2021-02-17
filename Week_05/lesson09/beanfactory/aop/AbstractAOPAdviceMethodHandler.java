package lesson09.beanfactory.aop;

import lesson09.beanfactory.factory.config.AopAdviceDefinition;
import lesson09.beanfactory.factory.config.enums.AopAdviceTypeEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: 抽象 AOP 通知方法处理类
 * @date 2021-02-17 20:25
 */
@Data
public abstract class AbstractAOPAdviceMethodHandler {

    /**
     * 目标对象
     */
    protected Object target;

    /**
     * 切面对象
     */
    protected Object aspect;

    /**
     * 通知方法定义
     */
    protected Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap;

    public AbstractAOPAdviceMethodHandler() {
    }

    public AbstractAOPAdviceMethodHandler(Object target, Object aspect,
                                          Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        this.target = target;
        this.aspect = aspect;
        this.adviceDefMap = adviceDefMap;
    }
}
