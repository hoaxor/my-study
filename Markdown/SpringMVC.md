#  SpringMVC

## 简介



![image-20220606161810761](\picture\image-20220606161810761.png)



## idea新建web工程

![image-20220606174141250](\picture\image-20220606174141250.png)

tomcat启动localhost日志中文乱码

idea64.exe.vmoptions增加

`-Dfile.encoding=UTF-8`

```properties
-Xmx3072m
-Djava.net.preferIPv4Stack=true
#-javaagent:D:\Program Files\JetBrains\IntelliJ IDEA 2019.3.3\bin\jetbrains-agent.jar
-javaagent:E:\GoogleDownload\ja-netfilter-all\ja-netfilter.jar=jetbrains
-Dfile.encoding=UTF-8
```



## RequestMapping

```
// 用在类上，访问类中方法方法时需要加上path
// 支持Ant风格URL
// ? 匹配单个字符
// * 匹配任意多个字符，任意一层路径
// ** 匹配多层路径
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {
    String name() default "";

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};
    //限定请求方式，默认处理所有请求方法，GET、PUT、DELETE、POST等
    RequestMethod[] method() default {};
    // 1.限定请求参数必须包含指定参数名的参数
    // params={"username"}，必须包含username
    // 2.限定请求参数不包含指定参数名的参数
    // params={"!username"}，必须不包含username
    // 2.限定请求参数必须包含指定参数且不能为指定值
    // params={"username!=123"}，请求参数包含username且不能等于123
    // 可以组合
    String[] params() default {};
    // 限定请求头
    String[] headers() default {};
    // 限定接受的Content-Type
    String[] consumes() default {};
    // 告诉客户端返回的Content-Type
    String[] produces() default {};
}

```



## PathVariable

```java
//映射 URL 绑定的占位符
// 带占位符的 URL 是 Spring3.0 新增的功能，该功能在SpringMVC 向 REST 目标挺进发展过程中具有里程碑的意义
// 通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中：URL 中的 {xxx} 占位符可以通过@PathVariable(“xxx“) 绑定到操作方法的入参中。
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathVariable {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default true;
}

//@PathVariable可以用来映射URL中的占位符到目标方法的参数中
@RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable("id") Integer id)
    {
        System.out.println("testPathVariable:"+id);
        return SUCCESS;
    }
```

## RequestParam

@RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）

```java
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    // 参数名
    String name() default "";
    // 是否包含该参数，默认为true，表示该请求路径中必须包含该参数，如果不包含就报错。
    boolean required() default true;
    // 默认参数值，如果设置了该值，required=true将失效，自动为false,如果没有传该参数，就使用默认值
    String defaultValue() default "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n";
}
```

## RequestHeader

@RequestHeader注解用于映射请求头数据到Controller方法的对应参数。

```java
public void queryUser(

@RequestHeader(value="Accept-Encoding",defaultValue="UTF-8") String encoding,  

@RequestHeader("Keep-Alive") long keepAlive)  {  

}  
```



## CookieValue

用来获取Cookie中的值

```java
/**
 * 验证用户信息
 * @param token
 * @return
 */
@GetMapping("verify") //直接获取cookie中的token值
public ResponseEntity<UserInfo> verifyUser(@CookieValue("LY_TOKEN") String token) {
    try {
        // 获取token信息
        UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
        // 成功后直接返回
        return ResponseEntity.ok(userInfo);
    } catch (Exception e) {
        // 抛出异常，证明token无效，直接返回401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
```

## ModelAttribute

```java
public @interface ModelAttribute {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean binding() default true;
}
```



### 1. 用在入参

运用在参数上，会将客户端传递过来的参数按名称注入到指定对象中，并且会将这个对象自动加入`ModelMap`中

用在方法的入参上依次做如下操作：

- 从隐含对象中（ModelMap）获取隐含的模型数据
- 将请求参数绑定到隐含对象（ModelMap）中
- 将隐含对象（ModelMap）传入到入参
- 将入参绑定到Model

### 2. 用在方法上

被`@ModelAttribute`注释的方法会在此`controller`的每个方法执行前被执行 ，如果有返回值，则自动将该返回值加入到`ModelMap`中

`model`属性的名称没有指定，不用显示的向`model`中放数据，返回的数据会被隐含地放入到`model`中，在 `model` 中的 `key` 为 “返回类型首字母小写”，`value` 为返回的值。 上面相当于**model.addAttribute(“user”, user)**。

和`RequestMapping`注解一样都可以通过入参接收前台提交的数据



## RequestBody



## ResponseBody



## ResponseEntity

```java
public class FileDownloadController {

    @ResponseBody
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = ((HttpServlet) request).getServletContext();
        String realPath = servletContext.getRealPath("/js/test.js");
        FileInputStream fileInputStream = new FileInputStream(realPath);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();

        return new ResponseEntity<byte[]>(bytes, HttpStatus.OK);
    }
}
```



## HttpMessageConverter

HttpMessageConverter是spring3.0新添加的接口，负责将请求信息转换为一个对象，将对象输出为响应信息

```java
public interface HttpMessageConverter<T> {
    // 可以读取的对象类型，
    boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);

    boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);

    List<MediaType> getSupportedMediaTypes();

    default List<MediaType> getSupportedMediaTypes(Class<?> clazz) {
        return !this.canRead(clazz, (MediaType)null) && !this.canWrite(clazz, (MediaType)null) ? Collections.emptyList() : this.getSupportedMediaTypes();
    }

    T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException;

    void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException;
}

```





## SpringMVC的数据响应

### 数据响应方式

#### 页面跳转

- 直接返回字符串，此种方式会将返回的字符串与视图解析器的前后缀拼接后跳转

  - 在`spring-mvc.xml`配置文件中设置如下

  ```xml
  <!--配置内部资源视图解析器-->
      <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <!--前缀-->
          <property name="prefix" value="/jsp/"></property>
          <!--后缀-->
          <property name="suffix" value=".jsp"></property>
          <!--最后拼接成完成的请求路径为：/jsp/资源视图名称.jsp-->
      </bean>
  
  ```

  ```java
  @Controller
  @RequestMapping("/user")
  public class UserController {
  
      @RequestMapping(value = "/quick",method = RequestMethod.POST,params = {"username"})
      public String save(){
          System.out.println("Controller save running....");
          return "success";
      }
  }
  
  ```

  两者结合起来的请求转发资源地址是：/jsp/success.jsp

  转发可以在前面添加`forward:`作为前缀，默认可以不写`forward:/jsp/success.jsp`，**视图解析器不会拼接前缀**

  重定向可以在前面添加`redirect:`作为前缀，例如：`redirect:/success.jsp`，**视图解析器不会拼接前缀**，会自动为页面拼接项目名。

- 通过ModelAndView对象返回

  - 通过对象中的`setViewName`设置返回视图的名称,通过`addObject`可以设置模型的携带数据

  ```java
  // 方式一：在方法中new一个ModelAndView对象
  @Controller
  @RequestMapping("/user")
  public class UserController {
      @RequestMapping(value = "/quick2")
      public ModelAndView save2(){
          /*
           Model：模型，封装数据
           View：视图，展示页面
           */
          ModelAndView modelAndView = new ModelAndView();
          // 设置模型数据
          modelAndView.addObject("username","zhangsan");
          // 设置返回的视图名称
          modelAndView.setViewName("success");
          return modelAndView;
      }
  }
  
  // 方式二：通过方法来接收ModelAndView对象并返回，框架自动注入modelAndView对象，
  @Controller
  @RequestMapping("/user")
  public class UserController {
      @RequestMapping(value = "/quick3")
      public ModelAndView save3(ModelAndView modelAndView){
          // 设置模型数据
          modelAndView.addObject("username","书写形式2");
          // 设置返回的视图名称
          modelAndView.setViewName("success");
          return modelAndView;
      }
  }
  
  // 方式三：模型与视图拆开，入参可以是Model、ModelMap、Map类型，往入参中写入的数据可以在页面获取到
  @Controller
  @RequestMapping("/user")
  public class UserController {
      @RequestMapping(value = "/quick4")
      public String save4(Model model){
          // 模型数据添加
          model.addAttribute("username","形式3");
          return "success";
      }
  }
  
  // 方式四：视图HttpServletRequest存储在作用域里面
  /**
   * 存储数据到作用域里面
   * 不常用
   * @param request
   * @return
   */
  @RequestMapping(value = "/quick5")
  public String save5(HttpServletRequest request){
      request.setAttribute("username","存储到作用域里面");
      return "success";
  }
  
  ```

#### 回显数据

- 直接返回字符串

  - 通过`SpringMVC`框架注入的`response`对象，使用`response.getWriter().print(“Hello World”)`回写数据，此时不需要视图跳转，业务返回方法值为`void`

  ```java
  @Controller
  @RequestMapping("/user")
  public class UserController {
      /**
       * 不返回视图直接请求打印
       * @param response
       * @throws IOException
       */
      @RequestMapping(value = "/quick6")
      public void save6(HttpServletResponse response) throws IOException {
          response.getWriter().print("HelloWorld");
      }
  }
  
  ```

  - 将需要回写的字符串直接返回，但此时需要通过`@ResponseBody`注解告知`SpringMVC`框架，方法返回的字符串不是跳转是直接在`http`响应体中返回。通常回写`json`格式字符串

  ```java
  @Controller
  @RequestMapping("/user")
  public class UserController {
  	/**
       * 通过 @ResponseBody注解告知springmvc框架改方法不进行页面跳转，直接返回字符串
       * @return
       * @throws IOException
       */
      @RequestMapping(value = "/quick7")
      @ResponseBody
      public String save7() throws IOException {
          return  "HelloWorld2";
      }
  }
  ```

  - 返回对象或集合

  在`spring-mvc.xml`核心配置文件中配置，不然不会起效

  ```xml
  <!--配置处理器映射器-->
      <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
          <property name="messageConverters">
              <list>
                  <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
              </list>
          </property>
      </bean>
  
   <mvc:annotation-driven/>
  
  ```

  ```java
  @Controller
  @RequestMapping("/user")
  public class UserController {
  	/**
       * 根据springmvc框架来进行转换
       * @return
       * @throws IOException
       */
      @RequestMapping(value = "/quick10")
      @ResponseBody
      public User save10() throws IOException {
          User user = new User();
          user.setUsername("zhangsan");
          user.setAge(18);
          return  user;
      }
  }
  
  ```

  

## DisptacherServlet

![image-20220608230748457](\picture\image-20220608230748457.png)

```java
//核心代码    
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerExecutionChain mappedHandler = null;
        boolean multipartRequestParsed = false;
        // 异步处理器
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

        try {
            try {
                ModelAndView mv = null;
                Object dispatchException = null;

                try {
                    // 检查是否文件上传
                    processedRequest = this.checkMultipart(request);
                    multipartRequestParsed = processedRequest != request;
                    // 根据请求地址、请求方法等获取处理器
                    mappedHandler = this.getHandler(processedRequest);
                    // 没有找到处理器，抛出404异常
                    if (mappedHandler == null) {
                        this.noHandlerFound(processedRequest, response);
                        return;
                    }
                    // 获取适配器，反射工具
                    HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler());
                    String method = request.getMethod();
                    boolean isGet = HttpMethod.GET.matches(method);
                    if (isGet || HttpMethod.HEAD.matches(method)) {
                        long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                        if ((new ServletWebRequest(request, response)).checkNotModified(lastModified) && isGet) {
                            return;
                        }
                    }
                    // 
                    if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                        return;
                    }
                    // 用适配器执行目标方法，将执行后的结果封装成ModelAndView对象
                    mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
                    if (asyncManager.isConcurrentHandlingStarted()) {
                        return;
                    }
                    // 应用默认行为
                    this.applyDefaultViewName(processedRequest, mv);
                    // 根据目标方法的执行结果，完成页面跳转
                    mappedHandler.applyPostHandle(processedRequest, response, mv);
                } catch (Exception var20) {
                    dispatchException = var20;
                } catch (Throwable var21) {
                    dispatchException = new NestedServletException("Handler dispatch failed", var21);
                }

                this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
            } catch (Exception var22) {
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, var22);
            } catch (Throwable var23) {
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, new NestedServletException("Handler processing failed", var23));
            }

        } finally {
            if (asyncManager.isConcurrentHandlingStarted()) {
                if (mappedHandler != null) {
                    mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
                }
            } else if (multipartRequestParsed) {
                this.cleanupMultipart(processedRequest);
            }

        }
    }

```



#### 九大组件

DispatcherServlet初始化组件代码

```java
    protected void initStrategies(ApplicationContext context) {
        this.initMultipartResolver(context);
        this.initLocaleResolver(context);
        this.initThemeResolver(context);
        this.initHandlerMappings(context);
        this.initHandlerAdapters(context);
        this.initHandlerExceptionResolvers(context);
        this.initRequestToViewNameTranslator(context);
        this.initViewResolvers(context);
        this.initFlashMapManager(context);
    }
```



## 视图解析分析

- 对于控制器的目标方法，无论其返回值是`String`、`View`、`ModelMap`或是`ModelAndView`，`SpringMVC`都会在内部将它们封装为一个`ModelAndView`对象进行返回。

- `SpringMVC`借助视图解析器`ViewResolver`得到最终的视图对象`View`，最终的视图可以是`JSP`也可是`Excel`、
  `JFreeChart`等各种表现形式的视图。

解析流程

1、调用目标方法，SpringMVC将目标方法返回的`String`、`View`、`ModelMap`或是`ModelAndView`都转换为一个`ModelAndView`对象；

2、然后通过视图解析器`ViewResolver`对`ModelAndView`对象中的`View`对象进行解析，将该逻辑视图`View`对象解析为一个物理视图`View`对象；

3、最后调用物理视图View对象的render()方法进行视图渲染，得到响应结果。


原文链接：https://blog.csdn.net/xiangwanpeng/article/details/53144002

```java
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv, @Nullable Exception exception) throws Exception {
        boolean errorView = false;
        // 判断是否发生异常
        if (exception != null) {
            if (exception instanceof ModelAndViewDefiningException) {
                this.logger.debug("ModelAndViewDefiningException encountered", exception);
                mv = ((ModelAndViewDefiningException)exception).getModelAndView();
            } else {
                Object handler = mappedHandler != null ? mappedHandler.getHandler() : null;
                mv = this.processHandlerException(request, response, handler, exception);
                errorView = mv != null;
            }
        }

        if (mv != null && !mv.wasCleared()) {
            // 页面渲染
            this.render(mv, request, response);
            if (errorView) {
                WebUtils.clearErrorRequestAttributes(request);
            }
        } else if (this.logger.isTraceEnabled()) {
            this.logger.trace("No view rendering, null ModelAndView returned.");
        }

        if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
            if (mappedHandler != null) {
                mappedHandler.triggerAfterCompletion(request, response, (Exception)null);
            }

        }
    }
//渲染
    protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale locale = this.localeResolver != null ? this.localeResolver.resolveLocale(request) : request.getLocale();
        response.setLocale(locale);
        //视图名，方法返回值
        String viewName = mv.getViewName();
        View view;
        if (viewName != null) {
            // 根据视图名获取视图对象，遍历容器中获取的视图解析器，用视图解析器解析获取视图
            // 
            view = this.resolveViewName(viewName, mv.getModelInternal(), locale, request);
            if (view == null) {
                throw new ServletException("Could not resolve view with name '" + mv.getViewName() + "' in servlet with name '" + this.getServletName() + "'");
            }
        } else {
            view = mv.getView();
            if (view == null) {
                throw new ServletException("ModelAndView [" + mv + "] neither contains a view name nor a View object in servlet with name '" + this.getServletName() + "'");
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Rendering view [" + view + "] ");
        }

        try {
            if (mv.getStatus() != null) {
                response.setStatus(mv.getStatus().value());
            }
            // 视图对象渲染
            // InternalResourceView
            view.render(mv.getModelInternal(), request, response);
        } catch (Exception var8) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Error rendering view [" + view + "]", var8);
            }

            throw var8;
        }
    }

```



### 视图解析器ViewResolver

原文链接：https://blog.csdn.net/xiangwanpeng/article/details/53144002

视图解析器的作用是将逻辑视图转为物理视图，所有的视图解析器都必须实现`ViewResolver`接口。

SpringMVC为逻辑视图名的解析提供了不同的策略，可以在Spring WEB上下文中配置一种或多种解析策略，并指定他们之间的先后顺序。每一种映射策略对应一个具体的视图解析器实现类。程序员可以选择一种视图解析器或混用多种视图解析器。可以通过order属性指定解析器的优先顺序，order越小优先级越高，SpringMVC会按视图解析器顺序的优先顺序对逻辑视图名进行解析，直到解析成功并返回视图对象，否则抛出ServletException异常。

**自定义视图解析器需要实现spring提供的`org.springframework.core.Ordered`接口，调整视图解析器的优先级，否则会被默认视图解析器解析**

### 视图View

视图的作用是渲染模型数据，将模型里的数据以某种形式呈现给客户。

为了实现视图模型和具体实现技术的解耦，`Spring`在`org.springframework.web.servlet`包中定义了一个高度抽象的`View`接口。
视图对象由视图解析器负责实例化。由于视图是无状态的，所以他们不会有线程安全的问题。所谓视图是无状态的，是指对于每一个请求，都会创建一个`View`对象。
`JSP`是最常见的视图技术。

### mvc:view-controller

不经控制器直接跳转到页面，可以使用mvc:view-controller标签实现：

```xml
　　<!-- 配置直接转发的页面 -->      
<!-- 可以直接相应转发的页面, 而无需再经过 Handler 的方法.  -->  
    <mvc:view-controller path="/success" view-name="success"/>  
    <!-- 在实际开发中通常都需配置 mvc:annotation-driven 标签，  之前的页面才不会因为配置了直接转发页面而受到影响 -->  
    <mvc:annotation-driven></mvc:annotation-driven>  
```



## 数据绑定流程

- Spring MVC将`ServletRequest`对象传递给`DataBinder`（通过WebDataBinderFactory创建）。
- 控制类的处理方法将形参对象传递给`DataBinder`
- ``DataBinder`调用`ConversionService`组件，使用转换服务内部的转换器`converter`进行数据类型的转换工作。
- 然后将`ServletRequest`对象中的请求参数填充到方法参数对象中。对绑定好的数据会进行数据合法性检验
- 检验完成后生成数据绑定结果`BindingResult`对象（绑定结果是否异常）。最后`Spring MVC`将`BinderResult`对象中的内容赋值给处理方法的相应参数

原文链接：https://blog.csdn.net/weixin_44949462/article/details/119857384

![在这里插入图片描述](\picture\watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDk0OTQ2Mg==,size_16,color_FFFFFF,t_70.png)

### 自定义转换器

自定义转换器，实现`Converter`接口

```java
public class MyConverter implements Converter<String, User> {

    @Override
    public User convert(String source) {
        String[] split = source.split(";");
        User user = new User();
        user.setName(split[0]);
        user.setAge(Integer.parseInt(split[1]));
        return user;
    }
}
```



配置

```xml
    <!--通过 ConversionServiceFactoryBean  获取 conversionService
    自定义 conversionService
        通过属性注入自定义的转换器-->
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="com.hyh.springmvcdemo.converter.MyConverter"/>
            </set>
        </property>
    </bean>
    <!--通过配置conversion-service="conversionService" 使用自定义的conversionService-->
    <mvc:annotation-driven conversion-service="conversionService"/>

```



## mvc:annotation-driven

`<mvc:annotation-driven />` 是一种简写形式，完全可以手动配置替代这种简写形式，简写形式可以让初学都快速应用默认配置方案。

配置后会自动注册以下组件：

`RequestMappingHandlerMapping`

`RequestMappingHandlerAdapter`

`ExceptionHandlerExceptionResolver`



还提供以下支持：

- 支持使用`ConversionService`实例对表单参数进行类型转换
- 支持使用`NumberFormat`、`DateTimeFormat`注解完成数据类型的格式化
- 支持使用`Valid`注解对`JavaBean`实例进行`JSR 303`验证
- 支持使用`RequestBody`和`ResponseBody`注解

`org\springframework\spring-webmvc\5.3.12\spring-webmvc-5.3.12.jar!\META-INF\spring.handlers`

```properties
http\://www.springframework.org/schema/mvc=org.springframework.web.servlet.config.MvcNamespaceHandler

```

```java
public class MvcNamespaceHandler extends NamespaceHandlerSupport {
    public MvcNamespaceHandler() {
    }

    public void init() {
        //  annotation-driven 配置对应的解析类 AnnotationDrivenBeanDefinitionParser 
        this.registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
        this.registerBeanDefinitionParser("default-servlet-handler", new DefaultServletHandlerBeanDefinitionParser());
        this.registerBeanDefinitionParser("interceptors", new InterceptorsBeanDefinitionParser());
        this.registerBeanDefinitionParser("resources", new ResourcesBeanDefinitionParser());
        this.registerBeanDefinitionParser("view-controller", new ViewControllerBeanDefinitionParser());
        this.registerBeanDefinitionParser("redirect-view-controller", new ViewControllerBeanDefinitionParser());
        this.registerBeanDefinitionParser("status-controller", new ViewControllerBeanDefinitionParser());
        this.registerBeanDefinitionParser("view-resolvers", new ViewResolversBeanDefinitionParser());
        this.registerBeanDefinitionParser("tiles-configurer", new TilesConfigurerBeanDefinitionParser());
        this.registerBeanDefinitionParser("freemarker-configurer", new FreeMarkerConfigurerBeanDefinitionParser());
        this.registerBeanDefinitionParser("groovy-configurer", new GroovyMarkupConfigurerBeanDefinitionParser());
        this.registerBeanDefinitionParser("script-template-configurer", new ScriptTemplateConfigurerBeanDefinitionParser());
        this.registerBeanDefinitionParser("cors", new CorsBeanDefinitionParser());
    }
}
```

```java
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		......
        // 定义 RequestMappingHandlerMapping
		RootBeanDefinition handlerMappingDef = new RootBeanDefinition(RequestMappingHandlerMapping.class);
		handlerMappingDef.setSource(source);
		handlerMappingDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		handlerMappingDef.getPropertyValues().add("order", 0);
		handlerMappingDef.getPropertyValues().add("contentNegotiationManager", contentNegotiationManager);
        .....
        // 注册 RequestMappingHandlerMapping 类型对应的 RootBeanDefinition
		readerContext.getRegistry().registerBeanDefinition(HANDLER_MAPPING_BEAN_NAME , handlerMappingDef);

		
        // 定义 RequestMappingHandlerAdapter
		RootBeanDefinition handlerAdapterDef = new RootBeanDefinition(RequestMappingHandlerAdapter.class);
	   	......
        // 注册 RequestMappingHandlerAdapter 对应的 RootBeanDefinition
		readerContext.getRegistry().registerBeanDefinition(HANDLER_ADAPTER_BEAN_NAME , handlerAdapterDef);

```



```java
public interface BeanDefinitionParser {
    @Nullable
    BeanDefinition parse(Element var1, ParserContext var2);
}
```



### mvc:default-servlet-handler

使用`tomcat` 默认的`Servlet`来响应静态文件, 需要搭配`mvc:annotation-driven` 使用，否则动态请求无法响应



### JSR303数据校验

![image-20220615161221202](\picture\image-20220615161221202.png)

#### hibernate-validator 使用

`hibernate-validator`是对JSR303`的实现

```java
@Data
public class User {
    private int age;

    @NotEmpty
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @NumberFormat(style = NumberFormat.Style.DEFAULT, pattern = ".##")
    private double wage;

    @Email()
    private String email;
    
    
}

     /**
     * user 简单java对象未标注ModelAttribute默认会从隐含模型中获取，name为类名首字符小写
     * 否则会使用ModelAttribute指定的name,从ModelMap中获取
     * 使用Valid 开启校验
     * 入参中增加 BindingResult 用来接收参数校验结果
     * 
     */
    @RequestMapping("/hello3")
    @ResponseBody
    private User hello3(@Valid User user, String msg, BindingResult bindingResult, ModelMap map) {
        System.out.println("hello3");
        System.out.println(map.getAttribute("s"));
        System.out.println(msg);
        System.out.println(bindingResult);
        return user;
    }
```



## 拦截器

`SpringMVC`提供了拦截器机制，允许在运行目标方法之前进行一些拦截工作

```java
// 拦截器顶级接口
public interface HandlerInterceptor {
    // 目标方法之前运行，返回值用来判断是否执行目标方法
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    // 目标方法之后运行
    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    // 在整个响应完成之后，chan.doFilter() 资源响应之后
    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
```

```xml
    <!--设置拦截器-->
    <mvc:interceptors>
        <!--配置单个拦截器， 拦截所有请求-->
        <!--        <bean id="interceptorTest" class="com.hyh.springmvcdemo.interceptor.InterceptorTest"/>-->
        <!-- 配置单个拦截器， 拦截指定路径请求 -->
        <mvc:interceptor>
            <mvc:mapping path="/interceptor1"/>
            <bean id="interceptorTest" class="com.hyh.springmvcdemo.interceptor.InterceptorTest"/>
        </mvc:interceptor>
    </mvc:interceptors>
```



![image-20220616163809961](\picture\image-20220616163809961.png)

![image-20220616164017752](\picture\image-20220616164017752.png)

## 异常处理

`mvc:annotation-driven`开启后

如果异常解析器都不能处理该异常则抛出异常交给web容器处理

![image-20220616173115614](\picture\image-20220616173115614.png)



### ExceptionHandlerExceptionResolver

```java
@Controller
public class ExceptionController {

    @RequestMapping("/cal")
    public String calc(Integer i, ModelMap map) {
        int i1 = 10 / i;
        map.put("msg", i1);
        return "hello";
    }

    /**
     * 告诉SpringMVC这个方法专门处理这个类中其它发生的异常
     */
    @ExceptionHandler(value = {ArithmeticException.class})
    public ModelAndView exceptionHandler2(Exception e) {
        System.out.println("controller exceptionHandler" + e);
        // 交由视图解析器处理
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ex", e);
        modelAndView.addObject("msg", "发生了异常");
        modelAndView.setViewName("hello");

        return modelAndView;
    }

}

/**
 * 全局异常处理器，集中处理所有异常
 * 使用ControllerAdvice，表明是异常处理类
 * @author : huang.yaohua
 * @date : 2022/6/16 17:47
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 
     * 属性value限定可以处理的异常的类型
     * 方法Exception类型入参用来接收异常信息
     * 可以返回ModelAndView类型
     *  如果有多个异常处理方法，精确匹配
     *  全局异常处理器与本类异常处理器同时存在时，本类优先
     */
    @ExceptionHandler(value = {Exception.class})
    public String globalExceptionHandler(Exception e) {
        System.out.println("global exception handler" + e);
        // 交由视图解析器处理
        return "hello";
    }


}
```

### ResponseStatusExceptionResolver

用于自定义异常，优先级低于`ExceptionHandlerExceptionResolver`

```java
/**
 * 使用ResponseStatus注解
 * @author : huang.yaohua
 * @date : 2022/6/16 20:51
 */
@ResponseStatus(reason = "自定义异常",code = HttpStatus.NOT_ACCEPTABLE)
public class MyException extends  RuntimeException{
    
}
```



### DefaultHandlerExceptionResolver

如果异常没有前面的处理器都不能处理则会由`DefaultHandlerExceptionResolver`处理，可以处理spring自定义的异常

### 自定义异常处理器

自定义异常优先级最低

```xml
    <!--自定义异常处理器-->
    <bean id="simpleMappingExceptionResolver"
          class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <!-- 配置异常对应要去的页面-->
        <property name="exceptionMappings">
            <props>
                <!--key 全类名，value 视图名-->
                <prop key="java.lang.NullPointerException">error</prop>
            </props>
        </property>
    </bean>
```



## SpringMVC运行流程

1. 前端控制器（DispatcherServlet）收到请求，调用`doDispatch`
2. 根据HandlerMapping中保存的请求映射信息找到处理当前请求的执行链（包含拦截器）
3. 根据当前处理器找到对应的HandlerAdapter（适配器）
4. 执行拦截器的preHandle
5. 适配器执行目标方法
   1. ModelAttribute注解标注的方法提前执行
   2. 执行目标方法
      - 确定方法入参
        1. 是否Model、Map、原生servlet入参
        2. 如果是自定义类型参数
           1. 检查隐藏模型中是否存在，如果存在就从隐藏模型中取，如果没有，再看是否SessionAttribute标注的属性如果是则从Session中拿，如果拿不到就会抛异常
           2. 以上都不是则使用反射创建对象
6. 执行拦截器的postHandle
7. 处理结果（页面渲染流程）
   1. 如果有异常使用异常解析器处理异常，处理完成后返回ModelAndView
   2. 调用render进行页面渲染
      1. 视图解析器根据视图名创建视图对象
      2. 视图对象调用render方法
   3. 执行拦截器的afterCompletion

![image-20220616215803317](E:\myCode\my-study\Markdown\picture\image-20220616215803317.png)

## SpringMVC和Spring整合

SpringMVC和Spring分容器，两个容器同时存在时，spring容器会成为spring-mvc容器的父容器，父容器不能从子容器中获取组件，

子容器可以从父容器中获取组件

spring.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.hyh">
        <!--过滤Controller-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

</beans>
```

spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc 	http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--包扫描规则： use-default-filters="false" 不使用默认过滤器，默认过滤器会将标注了Component、Service等注解的类
加入容器
<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/> 
 只处理Controller注解的类-->
    <context:component-scan base-package="com.hyh" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
</beans>
```



## 转发和重定向

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190319151923309.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MDAwMTEyNQ==,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190319152231556.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MDAwMTEyNQ==,size_16,color_FFFFFF,t_70)

### 一、转发和重定向的区别

​    request.getRequestDispatcher()是容器中控制权的转向，在客户端浏览器地址栏中不会显示出转向后的地址；服务器内部转发，整个过程处于同一个请求当中。

response.sendRedirect()则是完全的跳转，浏览器将会得到跳转的地址，并重新发送请求链接。这样，从浏览器的地址栏中可以看到跳转后的链接地址。不在同一个请求。重定向，实际上客户端会向服务器端发送两个请求。
所以转发中数据的存取可以用request作用域：`request.setAttribute(), request.getAttribute()`，重定向是取不到request中的数据的。只能用session。

​    forward()更加高效，在可以满足需要时，尽量使用RequestDispatcher.forward()方法。

RequestDispatcher是通过调用HttpServletRequest对象的getRequestDispatcher()方法得到的，是属于请求对象的方法。
    sendRedirect()是HttpServletResponse对象的方法，即响应对象的方法，既然调用了响应对象的方法，那就表明整个请求过程已经结束了，服务器开始向客户端返回执行的结果。

​    重定向可以跨域访问，而转发是在web服务器内部进行的，不能跨域访问。

### 二、转发和重定向总结   

   1、转发使用的是getRequestDispatcher()方法;重定向使用的是sendRedirect();

   2、转发：浏览器URL的地址栏不变。重定向：浏览器URL的地址栏改变；

   3、转发是服务器行为，重定向是客户端行为；

   4、转发是浏览器只做了一次访问请求。重定向是浏览器做了至少两次的访问请求；

   5、转发2次跳转之间传输的信息不会丢失，重定向2次跳转之间传输的信息会丢失（request范围）。

### 三、转发和重定向的选择   

   1、重定向的速度比转发慢，因为浏览器还得发出一个新的请求，如果在使用转发和重定向都无所谓的时候建议使用转发。

   2、因为转发只能访问当前WEB的应用程序，所以不同WEB应用程序之间的访问，特别是要访问到另外一个WEB站点上的资源的情况，这个时候就只能使用重定向了。 

  3、另外，重定向还有一个应用场景：避免在用户重新加载页面时两次调用相同的动作。
