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



### select标签

常用属性

| 属性            | 描述                                                         |
| :-------------- | :----------------------------------------------------------- |
| `id`            | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterType` | 将会传入这条语句的参数的类全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler）推断出具体传入语句的参数，默认值为未设置（unset）。 |
| parameterMap    | 用于引用外部 parameterMap 的属性，目前已被废弃。请使用行内参数映射和 parameterType 属性。 |
| `resultType`    | 期望从这条语句中返回结果的类全限定名或别名。 注意，如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身的类型。 resultType 和 resultMap 之间只能同时使用一个。 |
| `resultMap`     | 对外部 resultMap 的命名引用。结果映射是 MyBatis 最强大的特性，如果你对其理解透彻，许多复杂的映射问题都能迎刃而解。 resultType 和 resultMap 之间只能同时使用一个。 |
| `flushCache`    | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：false。 |
| `useCache`      | 将其设置为 true 后，将会导致本条语句的结果被二级缓存缓存起来，默认值：对 select 元素为 true。 |
| `timeout`       | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `fetchSize`     | 这是一个给驱动的建议值，尝试让驱动程序每次批量返回的结果行数等于这个设置值。 默认值为未设置（unset）（依赖驱动）。 |
| `statementType` | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| `resultSetType` | FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或 DEFAULT（等价于 unset） 中的一个，默认值为 unset （依赖数据库驱动）。 |
| `databaseId`    | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |
| `resultOrdered` | 这个设置仅针对嵌套结果 select 语句：如果为 true，则假设结果集以正确顺序（排序后）执行映射，当返回新的主结果行时，将不再发生对以前结果行的引用。 这样可以减少内存消耗。默认值：`false`。 |
| `resultSets`    | 这个设置仅适用于多结果集的情况。它将列出语句执行后返回的结果集并赋予每个结果集一个名称，多个名称之间以逗号分隔。 |



### insert, update 和 delete标签

数据变更语句 insert，update 和 delete 的实现非常接近：

| 属性               | 描述                                                         |
| :----------------- | :----------------------------------------------------------- |
| `id`               | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterType`    | 将会传入这条语句的参数的类全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler）推断出具体传入语句的参数，默认值为未设置（unset）。 |
| `parameterMap`     | 用于引用外部 parameterMap 的属性，目前已被废弃。请使用行内参数映射和 parameterType 属性。 |
| `flushCache`       | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：（对 insert、update 和 delete 语句）true。 |
| `timeout`          | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `statementType`    | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| `useGeneratedKeys` | （仅适用于 insert 和 update）这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系型数据库管理系统的自动递增字段），默认值：false。 |
| `keyProperty`      | （仅适用于 insert 和 update）指定能够唯一识别对象的属性，MyBatis 会使用 getGeneratedKeys 的返回值或 insert 语句的 selectKey 子元素设置它的值，默认值：未设置（`unset`）。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `keyColumn`        | （仅适用于 insert 和 update）设置生成键值在表中的列名，在某些数据库（像 PostgreSQL）中，当主键列不是表中的第一列的时候，是必须设置的。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `databaseId`       | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |



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



### 分页插件

https://pagehelper.github.io/docs/howtouse/

#### `PageHelper` 安全调用

##### 1. 使用 `RowBounds` 和 `PageRowBounds` 参数方式是极其安全的

##### 2. 使用参数方式是极其安全的

##### 3. 使用 ISelect 接口调用是极其安全的

ISelect 接口方式除了可以保证安全外，还特别实现了将查询转换为单纯的 count 查询方式，这个方法可以将任意的查询方法，变成一个 `select count(*)` 的查询方法。

##### 4. 什么时候会导致不安全的分页？

`PageHelper` 方法使用了静态的 `ThreadLocal` 参数，分页参数和线程是绑定的。

只要你可以保证在 `PageHelper` 方法调用后紧跟 MyBatis 查询方法，这就是安全的。因为 `PageHelper` 在 `finally` 代码段中自动清除了 `ThreadLocal` 存储的对象。

如果代码在进入 `Executor` 前发生异常，就会导致线程不可用，这属于人为的 Bug（例如接口方法和 XML 中的不匹配，导致找不到 `MappedStatement` 时）， 这种情况由于线程不可用，也不会导致 `ThreadLocal` 参数被错误的使用。

但是如果你写出下面这样的代码，就是不安全的用法：

```java
PageHelper.startPage(1, 10);
List<Country> list;
if(param1 != null){
    list = countryMapper.selectIf(param1);
} else {
    list = new ArrayList<Country>();
}
```

这种情况下由于 param1 存在 null 的情况，就会导致 PageHelper 生产了一个分页参数，但是没有被消费，这个参数就会一直保留在这个线程上。当这个线程再次被使用时，就可能导致不该分页的方法去消费这个分页参数，这就产生了莫名其妙的分页。

上面这个代码，应该写成下面这个样子：

```java
List<Country> list;
if(param1 != null){
    PageHelper.startPage(1, 10);
    list = countryMapper.selectIf(param1);
} else {
    list = new ArrayList<Country>();
}
```

这种写法就能保证安全。

如果你对此不放心，你可以手动清理 `ThreadLocal` 存储的分页参数，可以像下面这样使用：

```java
List<Country> list;
if(param1 != null){
    PageHelper.startPage(1, 10);
    try{
        list = countryMapper.selectAll();
    } finally {
        PageHelper.clearPage();
    }
} else {
    list = new ArrayList<Country>();
}
```

这么写很不好看，而且没有必要。

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

对于数值类型，还可以设置 `numericScale` 指定小数点后保留的位数。

```xml
#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}
```

最后，mode 属性允许你指定 `IN`，`OUT` 或 `INOUT` 参数。如果参数的 `mode` 为 `OUT` 或 `INOUT`，将会修改参数对象的属性值，以便作为输出参数返回。 如果 `mode` 为 `OUT`（或 `INOUT`），而且 `jdbcType` 为 `CURSOR`（也就是 Oracle 的 REFCURSOR），你必须指定一个 `resultMap` 引用来将结果集 `ResultSet` 映射到参数的类型上。要注意这里的 `javaType` 属性是可选的，如果留空并且 jdbcType 是 `CURSOR`，它会被自动地被设为 `ResultSet`。

```xml
#{department, mode=OUT, jdbcType=CURSOR, javaType=ResultSet, resultMap=departmentResultMap}
```



## returnType、resultMap特殊用法

#### id & result

```xml
<id property="id" column="post_id"/>
<result property="subject" column="post_subject"/>
```

这些元素是结果映射的基础。*id* 和 *result* 元素都将一个列的值映射到一个简单数据类型（String, int, double, Date 等）的属性或字段。

这两者之间的唯一不同是，*id* 元素对应的属性会被标记为对象的标识符，在比较对象实例时使用。 这样可以提高整体的性能，尤其是进行缓存和嵌套结果映射（也就是连接映射）的时候。

两个元素都有一些属性：

| 属性          | 描述                                                         |
| :------------ | :----------------------------------------------------------- |
| `property`    | 映射到列结果的字段或属性。如果 JavaBean 有这个名字的属性（property），会先使用该属性。否则 MyBatis 将会寻找给定名称的字段（field）。 无论是哪一种情形，你都可以使用常见的点式分隔形式进行复杂属性导航。 比如，你可以这样映射一些简单的东西：“username”，或者映射到一些复杂的东西上：“address.street.number”。 |
| `column`      | 数据库中的列名，或者是列的别名。一般情况下，这和传递给 `resultSet.getString(columnName)` 方法的参数一样。 |
| `javaType`    | 一个 Java 类的全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。 |
| `jdbcType`    | JDBC 类型，所支持的 JDBC 类型参见这个表格之后的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可以为空值的列指定这个类型。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的全限定名，或者是类型别名。 |

```xml
   <?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--名称空间使用接口全类名-->
<mapper namespace="com.hyh.springmvcdemo.mybatis.mapper.OmOrderMapper">
    <!--    <typeAliases>-->
    <!--        <typeAlias type="com.hyh.springmvcdemo.mybatis.model.OmOrder" alias="omOrder"/>-->
    <!--    </typeAliases>-->
    <!--级联属性的用法：POJO包含POJO使用级联属性-->
    <resultMap id="omOrderResultMap" type="omOrder">
        <id property="id" column="id"/>
        <result property="orderCode" column="order_code"/>
        <result property="createDate" column="create_date"/>
        <result property="orderState" column="order_state"/>
        <!--使用级联属性封装查询结果-->
        <result property="user.username" column="username"/>
        <result property="user.phoneNumber" column="phone_number"/>
    </resultMap>

    <!-- association 的用法一： POJO包含POJO使用 association-->
    <resultMap id="omOrderResultMap2" type="omOrder">
        <id property="id" column="id"/>
        <result property="orderCode" column="order_code"/>
        <result property="createDate" column="create_date"/>
        <result property="orderState" column="order_state"/>
        <!--使用 association 代替级联属性封装结果-->
        <association property="user" javaType="com.hyh.springmvcdemo.mybatis.model.User">
            <id property="username" column="username"/>
            <result property="phoneNumber" column="phone_number"/>
        </association>
    </resultMap>

<!--    &lt;!&ndash; association 的用法一： POJO包含POJO使用 association&ndash;&gt;
    <resultMap id="omOrderResultMap2" type="omOrder">
        <id property="id" column="id"/>
        <result property="orderCode" column="order_code"/>
        <result property="createDate" column="create_date"/>
        <result property="orderState" column="order_state"/>
        &lt;!&ndash;使用 association 代替级联属性封装omOrder.user，javaType user 属性全类名&ndash;&gt;
        <association property="user" javaType="com.hyh.springmvcdemo.mybatis.model.User">
            <id property="username" column="username"/>
            <result property="phoneNumber" column="phone_number"/>
        </association>
    </resultMap>-->

    <!-- association 的用法二： POJO包含POJO使用，分布查询-->
    <resultMap id="omOrderResultMap21" type="omOrder">
        <id property="id" column="id"/>
        <result property="orderCode" column="order_code"/>
        <result property="createDate" column="create_date"/>
        <result property="orderState" column="order_state"/>
        <!--使用 association 代替级联属性封装omOrder.user 
        使用select关联查询语句ID，查询omOrder时使用关联的SQL查询封装成user
        使用column，将指定列传递过去-->
        <association property="user" select="com.hyh.springmvcdemo.mybatis.mapper.OmOrderMapper.queryUser"
                     column="staff_id"/>
    </resultMap>


    <!--collection用法：POJO包含集合类型使用 collection-->
    <resultMap id="omOrderResultMap3" type="omOrder">
        <id property="id" column="id"/>
        <result property="orderCode" column="order_code"/>
        <result property="createDate" column="create_date"/>
        <result property="orderState" column="order_state"/>
        <!--使用 association 代替级联属性封装结果
           javaType Java类型全类名 -->
        <association property="user" javaType="com.hyh.springmvcdemo.mybatis.model.User">
            <id property="username" column="username"/>
            <result property="phoneNumber" column="phone_number"/>
        </association>
        <!--      ofType 集合内元素类型  -->
        <collection property="attrs" ofType="com.hyh.springmvcdemo.mybatis.model.OmProductAttr">
            <id property="attrId" column="character_id"/>
            <result property="attrValue" column="character_value"/>
        </collection>
    </resultMap>


    <!-- id 方法名
         resultType 返回值类型全类名
         使用#{id}就告诉 MyBatis 创建一个预处理语句（PreparedStatement）参数，
         在 JDBC 中，这样的一个参数在 SQL 中会由一个“?”来标识，并被传递到一个新的预处理语句中
         -->
    <select id="getOmOrder" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{id}
    </select>

    <select id="getOmOrder2" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{arg0}
          and order_state = #{arg1}
    </select>

    <select id="getOmOrder3" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{id}
          and order_state = #{orderState}
    </select>

    <select id="getOmOrder4" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{id}
          and order_state = #{orderState}
    </select>

    <select id="getOmOrder5" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{id}
          and order_state = #{order.orderState}
    </select>

    <select id="getOmOrder6" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state
        from om_order
        where id = #{id,jdbcType=INTEGER}
          and order_state = #{orderState}
    </select>

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

    <select id="getOmOrderAndUser" resultMap="omOrderResultMap">
        select id, order_code, create_date, order_state, 'ztesoft' username, '10086' phone_number
        from om_order
        where id = #{id,jdbcType=INTEGER}
          and order_state = #{orderState}
    </select>

    <select id="getOmOrderAndUser2" resultMap="omOrderResultMap21">
        select id, order_code, create_date, order_state, 263292 staff_id
        from om_order
        where id = #{id,jdbcType=INTEGER}
          and order_state = #{orderState}
    </select>

    <resultMap id="user" type="com.hyh.springmvcdemo.mybatis.model.User">
        <result property="phoneNumber" column="mobile_tel"/>
    </resultMap>

    <select id="queryUser" resultType="user">
        select a.username, a.mobile_tel
        from uos_staff a
        where staff_id = #{staff_id}
    </select>

    <select id="getOmOrderAndProductAttrs" resultMap="omOrderResultMap3">
        select o.id,
               order_code,
               o.create_date,
               o.order_state,
               'ztesoft' username,
               '10086'   phone_number,
               a.character_id,
               a.character_value
        from oss_iom.om_order o,
             oss_iom.om_indep_prod_order_attr a
        where id = #{id,jdbcType=INTEGER}
          and a.service_order_id = o.id
          and order_state = #{orderState}
    </select>


</mapper>
```

```java
public interface OmOrderMapper {

    /**
     * 一个参数不需要标注Param注解
     */
    //    @Select("select id, order_code orderCode from om_order where id = #{id}")
    OmOrder getOmOrder(Integer id);

    /**
     * 传入多个参数，且未指定Param注解时使用默认名arg0、arg1...获取参数值
     */
    OmOrder getOmOrder2(Integer id, String orderState);

    /**
     * 传入Map使用key获取参数值
     */
    OmOrder getOmOrder3(Map<String, Object> param);

    /**
     * 传入自定义POJO使用属性名获取参数值
     */
    OmOrder getOmOrder4(OmOrder omOrder);

    /**
     * 传入多个参数，指定Param注解时使用注解值获取参数值，简单类型直接使用注解值获取参数 POJO类型使用注解值+属性名获取参数值
     */
    OmOrder getOmOrder5(@Param("id") Integer id, @Param("order") OmOrder omOrder);

    OmOrder getOmOrder6(@Param("id") Integer id, @Param("orderState") String orderState);

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

    /**
     * 使用级联属性
     */
    List<OmOrder> getOmOrderAndUser(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * association 的用法二： 分步查询
     */
    List<OmOrder> getOmOrderAndUser2(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * collection用法：POJO包含集合类型使用 collection
     */
    List<OmOrder> getOmOrderAndProductAttrs(@Param("id") Integer id, @Param("orderState") String orderState);
}
```



## 延迟加载、按需加载



## 动态SQL

动态 SQL 是 MyBatis 的强大特性之一。如果你使用过 JDBC 或其它类似的框架，你应该能理解根据不同条件拼接 SQL 语句有多痛苦，例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL，可以彻底摆脱这种痛苦。

### if标签

![image-20220620225945066](\picture\image-20220620225945066.png)



### where标签

![image-20220620230459918](\picture\image-20220620230459918.png)

### trim标签

![image-20220620230846133](\picture\image-20220620230846133.png)

### foreach标签

![image-20220620231515563](picture\image-20220620231515563.png)



### choose标签

### OGNL

![image-20220620232223734](picture\image-20220620232223734.png)

### bind标签

### include标签

### sql标签

https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#sql

## 缓存机制

映射语句文件中的所有 select 语句的结果将会被缓存。

映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。

缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。

缓存不会定时进行刷新（也就是说，没有刷新间隔）。

缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。

缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。

### 一级缓存

默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存。SqlSession级别的缓存。

查询过的数据会保存在缓存中，下次查直接从缓存中取。（同一方法、入参相同）

```java
//一级缓存实现类
public class PerpetualCache implements Cache {

}

public interface Cache {
    String getId();

    void putObject(Object var1, Object var2);

    Object getObject(Object var1);

    Object removeObject(Object var1);

    void clear();

    int getSize();

    ReadWriteLock getReadWriteLock();
}
```

缓存key

```tex
-1694380779:3357583145:com.hyh.springmvcdemo.mybatis.mapper.OmOrderMapper.getOmOrder:0:2147483647:select id, order_code, create_date, order_state
        from om_order
        where id = ?:352880:test
```

一级缓存失效场景：

1、查询条件、查询语句相同，**使用了不同的sqlSession**

2、查询条件**不同**、查询语句相同，使用相同的sqlSession

3、在SqlSession使用期间执行了增删改，会清空缓存

4、手动清空了缓存



### 二级缓存

全局范围的缓存（namespace级别缓存），默认不开启，二级缓存是事务性的。这意味着，当 SqlSession 完成并提交时，或是完成并回滚，但没有执行 flushCache=true 的 insert/delete/update 语句时，缓存会获得更新。

使用步骤

settings配置

```xml
        <!-- 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。 true开启 二级缓存	-->
        <setting name="cacheEnabled" value="true"/>
```

mapper配置

```xml
    <!--    开启二级缓存 
        eviction 清除策略有：默认LRU
LRU – 最近最少使用：移除最长时间不被使用的对象。
FIFO – 先进先出：按对象进入缓存的顺序来移除它们。
SOFT – 软引用：基于垃圾回收器状态和软引用规则移除对象。
WEAK – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象。
flushInterval（刷新间隔）任意的正整数，以毫秒为单位。 默认情况是没有刷新间隔，缓存仅仅会在调用语句时刷新。
size 引用数目 任意正整数，要注意欲缓存对象的大小和运行环境中可用的内存资源。默认值是 1024
readOnly（只读）属性可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓存对象的相同实例。 因此这些对象不能被修改。这就提供了可观的性能提升。而可读写的缓存会（通过序列化）返回缓存对象的拷贝。 速度上会慢一些，但是更安全，因此默认值是 false-->
    <cache eviction="FIFO" flushInterval="100000" size="2048"/>
```



**注意：**

缓存POJO类需要实现`Serializable`接口，否则会抛出异常

org.apache.ibatis.cache.CacheException: Error serializing object.  Cause: java.io.NotSerializableException: com.hyh.springmvcdemo.mybatis.model.OmOrder



**缓存查询的顺序：任何时候优先从二级缓存中获取，在从一级缓存获取，最后取查数据库**

![image-20220621144658246](\picture\image-20220621144658246.png)

缓存相关配置

```xml
        <!-- 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。 true开启 二级缓存	-->
        <setting name="cacheEnabled" value="true"/>
<!-- useCache 配置select标签 是否使用二级缓存，优先级高于 cacheEnabled-->

<!--执行flushCache=true 的 insert/delete/update/select标签 会清空一级和二级缓存-->
//清空一级缓存
sqlSession.clearCache();

```



### 自定义缓存

通过实现你自己的缓存，或为其他第三方缓存方案创建适配器，来完全覆盖缓存行为。

```xml
<cache type="com.domain.something.MyCustomCache"/>
```

![image-20220621144939789](\picture\image-20220621144939789.png)

EHCache是专业的Java进程内的缓存框架

### cache-ref

对某一命名空间的语句，只会使用该命名空间的缓存进行缓存或刷新。 但你可能会想要在多个命名空间中共享相同的缓存配置和实例。要实现这种需求，你可以使用 cache-ref 元素来引用另一个缓存。

```xml
<cache-ref namespace="com.someone.application.data.SomeMapper"/>
```

## 逆向工程



