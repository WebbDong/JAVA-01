package lesson09.bytebuddyagent.algorithm;

import java.util.Arrays;

/**
 * 插入排序
 * 1、插入排序不需要额外的存储空间，空间复杂度是O(1)，所以是原地排序
 * 2、插入排序，对于值相同的元素，我们可以选择将后面出现的元素，插入到前面出现元素的后面。这样保持
 *      原有前后顺序不变，所以是稳定排序算法
 * 3、最好情况时间复杂度为O(n)，最坏情况时间复杂度O(n^2)，平均时间复杂度为O(n^2)
 * 4、插入排序与冒泡排序虽然时间复杂度是一样的，但是从代码实现上来看，
 *    冒泡排序的数据交换要比插入排序的数据移动要复杂，冒泡排序需要3个赋值，而插入排序只需要1个。
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr1 = {4, 5, 6, 1, 3, 2};
        insertionSort(arr1);
        System.out.println(Arrays.toString(arr1));

        int[] arr2 = {6, 5, 4, 3, 2, 1};
        insertionSort(arr2);
        System.out.println(Arrays.toString(arr2));
    }

    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            int value = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > value) {
                    // 移动数据
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            // 插入数据
            arr[j + 1] = value;
        }
    }

}
