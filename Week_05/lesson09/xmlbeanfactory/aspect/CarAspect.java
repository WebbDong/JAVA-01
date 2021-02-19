package lesson09.xmlbeanfactory.aspect;

/**
 * @author Webb Dong
 * @description: Car AOP 切面
 * @date 2021-02-19 23:02
 */
public class CarAspect {

    public void beforeAdvice() {
        System.out.println("CarAspect.beforeAdvice()");
    }

    public void afterReturning() {
        System.out.println("CarAspect.afterReturning()");
    }

}
