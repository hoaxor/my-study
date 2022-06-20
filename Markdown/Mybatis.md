# Mybatis

## 简介

![image-20220617111228039](\picture\image-20220617111228039.png)

Mybatis是一个半自动持久化框架

`MyBatis`本是`apache`的一个开源项目`iBatis`，2010年这个项目由`apache software foundation`迁移到了`google code`，并且改名为`MyBatis`。2013年11月迁移到`Github`。

`iBATIS`一词来源于“internet”和“abatis”的组合，是一个基于`Java`的持久层框架。`iBATIS`提供的持久层框架包括`SQL Maps`和`Data Access Objects`（DAOs）。

`MyBatis` 是一款优秀的持久层框架，它支持定制化 `SQL`、存储过程以及高级映射。`MyBatis` 避免了几乎所有的 `JDBC` 代码和手动设置参数以及获取结果集。`MyBatis` 可以使用简单的 `XML` 或注解来配置和映射原生信息，将接口和 `Java` 的 `POJOs`(Plain Ordinary Java Object,普通的 Java对象)映射成数据库中的记录。



## 单独使用Mybatis

https://mybatis.org/mybatis-3/zh/getting-started.html#%E5%85%A5%E9%97%A8

导包

```xml
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
```



配置

主配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <!-- 事务管理器-->
            <transactionManager type="JDBC"/>
            <!-- 数据源-->
            <dataSource type="POOLED">
                <property name="driver" value="oracle.jdbc.OracleDriver"/>
                <property name="url" value="jdbc:oracle:thin:@10.229.19.51:9090:osstestdb"/>
                <property name="username" value="wm"/>
                <property name="password" value="qwe123#$"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!-- 引入外部Mapper-->
        <mapper resource="mapper/OmOrderMapper.xml"/>
    </mappers>
    
</configuration>
```

映射器配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--名称空间使用接口全类名-->
<mapper namespace="com.hyh.springmvcdemo.mybatis.mapper.OmOrderMapper">
    <!-- id 方法名
         resultType 返回值类型全类名-->
    <select id="getOmOrder" resultType="com.hyh.springmvcdemo.mybatis.model.OmOrder">
        select id, order_code orderCode from om_order where id = #{id}
    </select>
</mapper>
```



测试

```java
    @Test
    public void test1() throws IOException {
        // 从 XML 中构建 SqlSessionFactory
        // MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，使得从类路径或其它位置加载资源文件更加容易。
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //sqlSessionFactory.openSession 获取SqlSession实例
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OmOrderMapper mapper = session.getMapper(OmOrderMapper.class);
            OmOrder omOrder = mapper.getOmOrder(352880);
            System.out.println(omOrder);
        }
    }
```



**对命名空间的一点补充**

在之前版本的 MyBatis 中，**命名空间（Namespaces）**的作用并不大，是可选的。 但现在，随着命名空间越发重要，你必须指定命名空间。

命名空间的作用有两个，一个是利用更长的全限定名来将不同的语句隔离开来，同时也实现了你上面见到的接口绑定。就算你觉得暂时用不到接口绑定，你也应该遵循这里的规定，以防哪天你改变了主意。 长远来看，只要将命名空间置于合适的 Java 包命名空间之中，你的代码会变得更加整洁，也有利于你更方便地使用 MyBatis。

**命名解析：**为了减少输入量，MyBatis 对所有具有名称的配置元素（包括语句，结果映射，缓存等）使用了如下的命名解析规则。

- 全限定名（比如 “com.mypackage.MyMapper.selectAllThings）将被直接用于查找及使用。
- 短名称（比如 “selectAllThings”）如果全局唯一也可以作为一个单独的引用。 如果不唯一，有两个或两个以上的相同名称（比如 “com.foo.selectAllThings” 和 “com.bar.selectAllThings”），那么使用时就会产生“短名称不唯一”的错误，这种情况下就必须使用全限定名。



## SqlSessionFactoryBuilder

建造者模式，构建`SqlSessionFactory`SqlSession工厂

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。



## SqlSessionFactory

每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。SqlSession工厂获取SqlSession实例

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

## SqlSession



每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。 这个关闭操作很重要，为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中。



## Mapper

映射器是一些绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的。虽然从技术层面上来讲，任何映射器实例的最大作用域与请求它们的 SqlSession 相同。但方法作用域才是映射器实例的最合适的作用域。 也就是说，映射器实例应该在调用它们的方法中被获取，使用完毕之后即可丢弃。 映射器实例并不需要被显式地关闭。尽管在整个请求作用域保留映射器实例不会有什么问题，但是你很快会发现，在这个作用域上管理太多像 SqlSession 的资源会让你忙不过来。





## 类型别名（typeAliases）

类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写。例如：

```xml
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
  <typeAlias alias="Comment" type="domain.blog.Comment"/>
  <typeAlias alias="Post" type="domain.blog.Post"/>
  <typeAlias alias="Section" type="domain.blog.Section"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>
</typeAliases>
```

也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如：

```xml
<typeAliases>
  <package name="domain.blog"/>
</typeAliases>
```

每一个在包 `domain.blog` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `domain.blog.Author` 的别名为 `author`；若有注解，则别名为其注解值。见下面的例子：

```java
@Alias("author")
public class Author {
    ...
}
```



## 类型处理器（typeHandlers）

MyBatis 在设置预处理语句（PreparedStatement）中的参数或从结果集中取出一个值时， 都会用类型处理器将获取到的值以合适的方式转换成 Java 类型。下表描述了一些默认的类型处理器。

**提示** 从 3.4.5 开始，MyBatis 默认支持 JSR-310（日期和时间 API） 。

重写已有的类型处理器或创建你自己的类型处理器来处理不支持的或非标准的类型。 具体做法为：实现 `org.apache.ibatis.type.TypeHandler` 接口， 或继承一个很便利的类 `org.apache.ibatis.type.BaseTypeHandler`， 并且可以（可选地）将它映射到一个 JDBC 类型。比如：

```java
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ExampleTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getString(columnIndex);
  }
}
```



## 插件（plugins）

MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)



## 参数传递

![image-20220617172656883](\picture\image-20220617172656883.png)

```java
package com.hyh.springmvcdemo.mybatis.mapper;

import com.hyh.springmvcdemo.mybatis.model.OmOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author : huang.yaohua
 * @date : 2022/6/17 11:51
 */
//@Mapper()
public interface OmOrderMapper {

    //    @Select("select id, order_code orderCode from om_order where id = #{id}")
    OmOrder getOmOrder(Integer id);

    OmOrder getOmOrder2(Integer id, String orderState);

    OmOrder getOmOrder3(Map<String, Object> param);

    OmOrder getOmOrder4(OmOrder omOrder);

    OmOrder getOmOrder5(@Param("id") Integer id, @Param("order") OmOrder omOrder);

    OmOrder getOmOrder6(@Param("id") Integer id, @Param("orderState") String orderState);
}

```

JDBC 要求，如果一个列允许使用 null 值，并且会使用值为 null 的参数，就必须要指定 JDBC 类型（jdbcType）。阅读 `PreparedStatement.setNull()`的 JavaDoc 来获取更多信息。

要更进一步地自定义类型处理方式，可以指定一个特殊的类型处理器类（或别名），比如：

```xml
#{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
```

默认情况下，使用 `#{}` 参数语法时，MyBatis 会创建 `PreparedStatement` 参数占位符，并通过占位符安全地设置参数（就像使用 ? 一样），`${column}` 会被直接替换，用这种方式接受用户的输入，并用作语句参数是不安全的，会导致潜在的 SQL 注入攻击。因此，要么不允许用户输入这些字段，要么自行转义并检验这些参数。



## returnType、resultMap特殊用法

```xml
    <!--内建的类型别名 map 对应Java类型为 Map -->
    <select id="getOmOrderMap" resultType="map">
        select id, order_code orderCode, create_date createDate, order_state orderState
        from om_order
        where id = #{id,jdbcType=INTEGER}
          and order_state = #{orderState}
    </select>
    <!--查询多条，返回一个map, 主键作为map的key, 返回多条记录时，resultType、resultMap值为集合内元素类型-->
    <select id="getOmOrderMaps" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{id,jdbcType=INTEGER}
          and order_state = #{orderState}
    </select>
    <!--返回多条记录时，resultType、resultMap值为集合内元素类型-->
    <select id="getOmOrders" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{id,jdbcType=INTEGER}
          and order_state = #{orderState}
    </select>
```

```java
    /**
     * 返回值为Map，列名全大写作为Key，,mybatis使用HashMap封装结果
     */
    Map<String, Object> getOmOrderMap(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * 使用MapKey注解指定列名作为key
     */
    @MapKey("id")
    Map<Integer, OmOrder> getOmOrderMaps(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * 返回结合类型
     */
    List<OmOrder> getOmOrders(@Param("id") Integer id, @Param("orderState") String orderState);
```



