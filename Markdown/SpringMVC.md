# SpringMVC

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
