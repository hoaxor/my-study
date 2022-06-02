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

@Value

基本数据类型+string，设置初始值

**2.ioc操作两部分：**

（1）ioc的配置文件方式

（2）ioc的注解方式



**IoC容器底层原理使用技术**

（1）xml配置文件

（2）dom4j解析xml

（3）工厂设计模式

（4）反射

![image-20220522192527671](\picture\image-20220522192527671.png)

1. BeanFactory是基础，BeanFactory和它的子接口定义的API满足了spring环境中对bean管理和配置的需求；
2. ApplicationContext是扩展，以BeanFactory为主线，通过继承的方式综合了环境、国际化、资源、事件等多条支线，自己又规定了一些扩展服务（如返回context的id，应用名称等），而所有支线都以bean服务为基础；
![这里写图片描述](https://img-blog.csdn.net/20180812101356107?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JvbGluZ19jYXZhbHJ5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
原文链接：https://blog.csdn.net/boling_cavalry/article/details/81603303

![image-20220522194502291](\picture\image-20220522194502291.png)

![image-20220522194749268](\picture\image-20220522194749268.png)

![image-20220522195128373](\picture\image-20220522195128373.png)



![image-20220522195307174](\picture\image-20220522195307174.png)

##### 泛型依赖注入



#### Ioc源码分析

源码分析思路：

1. 从hello world开始

```java
    public void refresh() throws BeansException, IllegalStateException {
        synchronized(this.startupShutdownMonitor) {
            // 准备环节
            this.prepareRefresh();
            // 解析xml, 加载BeanDefinitions
            ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
            this.prepareBeanFactory(beanFactory);

            try {
                this.postProcessBeanFactory(beanFactory);
                this.invokeBeanFactoryPostProcessors(beanFactory);
                // 注册spring内部类的后置处理器
                this.registerBeanPostProcessors(beanFactory);
                // 国际化支持
                this.initMessageSource();
                this.initApplicationEventMulticaster();
                this.onRefresh();
                // 初始化监控器
                this.registerListeners();
                // 初始化bean
                this.finishBeanFactoryInitialization(beanFactory);
                // 完成初始化
                this.finishRefresh();
            } catch (BeansException var9) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + var9);
                }

                this.destroyBeans();
                this.cancelRefresh(var9);
                throw var9;
            } finally {
                this.resetCommonCaches();
            }

        }
    }

```

```java
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        if (beanFactory.containsBean("conversionService") && beanFactory.isTypeMatch("conversionService", ConversionService.class)) {
            beanFactory.setConversionService((ConversionService)beanFactory.getBean("conversionService", ConversionService.class));
        }

        if (!beanFactory.hasEmbeddedValueResolver()) {
            beanFactory.addEmbeddedValueResolver((strVal) -> {
                return this.getEnvironment().resolvePlaceholders(strVal);
            });
        }

        String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
        String[] var3 = weaverAwareNames;
        int var4 = weaverAwareNames.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String weaverAwareName = var3[var5];
            this.getBean(weaverAwareName);
        }

        beanFactory.setTempClassLoader((ClassLoader)null);
        beanFactory.freezeConfiguration();
        // 初始化单实例bean
        beanFactory.preInstantiateSingletons();
    }

    public void preInstantiateSingletons() throws BeansException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Pre-instantiating singletons in " + this);
        }
        // 获取所有beanI
        List<String> beanNames = new ArrayList(this.beanDefinitionNames);
        Iterator var2 = beanNames.iterator();

        while(true) {
            String beanName;
            Object bean;
            do {
                while(true) {
                    RootBeanDefinition bd;
                    do {
                        do {
                            do {
                                if (!var2.hasNext()) {
                                    var2 = beanNames.iterator();

                                    while(var2.hasNext()) {
                                        beanName = (String)var2.next();
                                        Object singletonInstance = this.getSingleton(beanName);
                                        if (singletonInstance instanceof SmartInitializingSingleton) {
                                            SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton)singletonInstance;
                                            if (System.getSecurityManager() != null) {
                                                AccessController.doPrivileged(() -> {
                                                    smartSingleton.afterSingletonsInstantiated();
                                                    return null;
                                                }, this.getAccessControlContext());
                                            } else {
                                                smartSingleton.afterSingletonsInstantiated();
                                            }
                                        }
                                    }

                                    return;
                                }

                                beanName = (String)var2.next();
                                bd = this.getMergedLocalBeanDefinition(beanName);
                            } while(bd.isAbstract());
                        } while(!bd.isSingleton());
                    } while(bd.isLazyInit());

                    if (this.isFactoryBean(beanName)) {
                        bean = this.getBean("&" + beanName);
                        break;
                    }

                    this.getBean(beanName);
                }
            } while(!(bean instanceof FactoryBean));

            FactoryBean<?> factory = (FactoryBean)bean;
            boolean isEagerInit;
            if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
                SmartFactoryBean var10000 = (SmartFactoryBean)factory;
                ((SmartFactoryBean)factory).getClass();
                isEagerInit = (Boolean)AccessController.doPrivileged(var10000::isEagerInit, this.getAccessControlContext());
            } else {
                isEagerInit = factory instanceof SmartFactoryBean && ((SmartFactoryBean)factory).isEagerInit();
            }

            if (isEagerInit) {
                this.getBean(beanName);
            }
        }
    }


```





### AOP（Aspect Oriented Programming）

面向切面编程，在不修改源码的前提下进行功能增强。

在程序运行期间，将某段代码动态切入到方法的指定位置。

#### JDK动态代理

```java
public class ProxyTest {
    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        // 创建动态代理对象
        // 
        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(calculator.getClass().getClassLoader(),
                calculator.getClass().getInterfaces(),
                ((proxy, method, args1) -> {
                    System.out.println("args=" + Arrays.toString(args1));
                    // calculator 被代理对象
                    // args1 方法入参
                    // 必须调用method.invoke，否则不会调用目标方法
                    // method.invoke的第一个参数必须是目标对象，
                    // 如果传入proxy会循环调用InvocationHandler.invoke，导致栈溢出
                    return method.invoke(calculator, args1);
                }));
        // 动态代理对象实现了目标对象的所有接口，可以进行类型转换
        System.out.println(proxyInstance.add(1, 1));

    }
}
// 被代理对象的类加载器
// 被代理对象实现的接口列表
// InvocationHandler
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
    {}

public interface InvocationHandler {

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param   proxy the proxy instance that the method was invoked on
     *
     * @param   method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param   args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return  the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     */
    // proxy 代理对象
    // 目标方法
    // 方法入参
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}

```

```java
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int i, int j) {
        return i + j;
    }

    @Override
    public int sub(int i, int j) {
        return i - j;
    }

    @Override
    public int mul(int i, int j) {
        return i * j;
    }

    @Override
    public int div(int i, int j) {
        return i / j;
    }
}

public interface Calculator {
    int add(int i, int j);

    int sub(int i, int j);

    int mul(int i, int j);

    int div(int i, int j);
}
```

局限：

- 目标对象必须实现至少一个接口，否则无法使用JDK的动态代理

- 使用起来比较复杂





![image-20220601175519002](\picture\image-20220601175519002.png)





![image-20220515223452360](\picture\image-20220515223452360.png)

![image-20220515223556530](\picture\image-20220515223556530.png)

https://www.jianshu.com/p/2e8409bc8c3b

#### 通知类型：

@Before，前置通知

```java
    /**
     * 方法执行前执行
     * joinPoint 可以省略
     */
    @Before("execution(public * com.hyh..BaseService.getSome(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println("before");
        System.out.println(joinPoint.getTarget());
    }
```



@AfterReturning，后置通知

```java
    /**
     * 方法返回后执行
     * JoinPoint 必须是第一个或者省略
     * returning 用来就收方法返回值的 参数名，入参名
     */
    @AfterReturning(value = "execution(public * com.hyh..BaseService.getSome(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("after returning");
        System.out.println(joinPoint);
        System.out.println("result=" + result);
    }
```



@Around，环绕通知

```java
    /**
     * 可以实现其他所有通知的功能
     * 通过 ProceedingJoinPoint  推进目标方法的执行
     */
    @Around("execution(public * com.hyh..BaseService.getSome(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println(joinPoint.getSignature());
        // 利用反射执行目标方法
        joinPoint.proceed();
        // 可以修改目标方法的返回值
        return "1";
    }
```



@AfertThrowing，异常通知

```java
    /**
     * 方法异常后执行
     */
    @AfterThrowing(value = "execution(public * com.hyh..BaseService.getSome(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, RuntimeException e) {
        System.out.println("after throwing");
        System.out.println(joinPoint);
        System.out.println(e);
    }
```



@After，最终通知

```java
    @Around("execution(public * com.hyh..BaseService.getSome(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println(joinPoint.getSignature());
        // 利用反射执行目标方法
        //前置通知
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            // 异常通知
        } finally {
            //最终通知
        }
        //返回通知
        // 可以修改目标方法的返回值
        return proceed;
    }
```



#### 切入点表达式：

execution([权限修饰符] [返回值类型] [简单类名/全类名] \[方法名\](\[参数列表\]))

`*`：匹配任何数量字符，在参数列中匹配一个任意类型参数，在包名中匹配任意一层子包，权限修饰符不能用

`..`：匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数。

`+`：匹配指定类型的子类型；仅能作为后缀放在类型模式后边。

组合切入点表达式

​    AspectJ使用 且（&&）、或（||）、非（！）来组合切入点表达式。

​    在Schema风格下，由于在XML中使用“&&”需要使用转义字符“&&”来代替之，所以很不方便，因此Spring ASP 提供了and、or、not来代替&&、||、！。



```Java
package com.hyh.aop.aspcect;

import com.hyh.ioc.setinjection.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.Date;

/**
 * 切面类注解，表明当前类是切面类
 *
 * @author : huang.yaohua
 * @date : 2022/5/15 22:56
 */
@Aspect
public class MyAspect {


    /**
     * Before注解：
     * 属性value切入点表达式，表示切面的执行位置
     * 前置通知方法的定义
     * 方法可以有参数，如果有是JoinPoint，也可以没有
     * 在目标方法之前执行
     * 不会影响目标方法的执行
     * 不会修改目标方法的执行结果
     * before:前置通知(应用：各种校验)：在方法执行前执行，如果通知抛出异常，阻止方法运行
     */
    @Before("execution(public String com.hyh.aop.service.impl.OrderServiceImpl.createOrder())")
    public void before() {
        //切面代码
        System.out.println("Before");

    }

    /**
     * after:最终通知(应用：清理现场)：方法执行完毕后执行，无论方法中是否出现异常
     * joinPoint 必须是第一个参数
     * 获取被执行方法的信息
     *
     * @param joinPoint 必须是第一个参数
     */
    @After("execution(String com.hyh.aop.service.impl.OrderServiceImpl.createOrder(..))")
    public void after(JoinPoint joinPoint) {
        //切面代码
//        System.out.println(joinPoint.getSignature());
//        System.out.println(joinPoint.getKind());
//        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println("After");

    }

    /**
     * 在after后执行
     * 在目标方法后执行
     * 能获取到目标方法的执行结果
     * 不会影响目标方法的执行
     * 方法有参数，用于接收方法返回值
     * 属性：
     * value 切入点表达式
     * returning 自定义变量，表示目标方法的返回值
     * 自定义变量名必须和通知方法的形参名一样
     */
    @AfterReturning(value = "execution(* *..OrderServiceImpl.createStudent(..))", returning = "student")
    public void afterReturning(Student student) {
        //切面代码
        System.out.println("AfterReturning");
        System.out.println("After returning student=" + student);
        //引用类型的返回值，在这里修改会影响目标方法最终的返回值 
        student.setStudent("hyh aft");
    }

    @AfterReturning(value = "execution(* *..OrderServiceImpl.createOrder(..))", returning = "order")
    public void afterReturning2(Object order) {
        //切面代码
        System.out.println("AfterReturning");
        System.out.println("After returning order=" + order);
    }

    /**
     * 可以有返回值，返回值是目标方法的返回值，或通知方法内返回值
     * around:环绕通知(应用：十分强大，可以做任何事情)：可以阻止方法的执行，必须手动执行目标方法
     */
    @Around("execution(String com.hyh.aop.service.impl.OrderServiceImpl.createOrder(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //切面代码
        System.out.println("around before");
        // 不调用proceed不会调用目标方法，可以控制是否执行目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("around proceed=" + proceed);
        System.out.println("around after");
        // 可以修改目标方法的返回值
        if (null != proceed) {
            return "2";
        }
        return proceed;
    }

    /**
     * 异常通知
     * 目标方法发生异常后执行否则不会执行
     * 不是异常处理程序，可以得到发生异常的通知，目标是监控目标方法的异常
     * value 切入点表达式
     * throwing 自定义变量，表示目标方法抛出的异常，变量名必须和通知方法的形参名一样
     */
    @AfterThrowing(value = "studentPointcut()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        System.out.println("AfterThrowing ex=" + ex.getMessage());
        // 异常发生时，可以记录异常信息
        // 发送短信、邮件通知
    }

    /**
     * Pointcut定义和管理切入点，不是通知注解
     * 放在方法上面，这个方法相当于切入点表达式的别名
     * 其他通知注解可以使用这个方法名，表明使用了这个切入点表达式
     * 属性：
     * value 切入点表达式
     * 相当于
     */
    @Pointcut("execution(* *..OrderServiceImpl.createStudent(..))")
    private void studentPointcut() {
        //无需代码
    }
}
```

![image-20220516232003980](\picture\image-20220516232003980.png)

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



#### spring事务管理器



![image-20220517232018869](\picture\image-20220517232018869.png)

#### 事务管理器工作方式

![image-20220517232246845](\picture\image-20220517232246845.png)

- spring事务使用的AOP的环绕通知

环绕通知：可以在目标方法执行前后都能增强功能

![image-20220517232745350](\picture\image-20220517232745350.png)



#### 事务定义接口（TransactionDefinition）

##### 隔离级别

- 默认（使用数据库默认事务隔离级别mysql 默认 可重复读，oralce默认读已提交）

- 读未提交（read uncommitted）

- 读已提交

- 可重复读

- 串行化

##### 传播行为

`REQUIRED`（有事务则加入，没有则创建）是spring事务的默认方式

`REQUIRES_NEW`（新事务，有事务就创建新事务）

`NESTED`（嵌套事务，有事务就创建子事务，子事务不会影响外层事务，外层事务会影响子事务）

`SUPPORTS`（支持事务，有没有都可以）这个传播行为和不写没多大区别，以后有这需求，可以不用写`@Transactional`

`NOT_SUPPORTED`（不支持事务，有事务也是以非事务方式执行）

`MANDATORY`（必须有事务，没有就抛异常）

`NEVER`（不可能有事务，有事务就抛异常）

![image-20220517231935155](\picture\image-20220517231935155.png)



https://blog.csdn.net/weixin_43072970/article/details/107756796

##### 事务超时（单位秒）

超时时间，默认-1

表示一个事务最长的执行时间，超时则回滚事务

#### 使用Transactional控制事务

![image-20220518223434591](\picture\image-20220518223434591.png)

![image-20220518223530609](\picture\image-20220518223530609.png)

jdbcTemplate、mybatis使用DataSourceTransactionManager

hibernate使用HibernateTransactionManager



![image-20220518223913589](\picture\image-20220518223913589.png)

事务控制模式：声明式、编程式

![image-20220518224752984](\picture\image-20220518224752984.png)

#### 使用AspectJ框架控制事务

![image-20220518232102954](\picture\image-20220518232102954.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
    <context:property-placeholder location="classpath:transaction/jdbc.properties"/>

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
    <!-- 事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--声明业务方法事务属性（隔离级别、传播行为、超时时间）-->
    <tx:advice id="serviceAdvice">
        <tx:attributes>
            <!-- name 业务方法名称：1.使用方法全名、2.使用带部分通配符的方法名、3.使用“*”           -->
            <tx:method name="get*" isolation="DEFAULT" propagation="REQUIRED" timeout="-1"
                       rollback-for="java.lang.NullPointerException,java.lang.IndexOutOfBoundsException"/>
            <tx:method name="set*" isolation="DEFAULT" propagation="REQUIRES_NEW" timeout="-1"
                       rollback-for="java.lang.NullPointerException,java.lang.IndexOutOfBoundsException"/>
            <!--除上述之外的方法使用以下事务配置-->
            <tx:method name="*" isolation="DEFAULT" propagation="REQUIRES_NEW" timeout="-1"
                       rollback-for="java.lang.NullPointerException,java.lang.IndexOutOfBoundsException"/>
        </tx:attributes>
    </tx:advice>
    <!--切入点表达式：表明包中的哪些类、类中的方法参加事务-->
    <aop:config>
        <!--   id切入点表达式的名称   唯一的  -->
        <aop:pointcut id="servicePointcut" expression="execution(* *..transaction.service..*.*(..))"/>
        <!--关联切入点表达式和事务通知-->
        <aop:advisor advice-ref="serviceAdvice" pointcut-ref="servicePointcut"/>
    </aop:config>
</beans>
```



#### 事务失效场景

https://www.cnblogs.com/konglxblog/p/16229394.html

## 一、事务方法访问修饰符非public，导致事务失效

如果事务是static、final的，同样无法通过动态代理，事务也是不会生效的。
　　Spring的声明式事务是基于动态代理实现的，我们无法重写final修饰的方法；
　　不管是JDK动态代理还是Cglib的动态代理，就是要通过代理的方式获取到代理的具体对象，而static方法修饰的方法是属于类的，不属于任何对象，所以static方法不能被重写，即便写法上是重写，但是并不具备重写的含义，也就是说static方法也不被进行动态代理。

原文链接：https://blog.csdn.net/qq_16268979/article/details/123707823

**2、解决**
方式一：将方法修饰符改为public
方式二：开启AspectJ代理模式
