# 目录介绍
- lesson01: 本周第一课
  - bytecode: JVM字节码
  - classloader: 类加载器
  - util: 工具类
  - img: 图片
  
# 作业
  - 第一题: [请查看字节码文件分析](#byteCodeFileAnalysis)
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
> ### (1) 类信息与常量池信息
```
Classfile /D:/lesson01/bytecode/HelloByteCode.class
  Last modified Jan 9, 2021; size 1888 bytes
  MD5 checksum d0225425f9a99537afc8664fa6125129
  Compiled from "HelloByteCode.java"
public class lesson01.bytecode.HelloByteCode
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
    #1 = Methodref          #7.#60        // java/lang/Object."<init>":()V
    #2 = Fieldref           #61.#62       // java/lang/System.out:Ljava/io/PrintStream;
    #3 = String             #32           // myMethod
    #4 = Methodref          #63.#64       // java/io/PrintStream.println:(Ljava/lang/String;)V
    #5 = String             #33           // myStaticMethod
    #6 = String             #65           // sum = %d, division = %d, multiplication = %d, sub = %d%n
    #7 = Class              #66           // java/lang/Object
    #8 = Methodref          #67.#68       // java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
    #9 = Methodref          #63.#69       // java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
   #10 = Long               6000000l
   #12 = Float              3.14f
```
> 1. minor version 与 major version: 字节码格式版本号，52.0 = JDK8
> 2. flags: 访问权限修饰与属性
>   - ACC_PUBLIC 表示当前类的访问修饰是 public
>   - ACC_SUPER 是历史原因, JDK1.0 的BUG修正中引入 ACC_SUPER 标志来修正 invokespecial 指令调用 super 类方法的问题，从 Java 1.1 开始， 编译器一般都会自动生成 ACC_SUPER 标志。
> 3. Constant pool: 常量池
>   - \#1、\#2: 这些表示常量池成员的编号，常量池成员可以存储字符串、整数、浮点数、符号引用、常量编号(索引值)
>   - Methodref、Fieldref、String等: 用于说明此常量位存储的是什么类型的数据，例如 Methodref 代表这个常量指向的是一个方法
>
> ### (2) 方法信息
```
  public double myPublicMethod(int, double, java.lang.String);
    descriptor: (IDLjava/lang/String;)D
    flags: ACC_PUBLIC
    Code:
      stack=4, locals=5, args_size=4
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: new           #3                  // class java/lang/StringBuilder
         6: dup
         7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        10: ldc           #5                  // String myPublicMethod, x =
        12: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        15: iload_1
        16: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        19: ldc           #8                  // String , d =
        21: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        24: dload_2
        25: invokevirtual #9                  // Method java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        28: ldc           #10                 // String , str =
        30: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        33: aload         4
        35: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        38: invokevirtual #11                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        41: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        44: iload_1
        45: i2d
        46: dload_2
        47: dadd
        48: dreturn
      LineNumberTable:
        line 12: 0
        line 13: 44
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      49     0  this   Llesson01/bytecode/HelloByteCode;
            0      49     1     x   I
            0      49     2     d   D
            0      49     4   str   Ljava/lang/String;
```
> 1. descriptor: 方法描述
>   - (IDLjava/lang/String;)D
>     - 小括号内的为形参参数类型描述，小括号右边的是方法返回值类型描述。
>     - 下括号内 I 代表第一个参数为 int 类型，D 代表第二个参数为 double 类型。L 代表第三个参数是对象类型，java/lang/String 代表是 String 类型对象。
>     - 小括号右边的 D 代表方法的返回值为 double 类型。
>   - flags: 访问权限修饰符，ACC_PUBLIC 代表 public 方法，ACC_STATIC 代表静态方法。
>   - Code: 为操作码和操作数区域
>     - 操作码左边的数字代表当前操作码在字节码二进制文件中的字节位置
>     - stack: 执行该方法时需要的栈深度
>     - locals: 需要在局部变量表中保留多少个槽位
>     - args_size: 方法的参数个数
>       - 此示例有3个形参，但是 args_size 是 4，这是因为非静态方法有 this 引用，this 被分配在局部变量表的第0号槽位中
>   - LineNumberTable: 行号表
>     - 将 Code 区的操作码和源代码中的行号对应，Debug 的时候可以通过行号表来看源代码执行一行时需要执行多少个 JVM 操作码
>     - 例如 line 12: 0 ，12 代表源代码的行号，0 代表 Code 区操作码的行号
>   - LocalVariableTable: 局部变量表
>     - 其中包含了方法的参数，以及在方法体内定义的局部变量。
>     - 元素个数等于 args_size
>     - Start 和 Length: 代表当前局部变量或方法参数在 Code 操作码区中的作用域范围
>       - 例如当前示例 start 为 0，length 为 49 代表该变量的作用域从 0 一直到 48 也就是整个方法体。
>     - Slot: 槽位，从0开始
>     - Name: 变量名
>     - Signature: 变量类型描述
>
> ### (3) 方法体字节码解析













