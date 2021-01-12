# 第一周第一课
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
> ![alt 图片](./img/栈桢(stack%20frame).png "栈桢")
>
> 操作码（指令）由类型前缀**类型前缀**和**操作名称**两部分组成，例如: iadd 操作码，i 代表 Integer 类型，add 代表加法操作，
> 所以 iadd 就是整数类型数据的加法操作。
>
## 1.2 Java 字节码文件结构简述
> ### 字节码文件结构
> ![alt 图片](./img/Java%20字节码结构.png "Java 字节码结构")
> 
> ![alt 图片](./img/Class文件结构组织示意图.jpg "Class文件结构组织示意图")

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
>> ![alt 图片](./img/invokespecial和dup.jpg "invokespecial和dup")
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
> ### (6) 数组创建与元素访问
```
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=18, args_size=1

         .... 其他省略

       145: iconst_5
       146: newarray       int
       148: dup
       149: iconst_0
       150: iconst_1
       151: iastore
       152: dup
       153: iconst_1
       154: bipush        7
       156: iastore
       157: dup
       158: iconst_2
       159: sipush        220
       162: iastore
       163: dup
       164: iconst_3
       165: bipush        50
       167: iastore
       168: dup
       169: iconst_4
       170: sipush        1000
       173: iastore
       174: astore        14
       176: aload         14
       178: iconst_0
       179: iaload
       180: istore        15

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          244      26    16     i   I
          249      21    17   len   I
            0     335     0  args   [Ljava/lang/String;
            3     332     1     x   I
            6     329     2     y   I
           13     322     3   sum   I
           18     317     4 division   I
           23     312     5 multiplication   I
           28     307     6   sub   I
           75     260     7     b   B
           80     255     8     l   J
           84     251    10     f   F
           89     246    11     d   D
           92     243    13  bool   Z
          176     159    14  iArr   [I
          182     153    15 elementValue   I
          321      14    16 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 145行: 将 int 类型5压入栈顶  
> 146行: 先从栈弹出获取栈顶的值，获取到5，然后创建5个 int 类型元素的数组对象，并把该数组对象的引用值压入栈顶  
> 148行: 复制栈顶的 数组对象引用值 并将复制值压入栈顶  
> 149行: 将 int 类型0压入栈顶，该值为数组下标  
> 150行: 将 int 类型1压入栈顶，该值为要存入数组的元素值  
> 151行: 从操作数栈弹出三个值，分别是要存入的元素值、数组下标、数组的对象引用，并将元素值存入数组对应的下标中  
> 152行: 因为 iastore 指令会弹出数组的引用，所以要复制一下数组的引用并压入栈顶，供后续数组操作使用   
> 153行 - 173行: 与上述操作逻辑相同，将元素值存入数组中，此处省略详细分析   
> 174行: 将栈顶的 数组对象引用 保存到槽位为14的局部变量中，也就是将数组引用赋值给局部变量 iArr    
> 176行: 将槽位为14的局部变量引用值压入栈顶，也就是将 iArr 数组引用压入栈顶   
> 178行: 将 int 类型0压入栈顶，此值为数组的下标   
> 179行: 从栈顶弹出获取2个值，数组下标值和数组对象引用，在从数组中获取到该下标的元素值并压入栈顶   
> 180行: 将栈顶 int 类型数组元素值保存到槽位为15的局部变量中，也就是将数组下标为0的元素值赋值给局部变量 elementValue
>
> ### (7) 流程控制指令
> #### if else 语句: 
```
Constant pool:
    #2 = Fieldref           #94.#95       // java/lang/System.out:Ljava/io/PrintStream;
    #3 = Class              #96           // java/lang/StringBuilder
    #4 = Methodref          #3.#93        // java/lang/StringBuilder."<init>":()V
    #6 = Methodref          #3.#98        // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
    #7 = Methodref          #3.#99        // java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   #11 = Methodref          #3.#103       // java/lang/StringBuilder.toString:()Ljava/lang/String;
   #12 = Methodref          #104.#105     // java/io/PrintStream.println:(Ljava/lang/String;)V
   #33 = String             #125          // x = 10
   #34 = String             #126          // x = 20
   #35 = String             #127          // x =
   #43 = Utf8               <init>
   #44 = Utf8               ()V
   #93 = NameAndType        #43:#44       // "<init>":()V
   #94 = Class              #132          // java/lang/System
   #95 = NameAndType        #133:#134     // out:Ljava/io/PrintStream;
   #96 = Utf8               java/lang/StringBuilder
   #98 = NameAndType        #135:#136     // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   #99 = NameAndType        #135:#137     // append:(I)Ljava/lang/StringBuilder;
  #103 = NameAndType        #139:#140     // toString:()Ljava/lang/String;
  #104 = Class              #141          // java/io/PrintStream
  #105 = NameAndType        #142:#143     // println:(Ljava/lang/String;)V
  #125 = Utf8               x = 10
  #126 = Utf8               x = 20
  #127 = Utf8               x =
  #132 = Utf8               java/lang/System
  #133 = Utf8               out
  #134 = Utf8               Ljava/io/PrintStream;
  #135 = Utf8               append
  #136 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
  #137 = Utf8               (I)Ljava/lang/StringBuilder;
  #138 = Utf8               (D)Ljava/lang/StringBuilder;
  #139 = Utf8               toString
  #140 = Utf8               ()Ljava/lang/String;
  #141 = Utf8               java/io/PrintStream
  #142 = Utf8               println
  #143 = Utf8               (Ljava/lang/String;)V

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=18, args_size=1

         .... 其他省略

       182: iload_1
       183: bipush        10
       185: if_icmpne     199
       188: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       191: ldc           #33                 // String x = 10
       193: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       196: goto          275
       199: iload_1
       200: bipush        20
       202: if_icmple     216
       205: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       208: ldc           #34                 // String x > 20
       210: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       213: goto          275
       216: iload_1
       217: bipush        50
       219: if_icmpge     233
       222: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       225: ldc           #35                 // String x < 50
       227: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       230: goto          275
       233: iload_1
       234: bipush        60
       236: if_icmplt     250
       239: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       242: ldc           #36                 // String x >= 60
       244: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       247: goto          275
       250: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       253: new           #3                  // class java/lang/StringBuilder
       256: dup
       257: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
       260: ldc           #37                 // String x =
       262: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
       265: iload_1
       266: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
       269: invokevirtual #11                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
       272: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          278      26    16     i   I
          283      21    17   len   I
            0     371     0  args   [Ljava/lang/String;
            3     368     1     x   I
            6     365     2     y   I
           13     358     3   sum   I
           18     353     4 division   I
           23     348     5 multiplication   I
           28     343     6   sub   I
           75     296     7     b   B
           80     291     8     l   J
           84     287    10     f   F
           89     282    11     d   D
           92     279    13  bool   Z
          176     195    14  iArr   [I
          182     189    15 elementValue   I
          357      14    16 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 182行: 将槽位为1的局部变量值压入栈顶  
> 183行: 将常量值10压入栈顶  
> 185行: 从栈顶弹出2个 int 类型值然后比较，如果结果不相等，就跳转到第199行，否则就继续往下执行  
> 188行: 获取 System 的静态字段 out 并将其值加入栈顶中  
> 191行: 把字符串常量 x = 10 压入栈顶  
> 193行: 从栈顶弹出2个值，第一个值是 x = 10 字符串常量，第二个值是 out 静态字段，然后调用 println 方法打印 x = 10  
> 196行: 已经执行完 if else 代码块，所以跳转到275行，跳出 if else 代码块  
> 199行 - 272行: 执行逻辑相同，只是 if 的判断指令有所不同。  
> 250行 - 272行: 是使用 StringBuilder 进行字符串常量 "x = " 与 局部变量x进行拼接，然后调用 System 的静态字段 out 的 println 方法打印拼接后的字符串 
>
> #### for 语句:
```
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=18, args_size=1

         .... 其他省略

       275: iconst_0
       276: istore        16
       278: aload         14
       280: arraylength
       281: istore        17
       283: iload         16
       285: iload         17
       287: if_icmpge     304
       290: iload_3
       291: aload         14
       293: iload         16
       295: iaload
       296: iadd
       297: istore_3
       298: iinc          16, 1
       301: goto          283

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          278      26    16     i   I
          283      21    17   len   I
            0     371     0  args   [Ljava/lang/String;
            3     368     1     x   I
            6     365     2     y   I
           13     358     3   sum   I
           18     353     4 division   I
           23     348     5 multiplication   I
           28     343     6   sub   I
           75     296     7     b   B
           80     291     8     l   J
           84     287    10     f   F
           89     282    11     d   D
           92     279    13  bool   Z
          176     195    14  iArr   [I
          182     189    15 elementValue   I
          357      14    16 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 275行: 将 int 类型0压入栈顶  
> 276行: 将栈顶值保存到槽位为16的局部变量中，此时槽位为16的局部变量是 i   
> 278行: 将槽位为14的引用变量值压入栈顶，就是 iArr 数组对象   
> 280行: 首先弹出栈顶的数组对象引用值，获取该数组的长度值并压入栈顶   
> 281行: 将栈顶值保存到槽位为17的局部变量中，就是把数组长度保存到局部变量 len  
> 283行: 将槽位为16的局部变量值压入栈顶，局部变量 i 的值  
> 285行: 将槽位为17的局部变量值压入栈顶，局部变量 len 的值 
> 287行: 先从栈中弹出两个操作数，局部变量 len 的值和局部变量 i 的值，比较 len 和 i，当 len >= i 时则跳转到304行，跳出循环   
> 290行 - 301行: 为 for 循环体相关字节码   
> 290行: 将槽位为3的局部变量值压入栈顶，局部变量 sum 的值   
> 291行: 将槽位为14的局部变量引用值压入栈顶，也就是 iArr 数组对象引用值   
> 293行: 将槽位为16的局部变量 int 类型值压入栈顶，也就是局部变量 i 的值   
> 295行: 先从栈顶弹出两个值，第一个是局部变量 i 的值，第二个是 iArr 数组对象引用值，将 i 的值作为数组下标，取出对应的元素值并压入栈顶   
> 296行: 先从栈顶弹出两个值，第一个是 iArr[i] 的值，第二个是局部变量 sum 的值，并将它们相加，然后把相加后的值压入栈顶  
> 297行: 将栈顶的 int 类型值保存到槽位为3的局部变量中，局部变量 sum 中   
> 298行: 将槽位为16的局部变量值自增1，也就是 i++  
> 301行: 跳转到283行，重新判断循环条件，满足就继续执行循环体，否则跳出循环
>
> #### switch 语句: 
```
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=18, args_size=1

         .... 其他省略

       304: iload_2
       305: lookupswitch  { // 2
                      10: 332
                      50: 340
                 default: 348
            }
       332: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       335: ldc           #38                 // String y = 10
       337: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       340: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       343: ldc           #39                 // String y = 50
       345: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       348: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       351: ldc           #40                 // String default
       353: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V

        .... 其他省略

       246: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          278      26    16     i   I
          283      21    17   len   I
            0     379     0  args   [Ljava/lang/String;
            3     376     1     x   I
            6     373     2     y   I
           13     366     3   sum   I
           18     361     4 division   I
           23     356     5 multiplication   I
           28     351     6   sub   I
           75     304     7     b   B
           80     299     8     l   J
           84     295    10     f   F
           89     290    11     d   D
           92     287    13  bool   Z
          176     203    14  iArr   [I
          182     197    15 elementValue   I
          365      14    16 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 304行: 将槽位为2的局部变量值压入栈顶，局部变量 y 的值压入栈顶  
> 305行: 从栈顶弹出获取到局部变量 y 的值，并进行值的匹配，y 的值为10时跳转到332行，y 的值为50时跳转到340行，如果都不匹配执行 default 分支跳转到348行   
> 332行 - 353行: 都是调用不同匹配值的分支，调用 println 打印不同的字符串
>
> ### (8) 方法调用指令和参数传递
```
Constant pool:
   #14 = String             #109          // Hello
   #41 = Class              #136          // lesson01/bytecode/HelloByteCode
   #42 = Methodref          #41.#96       // lesson01/bytecode/HelloByteCode."<init>":()V
   #43 = Double             90.0d
   #45 = Methodref          #41.#137      // lesson01/bytecode/HelloByteCode.myPublicMethod:(IDLjava/lang/String;)D
   #46 = Utf8               <init>
   #47 = Utf8               ()V
   #53 = Utf8               myPublicMethod
   #54 = Utf8               (IDLjava/lang/String;)D
   #96 = NameAndType        #46:#47       // "<init>":()V
  #109 = Utf8               Hello
  #136 = Utf8               lesson01/bytecode/HelloByteCode
  #137 = NameAndType        #53:#54       // myPublicMethod:(IDLjava/lang/String;)D

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=7, locals=18, args_size=1

         .... 其他省略

       356: new           #41                 // class lesson01/bytecode/HelloByteCode
       359: dup
       360: invokespecial #42                 // Method "<init>":()V
       363: astore        16
       365: aload         16
       367: bipush        80
       369: ldc2_w        #43                 // double 90.0d
       372: ldc           #14                 // String Hello
       374: invokevirtual #45                 // Method myPublicMethod:(IDLjava/lang/String;)D
       377: pop2
       378: return
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          278      26    16     i   I
          283      21    17   len   I
            0     379     0  args   [Ljava/lang/String;
            3     376     1     x   I
            6     373     2     y   I
           13     366     3   sum   I
           18     361     4 division   I
           23     356     5 multiplication   I
           28     351     6   sub   I
           75     304     7     b   B
           80     299     8     l   J
           84     295    10     f   F
           89     290    11     d   D
           92     287    13  bool   Z
          176     203    14  iArr   [I
          182     197    15 elementValue   I
          365      14    16 helloByteCode   Llesson01/bytecode/HelloByteCode;
```
> 356行: 创建 HelloByteCode 对象，并将对象引用值压入栈顶   
> 359行: 复制栈顶的对象引用值并压入栈顶  
> 360行: 调用 HelloByteCode 的无参构造函数  
> 363行: 将栈顶的 HelloByteCode 对象引用值保存到槽位为16的局部变量中，此时的局部变量已经是 helloByteCode ，之前同样槽位为16的局部变量 i 超过了作用域，复用了同一个槽位  
> 365行: 将槽位为16的 helloByteCode 局部变量引用值压入栈顶  
> 367行: 将常量值80压入栈顶  
> 369行: 将常量编号为43的浮点型常量值90.0压入栈顶  
> 372行: 将字符串常量值 Hello 压入栈顶  
> 374行: 弹出栈中所有数据调用 HelloByteCode 的 myPublicMethod 方法并将参数传入，执行完后将返回值压入栈顶  
> 377行: 从栈顶将返回值弹出  
> 378行: 方法返回
> 
> 上述的情况是 myPublicMethod 方法有返回值，但是没有局部变量去接收的情况。
>
> myPublicMethod 方法没有返回值的字节码情况: 
```
       374: invokevirtual #45                 // Method myPublicMethod:(IDLjava/lang/String;)V
       377: return
```
> 没有返回值会直接 return
>
> myPublicMethod 方法有返回值并且也有局部变量接收的字节码情况: 
```
       374: invokevirtual #45                 // Method myPublicMethod:(IDLjava/lang/String;)D
       377: dstore        17
       379: return
```
> 有返回值且有局部变量接收，会使用指令将在栈顶的返回值保存到对应的局部变量
>
## 1.4 字节码相关 JDK 命令行工具
> ### javac 编译工具  
> 常用参数:
>   * -g 在生成的class文件中包含所有调试信息（包括局部变量），缺省情况下只生成行号和源文件信息。
>   * -g:none 不生成任何调试信息
>   * -g:{关键字列表} 只生成某些类型的调试信息，这些类型由逗号分隔的关键字列表所指定
>   * -encoding 指定编码
>   * -verbose 冗长输出。它包括了每个所加载的类和每个所编译的源文件的有关信息。
>
> ### javap 查看字节码工具 
> 常用参数: 
>   * -c 输出分解后的代码，例如，类中每一个方法内，包含 java 字节码的指令
>   * -verbose 输出栈大小，方法参数的个数、局部变量表等

# 2、类加载器
> ## 2.1 类的生命周期和加载过程

# 3、<span id="JVMMemoryStructureAndJMM">JVM 内存结构和 Java 内存模型</span>
> ## JVM 内存结构
> ![alt 图片](./img/JVM%20内存结构&堆内存&栈内存.png "JVM 内存结构&堆内存&栈内存")
> 
> ## Java 内存模型(JMM)
> ![alt 图片](./img/Java%20内存模型.png "Java 内存模型")
>
> JMM 规范明确定义了不同的线程之间，通过哪些方式，在什么时候可以看见其他线程保存到共享变量中的值；以及在必要时，
> 如何对共享变量的访问进行同步。这样的好处是屏蔽各种硬件平台和操作系统之间的内存访问差异，实现了 Java 并发程序真正的跨平台。
>
# 4、常用 JVM 启动参数

# 5、jstat、jstack 和 jmap 使用示例
> 检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。