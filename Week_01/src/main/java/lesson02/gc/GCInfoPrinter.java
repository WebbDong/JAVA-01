package lesson02.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author Webb Dong
 * @description: 打印当前 JVM 所使用的 GC
 * @date 2021-01-20 17:41
 */
public class GCInfoPrinter {

    /**
     * -XX:+UseSerialGC:
     * GC Name:Copy, [Eden Space, Survivor Space]
     * GC Name:MarkSweepCompact, [Eden Space, Survivor Space, Tenured Gen]
     *
     * -XX:+UseParNewGC: ParNew + Serial Old，这种组合已过时，不推荐使用
     * GC Name:ParNew, [Par Eden Space, Par Survivor Space]
     * GC Name:MarkSweepCompact, [Par Eden Space, Par Survivor Space, Tenured Gen]
     *
     * -XX:+UseParallelGC:
     * GC Name:PS Scavenge, [PS Eden Space, PS Survivor Space]
     * GC Name:PS MarkSweep, [PS Eden Space, PS Survivor Space, PS Old Gen]
     *
     * -XX:+UseConcMarkSweepGC:
     * GC Name:ParNew, [Par Eden Space, Par Survivor Space]
     * GC Name:ConcurrentMarkSweep, [Par Eden Space, Par Survivor Space, CMS Old Gen]
     *
     * -XX:-UseParNewGC -XX:+UseConcMarkSweepGC: Serial + CMS，这种组合已过时，不推荐使用
     * GC Name:Copy, [Eden Space, Survivor Space]
     * GC Name:ConcurrentMarkSweep, [Eden Space, Survivor Space, CMS Old Gen]
     *
     * -XX:+UseG1GC:
     * GC Name:G1 Young Generation, [G1 Eden Space, G1 Survivor Space]
     * GC Name:G1 Old Generation, [G1 Eden Space, G1 Survivor Space, G1 Old Gen]
     * @param args
     */
    public static void main(String[] args) {
        try {
            List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
                System.out.println("GC Name:" + gcMxBean.getName() + ", " + Arrays.toString(gcMxBean.getMemoryPoolNames()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
