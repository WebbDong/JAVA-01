package lesson11.guava.ranges;

import com.google.common.collect.BoundType;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

/**
 * 区间
 * @author Webb Dong
 * @date 2021-03-04 3:28 PM
 */
public class RangeExample {

    /**
     * 构建区间
     *      区间实例可以由 Range 类的静态方法获取:
     *          (a..b)	            open(C, C)
     *          [a..b]	            closed(C, C)
     *          [a..b)	            closedOpen(C, C)
     *          (a..b]	            openClosed(C, C)
     *          (a..+∞)	            greaterThan(C)
     *          [a..+∞)	            atLeast(C)
     *          (-∞..b)	            lessThan(C)
     *          (-∞..b]	            atMost(C)
     *          (-∞..+∞)	        all()
     *
     *      也可以明确地指定边界类型来构造区间:
     *          有界区间                                range(C, BoundType, C,   BoundType)
     *          无上界区间：((a..+∞) 或[a..+∞))          downTo(C, BoundType)
     *          无下界区间：((-∞..b) 或(-∞..b])          upTo(C, BoundType)
     */
    private static void createRangeExample() {
        System.out.println("----------------- createRangeExample ------------------");
        // [5..+∞)
        System.out.println(Range.atLeast(5));
        // [1..10)
        System.out.println(Range.range(1, BoundType.CLOSED, 10, BoundType.OPEN));
    }

    /**
     * 区间运算
     *      contains(C): 用来判断某个区间是否包含某个值
     *      containsAll(Iterable<? extends C>): 判断某个区间是否包含某个集合中的所有元素
     *
     *      查询运算:
     *          hasLowerBound() 和 hasUpperBound()：判断区间是否有特定边界，或是无限的
     *          lowerBoundType() 和 upperBoundType()：返回区间边界类型，CLOSED 或 OPEN；如果区间没有对应的边界，抛出 IllegalStateException；
     *          lowerEndpoint() 和 upperEndpoint()：返回区间的端点值；如果区间没有对应的边界，抛出 IllegalStateException；
     *          isEmpty()：判断是否为空区间。
     *
     *      关系运算:
     *          包含[enclose]
     *              如果内区间的边界没有超出外区间的边界，则外区间包含内区间。包含判断的结果完全取决于区间端点的比较
     *                  [3..6] 包含 [4..5] ；
     *                  (3..6) 包含 (3..6) ；
     *                  [3..6] 包含 [4..4)，虽然后者是空区间；
     *                  (3..6] 不包含 [3..6] ；
     *                  [4..5] 不包含 (3..6)，虽然前者包含了后者的所有值，离散域[discrete domains]可以解决这个问题
     *                  [3..6] 不包含 (1..1]，虽然前者包含了后者的所有值。
     *
     *          相连[isConnected]
     *
     *          交集[intersection]
     *              既包含于第一个区间，又包含于另一个区间的最大区间。当且仅当两个区间是相连的，它们才有交集。如果两个区间没有交集，
     *              该方法将抛出IllegalArgumentException。
     *
     *          跨区间[span]
     *              返回包括两个区间的最小区间，如果两个区间相连，那就是它们的并集。
     */
    private static void intervalArithmeticExample() {
        System.out.println("----------------- intervalArithmeticExample ------------------");
        // contains 和 containsAll
        System.out.println("Range.openClosed(20, 35).contains(20) = "
                + Range.openClosed(20, 35).contains(20));
        System.out.println("Range.closed(20, 35).contains(20) = "
                + Range.closed(20, 35).contains(20));
        System.out.println("Range.closed(20, 35).containsAll(Ints.asList(20, 35, 21)) = "
                + Range.closed(20, 35).containsAll(Ints.asList(20, 35, 21)));
        System.out.println("Range.closed(20, 35).containsAll(Ints.asList(20, 35, 36)) = "
                + Range.closed(20, 35).containsAll(Ints.asList(20, 35, 36)));

        // 查询运算
        System.out.println("Range.closed(4, 4).isEmpty() = " + Range.closed(4, 4).isEmpty());
        System.out.println("Range.openClosed(4, 4).isEmpty() = " + Range.openClosed(4, 4).isEmpty());
        System.out.println("Range.closedOpen(4, 4).isEmpty() = " + Range.closedOpen(4, 4).isEmpty());

        System.out.println("Range.closedOpen(4, 10).lowerEndpoint() = "
                + Range.closedOpen(4, 10).lowerEndpoint());
        System.out.println("Range.closedOpen(4, 10).upperEndpoint() = "
                + Range.closedOpen(4, 10).upperEndpoint());

        System.out.println("Range.closedOpen(4, 10).lowerBoundType() = "
                + Range.closedOpen(4, 10).lowerBoundType());
        System.out.println("Range.closedOpen(4, 10).upperBoundType() = "
                + Range.closedOpen(4, 10).upperBoundType());

        System.out.println("Range.closedOpen(4, 10).hasLowerBound() = "
                + Range.closedOpen(4, 10).hasLowerBound());
        System.out.println("Range.closedOpen(4, 10).hasUpperBound() = "
                + Range.closedOpen(4, 10).hasUpperBound());

        // 关系运算
        // 是否包含
        System.out.println("Range.closed(10, 20).encloses(Range.closed(13, 15)) = "
                + Range.closed(10, 20).encloses(Range.closed(13, 15)));
        System.out.println("Range.open(10, 20).encloses(Range.closed(10, 20)) = "
                + Range.open(10, 20).encloses(Range.closed(10, 20)));
        // 是否相连
        System.out.println("Range.closed(3, 5).isConnected(Range.open(5, 10)) = "
                + Range.closed(3, 5).isConnected(Range.open(5, 10)));
        System.out.println("Range.closed(0, 9).isConnected(Range.closed(3, 4)) = "
                + Range.closed(0, 9).isConnected(Range.closed(3, 4)));
        System.out.println("Range.closed(0, 5).isConnected(Range.closed(3, 9)) = "
                + Range.closed(0, 5).isConnected(Range.closed(3, 9)));
        System.out.println("Range.open(3, 5).isConnected(Range.open(5, 10)) = "
                + Range.open(3, 5).isConnected(Range.open(5, 10)));
        System.out.println("Range.closed(1, 5).isConnected(Range.closed(6, 10)) = "
                + Range.closed(1, 5).isConnected(Range.closed(6, 10)));
        // 获取交集
        System.out.println("Range.closed(3, 5).intersection(Range.open(5, 10)) = "
                + Range.closed(3, 5).intersection(Range.open(5, 10)));
        System.out.println("Range.closed(0, 9).intersection(Range.closed(3, 4)) = "
                + Range.closed(0, 9).intersection(Range.closed(3, 4)));
        System.out.println("Range.closed(0, 5).intersection(Range.closed(3, 9)) = "
                + Range.closed(0, 5).intersection(Range.closed(3, 9)));
//        System.out.println("Range.open(3, 5).intersection(Range.open(5, 10)) = "
//                + Range.open(3, 5).intersection(Range.open(5, 10)));
//        System.out.println("Range.closed(1, 5).intersection(Range.closed(6, 10)) = "
//                + Range.closed(1, 5).intersection(Range.closed(6, 10)));
        // 获取跨区间
        System.out.println("Range.closed(3, 5).span(Range.open(5, 10)) = "
                + Range.closed(3, 5).span(Range.open(5, 10)));
        System.out.println("Range.closed(0, 9).span(Range.closed(3, 4)) = "
                + Range.closed(0, 9).span(Range.closed(3, 4)));
        System.out.println("Range.closed(0, 5).span(Range.closed(3, 9)) = "
                + Range.closed(0, 5).span(Range.closed(3, 9)));
        System.out.println("Range.open(3, 5).span(Range.open(5, 10)) = "
                + Range.open(3, 5).span(Range.open(5, 10)));
        System.out.println("Range.closed(1, 5).span(Range.closed(6, 10)) = "
                + Range.closed(1, 5).span(Range.closed(6, 10)));
    }

    /**
     * 离散域
     */
    private static void discreteDomainExample() {
        System.out.println("----------------- discreteDomainExample ------------------");
        ContiguousSet<Integer> integers = ContiguousSet.create(Range.greaterThan(0), DiscreteDomain.integers());
        System.out.println(integers);
    }

    public static void main(String[] args) {
        createRangeExample();
        intervalArithmeticExample();
        discreteDomainExample();
    }

}
