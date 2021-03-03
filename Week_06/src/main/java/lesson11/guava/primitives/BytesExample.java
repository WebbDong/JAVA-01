package lesson11.guava.primitives;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.SignedBytes;

import java.util.Arrays;
import java.util.List;

/**
 * 原生类型 byte 工具类
 * @author Webb Dong
 * @date 2021-03-03 8:02 PM
 */
public class BytesExample {

    private static void bytesExample() {
        System.out.println("----------------- bytesExample ------------------");
        List<Byte> byteList = Bytes.asList((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
        System.out.println(byteList);

        byte[] bytes1 = {0, 2, 4, 6};
        byte[] bytes2 = {1, 3, 5, 7};
        byte[] concat = Bytes.concat(bytes1, bytes2);
        System.out.println(Arrays.toString(concat));

        boolean contains = Bytes.contains(bytes1, (byte) 6);
        System.out.println(contains);

        Bytes.reverse(bytes2);
        System.out.println(Arrays.toString(bytes2));

        System.out.println(Bytes.indexOf(bytes1, (byte) 4));
        System.out.println(Bytes.indexOf(bytes1, new byte[] {2, 4}));

        byte[] bytes3 = new byte[] {10, 20, 30, 40};
        // 数组扩容
        bytes3 = Bytes.ensureCapacity(bytes3, bytes3.length * 2, 10);
        System.out.println(Arrays.toString(bytes3));
        System.out.println(bytes3.length);
    }

    private static void signedBytesExample() {
        System.out.println("----------------- signedBytesExample ------------------");
        // 检查传入的 long 型数字是否在 byte 类型的取值范围内，如果超出范围抛出 IllegalArgumentException 异常，
        // 否则将传入的 long 型数字强转成 byte 并返回
        System.out.println(SignedBytes.checkedCast(12));
//        System.out.println(SignedBytes.checkedCast(128));

        System.out.println(SignedBytes.compare((byte) 10, (byte) 5));

        System.out.println(SignedBytes.join("|", new byte[] {5, 6, 7, 8, 9}));

        System.out.println(SignedBytes.max(new byte[] {10, 30, 80, 90, 100}));

        System.out.println(SignedBytes.min(new byte[] {10, 30, 80, 90, 100}));

        // 将传入的 long 型数字强转成 byte ，如果超出 byte 的取值范围，小于最小值的情况返回 Byte.MIN_VALUE，大于最大值的情况
        // 返回 Byte.MAX_VALUE
        SignedBytes.saturatedCast(128);

        byte[] bytes1 = {(byte) 100, (byte) 20, (byte) 10, (byte) 5, (byte) 2};
        // 降序排序
        SignedBytes.sortDescending(bytes1);
        System.out.println(Arrays.toString(bytes1));
    }

    public static void main(String[] args) {
        bytesExample();
        signedBytesExample();
    }

}
