package lesson11.guava.basicutilities;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import lombok.Builder;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 排序器
 *
 * 从实现上说，Ordering 实例就是一个特殊的 Comparator 实例。实现了 Comparator 接口
 * Ordering 把很多基于 Comparator 的静态方法包装为自己的实例方法（非静态方法），
 * 并且提供了链式调用方法，来定制和增强现有的比较器。
 *
 * 创建排序器:
 *      Ordering.natural(): 自然顺序排序器，对可排序类型做自然排序，如数字按大小，日期按先后排序
 *      Ordering.usingToString(): 按对象的字符串形式做字典排序，也就是按照调用 toString 方法返回的字符串的字典顺序排序
 *      Ordering.from(Comparator): 把给定的Comparator转化为排序器
 *
 * 链式调用方法:
 *      reverse(): 获取语义相反的排序器
 *      nullsFirst(): 使用当前排序器，但额外把null值排到最前面。
 *      nullsLast(): 使用当前排序器，但额外把null值排到最后面。
 *      compound(Comparator): 合成另一个比较器，以处理当前排序器中的相等情况。
 *      lexicographical(): 基于处理类型 T 的排序器，返回该类型的可迭代对象 Iterable<T> 的排序器。
 *      onResultOf(Function): 对集合中元素调用 Function，再按返回值用当前排序器排序。
 *      greatestOf(Iterable iterable, int k): 获取可迭代对象中最大的k个元素。
 *      isOrdered(Iterable): 判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。
 *      sortedCopy(Iterable): 判断可迭代对象是否已严格按排序器排序：不允许排序值相等的元素。
 *
 * @author Webb Dong
 * @date 2021-02-28 8:30 PM
 */
public class OrderingExample {

    @Builder
    private static class Person {

        private int age;

        private String name;

        @Override
        public String toString() {
            return name;
        }

    }

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
        List<Person> personList = new ArrayList<>();
        personList.add(Person.builder().name("Lisa").build());
        personList.add(Person.builder().name("Kobe").build());
        personList.add(Person.builder().name("Wade").build());
        personList.add(Person.builder().name("Paul").build());
        personList.add(Person.builder().name("James").build());
        personList.add(Person.builder().name("Harden").build());
        personList.add(Person.builder().name("Curry").build());
        personList.add(Person.builder().name("Adam").build());
        System.out.println("排序前: " + personList);
        personList.sort(usingToStringOrdering);
        System.out.println("排序后: " + personList);
    }

    public static void fromExample() {
        System.out.println("---------------------- fromExample ------------------------");
        Ordering<Integer> fromOrdering = Ordering.from(Comparator.comparingInt(v -> v));
//        Ordering<Integer> fromOrdering = Ordering.from(Comparator.comparingInt(v -> -v));
//        Ordering<Integer> fromOrdering = Ordering.from((v1, v2) -> v2 - v1);
        List<Integer> integerList = new ArrayList<>();
        integerList.add(8);
        integerList.add(10);
        integerList.add(4);
        integerList.add(9);
        integerList.add(2);
        integerList.add(6);
        integerList.add(7);
        integerList.add(5);
        integerList.add(3);
        integerList.add(1);
        System.out.println("排序前: " + integerList);
        integerList.sort(fromOrdering);
        System.out.println("排序后: " + integerList);
    }

    /**
     * 实现自定义的排序器时，除了 from 方法，也可以跳过实现 Comparator，而直接继承 Ordering
     */
    public static void customExample() {
        System.out.println("---------------------- customExample ------------------------");
        Ordering<Double> ordering = new Ordering<Double>() {

            @Override
            public int compare(@Nullable Double s1, @Nullable Double s2) {
                return s1.compareTo(s2);
            }

        };
        List<Double> integerList = new ArrayList<>();
        integerList.add(8.53);
        integerList.add(10.44);
        integerList.add(4.3);
        integerList.add(9.2);
        integerList.add(2.0);
        integerList.add(6.78);
        integerList.add(7.22);
        integerList.add(5.18);
        integerList.add(3.67);
        integerList.add(1.345);
        System.out.println("排序前: " + integerList);
        integerList.sort(ordering);
        System.out.println("排序后: " + integerList);
    }

    private static void reverseExample() {
        System.out.println("---------------------- reverseExample ------------------------");
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
        stringList.sort(naturalOrdering.reverse());
        System.out.println("排序后: " + stringList);
    }

    private static void nullsFirstExample() {
        System.out.println("---------------------- nullsFirstExample ------------------------");
        Ordering<Comparable> ordering = Ordering.natural();
        List<String> stringList = new ArrayList<>();
        stringList.add("zoo");
        stringList.add("water");
        stringList.add("park");
        stringList.add("london");
        stringList.add("son");
        stringList.add("bus");
        stringList.add("apple");
        stringList.add(null);
        System.out.println("排序前: " + stringList);
        stringList.sort(ordering.nullsFirst());
        System.out.println("排序后: " + stringList);
    }

    private static void nullsLastExample() {
        System.out.println("---------------------- nullsLastExample ------------------------");
        Ordering<Comparable> ordering = Ordering.natural();
        List<String> stringList = new ArrayList<>();
        stringList.add("zoo");
        stringList.add("water");
        stringList.add("park");
        stringList.add(null);
        stringList.add("london");
        stringList.add("son");
        stringList.add("bus");
        stringList.add("apple");
        System.out.println("排序前: " + stringList);
        stringList.sort(ordering.nullsLast());
        System.out.println("排序后: " + stringList);
    }

    private static void compoundExample() {
        System.out.println("---------------------- compoundExample ------------------------");
        Ordering<Comparable> ordering = Ordering.natural();
        List<String> stringList = new ArrayList<>();
        stringList.add("zoo");
        stringList.add("water");
        stringList.add("park");
        stringList.add("london");
        stringList.add("son");
        stringList.add("son");
        stringList.add("bus");
        stringList.add("apple");
        System.out.println("排序前: " + stringList);
        stringList.sort(ordering.reverse().compound(Comparator.reverseOrder()));
        System.out.println("排序后: " + stringList);
    }

    private static void onResultOfExample() {
        System.out.println("---------------------- onResultOfExample ------------------------");
        Ordering<Person> ordering = Ordering
                .natural()
                .nullsFirst()
                .onResultOf(new Function<Person, Integer>() {

                    @Override
                    public @Nullable Integer apply(@Nullable Person p) {
                        return p.age;
                    }

                });
        List<Person> personList = new ArrayList<>();
        personList.add(Person.builder().name("Lisa").age(23).build());
        personList.add(Person.builder().name("Kobe").age(40).build());
        personList.add(Person.builder().name("Wade").age(35).build());
        personList.add(Person.builder().name("Paul").age(34).build());
        personList.add(Person.builder().name("James").age(36).build());
        personList.add(Person.builder().name("Harden").age(31).build());
        personList.add(Person.builder().name("Curry").age(31).build());
        personList.add(Person.builder().name("Adam").age(28).build());
        System.out.println("排序前: " + personList);
        personList.sort(ordering);
        System.out.println("排序后: " + personList);
    }

    public static void main(String[] args) {
        naturalOrderingExample();
        usingToStringOrderingExample();
        fromExample();
        customExample();
        reverseExample();
        nullsFirstExample();
        nullsLastExample();
        compoundExample();
        onResultOfExample();
    }

}
