package lesson01.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * 读取包中指定的本地字节码文件流，并且将流转换成 byte 数组
     * @param in
     * @return
     */
    public static byte[] readLocalClassAsBytes(InputStream in) {
        byte[] bytes;
        try {
            bytes = new byte[in.available()];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            in.read(bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    /**
     * 获取网络中的字节码文件
     * @param urlString
     * @return
     * @throws IOException
     */
    public static byte[] getRemoteClassFileAsBytes(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(1000);
        conn.setRequestMethod("GET");
        try (InputStream in = conn.getInputStream()) {
            if (in == null) {
                throw new IOException("InputStream is null");
            }
            // 请求获取远程字节码文件
            List<Byte> byteList = new ArrayList<>();
            final int BUFFER_SIZE = 1024;
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                IntStream.range(0, len).boxed().forEach(i -> byteList.add(buffer[i]));
            }

            // List<Byte> 转 byte[]
            Object[] boxedArray = byteList.toArray();
            len = boxedArray.length;
            byte[] bytes = new byte[boxedArray.length];
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) boxedArray[i];
            }
            return bytes;
        }
    }

}
