package lesson01.jvmmemory;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Webb Dong
 * @description: JVM 内存实验
 * @date 2021-01-12 15:35
 */
public class JvmMemoryTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 启动参数配置 -XX:MaxDirectMemorySize=1023k 直接内存最大 1023k
        // 1024k
        final int BYTE_SIZE = 1024 * 1024;
        // 申请直接内存 1024k，报错
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BYTE_SIZE);

        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        System.out.println("PAGE SIZE: " + unsafe.pageSize());
        // 从本地内存同样申请 1024k，成功申请。所以不受 -XX:MaxDirectMemorySize 启动参数限制
        long address = unsafe.allocateMemory(BYTE_SIZE);
        System.out.println("指针地址: " + address);

        for (long l = 0, currentAddress = address, endAddress = address + BYTE_SIZE;
             currentAddress < endAddress;
             l++) {
            // 将这1024k大小的内存区域，每8个字节分配一个long类型，存入值
            unsafe.putAddress(currentAddress, l);
            // long类型8个字节，当前地址加上8，下一个存储位置
            currentAddress += Long.BYTES;
        }

        for (long addr = address, endAddress = address + BYTE_SIZE; addr < endAddress; addr += Long.BYTES) {
            System.out.println(unsafe.getLong(addr));
        }

        // 释放该地址的内存
        unsafe.freeMemory(address);
    }

}
