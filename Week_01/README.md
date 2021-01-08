# 目录介绍
- lesson01: 本周第一课
  - bytecode: JVM字节码
  - classloader: 类加载器
  - util: 工具类
  - img: 图片
  
# 作业
  - 第一题: 字节码文件分析 [点我](#byteCodeFileAnalysis)
  - 第二题: <https://github.com/WebbDong/JAVA-01/blob/main/Week_01/lesson01/classloader/HelloClassLoader.java>
  - 第三题: 
  - 第四题: 
-------------
# 1、Java 字节码
## 1.1 Java 字节码简介
> Java 字节码由单字节（byte）的指令组成，理论上最多支持 256 个操作码（opcode）。实际上 Java 只使用了200左右的操作码， 还有一些操作码则保留给调试操作。
>
> 根据指令的性质，主要分为四个大类:
>> 1.栈操作指令，包括与局部变量交互的指令  
>> 2.程序流程控制指令  
>> 3.对象操作指令，包括方法调用指令  
>> 4.算术运算以及类型转换指令
>
> 字节码的运行时结构:
>> JVM 基于栈的计算模型每个线程都有一个独属于自己的线程栈（JVM Stack），用于存储栈帧（Stack Frame）。  
>> 每一次方法调用，JVM 都会自动创建一个栈帧。每当为 Java 方法分配栈桢时，JVM 需要开辟一块额外的空间作为操作数栈，来存放计算的操作数以及返回结果。  
>
> 栈桢的组成部分:
>> 1.局部变量表 (Local Variables)  
>> 2.操作数栈 (Operand Stack)  
>> 3.动态链接 (Dynamic Linking)  
>> 4.方法返回地址 (Return Address)
>
> 下图所示:
> ![alt 图片](./lesson01/img/栈桢(stack%20frame).png "栈桢")
>
> 操作码（指令）由类型前缀**类型前缀**和**操作名称**两部分组成，例如: iadd 操作码，i 代表 Integer 类型，add 代表加法操作，
> 所以 iadd 就是整数类型数据的加法操作。
>
## 1.2 Java 字节码文件结构简述
> ### 字节码文件结构
> ![alt 图片](./lesson01/img/Java%20字节码结构.png "Java 字节码结构")
> 
> ![alt 图片](./lesson01/img/Class文件结构组织示意图.jpg "Class文件结构组织示意图")

## 1.3 <span id="byteCodeFileAnalysis">字节码文件分析</span>
```
  Last modified Jan 8, 2021; size 1734 bytes
  MD5 checksum ad7790070948dad90c5120665c98ebaf
  Compiled from "HelloByteCode.java"
public class lesson01.bytecode.HelloByteCode
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
    #1 = Methodref          #4.#56        // java/lang/Object."<init>":()V
    #2 = Fieldref           #57.#58       // java/lang/System.out:Ljava/io/PrintStream;
    #3 = String             #59           // sum = %d, division = %d, multiplication = %d, sub = %d%n
    #4 = Class              #60           // java/lang/Object
    #5 = Methodref          #61.#62       // java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
```
> * 字节码格式版本号: 52.0 = (JDK8)
> * flags: ACC_PUBLIC 表示 public 访问修饰
> * Constant pool: 常量池