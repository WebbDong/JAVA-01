# 第一题: 使用 [GCLogAnalysis.java](https://github.com/WebbDong/JAVA-01/blob/main/Week_02/lesson03/gc/GCLogAnalysis.java) 自己演练一遍串行 / 并行 /CMS/G1 的案例。
## 1. 测试环境
  - 操作系统: Windows 10
  - CPU: i9-9900KS 8核16线程
  - RAM: 32gb
  - 所有测试案例均关闭了自适应 -XX:-UseAdaptiveSizePolicy
  - 运行时间1秒

## 2. 串行 GC (Serial GC)
#### 1). JVM 配置  -Xms256m -Xmx256m
GC 执行情况:
  - 发生 OOM
  - 总 GC 次数: 65次
  - 总 GC 时间: 470ms
  - 平均 GC 暂停时间: 7.23ms
  - 最大 GC 暂停时间: 20.0ms
  - GC 之间的平均间隔时间: 39.0ms
  - Minor GC 次数: 10次
  - Minor GC 回收的内存量: 177.05mb
  - Minor GC 总时间: 100ms
  - Minor GC 平均时间: 10.0ms
  - Minor GC 之间的平均间隔时间: 91.0ms
  - Full GC 次数: 55次
  - Full GC 回收的内存量: 424.12mb
  - Full GC 总时间: 370ms
  - Full GC 平均时间: 6.73ms
  - Full GC 之间的平均间隔时间: 27.0ms
  
堆内存情况:
  - 年轻代堆内存总量: 78656K，已使用: 78534K，99.84%
  - eden 区总量: 69952K，已使用: 100%
  - from 区总量: 8704K，已使用: 98%
  - 老年代堆内存总量: 174784K，已使用: 174678K，99.94%

结论: 
  - 堆内存过小，10次 Minor GC 之后，之后全是 Full GC 直到第55次 Full GC 之后年轻代和老年代内存都已经满了，无法分配新内存而 OOM。

#### 2). JVM 配置  -Xms512m -Xmx512m
GC 执行情况:
  - 创建了16516次对象
  - 总 GC 次数: 30次
  - 总 GC 时间: 620ms
  - 平均 GC 暂停时间: 20.7ms
  - 最大 GC 暂停时间: 30.0ms
  - GC 之间的平均间隔时间: 57.0ms
  - Minor GC 次数: 23次
  - Minor GC 回收的内存量: 1.81gb
  - Minor GC 总时间: 430ms
  - Minor GC 平均时间: 18.7ms
  - Minor GC 之间的平均间隔时间: 64.0ms
  - Full GC 次数: 7次
  - Full GC 回收的内存量: 962.25mb
  - Full GC 总时间: 190ms
  - Full GC 平均时间: 27.1ms
  - Full GC 之间的平均间隔时间: 36.0ms
  
堆内存情况:
  - 年轻代堆内存总量: 157248K，已使用: 156153K，99.30%
  - eden 区总量: 139776K，已使用: 100%
  - from 区总量: 17472K，已使用: 93%
  - 老年代堆内存总量: 349568K，已使用: 349304K，99.92%

结论: 
  - 虽然不发生 OOM ，但是 GC 次数依然很频繁并且堆内存占用太高。
  - 频繁的 GC 导致过多的暂停时间，并且 Serial GC 是单线程串行化的造成吞吐量较低。

#### 3). JVM 配置  -Xms1g -Xmx1g
GC 执行情况:
  - 创建了25172次对象
  - 总 GC 次数: 23次
  - 总 GC 时间: 420ms
  - 平均 GC 暂停时间: 18.3ms
  - 最小 GC 暂停时间: 10.0ms
  - 最大 GC 暂停时间: 30.0ms
  - GC 之间的平均间隔时间: 39.0ms
  - Minor GC 次数: 23次
  - Minor GC 回收的内存量: 2.17gb
  - Minor GC 总时间: 420ms
  - Minor GC 平均时间: 18.3ms
  - Minor GC 之间的平均间隔时间: 39.0ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 314560K，已使用: 252997K，80.43%
  - eden 区总量: 279616K，已使用: 90%
  - from 区总量: 34944K，已使用: 0%
  - 老年代堆内存总量: 699072K，已使用: 371337K，53.12%

结论: 
  - 堆内存提高到1g之后没有出现 Full GC，出现 GC 的频率也降低，堆内存使用率也降低。同时提高了吞吐量。

#### 4). JVM 配置  -Xms2g -Xmx2g
GC 执行情况:
  - 创建了23405次对象
  - 总 GC 次数: 11次
  - 总 GC 时间: 440ms
  - 平均 GC 暂停时间: 40.0ms
  - 最小 GC 暂停时间: 30.0ms
  - 最大 GC 暂停时间: 50.0ms
  - GC 之间的平均间隔时间: 82.0ms
  - Minor GC 次数: 11次
  - Minor GC 总时间: 440ms
  - Minor GC 平均时间: 40.0ms
  - Minor GC 之间的平均间隔时间: 82.0ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 629120K，已使用: 114900K，18.26%
  - eden 区总量: 559232K，已使用: 8%
  - from 区总量: 69888K，已使用: 99%
  - 老年代堆内存总量: 1398144K，已使用: 1358319K，97.15%

结论: 
  - 堆内存提高到2g之后，GC 总次数下降，但是 GC 总暂停时间反而增加了20ms，大量对象被复制移动到了老年代。吞吐量反而有所下降。

#### 5). JVM 配置  -Xms4g -Xmx4g
GC 执行情况:
  - 创建了24889次对象
  - 总 GC 次数: 6次
  - 总 GC 时间: 320ms
  - 平均 GC 暂停时间: 53.3ms
  - 最小 GC 暂停时间: 40.0ms
  - 最大 GC 暂停时间: 70.0ms
  - GC 之间的平均间隔时间: 142.0ms
  - Minor GC 次数: 6次
  - Minor GC 总时间: 320ms
  - Minor GC 平均时间: 53.3ms
  - Minor GC 之间的平均间隔时间: 142.0ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 1258304K，已使用: 172860K，13.74%
  - eden 区总量: 1118528K，已使用: 2%
  - from 区总量: 139776K，已使用: 100%
  - 老年代堆内存总量: 2796224K，已使用: 852641K，30.49%

结论: 
  - 堆内存提高到4g之后，GC 总次数继续下降，GC 总暂停时间也下降，吞吐量有所回升，但是每次 GC 暂停时间有所提高。

#### 6). JVM 配置  -Xms8g -Xmx8g
GC 执行情况:
  - 创建了22878次对象
  - 总 GC 次数: 2次
  - 总 GC 时间: 160ms
  - 平均 GC 暂停时间: 80.0ms
  - 最小 GC 暂停时间: 80.0ms
  - 最大 GC 暂停时间: 80.0ms
  - GC 之间的平均间隔时间: 273.0ms
  - Minor GC 次数: 2次
  - Minor GC 总时间: 160ms
  - Minor GC 平均时间: 80.0ms
  - Minor GC 之间的平均间隔时间: 273.0ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 2516544K，已使用: 2125376K，84.46%
  - eden 区总量: 2236928K，已使用: 82%
  - from 区总量: 279616K，已使用: 99%
  - 老年代堆内存总量: 5592448K，已使用: 145374K，2.6%

结论:
  - 堆内存提高到8g之后，GC 次数进一步下降，每次 GC 的暂停时间进一步增加，吞吐量缺降低了。

#### 7). JVM 配置  -Xms16g -Xmx16g
GC 执行情况:
  - 创建了15457次对象
  - 总 GC 次数: 1次
  - 总 GC 时间: 90.0ms
  - Minor GC 次数: 1次
  - Minor GC 总时间: 90.0ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 5033152K，已使用: 471207K，9.4%
  - eden 区总量: 4473920K，已使用: 2%
  - from 区总量: 559232K，已使用: 64%
  - 老年代堆内存总量: 11184832K，已使用: 0K，0%

结论:
  - 堆内存提高到16g之后，只发生了一次 Minor GC 耗时 90ms，老年代内存没有使用，年轻代内存只用到 9.4%，但是吞吐量急剧下降。

## 3. 并行 GC (Parallel GC)
#### 1). JVM 配置  -Xms256m -Xmx256m
GC 执行情况:
  - 发生 OOM
  - 总 GC 次数: 45次
  - 总 GC 时间: 410ms
  - 平均 GC 暂停时间: 9.11ms
  - 最小 GC 暂停时间: 1.0ms
  - 最大 GC 暂停时间: 20.0ms
  - GC 之间的平均间隔时间: 59.0ms
  - Minor GC 次数: 8次
  - Minor GC 回收的内存量: 332.04mb
  - Minor GC 总时间: 30ms
  - Minor GC 平均时间: 3.75ms
  - Minor GC 之间的平均间隔时间: 71.0ms
  - Full GC 次数: 37次
  - Full GC 回收的内存量: 493.6mb
  - Full GC 总时间: 380ms
  - Full GC 平均时间: 10.27ms
  - Full GC 之间的平均间隔时间: 63.0ms
  
堆内存情况:
  - 年轻代堆内存总量: 76288K，已使用: 65536K，85.91%
  - eden 区总量: 65536K，已使用: 100%
  - from 区总量: 10752K，已使用: 0%
  - 老年代堆内存总量: 175104K，已使用: 175032K，99.96%

结论: 
  - 堆内存过小，8次 Minor GC 之后，之后全是 Full GC 直到第37次 Full GC 之后年轻代和老年代内存都已经满了，无法分配新内存而 OOM。

#### 2). JVM 配置  -Xms512m -Xmx512m
GC 执行情况:
  - 创建了15791次对象
  - 总 GC 次数: 36次
  - 总 GC 时间: 660ms
  - 平均 GC 暂停时间: 18.3ms
  - 最小 GC 暂停时间: 3.1ms
  - 最大 GC 暂停时间: 30.5ms
  - GC 之间的平均间隔时间: 72.0ms
  - Minor GC 次数: 11次
  - Minor GC 回收的内存量: 952.63mb
  - Minor GC 总时间: 50ms
  - Minor GC 平均时间: 4.55ms
  - Minor GC 之间的平均间隔时间: 178ms
  - Full GC 次数: 25次
  - Full GC 回收的内存量: 2.71gb
  - Full GC 总时间: 610ms
  - Full GC 平均时间: 24.4ms
  - Full GC 之间的平均间隔时间: 76.0ms
  
堆内存情况:
  - 年轻代堆内存总量: 153088K，已使用: 25745K，16.82%
  - eden 区总量: 131584K，已使用: 19%
  - from 区总量: 21504K，已使用: 0%
  - 老年代堆内存总量: 349696K，已使用: 349451K，99.93%

结论: 
  - 堆内存调整为512m之后，总暂停时间依然很高，老年代使用率也非常高。

#### 3). JVM 配置  -Xms1g -Xmx1g
GC 执行情况:
  - 创建了28327次对象
  - 总 GC 次数: 33次
  - 总 GC 时间: 430ms
  - 平均 GC 暂停时间: 13.0ms
  - 最小 GC 暂停时间: 10.0ms
  - 最大 GC 暂停时间: 30.0ms
  - GC 之间的平均间隔时间: 28.0ms
  - Minor GC 次数: 28次
  - Minor GC 回收的内存量: 5.03gb
  - Minor GC 总时间: 280ms
  - Minor GC 平均时间: 10.0ms
  - Minor GC 之间的平均间隔时间: 33ms
  - Full GC 次数: 5次
  - Full GC 回收的内存量: 1.53gb
  - Full GC 总时间: 150ms
  - Full GC 平均时间: 30.0ms
  - Full GC 之间的平均间隔时间: 146.0ms
  
堆内存情况:
  - 年轻代堆内存总量: 305664K，已使用: 221033K，72.31%
  - eden 区总量: 262144K，已使用: 67%
  - from 区总量: 43520K，已使用: 99%
  - 老年代堆内存总量: 699392K，已使用: 416310K，59.52%

结论: 
  - 堆内存调整为1g之后，总暂停时间明显减少，老年代使用率也明显降低。吞吐量增高。

#### 4). JVM 配置  -Xms2g -Xmx2g
GC 执行情况:
  - 创建了31206次对象
  - 总 GC 次数: 16次
  - 总 GC 时间: 260ms
  - 平均 GC 暂停时间: 16.3ms
  - 最小 GC 暂停时间: 10.0ms
  - 最大 GC 暂停时间: 30.0ms
  - GC 之间的平均间隔时间: 54.0ms
  - Minor GC 次数: 15次
  - Minor GC 回收的内存量: 5.81gb
  - Minor GC 总时间: 230ms
  - Minor GC 平均时间: 15.3ms
  - Minor GC 之间的平均间隔时间: 58ms
  - Full GC 次数: 1次
  - Full GC 回收的内存量: 0.98gb
  - Full GC 总时间: 30.0ms
  
堆内存情况:
  - 年轻代堆内存总量: 611840K，已使用: 574947K，93.97%
  - eden 区总量: 524800K，已使用: 92%
  - from 区总量: 87040K，已使用: 99%
  - 老年代堆内存总量: 1398272K，已使用: 665027K，47.56%

结论: 
  - 堆内存调整为2g之后，总暂停时间继续减少，吞吐量继续增高。

#### 5). JVM 配置  -Xms4g -Xmx4g
GC 执行情况:
  - 创建了31784次对象
  - 总 GC 次数: 8次
  - 总 GC 时间: 180ms
  - 平均 GC 暂停时间: 22.5ms
  - 最小 GC 暂停时间: 20.0ms
  - 最大 GC 暂停时间: 30.0ms
  - GC 之间的平均间隔时间: 103.0ms
  - Minor GC 次数: 8次
  - Minor GC 回收的内存量: 6.94gb
  - Minor GC 总时间: 180ms
  - Minor GC 平均时间: 22.5ms
  - Minor GC 之间的平均间隔时间: 103ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 1223168K，已使用: 205935K，16.84%
  - eden 区总量: 1048576K，已使用: 2%
  - from 区总量: 174592K，已使用: 99%
  - 老年代堆内存总量: 2796544K，已使用: 932883K，33.36%

结论: 
  - 堆内存调整为4g之后，总暂停时间进一步降低，没有 Full GC，吞吐量进一步提高。

#### 6). JVM 配置  -Xms8g -Xmx8g
GC 执行情况:
  - 创建了27747次对象
  - 总 GC 次数: 3次
  - 总 GC 时间: 90ms
  - 平均 GC 暂停时间: 22.5ms
  - 最大 GC 暂停时间: 30.0ms
  - GC 之间的平均间隔时间: 195.0ms
  - Minor GC 次数: 3次
  - Minor GC 回收的内存量: 5.62gb
  - Minor GC 总时间: 90ms
  - Minor GC 平均时间: 30ms
  - Minor GC 之间的平均间隔时间: 195.0ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 2446848K，已使用: 1596028K，65.23%
  - eden 区总量: 2097664K，已使用: 59%
  - from 区总量: 349184K，已使用: 99%
  - 老年代堆内存总量: 5592576K，已使用: 46309K，0.83%

结论: 
  - 堆内存调整为8g之后，总暂停时间缩短为90ms，没有 Full GC，但吞吐量缺有所下降。

#### 7). JVM 配置  -Xms16g -Xmx16g
GC 执行情况:
  - 创建了15066次对象
  - 总 GC 次数: 1次
  - 总 GC 时间: 30ms
  - Minor GC 次数: 1次
  - Minor GC 回收的内存量: 3.65gb
  - Minor GC 总时间: 30ms
  - Full GC 次数: 0次
  
堆内存情况:
  - 年轻代堆内存总量: 4893184K，已使用: 558097K，11.41%
  - eden 区总量: 4194304K，已使用: 4%
  - from 区总量: 698880K，已使用: 52%
  - 老年代堆内存总量: 11185152K，已使用: 8K，0%

结论: 
  - 堆内存调整为16g之后，总暂停时间缩短为30ms，没有 Full GC，但吞吐量急剧下降。

## 4. CMS
#### 1). JVM 配置  -Xms256m -Xmx256m
GC 执行情况:
  - 发生 OOM
  - 总暂停时间: 610ms
  - 平均暂停时间: 7.92ms
  - 最大暂停时间: 20.0ms
  - 总并发处理时间: 60.0ms
  - 平均并发处理时间: 0.769ms
  - 最大并发处理时间: 50.0ms
  - Minor GC 次数: 21
  - Minor GC 总时间: 310ms
  - Minor GC 平均时间: 14.8ms
  - Full GC 次数: 21
  - Full GC 总时间: 300ms
  - Full GC 平均时间: 14.3ms
  - Initial Mark 次数: 22
  - Concurrent Mark 次数: 22
  - Concurrent Preclean 次数: 18
  - Concurrent Abortable Preclean 次数: 14
  - Final Remark 次数: 13
  - Concurrent Sweep 次数: 13
  - Concurrent Reset 次数: 11
  
堆内存情况:
  - 年轻代堆内存总量: 78656K，已使用: 78184K，99.40%
  - eden 区总量: 69952K，已使用: 100%
  - from 区总量: 8704K，已使用: 94%
  - 老年代堆内存总量: 174784K，已使用: 174356K，99.76%

结论: 
  - 堆内存过小，CMS 发生多次退化，导致总暂停时间非常长，也发生了 大量的 Full GC，最终由于堆内存不足，导致 OOM

#### 2). JVM 配置  -Xms512m -Xmx512m
GC 执行情况:
  - 创建了15487次对象
  - 总暂停时间: 610ms
  - 平均暂停时间: 10.3ms
  - 最大暂停时间: 40.0ms
  - 总并发处理时间: 150.0ms
  - 平均并发处理时间: 1.97ms
  - 最大并发处理时间: 80.0ms
  - Minor GC 次数: 27
  - Minor GC 总时间: 540ms
  - Minor GC 平均时间: 20.0ms
  - Full GC 次数: 2
  - Full GC 总时间: 70.0ms
  - Full GC 平均时间: 35.0ms
  - Initial Mark 次数: 16
  - Concurrent Mark 次数: 16
  - Concurrent Preclean 次数: 16
  - Concurrent Abortable Preclean 次数: 16
  - Final Remark 次数: 14
  - Concurrent Sweep 次数: 14
  - Concurrent Reset 次数: 14
  
堆内存情况:
  - 年轻代堆内存总量: 157248K，已使用: 8984K，5.71%
  - eden 区总量: 139776K，已使用: 6%
  - from 区总量: 17472K，已使用: 0%
  - 老年代堆内存总量: 349568K，已使用: 349500K，99.98%

结论: 
  - 堆内存调整为512m之后，虽然不发生 OOM 但堆内存依然过小，CMS 依然多次退化，总暂停时间依然很长。

#### 3). JVM 配置  -Xms1g -Xmx1g
GC 执行情况:
  - 创建了26957次对象
  - 总暂停时间: 360ms
  - 平均暂停时间: 10.0ms
  - 最大暂停时间: 30.0ms
  - 总并发处理时间: 390.0ms
  - 平均并发处理时间: 13.9ms
  - 最大并发处理时间: 180.0ms
  - Minor GC 次数: 24
  - Minor GC 总时间: 330ms
  - Minor GC 平均时间: 13.7ms
  - Full GC 次数: 1
  - Full GC 总时间: 30.0ms
  - Full GC 平均时间: 30.0ms
  - Initial Mark 次数: 6
  - Concurrent Mark 次数: 6
  - Concurrent Preclean 次数: 6
  - Concurrent Abortable Preclean 次数: 6
  - Final Remark 次数: 5
  - Concurrent Sweep 次数: 5
  - Concurrent Reset 次数: 5
  
堆内存情况:
  - 年轻代堆内存总量: 314560K，已使用: 224716K，71.44%
  - eden 区总量: 279616K，已使用: 67%
  - from 区总量: 34944K，已使用: 99%
  - 老年代堆内存总量: 699072K，已使用: 636053K，90.99%

结论: 
  - 堆内存调整为1g之后，总暂停时间缩短，Full GC 只发生了一次。吞吐量也提高。

#### 4). JVM 配置  -Xms2g -Xmx2g
GC 执行情况:
  - 创建了24930次对象
  - 总暂停时间: 400ms
  - 平均暂停时间: 30.8ms
  - 最大暂停时间: 40.0ms
  - 总并发处理时间: 410.0ms
  - 平均并发处理时间: 82.0ms
  - Minor GC 次数: 11
  - Minor GC 总时间: 400ms
  - Minor GC 平均时间: 36.4ms
  - Full GC 次数: 0
  - Initial Mark 次数: 1
  - Concurrent Mark 次数: 1
  - Concurrent Preclean 次数: 1
  - Concurrent Abortable Preclean 次数: 1
  - Final Remark 次数: 1
  - Concurrent Sweep 次数: 1
  - Concurrent Reset 次数: 1
  
堆内存情况:
  - 年轻代堆内存总量: 629120K，已使用: 543138K，86.33%
  - eden 区总量: 559232K，已使用: 84%
  - from 区总量: 69888K，已使用: 99%
  - 老年代堆内存总量: 1398144K，已使用: 899354K，64.32%

结论: 
  - 堆内存调整为2g之后，没有 Full GC，吞吐量稳定。

#### 5). JVM 配置  -Xms4g -Xmx4g
GC 执行情况:
  - 创建了26607次对象
  - 总暂停时间: 350ms
  - 平均暂停时间: 43.8ms
  - 最大暂停时间: 50.0ms
  - Minor GC 次数: 8
  - Minor GC 总时间: 350ms
  - Minor GC 平均时间: 43.8ms
  - Full GC 次数: 0
  - CMS GC 次数: 0
  
堆内存情况:
  - 年轻代堆内存总量: 996800K，已使用: 137025K，13.75%
  - eden 区总量: 886080K，已使用: 2%
  - from 区总量: 110720K，已使用: 99%
  - 老年代堆内存总量: 3086784K，已使用: 1126908K，36.51%

结论: 
  - 堆内存调整为4g之后，没有 Full GC，也没有 Major GC，吞吐量稳定。

#### 6). JVM 配置  -Xms8g -Xmx8g
GC 执行情况:
  - 创建了26060次对象
  - 总暂停时间: 300ms
  - 平均暂停时间: 42.9ms
  - 最大暂停时间: 50.0ms
  - Minor GC 次数: 7
  - Minor GC 总时间: 300ms
  - Minor GC 平均时间: 42.9ms
  - Full GC 次数: 0
  - CMS GC 次数: 0
  
堆内存情况:
  - 年轻代堆内存总量: 996800K，已使用: 898706K，90.16%
  - eden 区总量: 886080K，已使用: 88%
  - from 区总量: 110720K，已使用: 99%
  - 老年代堆内存总量: 7281088K，已使用: 977130K，13.42%

结论: 
  - 堆内存调整为8g之后，没有 Full GC，也没有 Major GC，吞吐量稳定。

#### 7). JVM 配置  -Xms16g -Xmx16g
GC 执行情况:
  - 创建了23561次对象
  - 总暂停时间: 360ms
  - 平均暂停时间: 51.4ms
  - 最大暂停时间: 60.0ms
  - Minor GC 次数: 7
  - Minor GC 总时间: 360ms
  - Minor GC 平均时间: 13.6ms
  - Full GC 次数: 0
  - CMS GC 次数: 0
  
堆内存情况:
  - 年轻代堆内存总量: 996800K，已使用: 271038K，27.19%
  - eden 区总量: 886080K，已使用: 18%
  - from 区总量: 110720K，已使用: 100%
  - 老年代堆内存总量: 15669696K，已使用: 995653K，6.35%

结论: 
  - 堆内存调整为16g之后，没有 Full GC，也没有 Major GC，吞吐量略微有所下降。

## 5. G1
#### 1). JVM 配置  -Xms256m -Xmx256m
GC 执行情况:
  - 发生 OOM
  - 总暂停时间: 100ms
  - 平均暂停时间: 0.746ms
  - 最大暂停时间: 10.0ms
  - 总并发处理时间: 11.4ms
  - 平均并发处理时间: 0.758ms
  - Minor GC 次数: 48
  - Minor GC 总时间: 10.0ms
  - Minor GC 平均时间: 0.208ms
  - Full GC 次数: 18
  - Full GC 总时间: 90.0ms
  - Full GC 平均时间: 5.0ms
  
堆内存情况:
  - 整个堆内存总容量: 262144K，已使用: 661K
  - 每个 region 大小: 1024K，1个年轻代 region，0个 survivor 的 region

结论: 
  - OOM

#### 2). JVM 配置  -Xms512m -Xmx512m
GC 执行情况:
  - 创建了15685次对象
  - 总暂停时间: 300ms
  - 平均暂停时间: 1.09ms
  - 最大暂停时间: 30.0ms
  - 总并发处理时间: 42.1ms
  - 平均并发处理时间: 0.859ms
  - Minor GC 次数: 60
  - Minor GC 总时间: 60.0ms
  - Minor GC 平均时间: 1.0ms
  - Full GC 次数: 5
  - Full GC 总时间: 130.0ms
  - Full GC 平均时间: 26.0ms
  
堆内存情况:
  - 整个堆内存总容量: 524288K，已使用: 368745K
  - 每个 region 大小: 1024K，12个年轻代 region，2个 survivor 的 region

结论: 
  - 堆内存依然过小

#### 3). JVM 配置  -Xms1g -Xmx1g
GC 执行情况:
  - 创建了26175次对象
  - 总暂停时间: 360ms
  - 平均暂停时间: 3.71ms
  - 最大暂停时间: 10.0ms
  - 总并发处理时间: 15.1ms
  - 平均并发处理时间: 1.08ms
  - Minor GC 次数: 23
  - Minor GC 总时间: 180.0ms
  - Minor GC 平均时间: 7.83ms
  - Full GC 次数: 0
  
堆内存情况:
  - 整个堆内存总容量: 1048576K，已使用: 502835K
  - 每个 region 大小: 1024K，8个年轻代 region，7个 survivor 的 region

结论: 
  - 堆内存调整为1g之后，GC 次数减少，吞吐量提升。

#### 4). JVM 配置  -Xms2g -Xmx2g
GC 执行情况:
  - 创建了29985次对象
  - 总暂停时间: 240ms
  - 平均暂停时间: 7.74ms
  - 最大暂停时间: 20.0ms
  - 总并发处理时间: 4.64ms
  - 平均并发处理时间: 1.16ms
  - Minor GC 次数: 15
  - Minor GC 总时间: 140.0ms
  - Minor GC 平均时间: 9.33ms
  - Full GC 次数: 0
  
堆内存情况:
  - 整个堆内存总容量: 2097152K，已使用: 836235K
  - 每个 region 大小: 1024K，86个年轻代 region，30个 survivor 的 region

结论: 
  - 堆内存调整为2g之后，GC 次数减少，吞吐量提升。

#### 5). JVM 配置  -Xms4g -Xmx4g
GC 执行情况:
  - 创建了29794次对象
  - 总暂停时间: 200ms
  - 平均暂停时间: 12.5ms
  - 最大暂停时间: 20.0ms
  - Minor GC 次数: 16
  - Minor GC 总时间: 200.0ms
  - Minor GC 平均时间: 12.5ms
  - Full GC 次数: 0
  
堆内存情况:
  - 整个堆内存总容量: 4194304K，已使用: 3262280K
  - 每个 region 大小: 2048K，911个年轻代 region，90个 survivor 的 region

结论: 
  - 堆内存调整为4g之后，吞吐量稳定。

#### 6). JVM 配置  -Xms8g -Xmx8g
GC 执行情况:
  - 创建了30331次对象
  - 总暂停时间: 200ms
  - 平均暂停时间: 13.3ms
  - 最大暂停时间: 20.0ms
  - Minor GC 次数: 15
  - Minor GC 总时间: 200.0ms
  - Minor GC 平均时间: 13.3ms
  - Full GC 次数: 0
  
堆内存情况:
  - 整个堆内存总容量: 8388608K，已使用: 2386718K
  - 每个 region 大小: 4096K，197个年轻代 region，38个 survivor 的 region

结论: 
  - 堆内存调整为8g之后，吞吐量稳定。

#### 7). JVM 配置  -Xms16g -Xmx16g
GC 执行情况:
  - 创建了31771次对象
  - 总暂停时间: 220ms
  - 平均暂停时间: 20.0ms
  - 最大暂停时间: 20.0ms
  - Minor GC 次数: 11
  - Minor GC 总时间: 220.0ms
  - Minor GC 平均时间: 20.0ms
  - Full GC 次数: 0
  
堆内存情况:
  - 整个堆内存总容量: 16777216K，已使用: 1949475K
  - 每个 region 大小: 8192K，52个年轻代 region，13个 survivor 的 region

结论: 
  - 堆内存调整为16g之后，吞吐量稳定。







