package lesson11.guava.caches;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * Guava 缓存
 *      通常来说，Guava Cache适用于：
 *          1、你愿意消耗一些内存空间来提升速度。
 *          2、你预料到某些键会被查询一次以上。
 *          3、缓存中存放的数据总量不会超出内存容量。
 *
 * @author Webb Dong
 * @date 2021-03-03 1:27 PM
 */
public class GuavaCacheExample {

    @SneakyThrows
    private static void cacheLoaderExample() {
        System.out.println("----------------- cacheLoaderExample ------------------");
        LoadingCache<Object, Object> cache = CacheBuilder.newBuilder()
                // cache 初始化大小为10
                .initialCapacity(10)
                // 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                .concurrencyLevel(5)
                // 设置cache中的数据在写入之后的存活时间为10秒
                .expireAfterWrite(10, TimeUnit.SECONDS)
                // 当某个 key 取不到缓存时，就将 CacheLoader 的 load 方法的返回值返回
                .build(new CacheLoader<Object, Object>() {

                    @Override
                    public Object load(Object key) throws Exception {
                        return "new" + key;
                    }

                });

        cache.put("k1", 5000);
        for (int i = 0; i < 20; i++) {
            System.out.println("cache.getIfPresent(\"k1\") = " + cache.getIfPresent("k1"));
            System.out.println("cache.get(\"k1\") = " + cache.get("k1"));
            Thread.sleep(1000);
        }
    }

    @SneakyThrows
    private static void callableExample() {
        System.out.println("----------------- callableExample ------------------");
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                // cache 初始化大小为10
                .initialCapacity(10)
                // 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                .concurrencyLevel(5)
                // 设置cache中的数据在写入之后的存活时间为10秒
                .expireAfterWrite(5, TimeUnit.SECONDS)
                // 当某个 key 取不到缓存时，就将 CacheLoader 的 load 方法的返回值返回
                .build();

        cache.put("k2", 2000);
        for (int i = 0; i < 20; i++) {
            // 当某个 key 取不到缓存时，就将 Callable 的 call 方法的返回值返回
            System.out.println("cache.get(\"k2\", Callable) = " + cache.get(
                    "k2", () -> "new value"));
            Thread.sleep(1000);
        }
    }

    /**
     * 基于容量的回收
     */
    private static void sizeBasedEvictionExample() {
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                // 设置最大容量100，超过100时缓存将尝试回收最近没有使用或总体上很少使用的缓存项。
                .maximumSize(100)
                .build();
    }

    /**
     * 定时回收
     *      expireAfterAccess(long, TimeUnit): 缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样。
     *
     *      expireAfterWrite(long, TimeUnit): 缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
     *                                        如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
     */
    @SneakyThrows
    private static void timedEvictionExample() {
        System.out.println("----------------- timedEvictionExample ------------------");
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                // 5秒后缓存失效
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build();
        cache.put("k1", 900);
        for (int i = 0; i < 20; i++) {
            System.out.println("cache.getIfPresent(\"k1\") = " + cache.getIfPresent("k1"));
            Thread.sleep(1000);
        }
    }

    /**
     * 基于引用的回收
     *      CacheBuilder.weakKeys(): 使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），
     *                               使用弱引用键的缓存用 == 而不是 equals 比较键。
     *
     *      CacheBuilder.weakValues(): 使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），
     *                                 使用弱引用值的缓存用 == 而不是 equals 比较值。
     *
     *      CacheBuilder.softValues(): 使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。
     *                                 考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定
     *                                 使用软引用值的缓存同样用 == 而不是 equals 比较值。
     */
    private static void referenceBasedEvictionExample() {
        CacheBuilder.newBuilder()
                .weakKeys()
                .weakValues()
                .build();
    }

    /**
     * 显式清除
     */
    private static void invalidateExample() {
        System.out.println("----------------- invalidateExample ------------------");
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .initialCapacity(20)
                .build();
        cache.put("k1", 888);
        System.out.println("cache.getIfPresent(\"k1\") = " + cache.getIfPresent("k1"));
        cache.invalidate("k1");
        System.out.println("cache.getIfPresent(\"k1\") = " + cache.getIfPresent("k1"));
    }

    /**
     * 移除监听器
     *
     * 通过 CacheBuilder.removalListener(RemovalListener)，可以声明一个监听器，以便缓存项被移除时做一些额外操作。缓存项被移除时，
     * RemovalListener 会获取移除通知 [RemovalNotification]，其中包含移除原因 [RemovalCause]、键和值。
     */
    @SneakyThrows
    private static void removalListenerExample() {
        System.out.println("----------------- removalListenerExample ------------------");
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .removalListener(n ->
                        System.out.println("cause: " + n.getCause()
                                + ", key = " + n.getKey()
                                + ", value = " + n.getValue()))
                .build();
        cache.put("k1", 555);
        for (int i = 0; i < 10; i++) {
            System.out.println("cache.getIfPresent(\"k1\") = " + cache.getIfPresent("k1"));
            Thread.sleep(1000);
            if (i == 3) {
                cache.invalidate("k1");
            }
        }
    }

    /**
     * 统计信息
     *
     * CacheBuilder.recordStats() 用来开启 Guava Cache 的统计功能。统计打开后，Cache.stats() 方法会返回 CacheStats 对象以提供如下统计信息：
     *      hitRate()：缓存命中率；
     *      averageLoadPenalty()：加载新值的平均时间，单位为纳秒；
     *      evictionCount()：缓存项被回收的总数，不包括显式清除。
     */
    private static void statisticsExample() {
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .recordStats()
                .build();
        cache.put("k1", 1024);
        System.out.println("cache.getIfPresent(\"k1\") = " + cache.getIfPresent("k1"));
        CacheStats stats = cache.stats();
        System.out.println("stats.hitCount() = " + stats.hitCount());
        System.out.println("stats.hitRate() = " + stats.hitRate());
        System.out.println("stats.averageLoadPenalty() = " + stats.averageLoadPenalty());
        System.out.println("stats.evictionCount() = " + stats.evictionCount());
    }

    public static void main(String[] args) {
//        cacheLoaderExample();
//        callableExample();
//        sizeBasedEvictionExample();
//        timedEvictionExample();
//        referenceBasedEvictionExample();
//        invalidateExample();
//        removalListenerExample();
        statisticsExample();
    }

}
