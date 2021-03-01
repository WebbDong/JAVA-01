package lesson11.guava.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BoundType;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeMultiset;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 新集合类型
 *      Multiset 接口: Multiset 和 Set 的区别就是可以保存多个相同的对象。可以保存无序重复的数据
 *      SortedMultiset 接口: 支持高效地获取指定范围的子集，TreeMultiset 实现 SortedMultiset 接口
 *      Multimap 接口: Multimap 不是一个 Map，没有继承 java.util.Map 接口，将复杂的 Map 数据结构，例如 HashMap<K, Set<V>>
 *                      进行简化封装。
 *      BiMap 接口: BiMap 是一种特殊的 Map，提供 inverse() 方法可以直接反转键值映射，保证值是唯一的，因此 values() 返回 Set 而不是普通的 Collection
 * @author Webb Dong
 * @date 2021-03-01 2:47 PM
 */
public class NewCollections {

    /**
     * Multiset 接口的一些实现类示例
     */
    private static void multisetExample() {
        System.out.println("----------------- multisetExample ------------------");
        // HashMultiset 支持存入 null
        HashMultiset<Integer> hashMultiset = HashMultiset.create();
        hashMultiset.add(50);
        hashMultiset.add(40);
        hashMultiset.add(30);
        hashMultiset.add(30);
        hashMultiset.add(null);
        hashMultiset.add(60);
        hashMultiset.add(60);
        hashMultiset.add(60);
        hashMultiset.add(20);
        hashMultiset.add(null);
        hashMultiset.add(null);
        System.out.println("hashMultiset = " + hashMultiset);

        // TreeMultiset 有序插入数据，支持存入 null，如果 Comparator 的实现支持的话
        // 默认的排序器是 Ordering.natural() ，此排序器不支持 null
//        TreeMultiset<String> treeMultiset = TreeMultiset.create();
        TreeMultiset<String> treeMultiset = TreeMultiset.create((v1, v2) -> {
            if (v1 == null || v2 == null) {
                return 1;
            }
            return v1.compareTo(v2);
        });
        treeMultiset.add("zoo");
        treeMultiset.add("water");
        treeMultiset.add("park");
        treeMultiset.add("london");
        treeMultiset.add("son");
        treeMultiset.add(null);
        treeMultiset.add(null);
        treeMultiset.add("bus");
        treeMultiset.add("apple");
        System.out.println("treeMultiset = " + treeMultiset);

        // LinkedHashMultiset 支持存入 null
        LinkedHashMultiset<Double> linkedHashMultiset = LinkedHashMultiset.create();
        linkedHashMultiset.add(3.14);
        linkedHashMultiset.add(3.14);
        linkedHashMultiset.add(3.14);
        linkedHashMultiset.add(null);
        linkedHashMultiset.add(null);
        linkedHashMultiset.add(1.07);
        linkedHashMultiset.add(2.45);
        linkedHashMultiset.add(8.89);
        linkedHashMultiset.add(10.58);
        System.out.println("linkedHashMultiset = " + linkedHashMultiset);

        // ConcurrentHashMultiset 线程安全，不支持存入 null
        ConcurrentHashMultiset<Long> concurrentHashMultiset = ConcurrentHashMultiset.create();
        concurrentHashMultiset.add(50000L);
        concurrentHashMultiset.add(60000L);
        concurrentHashMultiset.add(60000L);
        concurrentHashMultiset.add(40000L);
        concurrentHashMultiset.add(60000L);
        concurrentHashMultiset.add(80000L);
        concurrentHashMultiset.add(90000L);
        concurrentHashMultiset.add(70000L);
        System.out.println("concurrentHashMultiset = " + concurrentHashMultiset);

        // ImmutableMultiset 不可变元素重复 Set，不支持存入 null
        ImmutableMultiset<String> immutableMultiset = ImmutableMultiset.of(
                "United States", "United States", "Japan", "Canada", "Taiwan", "Korea");
        System.out.println("immutableMultiset = " + immutableMultiset);
    }

    /**
     * SortedMultiset 接口的实现类示例
     */
    private static void sortedMultisetExample() {
        System.out.println("----------------- sortedMultisetExample ------------------");
        SortedMultiset<Integer> sortedMultiset = TreeMultiset.create();
        sortedMultiset.add(777);
        sortedMultiset.add(777);
        sortedMultiset.add(555);
        sortedMultiset.add(777);
        sortedMultiset.add(444);
        sortedMultiset.add(333);
        sortedMultiset.add(111);
        System.out.println("sortedMultiset = " + sortedMultiset);

        // subMultiset 获取指定元素值范围的子集，可以指定区间的开闭
        // [111, 444)，获取从111到444区间范围的子集，包含111，不包含444
        SortedMultiset<Integer> subSortedMultiset = sortedMultiset.subMultiset(
                111, BoundType.CLOSED, 444, BoundType.OPEN);
        System.out.println("subSortedMultiset = " + subSortedMultiset);
    }

    /**
     * Multimap
     */
    private static void multimapExample() {
        System.out.println("----------------- multimapExample ------------------");
        // ArrayListMultimap 行为类似 HashMap<K, Collection<V>>
        ArrayListMultimap<String, Integer> arrayListMultimap = ArrayListMultimap.create();
        arrayListMultimap.put("k5", 100);
        arrayListMultimap.put("k5", 200);
        arrayListMultimap.put("k5", 300);
        arrayListMultimap.put("k5", 400);
        arrayListMultimap.put("k3", 30);
        arrayListMultimap.put("k2", 20);
        arrayListMultimap.put("k1", 10);
        arrayListMultimap.put("k4", 40);
        System.out.println("arrayListMultimap = " + arrayListMultimap);

        // HashMultimap 行为类似 HashMap<K, Set<V>>
        HashMultimap<String, Integer> hashMultimap = HashMultimap.create();
        hashMultimap.put("k10", 1000);
        hashMultimap.put("k10", 2000);
        hashMultimap.put("k10", 3000);
        hashMultimap.put("k9", 4000);
        hashMultimap.put("k8", 4500);
        hashMultimap.put("k7", 5000);
        hashMultimap.put("k15", 5500);
        hashMultimap.put("k20", 5555);
        hashMultimap.put("k11", 6000);
        hashMultimap.put("k12", 7000);
        System.out.println("hashMultimap = " + hashMultimap);

        // LinkedListMultimap 行为类似 LinkedHashMap<K, LinkedList<V>>
        LinkedListMultimap<String, Integer> linkedListMultimap = LinkedListMultimap.create();
        linkedListMultimap.put("k10", 1100);
        linkedListMultimap.put("k10", 2200);
        linkedListMultimap.put("k10", 3300);
        linkedListMultimap.put("k9", 4400);
        linkedListMultimap.put("k8", 4500);
        linkedListMultimap.put("k7", 5500);
        linkedListMultimap.put("k15", 5500);
        linkedListMultimap.put("k20", 5555);
        linkedListMultimap.put("k11", 6600);
        linkedListMultimap.put("k12", 7000);
        linkedListMultimap.put("k12", 9000);
        System.out.println("linkedListMultimap = " + linkedListMultimap);

        // LinkedHashMultimap 行为类似 LinkedHashMap<K, Set<V>> ，LinkedHashMultimap 与 HashMultimap 类似，区别在于 LinkedHashMultimap 能够插入有序
        LinkedHashMultimap<String, Integer> linkedHashMultimap = LinkedHashMultimap.create();
        linkedHashMultimap.put("k22", 8856);
        linkedHashMultimap.put("k22", 8860);
        linkedHashMultimap.put("k22", 8862);
        linkedHashMultimap.put("k22", 8863);
        linkedHashMultimap.put("k21", 9000);
        linkedHashMultimap.put("k23", 9500);
        linkedHashMultimap.put("k24", 9600);
        linkedHashMultimap.put("k25", 9700);
        System.out.println("linkedHashMultimap = " + linkedHashMultimap);

        // TreeMultimap 行为类似 TreeMap<K, TreeSet<V>> ，key 和 value 都能保证顺序
        TreeMultimap<String, Integer> treeMultimap = TreeMultimap.create();
        treeMultimap.put("k32", 8);
        treeMultimap.put("k31", 90);
        treeMultimap.put("k34", 10);
        treeMultimap.put("k33", 50);
        treeMultimap.put("k30", 99999);
        treeMultimap.put("k30", 1000000);
        treeMultimap.put("k30", 5);
        System.out.println("treeMultimap = " + treeMultimap);

        // ImmutableListMultimap 行为类似 ImmutableMap<K, ImmutableList<V>> 不可变形式
        ImmutableListMultimap<String, Integer> immutableListMultimap = ImmutableListMultimap.<String, Integer>builder()
                .put("k36", 33)
                .put("k37", 20)
                .put("k40", 10)
                .put("k50", 20)
                .put("k55", 25)
                .put("k50", 16)
                .build();
        System.out.println("immutableListMultimap = " + immutableListMultimap);

        // ImmutableSetMultimap 行为类似 ImmutableMap<K, ImmutableSet<V>> 不可变形式
        ImmutableSetMultimap<String, Integer> immutableSetMultimap = ImmutableSetMultimap.<String, Integer>builder()
                .put("k52", 41)
                .put("k51", 40)
                .put("k50", 45)
                .put("k53", 50)
                .put("k54", 60)
                .build();
        System.out.println("immutableSetMultimap = " + immutableSetMultimap);

        // 自定义 Multimap
        Multimap<String, Double> customMultimap = Multimaps.newMultimap(new TreeMap<>(), () -> new ArrayList<>());
        customMultimap.put("k61", 3.14);
        customMultimap.put("k60", 4.58);
        customMultimap.put("k62", 6.55);
        customMultimap.put("k63", 10.58);
        customMultimap.put("k60", 15.58);
        System.out.println("customMultimap = " + customMultimap);
    }

    /**
     * 传统上，实现键值对的双向映射需要维护两个单独的map，并保持它们间的同步。但这种方式很容易出错，而且对于值已经在map中的情况，
     * 会变得非常混乱。例如：
     *
     * Map<String, Integer> nameToId = Maps.newHashMap();
     * Map<Integer, String> idToName = Maps.newHashMap();
     * nameToId.put("Bob", 42);
     * idToName.put(42, "Bob");
     * // 如果我们忘了同步两个map，会有 bug 产生
     * // 如果 "Bob" 和 42 已经在 map 中了，会发生什么?
     *
     * 使用 BiMap 可以简化此需求操作
     *
     * 在 BiMap 中，如果你想把键映射到已经存在的值，会抛出 IllegalArgumentException 异常。如果对特定值，你想要强制替换它的键，
     * 可以使用 BiMap.forcePut(key, value)。
     */
    private static void biMapExample() {
        System.out.println("----------------- biMapExample ------------------");
        // Hash 算法的 BiMap
        HashBiMap<String, Integer> hashBiMap = HashBiMap.create();
        hashBiMap.put("k1", 10);
        hashBiMap.put("k2", 20);
        hashBiMap.put("k3", 30);
        hashBiMap.put("k4", 40);
        hashBiMap.put("k5", 50);
        hashBiMap.put("k6", 60);
        hashBiMap.put("k7", 5);
        // 60 已经映射给了 k6，如果直接这样设置，就会抛出 IllegalArgumentException
//        hashBiMap.put("k5", 60);
        // 可以使用 forcePut 来覆盖设置，设置成功后 k6 将会被删除，因为 BiMap 保证 value 的唯一性
        hashBiMap.forcePut("k5", 60);
        System.out.println("hashBiMap = " + hashBiMap);
        System.out.println("hashBiMap.inverse() = " + hashBiMap.inverse());


    }

    public static void main(String[] args) {
        multisetExample();
        sortedMultisetExample();
        multimapExample();
        biMapExample();
    }

}
