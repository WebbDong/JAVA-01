# 第一周第二课
# 作业
  - 第一题: 
  
# 笔记目录

# 1、JDK 自带命令行工具
> ![alt 图片](./img/常用%20JDK%20自带命令行工具.png "常用 JDK 自带命令行工具")
>
>> ## 1.1 jps
>> 用于查看 JVM 进程相关信息  
>>
>> jps 常用参数: 
>>   - -q: 只显示 JVM 进程
>>   - -m: 输出传递给main方法的参数
>>   - -l: 输出应用程序 main class 的完整 package 名或者应用程序的 jar 文件完整名称
>>   - -v: 输出传递给 JVM 的参数，可以看到 JVM 启动参数
>>   - 参数组合使用: -mlv，将 -m、-l、-v 组合使用
>>
>> ## 1.2 jinfo
>> 用于打印和动态修改虚拟机参数，也可以打印 JVM 参数和系统参数。
>> 
>> jinfo 语法: 
```
Usage:
    jinfo [option] <pid>
        (to connect to running process)
    jinfo [option] <executable <core>
        (to connect to a core file)
    jinfo [option] [server_id@]<remote server IP or hostname>
        (to connect to remote debug server)

where <option> is one of:
    -flag <name>         to print the value of the named VM flag
    -flag [+|-]<name>    to enable or disable the named VM flag
    -flag <name>=<value> to set the named VM flag to the given value
    -flags               to print VM flags
    -sysprops            to print Java system properties
    <no option>          to print both of the above
    -h | -help           to print this help message
```
>> jinfo 常用参数: 
>>   - -flag \<name>: 用于打印虚拟机标记参数的值，name表示虚拟机标记参数的名称，用来查看某个 JVM 启动参数当前的状态，是开启还是关闭。
>>   - -flag \[+|-]<name>: 用于开启或关闭 JVM 标记参数。+表示开启，-表示关闭。
>>   - -flag \<name>=\<value>: 用于设置 JVM 标记参数，但并不是每个参数都可以被动态修改的。
>>   - -flags: 打印 JVM 参数
>>   - -sysprops: 打印系统参数
>>   - 不指定参数标签: 默认打印 JVM 参数和系统参数
>>
>> ## 1.3 jstat
>> jstat 语法: jstat \[-命令选项] \[JVM 进程 ID] \[间隔时间/毫秒] \[查询次数]  
>>
>> jstat 常用参数: 
>>> #### 1. 类加载器信息统计
>>> 命令: jstat -class VMID
>>> 
>>> 结果: 
```
d:\>jstat -class 6820
Loaded  Bytes   Unloaded    Bytes      Time
  6592 11888.0         0     0.0       1.67
```
>>> 详解:
>>>   - Loaded: 加载 class 的数量
>>>   - Bytes: 加载类所占用的字节数
>>>   - Unloaded: 卸载类的数量
>>>   - Bytes: 卸载类的字节数
>>>   - Time: 加载和卸载类所花费的时间
>>>
>>> #### 2. 编译信息统计
>>> 命令: jstat -compiler VMID
>>>
>>> 结果: 
```
d:\>jstat -compiler 6820
Compiled Failed Invalid   Time   FailedType FailedMethod
    3970      4       0     6.79          1 org/springframework/core/annotation/AnnotationUtils findAnnotation
```
>>> 详解: 
>>>   - Compiled: 编译任务执行数量
>>>   - Failed: 编译任务执行失败数量
>>>   - Invalid: 编译任务执行失效数量
>>>   - Time: 编译任务消耗时间
>>>   - FailedType: 最后一个编译失败任务的类型
>>>   - FailedMethod: 最后一个编译失败任务所在的类及方法
>>>
>>> #### 3. 堆内存与垃圾回收统计
>>> 命令: jstat -gc VMID
>>>
>>> 结果:
```
d:\>jstat -gc 17844
 S0C    S1C     S0U     S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
21504.0 21504.0  0.0    0.0   131584.0 97391.5   349696.0     0.0     4480.0 776.7  384.0   76.6       0    0.000   0      0.000    0.000
```
>>> 详解:
>>>   - S0C: survivor0 的容量 (字节)
>>>   - S1C: survivor1 的容量 (字节)
>>>   - S0U: survivor0 目前已使用空间 (字节)
>>>   - S1U: survivor1 目前已使用空间 (字节)
>>>   - EC: 年轻代中Eden（伊甸园）的容量 (字节)
>>>   - EU: 年轻代中Eden（伊甸园）目前已使用空间 (字节)
>>>   - OC: 老年代的容量 (字节)
>>>   - OU: 老年代目前已使用空间 (字节)
>>>   - MC: Metaspace 的容量 (字节)
>>>   - MU: Metaspace 目前已使用空间 (字节)
>>>   - CCSC: 压缩类空间的容量 (字节)
>>>   - CCSU: 压缩类空间目前已使用空间 (字节)
>>>   - YGC: 从应用程序启动到采样时年轻代中gc次数
>>>   - YGCT: 从应用程序启动到采样时年轻代中gc所用时间(s)
>>>   - FGC: 从应用程序启动到采样时老年代(全gc、Full GC)gc次数
>>>   - FGCT: 从应用程序启动到采样时老年代(全gc、Full GC)gc所用时间(s)
>>>   - GCT: 从应用程序启动到采样时gc用的总时间(s)
>>>
>>> #### 4. 堆内存容量统计
>>> 命令: jstat -gccapacity VMID
>>>
>>> 结果: 
```
d:\>jstat -gccapacity 6820
 NGCMN    NGCMX     NGC     S0C      S1C       EC      OGCMN      OGCMX       OGC         OC        MCMN     MCMX      MC       CCSMN    CCSMX     CCSC    YGC    FGC
174592.0 2789376.0 517632.0 17408.0 21504.0 263168.0   349696.0  5578752.0   154624.0   154624.0      0.0 1079296.0  33276.0      0.0 1048576.0   4348.0      5     1
```
>>> 详解: 
>>>   - NGCMN: 年轻代(young)中初始化(最小)的大小 (字节)
>>>   - NGCMX: 年轻代(young)的最大容量 (字节)
>>>   - NGC: 年轻代(young)中当前的容量 (字节)
>>>   - S0C: survivor0 的容量 (字节)
>>>   - S1C: survivor1 的容量 (字节)
>>>   - EC: 年轻代中Eden（伊甸园）的容量 (字节)
>>>   - OGCMN: 老年代中初始化(最小)的大小 (字节)
>>>   - OGCMX: 老年代的最大容量 (字节)
>>>   - OGC: 老年代当前新生成的容量 (字节)
>>>   - OC: 老年代当前的容量 (字节)
>>>   - MCMN: Metaspace 最小容量 (字节)
>>>   - MCMX: Metaspace 最大容量 (字节)
>>>   - MC: 当前 Metaspace 空间大小 (字节)
>>>   - CCSMN: 最小压缩类空间的容量 (字节)
>>>   - CCSMX: 最大压缩类空间的容量 (字节)
>>>   - CCSC: 当前压缩类空间大小 (字节)
>>>   - YGC: 从应用程序启动到采样时年轻代中gc次数
>>>   - FGC: 从应用程序启动到采样时old代(全gc)gc次数
>>>
>>> #### 5. 年轻代（新生代）区域统计
>>> 命令: jstat -gcnew VMID
>>> 
>>> 结果: 
```
 S0C    S1C       S0U    S1U   TT MTT  DSS      EC       EU       YGC     YGCT  
37376.0 21504.0    0.0 21480.7  6  15 37376.0 217088.0 127944.0      5    0.067
```
>>> 详解:
>>>   - S0C: survivor0 的容量 (字节)
>>>   - S1C: survivor1 的容量 (字节)
>>>   - S0U: survivor0 目前已使用空间 (字节)
>>>   - S1U: survivor1 目前已使用空间 (字节)
>>>   - TT: 持有次数限制
>>>   - MTT: 最大持有次数限制
>>>   - DSS: 期望的 survivor 大小 (字节)
>>>   - EC: 年轻代中Eden（伊甸园）的容量 (字节)
>>>   - EU: 年轻代中Eden（伊甸园）目前已使用空间 (字节)
>>>   - YGC: 从应用程序启动到采样时年轻代中gc次数
>>>   - YGCT: 从应用程序启动到采样时年轻代中gc所用时间(s)
>>>
>>> #### 6. 年轻代（新生代）容量统计
>>> 命令: jstat -gcnewcapacity VMID
>>>
>>> 结果:
```
  NGCMN      NGCMX       NGC      S0CMX     S0C     S1CMX     S1C       ECMX        EC      YGC   FGC 
  174592.0  1397760.0   450560.0 465920.0  37376.0 465920.0  21504.0  1396736.0   217088.0     5     1
```
>>> 详解:
>>>   - NGCMN: 年轻代(young)中初始化(最小)的大小 (字节)
>>>   - NGCMX: 年轻代(young)的最大容量 (字节)
>>>   - NGC: 年轻代(young)中当前的容量 (字节)
>>>   - S0CMX: 年轻代中 survivor0 的最大容量 (字节)
>>>   - S0C: 年轻代中 survivor0 的容量 (字节)
>>>   - S1CMX: 年轻代中 survivor1 的最大容量 (字节)
>>>   - S1C: 年轻代中 survivor1 的最大容量 (字节)
>>>   - ECMX: 年轻代中Eden（伊甸园）的最大容量 (字节)
>>>   - EC: 年轻代中Eden（伊甸园）的容量 (字节)
>>>   - YGC: 从应用程序启动到采样时年轻代中gc次数
>>>   - FGC: 从应用程序启动到采样时old代(全gc)gc次数
>>>
>>> #### 7. 老年代区域统计
>>> 命令: jstat -gcold VMID
>>> 
>>> 结果:
```
   MC       MU      CCSC     CCSU       OC          OU       YGC    FGC    FGCT     GCT   
 25216.0  24141.1   3712.0   3437.9    349696.0     27429.0      5     1    0.025    0.092
```
>>> 详解:
>>>   - MC: Metaspace 的容量 (字节)
>>>   - MU: Metaspace 目前已使用空间 (字节)
>>>   - CCSC: 压缩类空间的容量 (字节)
>>>   - CCSU: 压缩类空间目前已使用空间 (字节)
>>>   - OC: 老年代的容量 (字节)
>>>   - OU: 老年代目前已使用空间 (字节)
>>>   - YGC: 从应用程序启动到采样时年轻代中gc次数
>>>   - FGC: 从应用程序启动到采样时old代(全gc)gc次数
>>>   - FGCT: 从应用程序启动到采样时old代(全gc)gc所用时间(s)
>>>   - GCT: 从应用程序启动到采样时gc用的总时间(s)
>>>
>>> #### 8. 老年代容量统计
>>> 命令: jstat -gcoldcapacity VMID
>>>
>>> 结果:
```
   OGCMN       OGCMX        OGC         OC       YGC   FGC    FGCT     GCT   
   349696.0   2796544.0    349696.0    349696.0     5     1    0.025    0.092
```
>>> 详解:
>>>   - OGCMN: 老年代中初始化(最小)的大小 (字节)
>>>   - OGCMX: 老年代的最大容量(字节)
>>>   - OGC: 老年代当前新生成的容量 (字节)
>>>   - OC: 老年代的容量 (字节)
>>>   - YGC: 从应用程序启动到采样时年轻代中gc次数
>>>   - FGC: 从应用程序启动到采样时old代(全gc)gc次数
>>>   - FGCT: 从应用程序启动到采样时old代(全gc)gc所用时间(s)
>>>   - GCT: 从应用程序启动到采样时gc用的总时间(s)
>>>
>>> #### 9. 统计 gc 信息
>>> 命令: jstat -gcutil VMID
>>>
>>> 结果:
```
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
  0.00  99.89  58.94   7.84  95.74  92.62      5    0.067     1    0.025    0.092
```
>>> 详解:
>>>   - S0: survivor0 已使用的占当前容量百分比
>>>   - S1: survivor1 已使用的占当前容量百分比
>>>   - E: 年轻代中Eden（伊甸园）已使用的占当前容量百分比
>>>   - O: 老年代已使用的占当前容量百分比
>>>   - M: Metaspace 已使用的占当前容量百分比
>>>   - CCS: 压缩空间使用比例
>>>   - YGC: 从应用程序启动到采样时年轻代中gc次数
>>>   - YGCT: 从应用程序启动到采样时年轻代中gc所用时间(s)
>>>   - FGC: 从应用程序启动到采样时old代(全gc)gc次数
>>>   - FGCT: 从应用程序启动到采样时old代(全gc)gc所用时间(s)
>>>   - GCT: 从应用程序启动到采样时gc用的总时间(s)
>>>
>> ## 1.4 jmap
>>
>> ## 1.5 jstack
>>
>> ## 1.6 jcmd
>>
>>
>
>
>
>
>
>
>
>
>