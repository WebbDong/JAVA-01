package lesson01.util;

import java.util.stream.IntStream;

/**
 * @author Webb Dong
 * @description: 字节码工具类
 * @date 2021-01-07 16:12
 */
public class ClassCodeUtils {

    private ClassCodeUtils() {}

    /**
     * 解码 xlass 的字节码，将字节码文件数据的每一个字节都用 255 减去当前的字节数值获取原字节码数据
     * @param bytes
     */
    public static void xlassDecode(byte[] bytes) {
        IntStream.range(0, bytes.length).boxed()
                .forEach(i -> bytes[i] = (byte) (255 - bytes[i]));
    }

}
