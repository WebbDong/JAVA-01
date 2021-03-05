package lesson11.guava.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.Builder;

/**
 * Hash
 *      Java内建的散列码 [hash code] 概念被限制为32位，并且没有分离散列算法和它们所作用的数据，因此很难用备选算法进行替换。此外，
 *      使用Java内建方法实现的散列码通常是劣质的，部分是因为它们最终都依赖于JDK类中已有的劣质散列码。
 *
 * Guava 散列包的组成
 *      HashFunction
 *          是一个单纯的（引用透明的）、无状态的方法，它把任意的数据块映射到固定数目的位指，并且保证相同的输入一定产生相同的输出，
 *          不同的输入尽可能产生不同的输出。
 *
 *      Hasher
 *          HashFunction 的实例可以提供有状态的 Hasher，Hasher 提供了流畅的语法把数据添加到散列运算，然后获取散列值。
 *          Hasher 可以接受所有原生类型、字节数组、字节数组的片段、字符序列、特定字符集的字符序列等等，或者任何给定了 Funnel 实现的对象。
 *          Hasher 实现了 PrimitiveSink 接口，这个接口为接受原生类型流的对象定义了 fluent 风格的API
 *
 *      Funnel
 *          Funnel 描述了如何把一个具体的对象类型分解为原生字段值，从而写入 PrimitiveSink。
 *
 *      HashCode
 *          一旦 Hasher 被赋予了所有输入，就可以通过 hash() 方法获取 HashCode 实例（多次调用 hash() 方法的结果是不确定的）。
 *          HashCode 可以通过 asInt()、asLong()、asBytes() 方法来做相等性检测，此外，writeBytesTo(array, offset, maxLength)
 *          把散列值的前maxLength字节写入字节数组。
 *
 * @author Webb Dong
 * @date 2021-03-05 11:13 AM
 */
public class HashExample {

    @Builder
    private static class Person {

        int id;

        String firstName;

        String lastName;

        int birthYear;

    }

    public static void main(String[] args) {
        Person person = Person.builder()
                .id(1)
                .firstName("Webb")
                .lastName("Dong")
                .birthYear(1891)
                .build();

        HashFunction sha256Func = Hashing.sha256();
        HashCode hashCode = sha256Func.newHasher()
                .putLong(1000L)
                .putString("Kawasaki", Charsets.UTF_8)
                .putObject(person, (from, into) -> {
                    into.putInt(from.id)
                            .putString(from.firstName, Charsets.UTF_8)
                            .putString(from.lastName, Charsets.UTF_8)
                            .putInt(from.birthYear);
                })
                .hash();
        System.out.println(hashCode.toString());
        System.out.println(hashCode.hashCode());
        System.out.println(hashCode.asLong());
    }

}
