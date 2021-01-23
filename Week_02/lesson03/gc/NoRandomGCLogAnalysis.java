package lesson03.gc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Webb Dong
 * @description: 演示 GC 日志生成与分析，不使用随机算法
 * @date 2021-01-22 14:03
 */
public class NoRandomGCLogAnalysis {

    public static void main(String[] args) {
        // 持续运行毫秒数; 可根据需要进行修改
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        // 当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        // 结束时间戳
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");
        // 缓存 2000 个对象，让其进入老年代
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        // 在此时间范围内,持续循环
        try {
            while (System.currentTimeMillis() < endMillis) {
                // 生成垃圾对象
                Object garbage = generateGarbage(counter.intValue());
                if (counter.intValue() < cacheSize) {
                    cachedGarbage[counter.intValue()] = garbage;
                }
                counter.increment();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.out.println("执行异常!异常前共生成对象次数:" + counter.longValue());
            return;
        }
        System.out.println("执行结束!共生成对象次数:" + counter.longValue());
    }

    /**
     * 生成对象
     * @return
     */
    private static Object generateGarbage(int n) {
        final int SIZE = 40960;
        int type = n % 4;
        Object result;
        switch (type) {
            case 0:
                result = new int[SIZE];
                break;
            case 1:
                result = new byte[SIZE];
                break;
            case 2:
                result = new double[SIZE];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    builder.append(i);
                }
                result = builder.toString();
                break;
        }
        return result;
    }

}
