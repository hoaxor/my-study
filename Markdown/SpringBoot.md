# SpringBoot



## 概念

## 依赖管理

几乎声明了所有开发中常用依赖的依赖的版本号

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.7</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.6.7</version>
  </parent>
```

- 开发导入starter场景启动器

只要引入stater，这个场景的所有常规需要的依赖我们都自动引入

所有spring提供的stater

https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters

官方命名：`spring-boot-starter-*`

第三方命名：`thirdpartyproject-spring-boot-starter`

- 无需关注版本号，自动版本仲裁

- 可以修改版本号

## 自动配置

- 自动配置好Tomcat

  - 引入tomcat依赖
  - 配置tomcat

  ```xml
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <version>2.6.7</version>
        <scope>compile</scope>
      </dependency>
  ```

  

- 自动配置好了SpringMVC
  - 引入SpringMVC全套组件
  - 自动配置好了SpringMVC常用功能
- 自动配置web常用功能，如字符编码问题、dispatchServlet、文件上传等功能
- 主程序及其所有子包里面的组件都会被默认扫描进来
  - 可以通过`@SpringBootApplication(scanBasePackages = "com.hyh")`或添加`@ComponentScan`修改默认扫描路径
- 各种配置拥有默认值
  - 默认配置最终会绑定到某个类上，此类会在容器中创建
- 按需加载所有自动配置项
  - 引入哪些场景，这个场景的自动配置才会开启
  - SpringBoot所有自动配置都在spring-boot-autoconfigure包中

## 底层注解

### @Configuration

Spring启动时会扫描所有带@Component和@Configuration注解的类，并将这些类定义为BeanDefinition并且放到beanDefinitionMap集合中，然后Spring会针对这些BeanDefinition做一些BeanFactory的后置处理，其中有一个类ConfigurationClassPostProcessor，如果类上配置了@Configuration且属性proxyBeanMethods=true则使用Spring CGLIB生成一个对应的配置增强类，当调用@Bean注解上的方法进行实例化对象时会优先在spring容器里查找，如果存在就直接返回，否则就会走正常的Spring Bean的初始化流程

原文链接：https://blog.csdn.net/flyfhj/article/details/122770171

```java
package com.hyh.springbootdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Configuration 告诉容器这是一个配置类，同@Component 注解
 *
 * @author MLY
 */
@Configuration(proxyBeanMethods = false)
//@Component
public class MyConfig {
    /**
     * 使用bean标签给容器中添加组件
     * 以方法名作为beanId，返回类型就是组件类型，返回值就是在容器中的实例
     * 容器中的组件默认是单例
     * 属性proxyBeanMethods=true则使用Spring CGLIB生成一个对应的配置增强类，
     * 当调用@Bean注解上的方法进行实例化对象时会优先在spring容器里查找，如果存在就直接返回，
     * 否则就会走正常的Spring Bean的初始化流程
     *  Configuration的两种配置模式即：FULL和LITE
     */
    @Bean
    public LocalDate localDate() {
        return LocalDate.now();
    }

    @Bean
    public Integer integer() {
        return 1;
    }
}

```



### @Import

@Import 注解提供了类似 @Bean 注解的功能。



### @Conditional

条件装配：满足注解指定条件的则进行组件注入

```text
ConditionalOnDefaultWebSecurity (org.springframework.boot.autoconfigure.security)
ConditionalOnWarDeployment (org.springframework.boot.autoconfigure.condition)
ConditionalOnBean (org.springframework.boot.autoconfigure.condition)
Profile (org.springframework.context.annotation)
Profile (org.springframework.context.annotation)
ConditionalOnClass (org.springframework.boot.autoconfigure.condition)
ConditionalOnEnabledResourceChain (org.springframework.boot.autoconfigure.web)
ConditionalOnWebApplication (org.springframework.boot.autoconfigure.condition)
ConditionalOnRepositoryType (org.springframework.boot.autoconfigure.data)
Profile (org.springframework.context.annotation)
ConditionalOnMissingBean (org.springframework.boot.autoconfigure.condition)
    ConditionalOnMissingFilterBean (org.springframework.boot.autoconfigure.web.servlet)
ConditionalOnResource (org.springframework.boot.autoconfigure.condition)
ConditionalOnMissingClass (org.springframework.boot.autoconfigure.condition)
ConditionalOnExpression (org.springframework.boot.autoconfigure.condition)
ConditionalOnSingleCandidate (org.springframework.boot.autoconfigure.condition)
ConditionalOnNotWebApplication (org.springframework.boot.autoconfigure.condition)
ConditionalOnProperty (org.springframework.boot.autoconfigure.condition)
ConditionalOnJndi (org.springframework.boot.autoconfigure.condition)
ConditionalOnCloudPlatform (org.springframework.boot.autoconfigure.condition)
ConditionalOnJava (org.springframework.boot.autoconfigure.condition)

```



```java
```



### @ImportResource

导入beans.xml



### @ConfigurationProperties + @EnableConfigurationProperties

```java
//使用以下两种方式开启配置类
//@ConfigurationPropertiesScan(basePackages = "com.hyh.springbootdemo.model")
//@EnableConfigurationProperties(Car.class)
public class MyService{
    
}


package com.hyh.springbootdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// 单独使用ConfigurationProperties注解，此类不会被加载到容器
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "car")
public class Car {
    private String brand;

    private double price;

}

```



### @ConfigurationProperties + @Component

```java

// 使用ConfigurationProperties + Component注解将配置类加载到容器
// 可以获取到配置文件中的值
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "car")
public class Car {
    private String brand;

    private double price;

}

```



### @SpringBootApplication

```java


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    Class<?>[] exclude() default {};

    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    String[] excludeName() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackages"
    )
    String[] scanBasePackages() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackageClasses"
    )
    Class<?>[] scanBasePackageClasses() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "nameGenerator"
    )
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    @AliasFor(
        annotation = Configuration.class
    )
    boolean proxyBeanMethods() default true;
}

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Indexed
public @interface SpringBootConfiguration {
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    
}

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
}
```



## 单元测试

### JUnit5

官方文档

https://junit.org/junit5/docs/current/user-guide/
