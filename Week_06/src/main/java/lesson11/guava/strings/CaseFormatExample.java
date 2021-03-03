package lesson11.guava.strings;

import com.google.common.base.CaseFormat;

/**
 * CaseFormat 大小写格式
 *      CaseFormat 被用来方便地在各种 ASCII 大小写规范间转换字符串 —— 比如，编程语言的命名规范。
 *      格式                     范例
 *   LOWER_CAMEL              lowerCamel
 *   LOWER_HYPHEN             lower-hyphen
 *   LOWER_UNDERSCORE         lower_underscore
 *   UPPER_CAMEL              UpperCamel
 *   UPPER_UNDERSCORE         UPPER_UNDERSCORE
 *
 * @author Webb Dong
 * @date 2021-03-03 7:46 PM
 */
public class CaseFormatExample {

    public static void main(String[] args) {
        // 将 UPPER_UNDERSCORE 格式转换成 LOWER_CAMEL
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"));
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, "constant-name"));
    }

}
