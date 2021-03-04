package lesson11.guava.primitives;

import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedInts;

/**
 * 原生类型 int 工具类
 * @author Webb Dong
 * @date 2021-03-04 00:27
 */
public class IntsExample {

    private static void intsExample() {
        System.out.println("----------------- intsExample ------------------");
        Integer num1 = Ints.tryParse("500");
        System.out.println(num1);
        // 如果超出 Integer 取值范围，返回 null
        Integer num2 = Ints.tryParse("" + Long.MAX_VALUE);
        System.out.println(num2);
    }

    private static void unsignedIntsExample() {
        System.out.println("----------------- unsignedIntsExample ------------------");
        int n1 = UnsignedInts.decode("100");
        System.out.println(n1);

        int n2 = UnsignedInts.parseUnsignedInt("9999");
        System.out.println(n2);

        int n3 = UnsignedInts.divide(50, 2);
        System.out.println(n3);

        // 取余数
        int n4 = UnsignedInts.remainder(50, 3);
        System.out.println(n4);
    }

    private static void unsignedIntegerExample() {
        System.out.println("----------------- unsignedIntegerExample ------------------");
        System.out.println(UnsignedInteger.ONE);
        System.out.println(UnsignedInteger.ZERO);
        System.out.println(UnsignedInteger.fromIntBits(1024));
        System.out.println(UnsignedInteger.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        intsExample();
        unsignedIntegerExample();
        unsignedIntsExample();
    }

}
