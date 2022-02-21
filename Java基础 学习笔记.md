# Java 学习笔记

## Java 简介

Java最早是由SUN公司（已被Oracle收购）的[詹姆斯·高斯林](https://en.wikipedia.org/wiki/James_Gosling)（高司令，人称Java之父）在上个世纪90年代初开发的一种编程语言，最初被命名为Oak，目标是针对小型家电设备的嵌入式应用，结果市场没啥反响。谁料到互联网的崛起，让Oak重新焕发了生机，于是SUN公司改造了Oak，在1995年以Java的名称正式发布，原因是Oak已经被人注册了，因此SUN注册了Java这个商标。随着互联网的高速发展，Java逐渐成为最重要的网络编程语言。 

### Java Edition

- Java SE: Standard Edition
- Java EE: Enterprise Edition
- Java ME: Micro Edition

因此我们推荐的Java学习路线图如下：

1. 首先要学习Java SE，掌握Java语言本身、Java核心开发技术以及Java标准库的使用；
2. 如果继续学习Java EE，那么Spring框架、数据库开发、分布式架构就是需要学习的；
3. 如果要学习大数据开发，那么Hadoop、Spark、Flink这些大数据平台就是需要学习的，他们都基于Java或Scala开发；
4. 如果想要学习移动开发，那么就深入Android平台，掌握Android App开发。

### JDK 版本



| 时间   | 版本      |
| ------ | --------- |
| 1995   | 1.0       |
| 1998   | 1.2       |
| 2000   | 1.3       |
| 2002   | 1.4       |
| 2004   | 1.5 / 5.0 |
| 2005   | 1.6 / 6.0 |
| 2011   | 1.7 / 7.0 |
| 2014   | 1.8 / 8.0 |
| 2017/9 | 1.9 / 9.0 |
| 2018/3 | 10        |
| 2018/9 | 11        |
| 2019/3 | 12        |
| 2019/9 | 13        |
| 2020/3 | 14        |
| 2020/9 | 15        |
| 2021/3 | 16        |
| 2021/9 | 17        |

### 名词解释

* JDK：Java Development Kit

* JRE：Java Runtime Environment



## JDK 安装

### 环境变量配置

#### Windows配置

```tex
C:\Program Files\Java\jdk-17
```

#### Linux配置

```shell
Path=%JAVA_HOME%\bin;
```

## Java程序基础

### 内存单位换算

8 bit = 1 B（bit 比特 B字节）

1024 B = 1 KB

1024 KB = 1 MB

1024 MB = 1 GB

1024 GB = 1 TB

### 数字的二进制表示

1. 整数都是以补码的形式表示的，正数二进制数最高位为0，负数二进制最高位为1。正数的补码、反码和原码都一样，负数的补码是反码加一的结果。

2. 小数在Java中以浮点数形式表示（科学计数法），Java中的浮点数遵循IEEE-754标准。

   浮点数无法表示零，所以取值范围分为正负两个区间

   下面着重分析单浮点数

   单精度浮点数分配四个字节总共32 Bit

   最高位为符号位，**指数位（阶码位）**在符号位后占8 Bit，其余位**有效数字位（尾数位）**占23 Bit

   **阶码位**

   IEEE-754标准规定阶码位存储的是指数对应的移码（移码是一个真值在数轴正向平移一个偏移量之后得到的，

   移码的几何意义是把真值映射到一个整数域。对计算机来说移码比较两个真值的大小非常简单，只要高位对齐后逐个比较即可。不用考虑负号的问题。

   IEEE-754规定偏移量是2^(n-1) - 1，这样能表示的指数范围为[-126, 127]，能表示的制数的最大值为**2^127**约等于**1.7*10^38**。

   **8位二进制数能表示的指数的范围是[-128,127]若偏移量为2^(n-1)（128）则可以表示的阶码的范围是[0,255]，由于计算机规定阶码全为0或全为1会被当做特殊值处理（全为0认为是机器零，全为1认为是无穷大），除去这两个特殊值阶码的范围变为[1,254]，当偏移量为128由阶码公式可得指数的范围变为[-127,126]，此时指数的最大值为126，会缩小浮点数所能表示的取值范围，所以IEEE-754规定偏移量为2^(n-1) - 1，这样指数的范围会变成[-126,127]，指数最大值为127**

   
   
   阶码公式：(E阶码、e真值、y=n-1，n为指数位数)
   $$
   E = e + 2^y -1
   $$
   **尾数位**

   IEEE-754标准规定尾数以原码表示，能表示的最大值是二进制的1.111...1（小数点后23个1），

   是一个无限接近2的数字，为了节约存储空间，将符合规格化尾数的首个1省略，所以尾数实际上表示了24位

   

   单精度浮点数能表示最大十进制小数为：**2 *  1.7 * 10^38**

   

   **二进制小数转十进制方法：**

   1.010 1011 * 2^10

   2^2 + 2^0 + 2^-2 + 2^-4 + 2^-5 = 5.34375

   十进制小数转二进制方法：

   1）5.34375整数位为5，转二进制为101；

   2）小数位0.34375 * 2 = 0.6875，整数位为0，结果101.0；

   3）继续0.6875 * 2 = 1.375，整数位为1，结果101.01；

   4）去掉整数继续乘2，0.375 * 2 = 0.75，结果101.010；

   5）循环到没有小数为止……；

   6）最终结果：101.01011。
   
   原文链接：https://blog.csdn.net/iteye_5562/article/details/82618153

### 字符集

ASCII 码 8 Bit

GB2312

GBK

Unicode的实现:

UTF-8、UTF-16、UTF-32

UTF-8：目前互联网使用最广泛的一种Unicode编码方式，特点是可变长，使用1~4个字节表示一个字符，

编码规则如下：

1. 对于单个字节的字符，第一位设为 0，后面的 7 位对应这个字符的 Unicode 码点。因此，对于英文中的 0 - 127 号字符，与 ASCII 码完全相同。这意味着 ASCII 码那个年代的文档用 UTF-8 编码打开完全没有问题。
2. 对于需要使用 N 个字节来表示的字符（N > 1），第一个字节的前 N 位都设为 1，第 N + 1 位设为0，剩余的 N - 1 个字节的前两位都设位 10，剩下的二进制位则使用这个字符的 Unicode 码点来填充。

编码规则如下：

| Unicode 十六进制码点范围 | UTF-8 二进制                        |
| ------------------------ | ----------------------------------- |
| 0000 0000 - 0000 007F    | 0xxxxxxx                            |
| 0000 0080 - 0000 07FF    | 110xxxxx 10xxxxxx                   |
| 0000 0800 - 0000 FFFF    | 1110xxxx 10xxxxxx 10xxxxxx          |
| 0001 0000 - 0010 FFFF    | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx |


根据上面编码规则对照表，进行 UTF-8 编码和解码就简单多了。下面以汉字“汉”为利，具体说明如何进行 UTF-8 编码和解码。

“汉”的 Unicode 码点是 0x6c49（110 1100 0100 1001），通过上面的对照表可以发现，0x0000 6c49 位于第三行的范围，那么得出其格式为 1110xxxx 10xxxxxx 10xxxxxx。接着，从“汉”的二进制数最后一位开始，从后向前依次填充对应格式中的 x，多出的 x 用 0 补上。这样，就得到了“汉”的 UTF-8 编码为 11100110 10110001 10001001，转换成十六进制就是 0xE6 0xB7 0x89。

解码的过程也十分简单：如果一个字节的第一位是 0 ，则说明这个字节对应一个字符；如果一个字节的第一位1，那么连续有多少个 1，就表示该字符占用多少个字节。

原文链接：https://blog.csdn.net/hezh1994/article/details/78899683

### 数据类型

#### 基本类型

整数型：byte、short、int、long

浮点型：float、double

字符型：char

布尔型：boolean

##### 整数运算(二进制运算符号位也会参与运算)

###### 四则运算

1. 整数的数值表示不但是精确的，而且整数运算永远是精确的，即使是除法也是精确的，因为两个整数相除只能得到结果的整数部分：
2. 整数由于存在范围限制，如果计算结果超出了范围，就会产生溢出，而溢出**不会出错**

```java
public class Main {
    public static void main(String[] args) {
        int x = 2147483640;
        int y = 15;
        int sum = x + y;
        System.out.println(sum); // -2147483641
    }
}

```

要解释上述结果，我们把整数`2147483640`和`15`换成二进制做加法：

```tex
  0111 1111 1111 1111 1111 1111 1111 1000
+ 0000 0000 0000 0000 0000 0000 0000 1111
-----------------------------------------
  1000 0000 0000 0000 0000 0000 0000 0111
```

由于最高位计算结果为`1`，因此，加法结果变成了一个负数。

###### 移位运算

移位运算仅作用于整型和长整型数，移位运算时符号位会参与移位，移动的位数是一个mod 32 （长整型是mod 64）的结果，移位运算符分为两类：

1. 带符号移位运算符

   带符号移位运算符有两种左移和右移。

2. 无符号移位运算符

   无符号移位运算符只有一种，无符号右移。

表1：带符号移位

| 正数/负数 | 左移（<<） | 右移（>>）                         |
| --------- | ---------- | ---------------------------------- |
| 正数      | 低位补0    | 高位补0                            |
| 负数      | 定位补0    | 高位补<font color=red>**1**</font> |

例

```Java
int n = 7;       // 00000000 00000000 00000000 00000111 = 7
int a = n << 1;  // 00000000 00000000 00000000 00001110 = 14
int b = n << 2;  // 00000000 00000000 00000000 00011100 = 28
int c = n << 28; // 01110000 00000000 00000000 00000000 = 1879048192
int d = n << 29; // 11100000 00000000 00000000 00000000 = -536870912
```

```java
int n = -536870912;// 11111000 00000000 00000000 00000000 = -536870912
int a = n >> 1;    // 11110000 00000000 00000000 00000000 = -268435456
int b = n >> 2;    // 11111000 00000000 00000000 00000000 = -134217728
int c = n >> 28;   // 11111111 11111111 11111111 11111110 = -2
int d = n >> 29;   // 11111111 11111111 11111111 11111111 = -1
```

表2：无符号移位，

| 正数/负数 | 右移（>>>）                        |
| --------- | ---------------------------------- |
| 正数      | 高位补0                            |
| 负数      | 高位补<font color=red>**0**</font> |

```java
int n = -536870912;
int a = n >>> 1;  // 01110000 00000000 00000000 00000000 = 1879048192
int b = n >>> 2;  // 00111000 00000000 00000000 00000000 = 939524096
int c = n >>> 29; // 00000000 00000000 00000000 00000111 = 7
int d = n >>> 31; // 00000000 00000000 00000000 00000001 = 1
```

###### 位运算

位运算是按位进行与、或、非和异或的运算。

与运算的规则是，必须两个数同时为`1`，结果才为`1`：

```Java
n = 0 & 0; // 0
n = 0 & 1; // 0
n = 1 & 0; // 0
n = 1 & 1; // 1
```

或运算的规则是，只要任意一个为`1`，结果就为`1`：

```Java
n = 0 | 0; // 0
n = 0 | 1; // 1
n = 1 | 0; // 1
n = 1 | 1; // 1
```

非运算的规则是，`0`和`1`互换：

```Java
n = ~0; // 1
n = ~1; // 0
```

异或运算的规则是，如果两个数不同，结果为`1`，否则为`0`：

```Java
n = 0 ^ 0; // 0
n = 0 ^ 1; // 1
n = 1 ^ 0; // 1
n = 1 ^ 1; // 0
```

#### 引用类型

Object类是所有引用类型的父类。

### 面向对象编程基础

#### 访问控制修饰符

- **default** (即默认，什么也不写）: 在同一包内可见，不使用任何修饰符。使用对象：类、接口、变量、方法。
- **private** : 在同一类内可见。使用对象：变量、方法。 **注意：不能修饰类（外部类）**
- **public** : 对所有类可见。使用对象：类、接口、变量、方法
- **protected** : 对同一包内的类和所有子类可见。使用对象：变量、方法。



| 修饰符      | 当前类 | 同一包内 | 子孙类(同一包) | 子孙类(不同包)                                               | 其他包 |
| :---------- | :----- | :------- | :------------- | :----------------------------------------------------------- | :----- |
| `public`    | Y      | Y        | Y              | Y                                                            | Y      |
| `protected` | Y      | Y        | Y              | Y/N（[说明](https://www.runoob.com/java/java-modifier-types.html#protected-desc)） | N      |
| `default`   | Y      | Y        | Y              | N                                                            | N      |
| `private`   | Y      | N        | N              | N                                                            | N      |

#### 内部类

##### Inner Class

如果一个类定义在另一个类的内部，这个类就是Inner Class：

```java
class Outer {
    class Inner {
        // 定义了一个Inner Class
    }
}
```

要实例化一个`Inner`，我们必须首先创建一个`Outer`的实例，然后，调用`Outer`实例的`new`来创建`Inner`实例：

Inner Class除了有一个`this`指向它自己，还隐含地持有一个Outer Class实例，可以用`Outer.this`访问这个实例

`Outer`类被编译为`Outer.class`，而`Inner`类被编译为`Outer$Inner.class`。

```java
Outer.Inner inner = outer.new Inner();
```



##### Anonymous Class

```Java
Runnable r = new Runnable() {
    // 实现必要的抽象方法...
};
```

匿名类和Inner Class一样，可以访问Outer Class的`private`字段和方法。之所以我们要定义匿名类，是因为在这里我们通常不关心类名，比直接定义Inner Class可以少写很多代码。

观察Java编译器编译后的`.class`文件可以发现，`Outer`类被编译为`Outer.class`，而匿名类被编译为`Outer$1.class`。如果有多个匿名类，Java编译器会将每个匿名类依次命名为`Outer$1`、`Outer$2`、`Outer$3`……



##### Static Nested Class

最后一种内部类和Inner Class类似，但是使用`static`修饰，称为静态内部类（Static Nested Class）：

```java
public class Main {
    public static void main(String[] args) {
        Outer.StaticNested sn = new Outer.StaticNested();
        sn.hello();
    }
}

class Outer {
    private static String NAME = "OUTER";

    private String name;

    Outer(String name) {
        this.name = name;
    }

    static class StaticNested {
        void hello() {
            System.out.println("Hello, " + Outer.NAME);
        }
    }
}

```



#### classpath

`classpath`是`JVM`用到的一个环境变量，它用来指示`JVM`如何搜索`class`。

`classpath`就是一组目录的集合，它设置的搜索路径与操作系统相关。例如，在Windows系统上，用`;`分隔，带空格的目录用`""`括起来，可能长这样：

```text
C:\work\project1\bin;C:\shared;"D:\My Documents\project1\bin"
```

在Linux系统上，用`:`分隔，可能长这样：

```tex
/usr/shared:/usr/local/bin:/home/liaoxuefeng/bin
```

我们强烈*不推荐*在系统环境变量中设置`classpath`，那样会污染整个系统环境。在启动JVM时设置`classpath`才是推荐的做法。实际上就是给`java`命令传入`-classpath`或`-cp`参数：

```text
java -classpath .;C:\work\project1\bin;C:\shared abc.xyz.Hello
```

或者使用`-cp`的简写：

```text
java -cp .;C:\work\project1\bin;C:\shared abc.xyz.Hello
```

没有设置系统环境变量，也没有传入`-cp`参数，那么JVM默认的`classpath`为`.`，即当前目录：

```bash
java abc.xyz.Hello
```



#### jar包

jar包实际上就是一个zip格式的压缩文件，而jar包相当于目录。如果我们要执行一个jar包的`class`，就可以把jar包放到`classpath`中：

```bash
java -cp ./hello.jar abc.xyz.Hello
```

这样JVM会自动在`hello.jar`文件里去搜索某个类。

jar包还可以包含一个特殊的`/META-INF/MANIFEST.MF`文件，`MANIFEST.MF`是纯文本，可以指定`Main-Class`和其它信息。JVM会自动读取这个`MANIFEST.MF`文件，如果存在`Main-Class`，我们就不必在命令行指定启动的类名，而是用更方便的命令：

```bash
java -jar hello.jar
```

jar包还可以包含其它jar包，这个时候，就需要在`MANIFEST.MF`文件里配置`classpath`了。



### Java核心类

#### String

- Java字符串`String`是不可变对象；
- 字符串操作不改变原字符串内容，而是返回新字符串；
- 常用的字符串操作：提取子串、查找、替换、大小写转换等；
- Java使用Unicode编码表示`String`和`char`；
- 转换编码就是将`String`和`byte[]`转换，需要指定编码；
- 转换为`byte[]`时，始终优先考虑`UTF-8`编码。

#### StringBuilder

字符串拼接，非线程安全。

#### StringJoiner

字符串拼接

```java
public class Main {
    public static void main(String[] args) {
        String[] names = {"Bob", "Alice", "Grace"};
        var sj = new StringJoiner(", ");
        for (String name : names) {
            sj.add(name);
        }
        System.out.println(sj.toString());
    }
}

```



#### 包装类型

##### Byte

##### Short

##### Integer

##### Long

##### Character

##### Float

##### Double

##### Boolean



###### Auto Boxing

因为`int`和`Integer`可以互相转换：

```java
int i = 100;
Integer n = Integer.valueOf(i);
int x = n.intValue();
```

所以，Java编译器可以帮助我们自动在`int`和`Integer`之间转型：

```java
Integer n = 100; // 编译器自动使用Integer.valueOf(int)
int x = n; // 编译器自动使用Integer.intValue()
```

这种直接把`int`变为`Integer`的赋值写法，称为自动装箱（Auto Boxing），反过来，把`Integer`变为`int`的赋值写法，称为自动拆箱（Auto Unboxing）。

注意：自动装箱和自动拆箱只发生在编译阶段，目的是为了少写代码。

#### JavaBean

#### 枚举

#### BigInteger

#### BigDecimal

#### 常用工具类

##### Math

数学计算

##### Random

生成伪随机数

##### SecureRadom

生成安全的随机数



### 异常处理

Java规定：

- 必须捕获的异常，包括`Exception`及其子类，但不包括`RuntimeException`及其子类，这种类型的异常称为`Checked Exception`。
- 不需要捕获的异常，包括`Error`及其子类，`RuntimeException`及其子类，称为Unchecked Exception。

**注意：编译器对RuntimeException及其子类不做强制捕获要求，不是指应用程序本身不应该捕获并处理RuntimeException。是否需要捕获，具体问题具体分析。**



