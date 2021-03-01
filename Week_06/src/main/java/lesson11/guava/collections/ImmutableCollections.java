package lesson11.guava.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 不可变集合
 *
 * 不可变对象的优点
 *      1、当对象被不可信的库调用时，不可变形式是安全的
 *      2、不可变对象被多个线程调用时，不存在竞态条件问题
 *      3、不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率
 *      4、不可变对象因为有固定不变，可以作为常量来安全使用
 *
 * JDK也提供了 Collections.unmodifiableXXX 方法把集合包装为不可变形式，但是有如下缺点:
 *      1、笨重而且累赘：不能舒适地用在所有想做防御性拷贝的场景
 *      2、低效：包装过的集合仍然保有可变集合的开销，比如并发修改的检查、散列表的额外空间，等等
 *      3、不安全：无法保证没人通过原集合的引用进行修改
 *
 * 所有 Guava 不可变集合的实现都不接受 null 值。如果你需要在不可变集合中使用 null ，请使用 JDK 中的 Collections.unmodifiableXXX 方法
 *
 * @author Webb Dong
 * @date 2021-03-01 12:49 PM
 */
public class ImmutableCollections {

    /**
     * ImmutableList
     */
    public static void immutableListExample() {
        System.out.println("----------------- immutableListExample ------------------");
        ImmutableList<Integer> immutableList1 = ImmutableList.of(60, 70, 80, 90, 100, 20, 30, 40, 50, 10);
        System.out.println(immutableList1);

        ImmutableList<Integer> immutableList2 = ImmutableList.<Integer>builder()
                .add(500)
                .add(600)
                .add(700)
                .add(300)
                .add(800)
                .add(400)
                .add(200)
                .build();
        System.out.println(immutableList2);
    }

    /**
     * ImmutableSet
     */
    public static void immutableSetExample() {
        System.out.println("----------------- immutableSetExample ------------------");
        ImmutableSet<Double> immutableSet1 = ImmutableSet.of(50.55, 40.45, 10.25, 3.44, 7.56, 6.45, 40.05, 50.55);
        System.out.println(immutableSet1);

        ImmutableSet<Integer> immutableSet2 = ImmutableSet.<Integer>builder()
                .add(8000)
                .add(6000)
                .add(5000)
                .add(1000)
                .add(2000)
                .add(4000)
                .add(3000)
                .add(3000)
                .build();
        System.out.println(immutableSet2);
    }

    /**
     * ImmutableMap
     */
    public static void immutableMapExample() {
        System.out.println("----------------- immutableMapExample ------------------");
        ImmutableMap<String, String> immutableMap1 = ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3");
        System.out.println(immutableMap1);

        ImmutableMap<String, String> immutableMap2 = ImmutableMap.<String, String>builder()
                .put("k5", "v5")
                .put("k6", "v6")
                .put("k7", "v7")
                .put("k8", "v8")
                .build();
        System.out.println(immutableMap2);
    }

    /**
     * copyOf
     */
    public static void copyOfExample() {
        System.out.println("----------------- copyOfExample ------------------");
        List<Integer> list = new ArrayList<>(5);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        ImmutableList<Integer> immutableList = ImmutableList.copyOf(list);
        System.out.println(immutableList);

        Set<String> set = new HashSet<>();
        set.add("zoo");
        set.add("water");
        set.add("park");
        set.add("london");
        set.add("son");
        set.add("bus");
        set.add("apple");
        ImmutableSet<String> immutableSet = ImmutableSet.copyOf(set);
        System.out.println(immutableSet);

        Map<String, String> map = new HashMap<>();
        map.put("k10", "v10");
        map.put("k11", "v11");
        map.put("k12", "v12");
        map.put("k13", "v13");
        map.put("k14", "v14");
        map.put("k15", "v15");
        ImmutableMap<String, String> immutableMap = ImmutableMap.copyOf(map);
        System.out.println(immutableMap);
    }

    public static void main(String[] args) {
        immutableListExample();
        immutableSetExample();
        immutableMapExample();
        copyOfExample();
    }

}
