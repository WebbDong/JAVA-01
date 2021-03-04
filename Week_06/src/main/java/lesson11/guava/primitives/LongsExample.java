package lesson11.guava.primitives;

import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedLong;
import com.google.common.primitives.UnsignedLongs;

/**
 * 原生类型 long 工具类
 * @author Webb Dong
 * @date 2021-03-04 01:45
 */
public class LongsExample {

    public static void main(String[] args) {
        System.out.println(Longs.MAX_POWER_OF_TWO);
        System.out.println(Long.MAX_VALUE);
        System.out.println(UnsignedLong.fromLongBits(4444));
        System.out.println(UnsignedLongs.remainder(90, 7));
    }

}
