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
