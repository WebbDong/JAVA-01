package lesson11.guava.basicutilities;

import com.google.common.base.Throwables;

import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Throwables
 *      简化异常和错误的传播与检查
 *
 * Throwables.throwIfInstanceOf(Throwable throwable, Class<X> declaredType): 当传入的 throwable 是 declaredType 类型实例或其子类实例时，
 *      就抛出传入的异常。
 *
 * Throwables.throwIfUnchecked(Throwable throwable): 如果 throwable 是非检查异常或错误就抛出，也就是 RuntimeException 类型实例或子类实例，
 *      Error 类型实例或子类实例。
 *
 * Throwables.propagateIfPossible(Throwable throwable, Class<X> declaredType): 当传入的 throwable 是 declaredType 类型实例或其子类实例，
 *      或者是 RuntimeException 以及其子类的实例就抛出传入的异常，否则不抛出异常。此方法是 throwIfInstanceOf 和 throwIfUnchecked 的结合
 *
 * @author Webb Dong
 * @date 2021-02-28 23:53
 */
public class ThrowablesExample {

    public static void main(String[] args) throws Exception {
        try {
//            throw new IllegalStateException();
//            throw new SQLException();
            throw new EOFException();
        } catch (Throwable t) {
//            Throwables.propagateIfPossible(t, SQLException.class);
            Throwables.propagateIfPossible(t, SQLException.class, IOException.class);
        }
    }

}
