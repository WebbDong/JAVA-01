package lesson11.guava.basicutilities;

import com.google.common.base.Preconditions;

/**
 * 前置条件
 *      简化方法调用的前置条件判断
 *
 * Preconditions.checkArgument: 当第一个布尔参数为 false 时抛出 IllegalArgumentException
 * Preconditions.checkNotNull: 检查传入的值是否是 null，如果是 null 抛出 NullPointerException，如果不是 null，返回传入的值
 * Preconditions.checkState: 当第一个布尔参数为 false 时抛出 IllegalStateException，用来表示状态错误
 * Preconditions.checkElementIndex: 检查 index 作为索引值对某个集合、字符串或数组是否有效，index >= 0 && index < size。如果超出合法索引值范围，抛出 IndexOutOfBoundsException
 * Preconditions.checkPositionIndex: 检查index作为位置值对某个集合、字符串或数组是否有效，index >= 0 && index <= size。如果超出合法范围，抛出 IndexOutOfBoundsException
 * Preconditions.checkPositionIndexes: 检查 [start, end] 表示的位置范围对某个集合、字符串或数组是否有效。如果超出合法索引值范围，抛出 IndexOutOfBoundsException
 *
 * @author Webb Dong
 * @date 2021-02-28 6:42 PM
 */
public class PreconditionsExample {

    public static int checkArgumentExample(int x, int y) {
        Preconditions.checkArgument(y != 0, "y = %s, 除数不能为0", y);
        return x / y;
    }

    public static void checkNotNullExample(String str) {
        String value = Preconditions.checkNotNull(str);
        System.out.println("checkNotNullExample(String) value = " + value);
    }

    public static void checkStateExample(int[] arr) {
        Preconditions.checkState(arr.length <= 5, "数组大小不能大于 %s", 5);
    }

    public static void checkElementIndexExample(int[] arr, int index) {
        int i = Preconditions.checkElementIndex(index, arr.length, "索引不能超过" + (arr.length - 1));
        System.out.println("checkElementIndexExample() i = " + i);
    }

    public static void checkPositionIndexExample(int[] arr, int index) {
        int i = Preconditions.checkPositionIndex(index, arr.length, "位置值不能超过" + arr.length);
        System.out.println("checkPositionIndexExample() i = " + i);
    }

    public static void checkPositionIndexesExample(int[] arr, int start, int end) {
        Preconditions.checkPositionIndexes(start, end, arr.length);
    }

    public static void main(String[] args) {
        System.out.println("checkArgumentExample(10, 5) = " + checkArgumentExample(10, 5));
        checkNotNullExample("Ferrari");

        int[] arr1 = {1, 2, 3, 4, 5};
        checkStateExample(arr1);

        int[] arr2 = {1, 2, 3, 4, 5, 6};
        checkElementIndexExample(arr2, 1);

        int[] arr3 = {1, 2, 3, 4, 5, 6};
        checkPositionIndexExample(arr3, 6);

        int[] arr4 = {1, 2, 3, 4, 5, 6, 7, 8};
        checkPositionIndexesExample(arr4, 0, 5);
    }

}
