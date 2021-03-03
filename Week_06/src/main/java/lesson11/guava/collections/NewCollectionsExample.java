package lesson11.guava.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BoundType;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.Table;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeMultiset;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 新集合类型
 *      Multiset 接口: Multiset 和 Set 的区别就是可以保存多个相同的对象。可以保存无序重复的数据
 *      SortedMultiset 接口: 支持高效地获取指定范围的子集，TreeMultiset 实现 SortedMultiset 接口
 *      Multimap 接口: Multimap 不是一个 Map，没有继承 java.util.Map 接口，将复杂的 Map 数据结构，例如 HashMap<K, Set<V>> 进行简化封装。
 *      BiMap 接口: BiMap 是一种特殊的 Map，提供 inverse() 方法可以直接反转键值映射，保证值是唯一的，因此 values() 返回 Set 而不是普通的 Collection
 *      Table 接口: Table支持 row、column、value ，实现类似于 Map<R, Map<C, V>> 这种数据结构。
 *      ClassToInstanceMap 接口: 是一种特殊的 Map：它的键是 Class 类型，而值是符合键所指类型的对象。
 *      RangeSet 接口: 描述了一组不相连的、非空的区间。当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略。
 *      RangeMap 接口: 描述了”不相交的、非空的区间”到特定值的映射。和 RangeSet 不同，RangeMap 不会合并相邻的映射，即便相邻的区间映射到相同的值。
 * @author Webb Dong
 * @date 2021-03-01 2:47 PM
 */
public class NewCollectionsExample {

    private enum Week {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    private enum Activity {
        FOOTBALL,
        BASKETBALL,
        TENNIS,
        BASEBALL,
        SWIMMING,
        BADMINTON,
        FITNESS
    }

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

        // ImmutableBiMap 不可变 BiMap
        ImmutableBiMap<String, String> immutableBiMap = ImmutableBiMap.<String, String>builder()
                .put("k10", "Kobe")
                .put("k11", "Wade")
                .put("k12", "Chris")
                .put("k13", "Paul")
                .put("k14", "Curry")
                .build();
        System.out.println("immutableBiMap = " + immutableBiMap);
        System.out.println("immutableBiMap.inverse() = " + immutableBiMap.inverse());

        // EnumBiMap 枚举 BiMap，key 和 value 都必须是枚举类型
        EnumBiMap<Week, Activity> enumBiMap = EnumBiMap.create(Week.class, Activity.class);
        enumBiMap.put(Week.WEDNESDAY, Activity.BADMINTON);
        enumBiMap.put(Week.SUNDAY, Activity.BASKETBALL);
        enumBiMap.put(Week.MONDAY, Activity.FOOTBALL);
        enumBiMap.put(Week.THURSDAY, Activity.BASEBALL);
        enumBiMap.put(Week.SATURDAY, Activity.TENNIS);
        enumBiMap.put(Week.FRIDAY, Activity.SWIMMING);
        enumBiMap.put(Week.TUESDAY, Activity.FITNESS);
        System.out.println("enumBiMap = " + enumBiMap);
        System.out.println("enumBiMap.inverse() = " + enumBiMap.inverse());

        // EnumHashBiMap ，key 必须是枚举，value 可以是任何其他类型
        EnumHashBiMap<Week, Integer> enumHashBiMap = EnumHashBiMap.create(Week.class);
        enumHashBiMap.put(Week.MONDAY, 10);
        enumHashBiMap.put(Week.TUESDAY, 20);
        enumHashBiMap.put(Week.WEDNESDAY, 30);
        enumHashBiMap.put(Week.THURSDAY, 40);
        enumHashBiMap.put(Week.FRIDAY, 50);
        enumHashBiMap.put(Week.SATURDAY, 60);
        enumHashBiMap.put(Week.SUNDAY, 70);
        System.out.println("enumHashBiMap = " + enumHashBiMap);
        System.out.println("enumHashBiMap.inverse() = " + enumHashBiMap.inverse());
    }

    /**
     * Table有如下几种实现：
     *      HashBasedTable：本质上用 HashMap<R, HashMap<C, V>>实现；
     *      TreeBasedTable：本质上用 TreeMap<R, TreeMap<C,V>>实现；
     *      ImmutableTable：本质上用 ImmutableMap<R, ImmutableMap<C, V>>实现；注：ImmutableTable对稀疏或密集的数据集都有优化。
     *      ArrayTable：要求在构造时就指定行和列的大小，本质上由一个二维数组实现，以提升访问速度和密集Table的内存利用率。
     *                  ArrayTable与其他Table的工作原理有点不同
     */
    private static void tableExample() {
        System.out.println("----------------- tableExample ------------------");
        Table<String, Integer, String> table = HashBasedTable.create();
        table.put("IBM", 101, "Wade");
        table.put("IBM", 102, "Kobe");
        table.put("Microsoft", 103, "Chris");
        table.put("Microsoft", 104, "Paul");
        table.put("Google", 105, "James");
        System.out.println("table = " + table);
        System.out.println("table.get(\"IBM\", 102) = " + table.get("IBM", 102));
    }

    private static void classToInstanceMapExample() {
        System.out.println("----------------- classToInstanceMapExample ------------------");
        ClassToInstanceMap<String> classToInstanceMap1 = MutableClassToInstanceMap.create();
        classToInstanceMap1.putInstance(String.class, "str1");
        System.out.println("classToInstanceMap1 = " + classToInstanceMap1);
    }

    private static void rangeSetExample() {
        System.out.println("----------------- rangeSetExample ------------------");
        RangeSet<Integer> treeRangeSet = TreeRangeSet.create();
        treeRangeSet.add(Range.closed(1, 10));
        treeRangeSet.add(Range.openClosed(11, 15));
        treeRangeSet.add(Range.closedOpen(15, 20));
        // 空区间会被忽略
        treeRangeSet.add(Range.openClosed(0, 0));
        System.out.println("treeRangeSet = " + treeRangeSet);
        treeRangeSet.remove(Range.closed(12, 15));
        System.out.println("treeRangeSet.remove(Range.closed(12, 15)) = " + treeRangeSet);
        for (Range<Integer> r : treeRangeSet.asRanges()) {
            System.out.println(r);
        }
        System.out.println("treeRangeSet.contains(12) = " + treeRangeSet.contains(12));
        System.out.println("treeRangeSet.contains(19) = " + treeRangeSet.contains(19));

        RangeSet<Integer> immutableRangeSet = ImmutableRangeSet.<Integer>builder()
                .add(Range.closed(1, 10))
                .add(Range.openClosed(12, 15))
                .add(Range.closedOpen(16, 20))
                .build();
        System.out.println("immutableRangeSet = " + immutableRangeSet);
    }

    private static void rangeMapExample() {
        System.out.println("----------------- rangeMapExample ------------------");
        RangeMap<Integer, String> treeRangeMap = TreeRangeMap.create();
        treeRangeMap.put(Range.closed(1, 5), "Kobe");
        treeRangeMap.put(Range.openClosed(7, 10), "Wade");
        System.out.println("treeRangeMap = " + treeRangeMap);
        System.out.println("treeRangeMap.get(4) = " + treeRangeMap.get(4));
        System.out.println("treeRangeMap.get(10) = " + treeRangeMap.get(10));

        RangeMap<Integer, String> immutableRangeMap = ImmutableRangeMap.<Integer, String>builder()
                .put(Range.open(15, 20), "Curry")
                .build();
        System.out.println("immutableRangeMap = " + immutableRangeMap);
    }

    public static void main(String[] args) {
        multisetExample();
        sortedMultisetExample();
        multimapExample();
        biMapExample();
        tableExample();
        classToInstanceMapExample();
        rangeSetExample();
        rangeMapExample();
    }

}
