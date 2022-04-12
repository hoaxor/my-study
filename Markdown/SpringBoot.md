# SpringBoot

SpringBoot 是整合Spring技术栈的一站式框架

SpringBoot 是简化Spring 技术栈的快速开发脚手架



## 注解

### @Import <a href="https://www.cnblogs.com/zhoading/p/12194960.html">参考</a>

import只能用在类上，@Import通过快速导入的方式实现把实例加入spring的IOC容器中。

三种用法：

1. 直接填class数组。

对应的import的bean都将加入到spring容器中，这些在容器中bean名称是该类的**全类名** ，比如com.yc.类名

```java
@Import({ 类名.class , 类名.class... })
public class TestDemo {

}
```

2. ImportSelectort方式

这种方式的前提就是一个类要实现ImportSelector接口。

分析实现接口的selectImports方法中的：

- 返回值： 就是我们实际上要导入到容器中的组件全类名【**重点** 】
- 参数： AnnotationMetadata表示当前被@Import注解给标注的所有注解信息【不是重点】

> **需要注意的是selectImports方法可以返回空数组但是不能返回null，否则会报空指针异常！**

```java
public class Myclass implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.yc.Test.TestDemo3"};
    }
}
```

3. ImportBeanDefinitionRegistrar

同样是一个接口，类似于第二种ImportSelector用法，相似度80%，只不过这种用法比较自定义化注册

参数分析：

- 第一个参数：annotationMetadata 和之前的ImportSelector参数一样都是表示当前被@Import注解给标注的所有注解信息
- 第二个参数表示用于注册定义一个bean

```java
public class Myclass2 implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //指定bean定义信息（包括bean的类型、作用域...）
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(TestDemo4.class);
        //注册一个bean指定bean名字（id）
        beanDefinitionRegistry.registerBeanDefinition("TestDemo4444",rootBeanDefinition);
    }
}
```

### @Configuration







