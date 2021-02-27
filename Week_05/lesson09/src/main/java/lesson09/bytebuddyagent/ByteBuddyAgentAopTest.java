package lesson09.bytebuddyagent;

import lesson09.bytebuddyagent.algorithm.BubbleSort;
import lesson09.bytebuddyagent.algorithm.InsertionSort;
import lesson09.bytebuddyagent.algorithm.MergeSort;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * @author Webb Dong
 * @description: ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入下的 AOP
 * @date 2021-02-20 23:45
 */
public class ByteBuddyAgentAopTest {

    // -javaagent:D:/develop/workspace/java/bytebuddy-aop-agent/target/bytebuddy-aop-agent-1.0-SNAPSHOT.jar
    // https://github.com/WebbDong/bytebuddy-aop-agent
    public static void main(String[] args) {
        int[] arr = IntStream.range(0, 100000)
                .map(i -> ThreadLocalRandom.current().nextInt(60000))
                .toArray();
        InsertionSort.insertionSort(arr.clone());
        BubbleSort.bubbleSort(arr.clone());
        BubbleSort.bubbleSortOptimizing(arr.clone());
//        MergeSort.recursionMergeSort(arr.clone());
        MergeSort.iterationMergeSort(arr.clone());
    }

}
