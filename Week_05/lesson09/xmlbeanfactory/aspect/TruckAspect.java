package lesson09.xmlbeanfactory.aspect;

import lesson09.xmlbeanfactory.aop.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * @author Webb Dong
 * @description: Truck AOP 切面
 * @date 2021-02-20 00:27
 */
public class TruckAspect {

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("TruckAspect.around() start");
        System.out.println("TruckAspect.around() args: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("TruckAspect.around() target class name: " + joinPoint.getTarget().getClass().getName());
        System.out.println("TruckAspect.around() proxy class name: " + joinPoint.getThis().getClass().getName());
        Object ret = joinPoint.proceed();
        System.out.println("TruckAspect.around() end");
        return ret;
    }

}
