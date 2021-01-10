# 目录介绍
- lesson01: 第一周第一课，JVM 字节码、类加载器、JVM 启动参数
  - bytecode: JVM 字节码
  - classloader: 类加载器
  - util: 工具类
  - img: 图片
  
# 作业
  - 第一题: [请查看字节码文件分析](#byteCodeFileAnalysis)
  - 第二题: <https://github.com/WebbDong/JAVA-01/blob/main/Week_01/lesson01/classloader/HelloClassLoader.java>
  - 第三题: [请查看启动参数与 JVM 内存结构](#JVMMemoryStructureAndJMM)
  - 第四题: 
  
# 笔记目录
  - JVM 字节码
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
> 2. flags: 访问权限修饰符，ACC_PUBLIC 代表 public 方法，ACC_STATIC 代表静态方法。
> 3. Code: 源代码对应的 JVM 操作码和操作数区域。在进行字节码增强时重点操作的就是 Code 区这一部分。
>   - 操作码左边的数字代表当前操作码在字节码二进制文件中的字节位置
>   - stack: 执行该方法时需要的栈深度
>   - locals: 需要在局部变量表中保留多少个槽位
>   - args_size: 方法的参数个数
>     - 此示例有3个形参，但是 args_size 是 4，这是因为非静态方法有 this 引用，this 被分配在局部变量表的第0号槽位中
> 4. LineNumberTable: 行号表
>   - 将 Code 区的操作码和源代码中的行号对应，Debug 的时候可以通过行号表来看源代码执行一行时需要执行多少个 JVM 操作码
>   - 例如 line 12: 0 ，12代表源代码的行号，0代表 Code 区操作码的行号
> 5. LocalVariableTable: 局部变量表
>   - 其中包含了方法的参数，以及在方法体内定义的局部变量。
>   - 元素个数等于 args_size
>   - Start 和 Length: 代表当前局部变量或方法参数在 Code 操作码区中的作用域范围
>     - 例如当前示例 start 为0，length 为49代表该变量的作用域从0一直到48，也就是整个方法体。
>   - Slot: 槽位，从0开始，如果访问的是64位数据类型变量(例如: long类型变量，double类型变量)会占用2个槽位。
>   - Name: 变量名
>   - Signature: 变量类型描述
>
> ### (3) 基本数据类型变量定义
```
Constant pool:
   #22 = Long               6000000l
   #24 = Float              3.14f
   #25 = Double             4125.5647d

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=15, args_size=1
         0: bipush        10
         2: istore_1
         3: bipush        50
         5: istore_2

         .... 其他省略

        72: iconst_5
        73: istore        7
        75: ldc2_w        #22                 // long 6000000l
        78: lstore        8
        80: ldc           #24                 // float 3.14f
        82: fstore        10
        84: ldc2_w        #25                 // double 4125.5647d
        87: dstore        11
        89: iconst_1
        90: istore        13

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          162      18    14     i   I
            0     247     0  args   [Ljava/lang/String;
            3     244     1     x   I
            6     241     2     y   I
           13     234     3   sum   I
           18     229     4 division   I
           23     224     5 multiplication   I
           28     219     6   sub   I
           75     172     7     b   B
           80     167     8     l   J
           84     163    10     f   F
           89     158    11     d   D
           92     155    13  bool   Z
          233      14    14 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 0行: 将常量10压入操作数栈的栈顶中  
> 2行: 将栈顶 int 类型的值保存到槽位为1的局部变量中，也就是把常量10赋值给变量 x  
> 3行: 将常量50压入操作数栈的栈顶中  
> 5行: 将栈顶 int 类型的值保存到槽位为2的局部变量中，也就是把常量10赋值给变量 y 
> 72行: 将常量5压入操作数栈的栈顶中  
> 73行: 将栈顶 int 类型的值保存到槽位为7的局部变量中，也就是把常量5赋值给变量 b   
> 75行: 将常量编号为22的 long 类型的常量值压入操作数栈的栈顶中  
> 78行: 将栈顶 long 类型的值保存到槽位为8的局部变量中，也就是把常量6000000赋值给变量 l  
> 80行: 将常量编号为24的 float 类型常量值压入操作数栈的栈顶中  
> 82行: 将栈顶 float 类型的值保存到槽位为10的局部变量中  
> 84行: 将常量编号为25的 double 类型常量值压入操作数栈的栈顶中  
> 87行: 将栈顶 double 类型的值保存到槽位为11的局部变量中  
>
> ### (4) 创建对象与对象初始化
```
Constant pool:
   #36 = Class              #121          // lesson01/bytecode/HelloByteCode
   #37 = Methodref          #36.#86       // lesson01/bytecode/HelloByteCode."<init>":()V
   #41 = Utf8               <init>
   #42 = Utf8               ()V
   #86 = NameAndType        #41:#42       // "<init>":()V
   #121 = Utf8               lesson01/bytecode/HelloByteCode

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=15, args_size=1

         .... 其他省略

       224: new           #36                 // class lesson01/bytecode/HelloByteCode
       227: dup
       228: invokespecial #37                 // Method "<init>":()V
       231: astore        14

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          162      18    14     i   I
            0     247     0  args   [Ljava/lang/String;
            3     244     1     x   I
            6     241     2     y   I
           13     234     3   sum   I
           18     229     4 division   I
           23     224     5 multiplication   I
           28     219     6   sub   I
           75     172     7     b   B
           80     167     8     l   J
           84     163    10     f   F
           89     158    11     d   D
           92     155    13  bool   Z
          233      14    14 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 224行: 创建一个 HelloByteCode 对象, 并将其引用的引用值压入操作数栈的栈顶中，此时没有调用构造函数，所以对象没有初始化。  
> 227行: 复制栈顶的 对象引用值 并将复制值压入操作数栈的栈顶中  
> 228行: 调用 HelloByteCode 的无参构造函数
> 231行: 将栈顶的引用类型数值保存到槽位为14的局部变量中   
>
> 为什么创建对象时需要 dup 指令？
>> 使用 invokespecial 命令会从操作数堆栈中弹出 nargs 参数值和 objectref ，正是因为需要调用这个函数才导致中间必须要有一个 dup 指令，不然调用完 <init> 方法以后，操作数栈为空，就再也找不回刚刚创建的对象了。  
>>
>> 下图所示:  
>> ![alt 图片](./lesson01/img/invokespecial和dup.jpg "invokespecial和dup")
>
> ### (5) 算术运算
```
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=15, args_size=1

         .... 其他省略

         6: iload_1
         7: iload_2
         8: iadd
         9: bipush        60
        11: iadd
        12: istore_3
        13: iload_2
        14: iload_1
        15: idiv
        16: istore        4
        18: iload_1
        19: iload_2
        20: imul
        21: istore        5
        23: iload_2

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          162      18    14     i   I
            0     247     0  args   [Ljava/lang/String;
            3     244     1     x   I
            6     241     2     y   I
           13     234     3   sum   I
           18     229     4 division   I
           23     224     5 multiplication   I
           28     219     6   sub   I
           75     172     7     b   B
           80     167     8     l   J
           84     163    10     f   F
           89     158    11     d   D
           92     155    13  bool   Z
          233      14    14 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 6行: 将局部变量表中槽位为1的变量值压入操作数栈的栈顶中  
> 7行: 将局部变量表中槽位为2的变量值压入操作数栈的栈顶中   
> 8行: 将栈顶两个 int 型数值相加并将结果压入栈顶  
> 9行: 将常量60压入栈顶  
> 11行: 将栈顶两个 int 型数值相加并将结果压入栈顶，此时栈顶的值就是 x + y + 60  
> 12行: 将栈顶的值保存到槽位为3的局部变量中  
> 其他算术操作类似，以此类推。
>
> ### (6) 流程控制指令
```
Constant pool:
   #2 = Fieldref           #93.#94       // java/lang/System.out:Ljava/io/PrintStream;
   #33 = String             #124          // x = 10

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=17, args_size=1

         .... 其他省略

       176: iload_1
       177: bipush        10
       179: if_icmpne     193
       182: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       185: ldc           #33                 // String x = 10
       187: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       190: goto          235
       193: iload_1
       194: bipush        20
       196: if_icmpne     210
       199: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       202: ldc           #34                 // String x = 20
       204: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       207: goto          235
       210: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          238      26    15     i   I
          243      21    16   len   I
            0     331     0  args   [Ljava/lang/String;
            3     328     1     x   I
            6     325     2     y   I
           13     318     3   sum   I
           18     313     4 division   I
           23     308     5 multiplication   I
           28     303     6   sub   I
           75     256     7     b   B
           80     251     8     l   J
           84     247    10     f   F
           89     242    11     d   D
           92     239    13  bool   Z
          176     155    14  iArr   [I
          317      14    15 helloByteCode   Llesson01/bytecode/HelloByteCode;
```

> ### (7) 方法调用指令和参数传递
```
```

> ### (8) 数组创建
```
```

## 1.4 字节码相关 JDK 命令行工具

# 2、类加载器

# 3、<span id="JVMMemoryStructureAndJMM">JVM 内存结构和 Java 内存模型</span>
> ![alt 图片](./lesson01/img/JVM%20内存结构&堆内存&栈内存.png "JVM 内存结构&堆内存&栈内存")

# 4、JVM 启动参数
