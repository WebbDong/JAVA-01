package lesson11.guava.primitives;

import com.google.common.base.Converter;
import com.google.common.primitives.Shorts;

/**
 * 原生类型 short 工具类
 * @author Webb Dong
 * @date 2021-03-04 00:13
 */
public class ShortsExample {

    public static void main(String[] args) {
        // 如果一个数字在某个范围内则输出该数字，否则输出范围的最大值或最小值.
        System.out.println(Shorts.constrainToRange((short) 0, (short) 0, (short) 10));
        System.out.println(Shorts.constrainToRange((short) 10, (short) 0, (short) 10));
        System.out.println(Shorts.constrainToRange((short) 12, (short) 0, (short) 10));
        System.out.println(Shorts.constrainToRange((short) -1, (short) 0, (short) 10));

        Converter<String, Short> converter = Shorts.stringConverter();
        short n = converter.convert("888");
        System.out.println(n);

        Converter<Short, String> converter2 = converter.reverse();
        String s = converter2.convert((short) 90);
        System.out.println(s);
    }

}
