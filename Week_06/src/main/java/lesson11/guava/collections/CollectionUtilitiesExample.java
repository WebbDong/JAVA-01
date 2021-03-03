package lesson11.guava.collections;

import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Queues;
import com.google.common.collect.Range;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 集合工具类
 *      Collections2: Collection 工具类
 *      Lists: List 相关工具类
 *      Sets: Set 相关工具类
 *      Maps: Map 相关工具类
 *      Queues: Queue 相关工具类
 *      Multisets: Multiset 相关工具类
 *      Multimaps: Multimap 相关工具类
 *      Tables: Table 相关工具类
 *
 * @author Webb Dong
 * @date 2021-03-02 12:14 PM
 */
public class CollectionUtilitiesExample {

    private static void collections2Example() {
        System.out.println("----------------- collections2Example ------------------");
        ArrayList<Integer> integerList = Lists.newArrayList(300, 100, 200, 500, 400);
        System.out.println("integerList = " + integerList);
        // 过滤删选大于300的元素，返回过滤后的新结果集，不会修改原来的结果集
        Collection<Integer> filter = Collections2.filter(integerList, input -> input > 300);
        System.out.println("filter = " + filter);

        // 转换集合
        Collection<String> transform = Collections2.transform(integerList, input ->
                new StringBuilder().append("str").append(input).toString());
        System.out.println("transform = " + transform);

        // 先将元素排序，在排列
        integerList = Lists.newArrayList(300, 100, 200);
        Collection<List<Integer>> orderedPermutations1 = Collections2.orderedPermutations(integerList);
        System.out.println("orderedPermutations1 = {");
        for (List<Integer> list : orderedPermutations1) {
            System.out.println("    " + list);
        }
        System.out.println("}");
        Collection<List<Integer>> orderedPermutations2 = Collections2.orderedPermutations(
                integerList, (v1, v2) -> v2 - v1);
        System.out.println("orderedPermutations2 = {");
        for (List<Integer> list : orderedPermutations2) {
            System.out.println("    " + list);
        }
        System.out.println("}");

        // 排列
        Collection<List<Integer>> permutations = Collections2.permutations(integerList);
        System.out.println("permutations = {");
        for (List<Integer> list : permutations) {
            System.out.println("    " + list);
        }
        System.out.println("}");
    }

    private static void listsExample() {
        System.out.println("----------------- listsExample ------------------");
        ArrayList<Integer> integerArrayList = Lists.newArrayList(90, 80, 50, 60);
        System.out.println("integerArrayList = " + integerArrayList);

        LinkedList<String> linkedList = Lists.newLinkedList();
        linkedList.add("Wade");
        linkedList.add("Kobe");
        linkedList.add("Curry");
        linkedList.add("Paul");
        System.out.println("linkedList = " + linkedList);

        // 转换
        List<String> transform = Lists.transform(integerArrayList, input -> "str" + input);
        System.out.println("transform = " + transform);

        // 反转
        List<Integer> reverse = Lists.reverse(integerArrayList);
        System.out.println("reverse = " + reverse);

        // 分割集合
        List<List<Integer>> partition1 = Lists.partition(integerArrayList, 2);
        System.out.println("partition1 = " + partition1);
        List<List<Integer>> partition2 = Lists.partition(integerArrayList, 1);
        System.out.println("partition2 = " + partition2);

        // 笛卡儿积
        List<List<Integer>> cartesianProduct = Lists.cartesianProduct(
                Lists.newArrayList(10, 20), Lists.newArrayList(30, 40));
        System.out.println("cartesianProduct = " + cartesianProduct);
    }

    private static void setsExample() {
        System.out.println("----------------- setsExample ------------------");
        HashSet<String> hashSet1 = Sets.newHashSet();
        hashSet1.add("Ferrari");
        hashSet1.add("Lamborghini");
        hashSet1.add("Pagani");
        hashSet1.add("BMW");
        hashSet1.add("BenZ");
        System.out.println("hashSet1 = " + hashSet1);

        // 过滤
        Set<String> filterSet = Sets.filter(hashSet1, input -> !input.equals("Lamborghini"));
        System.out.println("filterSet = " + filterSet);

        HashSet<String> hashSet2 = Sets.newHashSet();
        hashSet2.add("Ferrari");
        hashSet2.add("Lamborghini");
        hashSet2.add("Audi");
        hashSet2.add("BMW");
        hashSet2.add("BenZ");
        // 获取两个集合中，不同的元素，只返回第一个集合的不同元素
        Sets.SetView<String> differenceSet1 = Sets.difference(hashSet1, hashSet2);
        System.out.println("differenceSet1 = " + differenceSet1);
        Sets.SetView<String> differenceSet2 = Sets.difference(hashSet2, hashSet1);
        System.out.println("differenceSet2 = " + differenceSet2);

        // 返回两个集合中，不同的元素，两个集合的不同元素都返回
        Sets.SetView<String> symmetricDifferenceSet = Sets.symmetricDifference(hashSet1, hashSet2);
        System.out.println("symmetricDifferenceSet = " + symmetricDifferenceSet);

        // 获取两个集合的交集
        Sets.SetView<String> intersectionSet = Sets.intersection(hashSet1, hashSet2);
        System.out.println("intersectionSet = " + intersectionSet);

        // 按照指定的 size 将元素进行组合
        Set<Set<String>> combinationSet = Sets.combinations(hashSet1, 4);
        System.out.println("combinationSet = {");
        for (Set<String> set : combinationSet) {
            System.out.println("    " + set);
        }
        System.out.println("}");
    }

    private static void mapsExample() {
        System.out.println("----------------- mapsExample ------------------");
        HashMap<String, Integer> hashMap1 = Maps.newHashMap();
        hashMap1.put("k1", 10);
        hashMap1.put("k2", 20);
        hashMap1.put("k3", 30);
        hashMap1.put("k4", 40);
        hashMap1.put("k5", 50);
        hashMap1.put("k11", 120);
        System.out.println("hashMap1 = " + hashMap1);

        // 使用 key 和 value 作为输入，计算出一个新的 value
        Map<String, String> transformEntriesMap = Maps.transformEntries(hashMap1,
                (key, value) -> "new" + value);
        System.out.println("transformEntriesMap = " + transformEntriesMap);

        // 使用 Function 计算出一个新的 value
        Map<String, String> transformValuesMap = Maps.transformValues(hashMap1,
                input -> "new" + input);
        System.out.println("transformValuesMap = " + transformValuesMap);

        // 根据 Entry 来过滤
        Map<String, Integer> filterEntriesMap = Maps.filterEntries(hashMap1,
                entry -> entry.getKey().equals("k3") && entry.getValue().equals(30));
        System.out.println("filterEntriesMap = " + filterEntriesMap);

        // 根据 key 来过滤
        Map<String, Integer> filterKeysMap = Maps.filterKeys(hashMap1, k -> k.equals("k5"));
        System.out.println("filterKeysMap = " + filterKeysMap);

        // 根据 value 来过滤
        Map<String, Integer> filterValuesMap = Maps.filterValues(hashMap1, v -> v.equals(20));
        System.out.println("filterValuesMap = " + filterValuesMap);

        NavigableMap<Integer, String> treeMap = Maps.newTreeMap();
        treeMap.put(0, "str0");
        treeMap.put(1, "str1");
        treeMap.put(2, "str2");
        treeMap.put(3, "str3");
        treeMap.put(4, "str4");
        // 根据指定的区间截取子 map
        NavigableMap<Integer, String> subMap = Maps.subMap(treeMap, Range.closedOpen(1, 3));
        System.out.println("subMap = " + subMap);

        ArrayList<Integer> integerArrayList = Lists.newArrayList(888, 777, 666, 555, 999, 333);
        // 将列表转换为 map ，列表的各个元素作为 value，Function.apply 返回值为 key
        ImmutableMap<String, Integer> immutableMap1 = Maps.uniqueIndex(integerArrayList, input -> "k" + (input * 2));
        System.out.println("immutableMap1 = " + immutableMap1);

        HashSet<Integer> hashSet = Sets.newHashSet(222, 444, 666, 555, 888, 999);
        // 与 uniqueIndex 相反，asMap 将列表的各个元素作为 key，Function.apply 返回值作为 value
        Map<Integer, String> asMap = Maps.asMap(hashSet, input -> "v" + input);
        System.out.println("asMap = " + asMap);

        HashMap<Object, Object> hashMap2 = Maps.newHashMap();
        hashMap2.put("k1", 10);
        hashMap2.put("k2", 20);
        hashMap2.put("k8", 60);
        hashMap2.put("k9", 70);
        hashMap2.put("k10", 90);
        hashMap2.put("k11", 200);
        // 对比两个 map 的不同点
        MapDifference<Object, Object> differenceMap = Maps.difference(hashMap1, hashMap2);
        System.out.println("differenceMap = " + differenceMap);
        // 两个 map 集合的键值对是否相等
        System.out.println("differenceMap.areEqual() = " + differenceMap.areEqual());
        // 返回两个 map 中健值对都相等的
        System.out.println("differenceMap.entriesInCommon() = " + differenceMap.entriesInCommon());
        // 只存在于左边的键值对
        System.out.println("differenceMap.entriesOnlyOnLeft() = " + differenceMap.entriesOnlyOnLeft());
        // 只存在于右边的键值对
        System.out.println("differenceMap.entriesOnlyOnRight() = " + differenceMap.entriesOnlyOnRight());
        // 返回两个 map 中，key 相同但是 value 不同的
        Map<Object, MapDifference.ValueDifference<Object>> entriesDifferingMap = differenceMap.entriesDiffering();
        System.out.println("differenceMap.entriesDiffering() = " + entriesDifferingMap);
        System.out.println("entriesDifferingMap.get(\"k11\").leftValue() = " + entriesDifferingMap.get("k11").leftValue());
        System.out.println("entriesDifferingMap.get(\"k11\").rightValue() = " + entriesDifferingMap.get("k11").rightValue());
    }

    private static void queuesExample() {
        System.out.println("----------------- queuesExample ------------------");
        ArrayBlockingQueue<Integer> arrayBlockingQueue = Queues.newArrayBlockingQueue(30);
        ArrayList<Integer> buffer = Lists.newArrayList();
        boolean[] isRunning = {true};
        new Thread(() -> {
            try {
                int sum = 0;
                while (isRunning[0]) {
                    // 每次到1000条数据才进行入库，等待5秒钟，没达到1000条也继续入库
                    Queues.drain(arrayBlockingQueue, buffer, 1000, Duration.ofSeconds(5));
                    sum += buffer.size();
                    System.out.println(buffer.size());
                    buffer.clear();
                }
                System.out.println("sum = " + sum);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i < 200000; i++) {
                try {
                    arrayBlockingQueue.put(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            isRunning[0] = false;
        }).start();
    }

    private static void multisetsExample() {
        System.out.println("----------------- multisetsExample ------------------");
        Multiset<Integer> multiset1 = HashMultiset.create();
        multiset1.add(50);
        multiset1.add(20);
        multiset1.add(90);
        multiset1.add(40);
        multiset1.add(70);
        Multiset<Integer> filterMultiset = Multisets.filter(multiset1, input -> input < 60);
        System.out.println("filterMultiset = " + filterMultiset);

        Multiset<Integer> multiset2 = HashMultiset.create();
        multiset2.add(50);
        multiset2.add(120);
        multiset2.add(290);
        multiset2.add(340);
        multiset2.add(470);
        multiset2.add(70);
        multiset2.add(70);
        Multiset<Integer> sumMultiset = Multisets.sum(multiset1, multiset2);
        System.out.println("sumMultiset = " + sumMultiset);

        Multiset<Integer> unionMultiset = Multisets.union(multiset1, multiset2);
        System.out.println("unionMultiset = " + unionMultiset);

        Multiset<Integer> differenceMultiset = Multisets.difference(multiset2, multiset1);
        System.out.println("differenceMultiset = " + differenceMultiset);
    }

    private static void multimapsExample() {
        System.out.println("----------------- multimapsExample ------------------");
        ListMultimap<String, Integer> listMultimap = Multimaps.newListMultimap(
                new HashMap<>(), () -> new ArrayList<>());
        listMultimap.put("k1", 10);
        listMultimap.put("k1", 10);
        listMultimap.put("k1", 10);
        listMultimap.put("k2", 120);
        listMultimap.put("k3", 30);
        listMultimap.put("k4", 40);
        listMultimap.put("k5", 50);
        System.out.println("listMultimap = " + listMultimap);

        SetMultimap<Integer, String> setMultimap = Multimaps.newSetMultimap(
                new HashMap<>(), () -> new HashSet<>());
        setMultimap.put(3, "Iverson");
        setMultimap.put(3, "Wade");
        setMultimap.put(3, "Paul");
        setMultimap.put(1, "T-Mac");
        setMultimap.put(8, "Kobe");
        setMultimap.put(13, "Harden");
        System.out.println("setMultimap = " + setMultimap);
    }

    private static void tablesExample() {
        System.out.println("----------------- tablesExample ------------------");
        Table<String, Integer, String> table1 = Tables.newCustomTable(
                new HashMap<>(), () -> new HashMap<>());
        table1.put("IBM", 101, "Wade");
        table1.put("IBM", 102, "Kobe");
        table1.put("Microsoft", 103, "Chris");
        table1.put("Microsoft", 104, "Paul");
        table1.put("Google", 105, "James");
        System.out.println("table1 = " + table1);

        Table<Integer, String, String> transposeTable = Tables.transpose(table1);
        System.out.println("transposeTable = " + transposeTable);
    }

    public static void main(String[] args) {
        collections2Example();
        listsExample();
        setsExample();
        mapsExample();
        queuesExample();
        multisetsExample();
        multimapsExample();
        tablesExample();
    }

}
