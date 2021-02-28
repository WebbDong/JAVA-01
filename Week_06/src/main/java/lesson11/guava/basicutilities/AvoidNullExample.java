package lesson11.guava.basicutilities;

import com.google.common.base.Optional;

import java.util.Set;

/**
 * 使用和规避 null
 * Google 认为相比默默地接受 null，使用快速失败操作拒绝 null 值对开发者更有帮助。
 *
 * null 的含糊语义让人很不舒服。null 很少可以明确地表示某种语义，例如，Map.get(key) 返回 null 时，可能表示 map 中的值是 null，
 * 亦或 map 中没有 key 对应的值。null 可以表示失败、成功或几乎任何情况。使用 null 以外的特定值，会让你的逻辑描述变得更清晰。
 *
 * Optional.of(T): 创建指定引用的 Optional 实例，若传入 null 则快速失败抛出异常
 * Optional.absent(): 创建引用缺失的 Optional 实例
 * Optional.fromNullable(T): 创建指定引用的 Optional 实例，若引用为 null 则表示缺失
 *
 * boolean isPresent(): 如果 Optional 包含非 null 的引用（引用存在），返回 true
 * T get(): 返回 Optional 所包含的引用，若引用缺失，则抛出 java.lang.IllegalStateException
 * T or(T): 返回 Optional 所包含的引用，若引用缺失，返回指定的值
 * T orNull(): 返回 Optional 所包含的引用，若引用缺失，返回 null
 * Set<T> asSet(): 返回 Optional 所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合。
 *
 * 使用 Optional 的意义
 *      使用Optional除了赋予null语义，增加了可读性，最大的优点在于它是一种傻瓜式的防护。Optional迫使你积极思考引用缺失的情况，
 *      因为你必须显式地从Optional获取引用。
 *
 * @author Webb Dong
 * @date 2021-02-28 3:22 PM
 */
public class AvoidNullExample {

    /**
     * Guava Optional 示例
     */
    public static void optionalExample() {
        Integer nullInteger = null;
        // of 方法传入 null 将直接报错
//        Optional<Integer> i1 = Optional.of(nullInteger);
        Optional<Integer> i2 = Optional.of(500);
        Optional<Integer> i3 = Optional.absent();

        // fromNullable 可以传入 null 和 非 null
        Optional<Integer> i4 = Optional.fromNullable(nullInteger);
        Optional<Integer> i5 = Optional.fromNullable(6000);

        if (i4.isPresent()) {
            System.out.println("i4.get() = " + i4.get());
        }
        if (i5.isPresent()) {
            System.out.println("i5.get() = " + i5.get());
        }
        System.out.println("i4.or(1) = " + i4.or(1));
        System.out.println("i4.orNull() = " + i4.orNull());
        System.out.println("i5.orNull() = " + i5.orNull());
        System.out.println("i4.asSet() = " + i4.asSet());

        // 返回获取的 Set 是 Collections.SingletonSet 的实例，返回的 SingletonSet 不可以增删改
        Set<Integer> asSet = i5.asSet();
        System.out.println("i5.asSet() = " + asSet);

        Integer ret1 = Optional.fromNullable(nullInteger).or(50);
        System.out.println("Optional.fromNullable(null).or(50) = " + ret1);
    }

    public static void main(String[] args) {
        optionalExample();
    }

}
