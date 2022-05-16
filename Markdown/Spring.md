# Spring



## Spring 框架概述

解决企业开发的复杂性

轻量、面向接口编程，解耦、方便集成各种优秀框架（提供对各种优秀框架的直接支持，简化框架的使用）



## Spring 核心

### IOC(inversion of control)

控制反转，创建、注入对象的过程交给Spring。

把对象的创建、属性赋值、生命周期交给代码之外的容器管理

#### DI(dependency injection)

IoC的一种技术实现，程序员只需要提供要使用的对象的名称就行了，对象如何创建，如何从容器中查找，获取都由容器内部自己实现。

**spring使用的DI实现IoC**



标准配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--名称空间-->
<!--xml 约束文件地址 http://www.springframework.org/schema/beans/spring-beans.xsd-->
<beans xmlns="http://www.springframework.org/schema/beans"       
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


</beans>
```



##### DI给属性赋值

###### 基于XML的DI

set注入：

```xml
<!--set 注入，spring调用类的set方法，通过set方法，完成属性赋值 
   1. 基本类型/String set注入如下， 属性未提供set方法时会报错 
   Student类中无 email属性，但有setEmail方法，可以成功创建对象，并调用setStudent
   
   2. 引用类型注入  ref="beanId"
   -->
<bean id="student1" class="com.hyh.di.setinjection.Student">
    <property name="age" value="18"/>
    <property name="student" value="hyh"/>
    <property name="email" value="111@qq.com"/>
    <property name="date" ref="date"/>
</bean>

<bean id="date" class="java.util.Date">
    <property name="time" value="123132213123"/>
</bean>
```

构造注入：

```xml
    <!--构造注入：使用形参name-->
    <bean id="student2" class="com.hyh.di.setinjection.Student">
        <constructor-arg name="student" value="hyh2"/>
        <constructor-arg name="age" value="19"/>
        <constructor-arg name="date" ref="date"/>
    </bean>

    <!--构造注入：使用index，可以省略index，但是入参顺序要求和构造函数一致-->
    <bean id="student3" class="com.hyh.di.setinjection.Student">
        <constructor-arg index="0" value="20"/>
        <constructor-arg index="1" value="hyh3"/>
        <constructor-arg name="date" ref="date"/>
    </bean>
    <bean id="date" class="java.util.Date">
        <property name="time" value="123132213123"/>
    </bean>
```

引用类型自动注入：

byName(类的引用类型的属性名和和bean的Id相同且类型相同时，可以使用byName自动注入)

```xml
    <!--引用类型自动注入   byName 引用类型的属性的名称和bean的ID相同且类型相同 -->
    <bean id="student4" class="com.hyh.di.setinjection.Student" autowire="byName">
        <property name="age" value="21"/>
        <property name="student" value="hyh4"/>
    </bean>

    <bean id="date" class="java.util.Date">
        <property name="time" value="123132213123"/>
    </bean>
```

byType(类中引用类型的数据类型和bean的class是同源的，可以使用byType自动注入)

```xml
    <!--引用类型自动注入   byType 引用类型的属性的classA和bean的classB同源
    (classA==classB 或 classA继承于classB 或 classB继承于classA)-->
    <bean id="student5" class="com.hyh.di.setinjection.Student" autowire="byType">
        <property name="age" value="22"/>
        <property name="student" value="hyh5"/>
    </bean>

    <bean id="date" class="java.util.Date">
        <property name="time" value="123132213123"/>
    </bean>

    <bean id="aEmail" class="java.lang.String">
        <constructor-arg index="0" value="22@qq.com"/>
    </bean>
```



###### 基于注解的DI

![image-20220515203846645](\picture\image-20220515203846645.png)

@AutoWired

spring框架提供，给引用类型赋值，支持byName，byType。默认byType

![image-20220515220222524](\picture\image-20220515220222524.png)

@Resouce

jdk提供，默认使用byName，若byName查找bean失败则byType查找

**2.ioc操作两部分：**

（1）ioc的配置文件方式

（2）ioc的注解方式



***\*3.\**\**ioc\**\**底层原理使用技术\****

（1）xml配置文件

（2）dom4j解析xml

（3）工厂设计模式

（4）反射

### AOP（Aspect Oriented Programming）

面向切面编程，在不修改源码的前提下进行功能增强。

![image-20220515223452360](\picture\image-20220515223452360.png)

![image-20220515223556530](\picture\image-20220515223556530.png)

https://www.jianshu.com/p/2e8409bc8c3b

## Spring事务

### 事务概念

事务是数据库操作的最基本单元，逻辑上一组操作，要么都成功，如果有一个失败所有操作都失败。

典型场景

- 事务特性
  - 原子性（Atomicity）
    - 事务必须是一个原子操作序列单元，事务中包含的各项操作在一次执行过程中，只允许出现两种状态之一，要么都成功，要么都失败。
  - 一致性（Consistency）
    - 事务的执行不能破坏数据的完整性和一致性，一个事务在执行之前和执行之后，数据库都必须处于一致性状态。
    - 拿转账来说，假设用户A和用户B两者的钱加起来一共是5000，那么不管A和B之间如何转账，转几次账，事务结束后两个用户的钱相加起来应该还得是5000，这就是事务的一致性。
  - 隔离性（Isolation）
    - 指在并发环境中，并发的事务是互相隔离的，一个事务的执行不能被其他事务干扰。也就是说，不同的事务并发操作相同的数据时，每个事务都有各自完整的数据空间。
    - 一个事务内部的操作及使用的数据对其他并发事务是隔离的，并发执行的各个事务是不能互相干扰的。
  - 持久性（Durability）
    - 指事务一旦提交后，数据库中的数据必须被永久的保存下来。即使服务器系统崩溃或者服务器宕机等故障。只要数据库重新启动，那么一定能够将其恢复到事务成功结束后的状态
    - 在事务进行过程中，未结束之前，DML语句是不会更改底层数据，只是将历史操作记录一下，在内存中完成记录。只有在十五结束的时候，而且是成功的结束时，才会修改底层硬盘文件中的数据

#### 事务隔离级别

SQL的标准事务隔离级别：

**读未提交（read uncommitted）**：一个事务还没提交时，它做的变更才会被其它事务看到。

**读提交（read committed）**：一个事务只有被提交之后，它做的变更才会被其他事务看到。

**可重复读（repeatable read）**：一个事务执行过程中，看到的数据，总是跟这个事务在启动时看到的数据是一致的。未提交变更对其他事务也是不可见的。

**串行化（serializable）**:对于同一行记录，写会加写锁，读会加读锁，当出现锁冲突时，后访问的事务需要等前一个事务执行完成，才能继续执行。

事务具有隔离性,理论上来说事务之间的执行不应该相互产生影响,其对数据库的影响应该和它们串行执行时一样。

然而完全的隔离性会导致系统并发性能很低,降低对资源的利用率,因而实际上对隔离性的要求会有所放宽,这也会一定程度造成对数据库一致性要求降低

#### 常见并发异常

##### 脏写

指事务回滚了其他事务对数据项的已提交修改，如：

![img](https://img-blog.csdnimg.cn/img_convert/9a29a435c7790cb54755208365023534.png)

在事务1对数据A的回滚，导致事务2对A的已提交

##### 丢失更新

丢失更新是指事务覆盖了其他事务对数据的已提交修改,导致这些修改好像丢失了一样。

![img](https://img-blog.csdnimg.cn/img_convert/aab7360c0e8aa5564e2ee99f557e4870.png)

事务1和事务2读取A的值都为10,事务2先将A加上10并提交修改,之后事务2将A减少10并提交修改,A的值最后为,导致事务2对A的修改好像丢失了一样

##### 脏读

脏读是指一个事务读取了另一个事务未提交的数据

![img](https://img-blog.csdnimg.cn/img_convert/79a9d07a8b1f35efe6256a6c0de7c219.png)

在事务1对A的处理过程中,事务2读取了A的值,但之后事务1回滚,导致事务2读取的A是未提交的脏数据。



##### 不可重复读

不可重复读是指一个事务对同一数据的读取结果前后不一致。脏读和不可重复读的区别在于:前者读取的是事务未提交的脏数据,后者读取的是事务已经提交的数据,只不过因为数据被其他事务修改过导致前后两次读取的结果不一样,比如下面这种情况

![img](https://img-blog.csdnimg.cn/img_convert/f5d1fb65278fa57aac12707395b7c285.png)

由于事务2对A的已提交修改,事务1前后两次读取的结果不一致。

 

##### 幻读

幻读是指事务读取某个范围的数据时，因为其他事务的操作导致前后两次读取的结果不一致。幻读和不可重复读的区别在于,不可重复读是针对确定的某一行数据而言,而幻读是针对不确定的多行数据。因而幻读通常出现在带有查询条件的范围查询中,比如下面这种情况:

![img](https://img-blog.csdnimg.cn/img_convert/a440c87a94f08cb301a7570de60444f6.png)

事务1查询A<5的数据,由于事务2插入了一条A=4的数据,导致事务1两次查询得到的结果不一样
