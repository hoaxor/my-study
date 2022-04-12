

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



| 时间     | 版本        |
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

| Unicode 十六进制码点范围      | UTF-8 二进制                           |
| --------------------- | ----------------------------------- |
| 0000 0000 - 0000 007F | 0xxxxxxx                            |
| 0000 0080 - 0000 07FF | 110xxxxx 10xxxxxx                   |
| 0000 0800 - 0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx          |
| 0001 0000 - 0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx |


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

| 正数/负数 | 左移（<<） | 右移（>>）                          |
| ----- | ------ | ------------------------------- |
| 正数    | 低位补0   | 高位补0                            |
| 负数    | 定位补0   | 高位补<font color=red>**1**</font> |

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

| 正数/负数 | 右移（>>>）                         |
| ----- | ------------------------------- |
| 正数    | 高位补0                            |
| 负数    | 高位补<font color=red>**0**</font> |

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



| 修饰符         | 当前类  | 同一包内 | 子孙类(同一包) | 子孙类(不同包)                                 | 其他包  |
| :---------- | :--- | :--- | :------- | :--------------------------------------- | :--- |
| `public`    | Y    | Y    | Y        | Y                                        | Y    |
| `protected` | Y    | Y    | Y        | Y/N（[说明](https://www.runoob.com/java/java-modifier-types.html#protected-desc)） | N    |
| `default`   | Y    | Y    | Y        | N                                        | N    |
| `private`   | Y    | N    | N        | N                                        | N    |

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



##### Auto Boxing

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



### 反射



### 注解

注解是放在Java源码的类、方法、字段、参数前的一种特殊“注释”，注释会被编译器直接忽略，注解则可以被编译器打包进入class文件，因此，注解是一种用作标注的“元数据”。

#### 分类

- 第一类是由编译器使用的注解，这类注解不会被编译进class文件。如：
  - `@Override`：让编译器检查该方法是否正确地实现了覆写；
  - `@SuppressWarnings`：告诉编译器忽略此处代码产生的警告。
- 第二类是由工具处理`.class`文件使用的注解，这类注解会被编译进入`.class`文件，但加载结束后并不会存在于内存中。如：
  - lombok提供的`@Getter/@Setter`，作用类上，生成所有成员变量的getter/setter方法。、`@NoArgsConstructor`：生成无参构造器。。。等等
- 第三类是在程序运行期能够读取的注解，它们在加载后一直存在于JVM中，这也是最常用的注解

#### 配置参数

- 所有基本类型；
- String；
- 枚举类型；
- 基本类型、String、Class以及枚举的数组。

#### 注解的使用

Java语言使用`@interface`语法来定义注解（`Annotation`），它的格式如下：

```java
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```

注解的参数类似无参数方法，可以用`default`设定一个默认值（强烈推荐）。最常用的参数应当命名为`value`。

##### 元注解

可以修饰注解的注解，这些注解就称为元注解（meta annotation）

- #### @Target

  - #### 最常用的元注解是`@Target`。使用`@Target`可以定义`Annotation`能够被应用于源码的哪些位置：

    - 类或接口：`ElementType.TYPE`；
    - 字段：`ElementType.FIELD`；
    - 方法：`ElementType.METHOD`；
    - 构造方法：`ElementType.CONSTRUCTOR`；
    - 方法参数：`ElementType.PARAMETER`。

```java
@Target(ElementType.METHOD)
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```

- #### @Retention

  - #### 元注解`@Retention`定义了`Annotation`的生命周期，如果`@Retention`不存在，则该`Annotation`默认为`CLASS`。因为通常我们自定义的`Annotation`都是`RUNTIME`，所以，务必要加上`@Retention(RetentionPolicy.RUNTIME)`这个元注解。

    - 仅编译期：`RetentionPolicy.SOURCE`；
    - 仅class文件：`RetentionPolicy.CLASS`；
    - 运行期：`RetentionPolicy.RUNTIME`。

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```

- #### @Repeatable

  - #### 使用`@Repeatable`这个元注解可以定义`Annotation`是否可重复。这个注解应用不是特别广泛。

    ```java
    @Repeatable(Reports.class)
    @Target(ElementType.TYPE)
    public @interface Report {
        int type() default 0;
        String level() default "info";
        String value() default "";
    }
    
    @Target(ElementType.TYPE)
    public @interface Reports {
        Report[] value();
    }
    
    ```

    经过`@Repeatable`修饰后，在某个类型声明处，就可以添加多个`@Report`注解：

    ```java
    @Report(type=1, level="debug")
    @Report(type=2, level="warning")
    public class Hello {
    }
    ```

- #### @Inherited

  - #### 使用`@Inherited`定义子类是否可继承父类定义的`Annotation`。`@Inherited`仅针对`@Target(ElementType.TYPE)`类型的`annotation`有效，并且仅针对`class`的继承，对`interface`的继承无效：

    ```java
    @Inherited
    @Target(ElementType.TYPE)
    public @interface Report {
        int type() default 0;
        String level() default "info";
        String value() default "";
    }
    
    ```

    在使用的时候，如果一个类用到了`@Report`：

    ```java
    @Report(type=1)
    public class Person {
    }
    ```

    则它的子类默认也定义了该注解：

    ```java
    public class Student extends Person {
    }
    ```

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```

自定义注解

```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
    int min() default 0;

    int max() default 100;
}

public class AnnotationTest {
    @Range(max = 30)
    private String name;

    public static void main(String[] args) throws IllegalAccessException {
        AnnotationTest annotationTest = new AnnotationTest();
//        annotationTest.name = "21";
        checkRange(annotationTest);
    }

    public static void checkRange(AnnotationTest annotationTest) throws IllegalAccessException {
        Class<?> clz = annotationTest.getClass();
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Range.class)) {
                Range range = field.getAnnotation(Range.class);
                Object o = field.get(annotationTest);
                if (o instanceof String) {
                    String s = (String) o;
                    if (s.length() < range.min() || s.length() > range.max()) {
                        throw new IllegalArgumentException("check range failure");
                    }
                }

            }

        }
    }
}
```



### 泛型

泛型就是定义一种模板类，使用方便，不需要强制转型，避免强制转型带来的异常

Java语言的泛型实现方式是擦拭法（Type Erasure）。所谓擦拭法是指，虚拟机对泛型其实一无所知，所有的工作都是编译器做的。

Java的泛型是由编译器在编译时实行的，编译器内部永远把所有类型`T`视为`Object`处理，但是，在需要转型的时候，编译器会根据`T`的类型自动为我们实行安全地强制转型。

#### Java泛型的局限

- 局限一：泛型不能是基本数据类型
- 局限二：无法取得带泛型的`Class`

```java
    public static void test1() {
        List<String> stringList = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        System.out.println(stringList.getClass());// class java.util.ArrayList
        System.out.println(integers.getClass());// class java.util.ArrayList
        System.out.println(stringList.getClass() == integers.getClass());//true
    }
```

- 局限三：无法判断带泛型的类型

```
public static void test2() {
    List<String> stringList = new ArrayList<>();
    //Illegal generic type for instanceof
    System.out.println(stringList instanceof List<String>);
}
```

- 局限四：不能实例化`T`类型：

```java
public class Pair<T> {
    private T first;
    private T last;
    public Pair() {
        // Compile error:
        first = new T();
        last = new T();
    }
}
```

#### 泛型继承

一个类可以继承自一个泛型类。例如：父类的类型是`Pair<Integer>`，子类的类型是`IntPair`，可以这么继承：

```java
public class IntPair extends Pair<Integer> {
}
IntPair ip = new IntPair(1, 2);

```

因为Java引入了泛型，所以，只用`Class`来标识类型已经不够了。实际上，Java的类型系统结构如下：

```ascii
                      ┌────┐
                      │Type│
                      └────┘
                         ▲
                         │
   ┌────────────┬────────┴─────────┬───────────────┐
   │            │                  │               │
┌─────┐┌─────────────────┐┌────────────────┐┌────────────┐
│Class││ParameterizedType││GenericArrayType││WildcardType│
└─────┘└─────────────────┘└────────────────┘└────────────┘
```



#### Extends

形如`<? extends Number>`的泛型定义称之为上界通配符（Upper Bounds Wildcards），把泛型类型`T`的上界限定在`Number`了。

`List<? extends Number>`无法add任何`Number`及其子类对象。唯一的例外是可以给方法参数传入`null`



```java
/**
 * 上界通配符: 可以读，不能写。
 *
 * @author : huang.yaohua
 * @date : 2022/3/29 17:13
 */
public class ExtendsTest {
    public static void main(String[] args) {
        List<? extends Father> list = new ArrayList<>();
        //只能放入null，不能放入任何Father及其子类
        list.add(null);


        //获取的元素泛型保留
        Father father = list.get(0);

        test1(list);
    }

    public static void test1(List<? extends Father> list) {
        System.out.println(list);
    }
}

class Father {
}

class Son2 extends Father {

}

class Son3 extends Father {

}
```



#### Super

使用`<? super Integer>`泛型定义称之为下界通配符（Lower Bounds Wildcards），把泛型类型`T`的下界限定在`Integer`了。

`List<? super Integer>`可以add`Integer`及其子类对象。

```java
/**
 * 下界通配符: 只能写，不能读
 *
 * @author : huang.yaohua
 * @date : 2022/3/29 17:18
 */
public class SuperTest {
    public static void main(String[] args) {
        List<? super Son2> list = new ArrayList<>();
        //只能add Son2及Son2子类
        //list.add(new Father());
        list.add(new Son2());
        list.add(new Son21());
        test1(list);

        // 泛型丢失
        Object object = list.get(0);

        List<Father> fathers = new ArrayList<>();
        test1(fathers);

        // 不能接收
//        List<Son21> son21List = new ArrayList<>();
//        test1(son21List);
    }

    /**
     * 可以接收
     * List<? super Son2>
     * List<Father>
     */
    private static void test1(List<? super Son2> son2) {
        System.out.println(son2);
    }
}
class Father {
}

class Son2 extends Father {

}

class Son3 extends Father {

}
```

#### PECS原则

何时使用`extends`，何时使用`super`？为了便于记忆，我们可以用PECS原则：Producer Extends Consumer Super。

即：如果需要返回`T`，它是生产者（Producer），要使用`extends`通配符；如果需要写入`T`，它是消费者（Consumer），要使用`super`通配符。

以`Collections`的`copy()`方法为例：

```java
public class Collections {
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i=0; i<src.size(); i++) {
            T t = src.get(i); // src是producer
            dest.add(t); // dest是consumer
        }
    }
}
```

需要返回`T`的`src`是生产者，因此声明为`List<? extends T>`，需要写入`T`的`dest`是消费者，因此声明为`List<? super T>`。



#### 无限定通配符

Java的泛型还允许使用无限定通配符（Unbounded Wildcard Type），即只定义一个`?`：

`<?>`通配符既没有`extends`，也没有`super`，因此：

- 不允许调用`set(T)`方法并传入引用（`null`除外）；
- 不允许调用`T get()`方法并获取`T`引用（只能获取`Object`引用）。

`<?>`通配符有一个独特的特点，就是：`Pair<?>`是所有`Pair<T>`的超类：

```java
    public static void main(String[] args) {
        Pair<Integer> p = new Pair<>(123, 456);
        Pair<?> p2 = p; // 安全地向上转型
        System.out.println(p2.getFirst() + ", " + p2.getLast());
    }

class Pair<T> {
    private T first;
    private T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }

    public T getFirst() {
        return first;
    }
    public T getLast() {
        return last;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public void setLast(T last) {
        this.last = last;
    }
}
```



#### 泛型和反射

Java的部分反射API也是泛型。例如：`Class<T>`就是泛型：

```Java
// compile warning:
Class clazz = String.class;
String str = (String) clazz.newInstance();

// no warning:
Class<String> clazz = String.class;
String str = clazz.newInstance();
```

我们可以声明带泛型的数组，但不能用`new`操作符创建带泛型的数组：

```java
Pair<String>[] ps = null; // ok
Pair<String>[] ps = new Pair<String>[2]; // compile error!
```

必须通过强制转型实现带泛型的数组：

```java
@SuppressWarnings("unchecked")
Pair<String>[] ps = (Pair<String>[]) new Pair[2];
```

使用泛型数组要特别小心，因为数组实际上在运行期没有泛型，编译器可以强制检查变量`ps`，因为它的类型是泛型数组。但是，编译器不会检查变量`arr`，因为它不是泛型数组。因为这两个变量实际上指向同一个数组，所以，操作`arr`可能导致从`ps`获取元素时报错，例如，以下代码演示了不安全地使用带泛型的数组：

```java
Pair[] arr = new Pair[2];
Pair<String>[] ps = (Pair<String>[]) arr;

ps[0] = new Pair<String>("a", "b");
arr[1] = new Pair<Integer>(1, 2);

// ClassCastException:
Pair<String> p = ps[1];
String s = p.getFirst();
```

要安全地使用泛型数组，必须扔掉`arr`的引用：

```java
@SuppressWarnings("unchecked")
Pair<String>[] ps = (Pair<String>[]) new Pair[2];
```

上面的代码中，由于拿不到原始数组的引用，就只能对泛型数组`ps`进行操作，这种操作就是安全的。

不能直接创建泛型数组`T[]`，因为擦拭后代码为`Object[]`，必须借助`Class<T>`来创建泛型数组

```java
    static <T> T[] createArray(Class<T> tClass, int length) {
        return (T[]) Array.newInstance(tClass, length);
    }
```



```java
    public static void main(String[] args) {
        String[] arr = asArray("one", "two", "three");
        System.out.println(Arrays.toString(arr));
        // ClassCastException:
        String[] firstTwo = pickTwo("one", "two", "three");
        System.out.println(Arrays.toString(firstTwo));
    }

    static <K> K[] pickTwo(K k1, K k2, K k3) {
        return asArray(k1, k2);
    }

    static <T> T[] asArray(T... objs) {
        return objs;
    }

```

直接调用`asArray(T...)`似乎没有问题，但是在另一个方法中，我们返回一个泛型数组就会产生`ClassCastException`，原因还是因为擦拭法，在`pickTwo()`方法内部，编译器无法检测`K[]`的正确类型，因此返回了`Object[]`。

如果仔细观察，可以发现编译器对所有可变泛型参数都会发出警告，除非确认完全没有问题，才可以用`@SafeVarargs`消除警告。



### 集合

#### 类图

![这里写图片描述](https://img-blog.csdn.net/20180803195348216?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ZlaXlhbmFmZmVjdGlvbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)



#### 常用集合的分类

Collection 接口的接口 对象的集合（单列集合）
├——-List 接口：元素按进入先后有序保存，可重复
│—————-├ LinkedList 接口实现类， 链表， 插入删除， 没有同步， 线程不安全
│—————-├ ArrayList 接口实现类， 数组， 随机访问， 没有同步， 线程不安全
│—————-└ Vector 接口实现类 数组， 同步， 线程安全
│ ———————-└ Stack 是Vector类的实现类
└——-Set 接口： 仅接收一次，不可重复，并做内部排序
├—————-└HashSet 使用hash表（数组）存储元素
│————————└ LinkedHashSet 链表维护元素的插入次序
└ —————-TreeSet 底层实现为二叉树，元素排好序

Map 接口 键值对的集合 （双列集合）
├———Hashtable 接口实现类， 同步， 线程安全
├———HashMap 接口实现类 ，没有同步， 线程不安全-
│—————–├ LinkedHashMap 双向链表和哈希表实现
│—————–└ WeakHashMap
├ ——–TreeMap 红黑树对所有的key进行排序
└———IdentifyHashMap

#### List和Set集合详解

1. list和set的区别：

![这里写图片描述](https://img-blog.csdn.net/20180803201610610?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ZlaXlhbmFmZmVjdGlvbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

2. List

**ArrayList**：底层数据结构是数组，查询快，增删慢，线程不安全，效率高，可以存储重复元素

**LinkedList** 底层数据结构是链表，查询慢，增删快，线程不安全，效率高，可以存储重复元素

**Vector**:底层数据结构是数组，查询快，增删慢，线程安全，效率低，可以存储重复元素

![这里写图片描述](https://img-blog.csdn.net/20180803201736883?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ZlaXlhbmFmZmVjdGlvbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

3. Set

**HashSet**底层数据结构采用哈希表（HashMap）实现，元素无序且唯一，线程不安全，效率高，可以存储null元素，元素的唯一性是靠所存储元素类型是否重写hashCode()和equals()方法来保证的，如果没有重写这两个方法，则无法保证元素的唯一性。

具体实现唯一性的比较过程：存储元素首先会使用hash()算法函数生成一个int类型hashCode散列值，然后已经的所存储的元素的hashCode值比较，如果hashCode不相等，则所存储的两个对象一定不相等，此时存储当前的新的hashCode值处的元素对象；如果hashCode相等，存储元素的对象还是不一定相等，此时会调用equals()方法判断两个对象的内容是否相等，如果内容相等，那么就是同一个对象，无需存储；如果比较的内容不相等，那么就是不同的对象，就该存储了，此时就要采用哈希的解决地址冲突算法（链表法），在当前hashCode值处类似一个新的链表， 在同一个hashCode值的后面存储存储不同的对象，这样就保证了元素的唯一性。


### HashCode与Equals

`equals()`方法要求我们必须满足以下条件：

- 自反性（Reflexive）：对于非`null`的`x`来说，`x.equals(x)`必须返回`true`；
- 对称性（Symmetric）：对于非`null`的`x`和`y`来说，如果`x.equals(y)`为`true`，则`y.equals(x)`也必须为`true`；
- 传递性（Transitive）：对于非`null`的`x`、`y`和`z`来说，如果`x.equals(y)`为`true`，`y.equals(z)`也为`true`，那么`x.equals(z)`也必须为`true`；
- 一致性（Consistent）：对于非`null`的`x`和`y`来说，只要`x`和`y`状态不变，则`x.equals(y)`总是一致地返回`true`或者`false`；
- 对`null`的比较：即`x.equals(null)`永远返回`false`。

在`List`中查找元素时，`List`的实现类通过元素的`equals()`方法比较两个元素是否相等，因此，放入的元素必须正确覆写`equals()`方法，Java标准库提供的`String`、`Integer`等已经覆写了`equals()`方法；

编写`equals()`方法可借助`Objects.equals()`判断。

如果不在`List`中查找元素，就不必覆写`equals()`方法。

**如果两个对象的equals结果是true，则两个对象的hashCode也必须相等**

**重写equals方法必须重写hashCode方法**

实现`hashCode()`方法可以通过`Objects.hashCode()`辅助方法实现。

`hashCode()`方法

