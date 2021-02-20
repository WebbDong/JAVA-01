package lesson09.bytebuddyagent.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 归并排序
 *      1、基于分治思想，先拆分再合并
 *      2、稳定的排序算法
 *      3、最好情况、最坏情况，平均情况，时间复杂度都是 O(nlogn)
 *      4、递归版本空间复杂度 O(nlogn)，迭代版本空间复杂度 o(n)， 所以不是原地排序算法
 */
public class MergeSort {

    public static void main(String[] args) {
//        int[] arr1 = {500, 400, 300, 100,  90, 9, 5, 2, 1, 0};
        int[] arr1 = {500, 400, 300, 100, 90, 9, 5};
//        recursionMergeSort(arr1);
        iterationMergeSort(arr1);
//        forkJoinMergeSort(arr1);
        System.out.println(Arrays.toString(arr1));
    }

    // --------------- 递归方式 -------------------

    /**
     * 备份数组
     */
    private static int[] tempArr;

    /**
     * 递归方式归并排序，递归方式是自顶向下
     * @param arr
     */
    public static void recursionMergeSort(int[] arr) {
        tempArr = new int[arr.length >> 1];
        divide(arr, 0, arr.length);
    }

    /**
     * 拆分
     * @param arr
     * @param b
     * @param e
     */
    private static void divide(int[] arr, int b, int e) {
        // 如果元素只有一个或者没有元素直接返回
        if (e - b < 2) {
            return;
        }

        // 除以2，将数组分割成两半
        int mid = (b + e) >> 1;
        divide(arr, b, mid);
        divide(arr, mid, e);
        merge(arr, b, mid, e);
    }

    /**
     * 合并
     * @param arr
     * @param b
     * @param m
     * @param e
     */
    private static void merge(int[] arr, int b, int m, int e) {
//        System.out.println("b = " + b + ", m = " + m + ", e = " + e);
        final int le = m - b;
        final int re = e;
        int ai = b;
        int li = 0;
        int ri = m;

        // 将左半部分的数组元素备份
        for (int i = 0; i < le; i++) {
            tempArr[i] = arr[b + i];
        }

        // 左半部分遍历结束后，就不需要在比较移动元素
        while (li < le) {
            if (ri < re && arr[ri] < tempArr[li]) {
                arr[ai++] = arr[ri++];
            } else {
                arr[ai++] = tempArr[li++];
            }
        }
//        System.out.println(Arrays.toString(arr));
    }

    // --------------- 迭代方式 -------------------

    /**
     * 迭代方式是自底向上
     * @param arr
     */
    public static void iterationMergeSort(int[] arr) {
        final int len = arr.length;
        tempArr = new int[len];
        for (int w = 1; w < len; w *= 2) {
            for (int b = 0; b < len - w; b += w + w) {
                merge2(arr, b, b + w, Math.min(b + w + w, len));
            }
//            System.out.println("------------------------------------");
        }
    }

    /**
     * 合并
     * @param arr
     * @param b 数组起始位置
     * @param m 数组中间位置
     * @param e 数组结尾位置
     */
    private static void merge2(int[] arr, int b, int m, int e) {
        // 左半部分数组指针
        int tai = b;
        // 右半部分数组指针
        int ri = m;

        // 备份需要比较操作的数据
        for (int i = b; i < e; i++) {
            tempArr[i] = arr[i];
        }

        for (int i = b; i < e; i++) {
            if (tai >= m) { // 当左半部分数组操作完后，直接将右半部分的元素赋值
                arr[i] = tempArr[ri++];
            } else if (ri >= e) { // 当右半部分数组操作完后，直接将左半部分的元素赋值
                arr[i] = tempArr[tai++];
            } else if (tempArr[ri] < tempArr[tai]) {
                arr[i] = tempArr[ri++];
            } else {
                arr[i] = tempArr[tai++];
            }
        }
//        System.out.println(Arrays.toString(arr));
    }

    // --------------- 使用 ForkJoin 方式 -------------------

    @Data
    @AllArgsConstructor
    private static class MergeSortTask extends RecursiveTask<int[]> {

        private int[] arr;

        private int begin;

        private int end;

        @Override
        protected int[] compute() {
            if (end - begin < 2) {
                return arr;
            } else {
                int mid = (end + begin) >> 1;
                MergeSortTask task1 = new MergeSortTask(arr, begin, mid);
                MergeSortTask task2 = new MergeSortTask(arr, mid, end);
                task1.fork();
                task2.compute();
                task1.join();
                merge(arr, begin, mid, end);
            }
            return arr;
        }

    }

    public static void forkJoinMergeSort(int[] arr) {
        tempArr = new int[arr.length >> 1];
        final ForkJoinPool fjp = new ForkJoinPool();
        final MergeSortTask task = new MergeSortTask(arr, 0, arr.length);
        fjp.execute(task);
        task.join();
        fjp.shutdown();
    }

}
