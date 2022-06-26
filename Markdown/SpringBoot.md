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

### 1.用法

注意：@Configuration注解的配置类有如下要求：

> 1. @Configuration必须是非本地的（即不能将配置类定义在其他类的方法内部，不能是private）。
> 2. @Configuration必须有一个无参构造函数。
> 3. @Configuration不可以是final类型（没法动态代理）；
> 4. @Configuration不可以是匿名类；
> 5. 嵌套的configuration必须是静态类。

Spring启动时会扫描所有带@Component和@Configuration注解的类，并将这些类定义为BeanDefinition并且放到beanDefinitionMap集合中，然后Spring会针对这些BeanDefinition做一些BeanFactory的后置处理，其中有一个类ConfigurationClassPostProcessor，如果类上配置了@Configuration且属性proxyBeanMethods=true则使用Spring CGLIB生成一个对应的配置增强类，当调用@Bean注解上的方法进行实例化对象时会优先在spring容器里查找，如果存在就直接返回，否则就会走正常的Spring Bean的初始化流程

原文链接：https://blog.csdn.net/flyfhj/article/details/122770171

```java
package com.hyh.springbootdemo.configuration;

import com.hyh.springbootdemo.anotation.MyComponent;
import com.hyh.springbootdemo.model.Baby;
import com.hyh.springbootdemo.model.Movie;
import com.hyh.springbootdemo.model.MyModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;


/**
 * Configuration 告诉容器这是一个配置类，同@Component 注解
 * 属性proxyBeanMethods=true则使用Spring CGLIB生成一个对应的配置增强类，
 * 当调用@Bean注解上的方法进行实例化对象时会优先在spring容器里查找，如果存在就直接返回，
 * 否则就会走正常的Spring Bean的初始化流程
 * Configuration的两种配置模式即：FULL和LITE
 *
 * @author MLY
 */
@Configuration(proxyBeanMethods = true)
@Import({MyModel.class, Date.class})
//@Component
public class MyConfig {
    /**
     * 使用bean标签给容器中添加组件
     * 以方法名作为beanId，返回类型就是组件类型，返回值就是在容器中的实例
     * 容器中的组件默认是单例
     */
    @Bean
    public LocalDate localDate() {
        System.out.println("create localDate");
        return LocalDate.now();
    }

    @Bean
    public Integer integer() {
        return 1;
    }

    @Bean
    public Baby baby() {
        return new Baby();
    }

    @Bean
    public MyModel myModel() {
        MyModel myModel = new MyModel();
        // proxyBeanMethods = false，不会给配置类生成代理对象，
        // 通过this调用本类@bean方法，会重新创建bean对象实例
        // proxyBeanMethods = true，会给配置类生成代理对象，
        // 通过this调用本类@bean方法，会从容器中获取bean对象实例
        myModel.setDate(this.localDate());
        return myModel;
    }

    @Bean
    @ConditionalOnBean(name = "baby")
    public Movie movie(Baby baby) {
        Movie movie = new Movie();
        movie.setStaff(Arrays.asList(baby));
        return movie;
    }
}


```



### @Import

@Import 注解提供了类似 @Bean 注解的功能。

**@Import可以用来批量导入需要注册的各种类，如普通的类、配置类，最后完成普通类和配置类中所有bean的注册。**

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {
    Class<?>[] value();
}

```

#### @Import的value常见的有5种用法

https://www.cnblogs.com/konglxblog/p/15390688.html

- **value为普通的类**

- **value为@Configuration标注的类**

多模块场景使用，导入其他模块配置类中的所有组件

- **value为@CompontentScan标注的类**

多模块场景使用，导入其他模块所有所有扫描到的组件

- **value为ImportBeanDefinitionRegistrar接口类型**

这个接口提供了通过spring容器api的方式直接向容器中注册bean。2个默认方法，都可以用来调用spring容器api来注册bean。

```java
public interface ImportBeanDefinitionRegistrar {
    // AnnotationMetadata类型的，通过这个可以获取被@Import注解标注的类所有注解的信息。
    // BeanDefinitionRegistry类型，是一个接口，内部提供了注册bean的各种方法。
    // BeanNameGenerator类型，是一个接口，内部有一个方法，用来生成bean的名称。
    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        this.registerBeanDefinitions(importingClassMetadata, registry);
    }

    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    }
}

public interface BeanDefinitionRegistry extends AliasRegistry {
    /**
     * 注册一个新的bean定义
     * beanName：bean的名称
     * beanDefinition：bean定义信息
    */
    void registerBeanDefinition(String var1, BeanDefinition var2) throws BeanDefinitionStoreException;
    /**
     * 通过bean名称移除已注册的bean
     * beanName：bean名称
    */
    void removeBeanDefinition(String var1) throws NoSuchBeanDefinitionException;
    /**
     * 通过名称获取bean的定义信息
     * beanName：bean名称
    */
    BeanDefinition getBeanDefinition(String var1) throws NoSuchBeanDefinitionException;
    /**
     * 查看beanName是否注册过
    */
    boolean containsBeanDefinition(String var1);
    /**
     * 获取已经定义（注册）的bean名称列表
    */
    String[] getBeanDefinitionNames();
    /**
     * 返回注册器中已注册的bean数量
    */
    int getBeanDefinitionCount();
    /**
     * 确定给定的bean名称或者别名是否已在此注册表中使用
     * beanName：可以是bean名称或者bean的别名
     */
    boolean isBeanNameInUse(String var1);
}
```

使用步骤

1. 定义ImportBeanDefinitionRegistrar接口实现类，在registerBeanDefinitions方法中使用registry来注册bean
2. 使用@Import来导入步骤1中定义的类

```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //定义一个bean：Service1
        BeanDefinition service1BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(Service1.class).getBeanDefinition();
        //注册bean
        registry.registerBeanDefinition("service1", service1BeanDinition);

        //定义一个bean：Service2，通过addPropertyReference注入service1
        BeanDefinition service2BeanDinition = BeanDefinitionBuilder.genericBeanDefinition(Service2.class).
                addPropertyReference("service1", "service1").
                getBeanDefinition();
        //注册bean
        registry.registerBeanDefinition("service2", service2BeanDinition);
    }
}
```



- **value为ImportSelector接口类型**

```java
public interface ImportSelector {
    /**
     * 返回需要导入的类名的数组，可以是任何普通类，配置类（@Configuration、@Bean、@CompontentScan等标注的类）
     * @importingClassMetadata：用来获取被@Import标注的类上面所有的注解信息
     */
    String[] selectImports(AnnotationMetadata importingClassMetadata);

    @Nullable
    default Predicate<String> getExclusionFilter() {
        return null;
    }
}
```

使用步骤

1. 定义ImportSelector接口实现类，在selectImports返回需要导入的类的名称数组
2. 使用@Import来导入步骤1中定义的类

```java
public class MySelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{Cup.class.getName(), ToiletPaper.class.getName()};
    }
}

@Data
public class Cup {
    private int capacity;
    
    private String color;
}

@Data
public class ToiletPaper {
    private String name;

    private int count;
}
```



- **value为DeferredImportSelector接口类型**

**springboot中的核心功能@EnableAutoConfiguration就是靠DeferredImportSelector来实现的。**

DeferredImportSelector是ImportSelector的子接口，既然是ImportSelector的子接口，所以也可以通过@Import进行导入，这个接口和ImportSelector不同地方有两点：

1. 延迟导入
2. 指定导入的类的处理顺序

延迟导入

比如@Import的value包含了多个普通类、多个@Configuration标注的配置类、多个ImportSelector接口的实现类，多个ImportBeanDefinitionRegistrar接口的实现类，还有DeferredImportSelector接口实现类，此时spring处理这些被导入的类的时候，**会将DeferredImportSelector类型的放在最后处理，会先处理其他被导入的类，其他类会按照value所在的前后顺序进行处理**。

那么我们是可以做很多事情的，比如我们可以在DeferredImportSelector导入的类中判断一下容器中是否已经注册了某个bean，如果没有注册过，那么再来注册。

以后我们会讲到另外一个注解@Conditional，这个注解可以按条件来注册bean，比如可以判断某个bean不存在的时候才进行注册，某个类存在的时候才进行注册等等各种条件判断，通过@Conditional来结合DeferredImportSelector可以做很多事情。

当@Import中有多个DeferredImportSelector接口的实现类时候，可以指定他们的顺序，指定顺序常见2种方式

- 实现Ordered接口的方式,值越小优先级越高

- 实现Order注解的方式,值越小优先级越高

#### 总结

1. @Import可以用来批量导入任何普通的组件、配置类，将这些类中定义的所有bean注册到容器中
2. @Import常见的5种用法需要掌握
3. 掌握ImportSelector、ImportBeanDefinitionRegistrar、DeferredImportSelector的用法
4. DeferredImportSelector接口可以实现延迟导入、按序导入的功能
5. spring中很多以@Enable开头的都是使用@Import集合ImportSelector方式实现的
6. BeanDefinitionRegistry接口：bean定义注册器，这个需要掌握常见的方法

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
}


```

#### @SpringBootConfiguration

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration //当前是一个配置类
@Indexed
public @interface SpringBootConfiguration {
}
```

#### @EnableAutoConfiguration

```java
//
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
}
```

##### @AutoConfigurationPackage

```java
// 它负责保存标注相关注解的类的所在包路径。使用一个BasePackage类，保存这个路径。
// 然后使用@Import注解将其注入到ioc容器中。这样，可以在容器中拿到该路径。
// 表示对于标注该注解的类的包，应当使用Registrar注册
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({Registrar.class}) 
public @interface AutoConfigurationPackage {
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}

    static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {
        Registrar() {
        }

        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            AutoConfigurationPackages.register(registry, (String[])(new AutoConfigurationPackages.PackageImports(metadata)).getPackageNames().toArray(new String[0]));
        }

        public Set<Object> determineImports(AnnotationMetadata metadata) {
            return Collections.singleton(new AutoConfigurationPackages.PackageImports(metadata));
        }
    }
```

##### @Import({AutoConfigurationImportSelector.class})

```java
// 给容量批量导入一些组件    
    protected AutoConfigurationImportSelector.AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
        if (!this.isEnabled(annotationMetadata)) {
            return EMPTY_ENTRY;
        } else {
            AnnotationAttributes attributes = this.getAttributes(annotationMetadata);
            //获取所有需要导入到容器的一些配置类 代码见下
            List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
            configurations = this.removeDuplicates(configurations);
            Set<String> exclusions = this.getExclusions(annotationMetadata, attributes);
            this.checkExcludedClasses(configurations, exclusions);
            configurations.removeAll(exclusions);
            configurations = this.getConfigurationClassFilter().filter(configurations);
            this.fireAutoConfigurationImportEvents(configurations, exclusions);
            return new AutoConfigurationImportSelector.AutoConfigurationEntry(configurations, exclusions);
        }
    }
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
        return configurations;
    }


    public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
        ClassLoader classLoaderToUse = classLoader;
        if (classLoader == null) {
            classLoaderToUse = SpringFactoriesLoader.class.getClassLoader();
        }

        String factoryTypeName = factoryType.getName();
        return (List)loadSpringFactories(classLoaderToUse).getOrDefault(factoryTypeName, Collections.emptyList());
    }
//获取系统中所有META-INF/spring.factories
//spring-boot-autoconfigure-2.6.7.jar/META-INF/spring.factories
    private static Map<String, List<String>> loadSpringFactories(ClassLoader classLoader) {
        Map<String, List<String>> result = (Map)cache.get(classLoader);
        if (result != null) {
            return result;
        } else {
            HashMap result = new HashMap();

            try {
                Enumeration urls = classLoader.getResources("META-INF/spring.factories");

                while(urls.hasMoreElements()) {
                    URL url = (URL)urls.nextElement();
                    UrlResource resource = new UrlResource(url);
                    Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                    Iterator var6 = properties.entrySet().iterator();

                    while(var6.hasNext()) {
                        Entry<?, ?> entry = (Entry)var6.next();
                        String factoryTypeName = ((String)entry.getKey()).trim();
                        String[] factoryImplementationNames = StringUtils.commaDelimitedListToStringArray((String)entry.getValue());
                        String[] var10 = factoryImplementationNames;
                        int var11 = factoryImplementationNames.length;

                        for(int var12 = 0; var12 < var11; ++var12) {
                            String factoryImplementationName = var10[var12];
                            ((List)result.computeIfAbsent(factoryTypeName, (key) -> {
                                return new ArrayList();
                            })).add(factoryImplementationName.trim());
                        }
                    }
                }

                result.replaceAll((factoryType, implementations) -> {
                    return (List)implementations.stream().distinct().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
                });
                cache.put(classLoader, result);
                return result;
            } catch (IOException var14) {
                throw new IllegalArgumentException("Unable to load factories from location [META-INF/spring.factories]", var14);
            }
        }
    }
```

#### @ComponentScan

常用属性如下：

basePackages、value：指定扫描路径，如果为空则以@ComponentScan注解的类所在的包为基本的扫描路径

**value和basePackages不能同时存在设置，可二选一**

**指定包名的方式扫描存在的一个隐患，若包被重名了，会导致扫描会失效，一般情况下面我们使用basePackageClasses的方式来指定需要扫描的包，这个参数可以指定一些类型，默认会扫描这些类所在的包及其子包中所有的类，这种方式可以有效避免这种问题。**

basePackageClasses：指定具体扫描的类，spring容器会扫描这些类所在的包及其子包中的类

nameGenerator：自定义bean名称生成器

resourcePattern：需要扫描包中的那些资源，默认是：**/*.class，即会扫描指定包中所有的class文件

useDefaultFilters：对扫描的类是否启用默认过滤器，默认为true

includeFilters：过滤器：用来配置被扫描出来的那些类会被作为组件注册到容器中

excludeFilters：过滤器，和includeFilters作用刚好相反，用来对扫描的类进行排除的，被排除的类不会被注册到容器中

lazyInit：是否延迟初始化被注册的bean

includeFilters和excludeFilters 的FilterType可选：ANNOTATION=注解类型 默认、ASSIGNABLE_TYPE(指定固定类)、ASPECTJ(ASPECTJ类型)、REGEX(正则表达式)、CUSTOM(自定义类型)，自定义的Filter需要实现TypeFilter接口
————————————————
版权声明：本文为CSDN博主「雷X峰」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/mapleleafforest/article/details/86623578

```java
// 包扫描，指定扫描包路径
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    
}
```

1、扫描@ComponentScan注解包下面的所有的可自动装备类，生成BeanDefinition对象，并注册到beanFactory对象中
2、通过DeferredImportSelectorHandler处理@EnableAutoConfiguration注解，后续会有专文介绍
3、将带有@Configuration 注解的类解析成ConfigurationClass对象并缓存，后面创建@Bean注解的Bean对象所对应的BeanDefinition时会用到

##### 自定义Filter

有时候我们需要用到自定义的过滤器，使用自定义过滤器的步骤：

> 1.设置@Filter中type的类型为：FilterType.CUSTOM
> 2.自定义过滤器类，需要实现接口：org.springframework.core.type.filter.TypeFilter
> 3.设置@Filter中的classses为自定义的过滤器类型

`TypeFilter`

**MetadataReader接口**

> 类元数据读取器，可以读取一个类上的任意信息，如类上面的注解信息、类的磁盘路径信息、类的class对象的各种信息，spring进行了封装，提供了各种方便使用的方法。

**MetadataReaderFactory接口**

> 类元数据读取器工厂，可以通过这个类获取任意一个类的MetadataReader对象。

```java
@FunctionalInterface
public interface TypeFilter {

    boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException;
}
    // MetadataReader接口,类元数据读取器，可以读取一个类上的任意信息，如类上面的注解信息、类的磁盘路径信息、类的class对象的各种信息，spring进行了封装，提供了各种方便使用的方法。
public interface MetadataReader {
    /**
     * 返回类文件的资源引用
    */
    Resource getResource();
    /**
     * 返回一个ClassMetadata对象，可以通过这个读想获取类的一些元数据信息，如类的class对象、是否是接口、是否有注解、是否是抽象类、父类名称、接口名称、内部包含的之类列表等等，可以去看一下源码
    */
    ClassMetadata getClassMetadata();
    /**
     * 获取类上所有的注解信息
    */
    AnnotationMetadata getAnnotationMetadata();
}

public interface MetadataReaderFactory {
    /**
     * 返回给定类名的MetadataReader对象
    */
    MetadataReader getMetadataReader(String className) throws IOException;
    /**
     * 返回指定资源的MetadataReader对象
    */
    MetadataReader getMetadataReader(Resource resource) throws IOException;
}
```

##### 总结

1. @ComponentScan用于批量注册bean，spring会按照这个注解的配置，递归扫描指定包中的所有类，将满足条件的类批量注册到spring容器中
2. 可以通过value、basePackages、basePackageClasses 这几个参数来配置包的扫描范围
3. 可以通过useDefaultFilters、includeFilters、excludeFilters这几个参数来配置类的过滤器，被过滤器处理之后剩下的类会被注册到容器中
4. 指定包名的方式配置扫描范围存在隐患，包名被重命名之后，会导致扫描实现，所以一般我们在需要扫描的包中可以创建一个标记的接口或者类，作为basePackageClasses的值，通过这个来控制包的扫描范围
5. @CompontScan注解会被ConfigurationClassPostProcessor类递归处理，最终得到所有需要注册的类。

### @ConditionalOnProperty

从@Conditional(OnPropertyCondition.class)代码，可以看出ConditionalOnProperty属于@Conditional的衍生注解。生效条件由OnPropertyCondition来进行判断。

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional({OnPropertyCondition.class})
public @interface ConditionalOnProperty {
	// 数组，获取对应property名称的值，与name不可同时使用
    String[] value() default {};
    // 配置属性名称的前缀，比如spring.http.encoding
    String prefix() default "";
    // 数组，配置属性完整名称或部分名称
	// 可与prefix组合使用，组成完整的配置属性名称，与value不可同时使用
    String[] name() default {};
	// 可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
    String havingValue() default "";
	// 缺少该配置属性时是否可以加载。如果为true，没有该配置属性时也会正常加载；反之则不会生效
    boolean matchIfMissing() default false;
}
```

### @AutoConfigureAfter

@AutoConfigureBefore 和 @AutoConfigureAfter 是 spring-boot-autoconfigure 包下的注解

用途
@AutoConfigureBefore(AAAA.class) 或 AutoConfigureBefore({AAAA.class, BBBB.class})

```java
@AutoConfigureBefore(AAAA.class)
public class CCCC {
}

```

说明 CCCC 将会在 AAAA 之前加载

@AutoConfigureAfter(AAAA.class) 或 AutoConfigureAfter({AAAA.class, BBBB.class})

说明 CCCC 将会在 AAAA 之后加载

```java
@AutoConfigureAfter(AAAA.class)
public class CCCC {
}

```



## 按需开启自动配置项

按照条件装配规则`@Conditional`，按需加载配置

## 修改默认配置

```java
        @Bean
        @ConditionalOnBean({MultipartResolver.class}) // 当容器中存在MultipartResolver组件
        @ConditionalOnMissingBean(
            name = {"multipartResolver"}
        )// 容器中没有id= multipartResolver的组件
        public MultipartResolver multipartResolver(MultipartResolver resolver) {
            // 注入id=multipartResolver的组件
            return resolver;
        }
```



springboot默认会在底层配置好所有组件，但是如果用户自己配置了，以用户自己配置的优先

```java
    
    // 设置传输编码
    @Bean
    @ConditionalOnMissingBean// 容器中没有这个组件时
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.properties.getCharset().name());
        filter.setForceRequestEncoding(this.properties.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.REQUEST));
        filter.setForceResponseEncoding(this.properties.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.RESPONSE));
        return filter;
    }
```



![image-20220529232550708](\picture\image-20220529232550708.png)

## 最佳实战

![image-20220529233055317](\picture\image-20220529233055317.png)

## 配置文件

### properties

### yaml

*YAML*是"YAML Ain't a Markup Language"（YAML不是一种[标记语言](https://baike.baidu.com/item/标记语言)）的[递归缩写](https://baike.baidu.com/item/递归缩写)。在开发的这种语言时，*YAML* 的意思其实是："Yet Another Markup Language"（仍是一种[标记语言](https://baike.baidu.com/item/标记语言)），但为了强调这种语言以数据做为中心，而不是以标记语言为重点，而用反向缩略语重命名。

```java
package com.hyh.springbootdemo.model.yaml;

import com.hyh.springbootdemo.model.Car;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : huang.yaohua
 * @date : 2022/5/30 16:26
 */
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String username;

    private boolean boss;

    private Date birth;

    private Integer age;

    private Car car;

    private String[] interests;

    private List<String> pets;

    private Map<String, Object> score;

    private Set<Double> salary;

    private Map<String, List<Car>> allCars;
}

```



```yaml
hyh:
  name: hyh

#数组1 ，行内写法 
arry1: [ 1, 2, 3 ]
#数组2，
array2:
  - 4
  - 5
  - 6

person:
  # 字符串可以使用单引号或双引号
  # 单引号中的内容会被转义
  # 双引号的内容不会被转义
  username: "hyh \n mj"
  boss: false
  birth: 2022/05/30
  age: 28
  interests: [ 跑步, 打游戏 ]
  pets:
    - cat
    - dog
    - duck
  #  score: 
  #    english: 80
  #    history: 80
  #    math: 90
  score: { englist:80,math:99 }
  salary:
    - 99.1
    - 1000.2
  car:
    brand: audio
    price: 99999
  all-cars:
    byd: [ { brand: byd, price: 898 } ]
    audio:
      - { brand: a1, price: 999 }
      - { brand: a2 , price:1000 }
      
  
```



## Web开发

### 静态资源

By default, Spring Boot serves static content from a directory called `/static` (or `/public` or `/resources` or `/META-INF/resources`) in the classpath or from the root of the `ServletContext`. It uses the `ResourceHttpRequestHandler` from Spring MVC so that you can modify that behavior by adding your own `WebMvcConfigurer` and overriding the `addResourceHandlers` method.

请求进来，先找Controller看能不能处理，不能处理的所有请求都交给静态资源处理器。如果都找不到则报404、

默认情况下，资源映射在 上`/**`，但您可以使用该`spring.mvc.static-path-pattern`属性对其进行调整。例如，将所有资源重新定位到`/resources/**`可以实现如下：

### 静态资源配置原理

SpringMVC自动配置类

```java
@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnWebApplication(
    type = Type.SERVLET
)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@ConditionalOnMissingBean({WebMvcConfigurationSupport.class})
@AutoConfigureOrder(-2147483638)
@AutoConfigureAfter({DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class, ValidationAutoConfiguration.class})
public class WebMvcAutoConfiguration {
}

```

springMVC配置类适配器

```java
    @Configuration(
        proxyBeanMethods = false
    )
    @Import({WebMvcAutoConfiguration.EnableWebMvcConfiguration.class})
    @EnableConfigurationProperties({WebMvcProperties.class, WebProperties.class})
    @Order(0)
    public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer, ServletContextAware {
        
    }
```

和配置文件WebMvcProperties、WebProperties进行了绑定

配置类只有一个有参构造器，所有参数都会从容器中取

```java
// WebProperties prefix = spring.web
// WebMvcProperties prefix = spring.mvc
// ListableBeanFactory bean工厂
// ObjectProvider<HttpMessageConverters> 所有转换器
// ResourceHandlerRegistrationCustomizer 资源处理自定义器
// DispatcherServletPath 
public WebMvcAutoConfigurationAdapter(WebProperties webProperties, WebMvcProperties mvcProperties, ListableBeanFactory beanFactory, ObjectProvider<HttpMessageConverters> messageConvertersProvider, ObjectProvider<WebMvcAutoConfiguration.ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider, ObjectProvider<DispatcherServletPath> dispatcherServletPath, ObjectProvider<ServletRegistrationBean<?>> servletRegistrations) {
            this.resourceProperties = webProperties.getResources();
            this.mvcProperties = mvcProperties;
            this.beanFactory = beanFactory;
            this.messageConvertersProvider = messageConvertersProvider;
            this.resourceHandlerRegistrationCustomizer = (WebMvcAutoConfiguration.ResourceHandlerRegistrationCustomizer)resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
            this.dispatcherServletPath = dispatcherServletPath;
            this.servletRegistrations = servletRegistrations;
            this.mvcProperties.checkConfiguration();
        }
```

配置静态资源处理的默认规则

```java
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
            } else {
                // 添加webjars访问处理规则
                this.addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
                // 添加静态资源访问处理规则
                this.addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
                    registration.addResourceLocations(this.resourceProperties.getStaticLocations());
                    if (this.servletContext != null) {
                        ServletContextResource resource = new ServletContextResource(this.servletContext, "/");
                        registration.addResourceLocations(new Resource[]{resource});
                    }

                });
            }
        }
```

欢迎页处理规则映射

```java
    WelcomePageHandlerMapping(TemplateAvailabilityProviders templateAvailabilityProviders, ApplicationContext applicationContext, Resource welcomePage, String staticPathPattern) {
        if (welcomePage != null && "/**".equals(staticPathPattern)) {
            logger.info("Adding welcome page: " + welcomePage);
            this.setRootViewName("forward:index.html");
        } else if (this.welcomeTemplateExists(templateAvailabilityProviders, applicationContext)) {
            logger.info("Adding welcome page template: index");
            this.setRootViewName("index");
        }

    }
```

### SpringMVC自动配置

org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration



### 请求参数映射

前端处理器`DispatcherServlet`处理流程

获取Handler

![image-20220626221417081](\picture\image-20220626221417081.png)



获取参数

矩阵变量

springboot 默认禁用了矩阵变量功能

使用前，需要手动开启

2.6.7版本默认开启



REST

参考：

https://blog.csdn.net/qq_21383435/article/details/80032375?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7EPayColumn-1-80032375-blog-111591122.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7EPayColumn-1-80032375-blog-111591122.pc_relevant_default&utm_relevant_index=1

提出时间：2000年。 属性：一种软件架构风格。（以Web为平台的。web服务的架构风格，前后端接口时候用到。）

外文名：Representational State Transfer，简称REST。 中文名：表现层状态转移。

URL中只使用名词来定位资源，用HTTP协议里的动词（GET、POST、PUT、DELETE）来实现资源的增删改查操作。

GET    用来获取资源，
POST  用来新建资源（也可以用于更新资源），
PUT    用来更新资源，
DELETE  用来删除资源

## 单元测试

### JUnit5

官方文档

https://junit.org/junit5/docs/current/user-guide/







## 扩展

### BeanNameGenerator

spring内置了3个实现

#### DefaultBeanNameGenerator

> 默认bean名称生成器，xml中bean未指定名称的时候，默认就会使用这个生成器，默认为：完整的类名#bean编号

#### AnnotationBeanNameGenerator

> 注解方式的bean名称生成器，比如通过@Component(bean名称)的方式指定bean名称，如果没有通过注解方式指定名称，默认会将完整的类名作为bean名称。

#### FullyQualifiedAnnotationBeanNameGenerator

> 将完整的类名作为bean的名称



### BeanDefinition接口：bean定义信息

用来表示bean定义信息的接口，我们向容器中注册bean之前，会通过xml或者其他方式定义bean的各种配置信息，bean的所有配置信息都会被转换为一个BeanDefinition对象，然后通过容器中BeanDefinitionRegistry接口中的方法，将BeanDefinition注册到spring容器中，完成bean的注册操作。
