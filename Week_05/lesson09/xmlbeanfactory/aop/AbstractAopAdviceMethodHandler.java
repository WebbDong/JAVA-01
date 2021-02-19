package lesson09.xmlbeanfactory.aop;

import lesson09.xmlbeanfactory.factory.config.AopAdviceDefinition;
import lesson09.xmlbeanfactory.factory.config.enums.AopAdviceTypeEnum;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Webb Dong
 * @description: 抽象 AOP 通知方法处理类
 * @date 2021-02-17 20:25
 */
@Data
public abstract class AbstractAopAdviceMethodHandler {

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

    /**
     * 构造器
     * @param target 目标对象
     * @param aspect 切面对象
     * @param adviceDefMap 通知方法定义
     */
    public AbstractAopAdviceMethodHandler(Object target, Object aspect,
                                          Map<AopAdviceTypeEnum, List<AopAdviceDefinition>> adviceDefMap) {
        this.target = target;
        this.aspect = aspect;
        this.adviceDefMap = adviceDefMap;
    }

    protected Object invokeAdvice(Object proxy, Method method, Object[] args) throws Throwable {
//        ProceedingJoinPoint joinPoint = new ProceedingJoinPointImpl(args, target, method, aspect);
        List<AopAdviceDefinition> aroundAdviceList = adviceDefMap.get(AopAdviceTypeEnum.AROUND);
        if (aroundAdviceList == null || aroundAdviceList.size() == 0) {
//            finishAdvice();
        } else {
            createAroundProceedingJoinPointList(aroundAdviceList);
        }
        return null;
    }

    private List<ProceedingJoinPoint> createAroundProceedingJoinPointList(
            List<AopAdviceDefinition> aroundAdviceList) {
        return null;
    }

}
