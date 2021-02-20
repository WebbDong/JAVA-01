package lesson09.bytebuddyagent.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序
 * 1、冒泡排序空间复杂度是O(1)，是原地排序。
 * 2、冒牌排序相邻的元素相等时，不做交换。故是稳定排序
 * 3、最好情况时间复杂度O(n)，最坏情况时间复杂度O(n^2)
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr1 = {9, 8, 6, 4, 3, 2, 1};
        bubbleSort(arr1);
        System.out.println(Arrays.toString(arr1));

//        int[] arr2 = {1, 2, 3, 4, 6, 8, 9};
        int[] arr2 = {9, 8, 6, 4, 3, 2, 1};
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        bubbleSortOptimizing(arr2);
        System.out.println(Arrays.toString(arr2));
        bubbleSortOptimizing(arr3);
        System.out.println(Arrays.toString(arr3));
    }

    /**
     * 冒泡
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0, length = arr.length; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序优化
     * @param arr
     */
    public static void bubbleSortOptimizing(int[] arr) {
        for (int i = 0, length = arr.length; i < length - 1; i++) {
            // 每一次冒泡结束后，如果没有交换就代表已经是有序了，结束循环
            boolean isExChanged = false;
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isExChanged = true;
                }
            }
            if (!isExChanged) {
                break;
            }
        }
    }

}
