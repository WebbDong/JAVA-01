package lesson11.guava.strings;

import com.google.common.base.CharMatcher;

/**
 * CharMatcher 字符匹配器
 * @author Webb Dong
 * @date 2021-03-03 6:37 PM
 */
public class CharMatcherExample {

    public static void main(String[] args) {
        String str1 = "str1\nstr2";
        // 移除 control 字符，例如换行符等c
        String res1 = CharMatcher.javaIsoControl().removeFrom(str1);
        System.out.println(res1);

        String str2 = "  str1  str2  ";
        // 去除开头和末尾的空格中间的空格替换成指定的字符
        String res2 = CharMatcher.whitespace().trimAndCollapseFrom(str2, '*');
        System.out.println(res2);

        String str3 = "a12345bcd";
        // 去掉数字
        String res3 = CharMatcher.digit().removeFrom(str3);
        System.out.println(res3);

        // 只保留数子
        System.out.println(CharMatcher.digit().retainFrom(str3));

        String str4 = "  a1b2c  ";
        // 将所有数字替换成 *
        System.out.println(CharMatcher.javaDigit().replaceFrom(str4, "*"));

        String str5 = "$$#@$% abcd 1234 %%435";
        // 保留数字和小写字母
        System.out.println(CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(str5));
    }

}
