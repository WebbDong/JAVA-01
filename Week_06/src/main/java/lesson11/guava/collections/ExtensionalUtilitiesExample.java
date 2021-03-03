package lesson11.guava.collections;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.List;

/**
 * 集合扩展工具类
 *      Forwarding 装饰器: 针对所有类型的集合接口，Guava 都提供了 Forwarding 抽象类以简化装饰者模式的使用。
 *      PeekingIterator: Iterators提供一个 Iterators.peekingIterator(Iterator) 方法，来把 Iterator 包装为 PeekingIterator，
 *                       这是 Iterator 的子类，它能让你事先窥视 [peek()] 到下一次调用 next() 返回的元素。
 *      AbstractIterator: 自定义 Iterator
 *      AbstractSequentialIterator
 *
 * @author Webb Dong
 * @date 2021-03-02 23:50
 */
public class ExtensionalUtilitiesExample {

    /**
     * Forwarding 装饰器
     *
     * Forwarding抽象类定义了一个抽象方法：delegate()，可以覆盖这个方法来返回被装饰对象。所有其他方法都会直接委托给 delegate()。
     * 例如说：ForwardingList.get(int) 实际上执行了 delegate().get(int)。
     * 通过创建 ForwardingXXX 的子类并实现 delegate() 方法，可以选择性地覆盖子类的方法来增加装饰功能，而不需要自己委托每个方法
     */
    private static void forwardingExample() {
        System.out.println("----------------- forwardingExample ------------------");
        class AddLoggingList<T> extends ForwardingList<T> {

            private final List<T> delegate;

            public AddLoggingList(List<T> delegate) {
                this.delegate = delegate;
            }

            @Override
            protected List<T> delegate() {
                return delegate;
            }

            @Override
            public boolean add(T element) {
                System.out.println("add " + element);
                return super.add(element);
            }

        }

        AddLoggingList<Integer> addLoggingList = new AddLoggingList<>(Lists.newArrayList());
        addLoggingList.add(100);
        addLoggingList.add(200);
        addLoggingList.add(300);
        addLoggingList.add(400);
        System.out.println("addLoggingList = " + addLoggingList);
    }

    /**
     * Iterators.peekingIterator 返回的 PeekingIterator 不支持在 peek() 操作之后调用 remove() 方法。
     */
    private static void peekingIteratorExample() {
        System.out.println("----------------- peekingIteratorExample ------------------");
        List<Integer> list = Lists.newArrayList(10, 20, 30, 30, 40, 50, 50, 60);
        List<Integer> result = Lists.newArrayList();
        PeekingIterator<Integer> iter = Iterators.peekingIterator(list.iterator());
        while (iter.hasNext()) {
            Integer current = iter.next();
            while (iter.hasNext() && iter.peek().equals(current)) {
                // 跳过重复元素
                iter.next();
            }
            result.add(current);
        }
        System.out.println("result = " + result);
    }

    /**
     * AbstractIterator 继承了 UnmodifiableIterator，所以禁止实现 remove() 方法。如果需要支持 remove() 的迭代器，
     * 就不应该继承 AbstractIterator。
     */
    private static void abstractIteratorExample() {
        System.out.println("----------------- abstractIteratorExample ------------------");
        // 忽略空值的 Iterator
        class SkipNullsIterator<T> extends AbstractIterator<T> {

            private final Iterator<T> delegate;

            public SkipNullsIterator(Iterator<T> delegate) {
                this.delegate = delegate;
            }

            @Override
            protected T computeNext() {
                while (delegate.hasNext()) {
                    T data = delegate.next();
                    if (data != null) {
                        return data;
                    }
                }
                return endOfData();
            }

        }

        SkipNullsIterator<Integer> iterator = new SkipNullsIterator(
                Lists.newArrayList(10, null, 20, null, null, 30, 40, 50).iterator());
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * computeNext(T) 方法，它能接受前一个值作为参数。
     * 必须额外传入一个初始值，或者传入 null 让迭代立即结束
     * computeNext(T) 假定 null 值意味着迭代的末尾 AbstractSequentialIterator 不能用来实现可能返回 null 的迭代器
     */
    private static void abstractSequentialIteratorExample() {
        System.out.println("----------------- abstractSequentialIteratorExample ------------------");
        Iterator<Long> iterator = new AbstractSequentialIterator<Long>(1L) {

            @Override
            protected @Nullable Long computeNext(Long previous) {
                long newVal = previous * 2;
                if (newVal >= Integer.MAX_VALUE) {
                    return null;
                }
                return newVal;
            }

        };

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public static void main(String[] args) {
        forwardingExample();
        peekingIteratorExample();
        abstractIteratorExample();
        abstractSequentialIteratorExample();
    }

}
