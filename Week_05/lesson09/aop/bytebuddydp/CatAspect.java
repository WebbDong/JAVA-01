package lesson09.aop.bytebuddydp;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Webb Dong
 * @description:
 * @date 2021-02-20 02:22
 */
public class CatAspect {

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method,
                                     @Advice.AllArguments Object[] args) {
        System.out.println("CatAspect.onMethodEnter start");
        System.out.println(method.getName());
        System.out.println(Arrays.toString(args));
        System.out.println("CatAspect.onMethodEnter end");
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method,
                                    @Advice.AllArguments Object[] args,
                                    @Advice.Return Object ret) {
        System.out.println("CatAspect.onMethodExit start");
        System.out.println(method.getName());
        System.out.println(Arrays.toString(args));
        System.out.println("ret = " + ret);
        System.out.println("CatAspect.onMethodExit end");
    }

}
