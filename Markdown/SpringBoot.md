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



