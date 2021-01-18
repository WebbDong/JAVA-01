package lesson01.util;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @author Webb Dong
 * @description: Base64 工具类
 * @date 2021-01-08 10:54
 */
public class Base64Utils {

    private Base64Utils() {}

    public static void main(String[] args) {
        String str = "996是一种福报？？？？？？？？";
        byte[] ciphertextBytes = encode(str.getBytes(Charset.forName("utf8")));
        System.out.println("ciphertext = " + new String(ciphertextBytes));

        byte[] decode = decode(ciphertextBytes);
        System.out.println("plaintext = " + new String(decode, Charset.forName("utf8")));
    }

    /**
     * BASE64加密
     */
    public static byte[] encode(byte[] plaintextBytes) {
        return Base64.getEncoder().encode(plaintextBytes);
    }

    /**
     * BASE64解密
     */
    public static byte[] decode(byte[] ciphertextBytes) {
        return Base64.getDecoder().decode(ciphertextBytes);
    }


}
