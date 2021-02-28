package lesson11.guava.basicutilities;

import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序器
 *
 * 从实现上说，Ordering 实例就是一个特殊的 Comparator 实例。实现了 Comparator 接口
 * Ordering 把很多基于 Comparator 的静态方法包装为自己的实例方法（非静态方法），
 * 并且提供了链式调用方法，来定制和增强现有的比较器。
 *
 * Ordering.natural(): 自然顺序排序器，对可排序类型做自然排序，如数字按大小，日期按先后排序
 * Ordering.usingToString():
 *
 * @author Webb Dong
 * @date 2021-02-28 8:30 PM
 */
public class OrderingExample {

    public static void naturalOrderingExample() {
        System.out.println("---------------------- naturalOrderingExample ------------------------");
        Ordering<Comparable> naturalOrdering = Ordering.natural();
        List<String> stringList = new ArrayList<>();
        stringList.add("zoo");
        stringList.add("water");
        stringList.add("park");
        stringList.add("london");
        stringList.add("son");
        stringList.add("bus");
        stringList.add("apple");
        System.out.println("排序前: " + stringList);
        stringList.sort(naturalOrdering);
        System.out.println("排序后: " + stringList);
    }

    public static void usingToStringOrderingExample() {
        System.out.println("---------------------- usingToStringOrderingExample ------------------------");
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        List<String> stringList = new ArrayList<>();
        stringList.add("zoo");
        stringList.add("water");
        stringList.add("park");
        stringList.add("london");
        stringList.add("son");
        stringList.add("bus");
        stringList.add("apple");
        System.out.println("排序前: " + stringList);
        stringList.sort(usingToStringOrdering);
        System.out.println("排序后: " + stringList);
    }

    public static void main(String[] args) {
        naturalOrderingExample();
        usingToStringOrderingExample();
    }

}
