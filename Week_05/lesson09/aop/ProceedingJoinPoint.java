package lesson09.aop;

/**
 * @author Webb Dong
 * @description: ProceedingJoinPoint
 * @date 2021-02-14 21:10
 */
public interface ProceedingJoinPoint {

    /**
     * 执行目标方法
     * @return
     * @throws Throwable
     */
    Object proceed() throws Throwable;

    /**
     * 执行目标方法并重新传递参数
     * @param args
     * @return
     * @throws Throwable
     */
    Object proceed(Object[] args) throws Throwable;

    /**
     * 获取被代理的目标对象
     * @return
     */
    Object getTarget();

    /**
     * 获取参数
     * @return
     */
    Object[] getArgs();

}
