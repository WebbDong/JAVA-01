package lesson11.guava.strings;

import com.google.common.base.Splitter;

import java.util.Arrays;

/**
 * Splitter 拆分器
 *
 * 拆分器工厂
 *      1、Splitter.on(char): 按单个字符拆分
 *      2、Splitter.on(CharMatcher): 按字符匹配器拆分
 *      3、Splitter.on(String): 按字符串拆分
 *      4、Splitter.on(Pattern)、Splitter.onPattern(String): 按正则表达式拆分
 *      5、Splitter.fixedLength(int): 按固定长度拆分；最后一段可能比给定长度短，但不会为空。
 *
 * 拆分器修饰符
 *      1、omitEmptyStrings(): 从结果中自动忽略空字符串
 *      2、trimResults(): 移除结果字符串的前导空白和尾部空白
 *      3、trimResults(CharMatcher): 给定匹配器，移除结果字符串的前导匹配字符和尾部匹配字符
 *      4、limit(int): 限制拆分出的字符串数量
 *
 * @author Webb Dong
 * @date 2021-03-03 6:07 PM
 */
public class SplitterExample {

    /**
     * JDK内建的字符串拆分工具有一些古怪的特性。比如，String.split悄悄丢弃了尾部的分隔符。
     * @param args
     */
    public static void main(String[] args) {
        String str = " ,a,,b, ";
        String[] split1 = str.split(",");
        System.out.println(Arrays.toString(split1));
        for (String s : split1) {
            System.out.println(s);
        }

        System.out.println("-------------------------------------");

        String str2 = "1,2,3,4,5,";
        Iterable<String> split2 = Splitter
                .on(',')
                .trimResults()
                .omitEmptyStrings()
                .limit(2)
                .split(str2);
        System.out.println(split2);
        for (String s : split2) {
            System.out.println(s);
        }
    }

}
