# Servlet

原文链接：https://blog.csdn.net/qq_19782019/article/details/80292110

## 什么是Servlet

Servlet（Server Applet），全称Java Servlet，未有中文译文。是用Java编写的服务器端程序。其主要功能在于交互式地浏览和修改数据，生成动态Web内容。狭义的Servlet是指Java语言实现的一个接口，广义的Servlet是指任何实现了这个Servlet接口的类，一般情况下，人们将Servlet理解为后者。

Servlet运行于支持Java的应用服务器中。从实现上讲，Servlet可以响应任何类型的请求，但绝大多数情况下Servlet只用来扩展基于HTTP协议的Web服务器。



## Servlet工作原理

`Servlet`定义了`Servlet`与**Servlet容器**之间的契约，`Servlet容器`将`Servlet`类载入内存中，并产生`Servlet`实例和调用它具体的方法，**同一个应用程序中，每种Servlet只有一个实例**



## Servlet的工作流程

![img](\picture\70.png)



## Servlet

用户请求致使**Servlet容器**调用`Servlet`的`Service()`方法，并传入一个`ServletRequest`对象和一个`ServletResponse`对象。`ServletRequest`对象和`ServletResponse`对象都是由**Servlet容器**（例如`TomCat`）封装好的，并不需要程序员去实现，程序员可以直接使用这两个对象。

**Servlet容器还会创建一个ServletContext对象。这个对象中封装了上下文（应用程序）的环境详情。每个应用程序只有一个ServletContext。每个Servlet对象也都有一个封装Servlet配置的ServletConfig对象。**

```java
public interface Servlet {
    // 当Servlet第一次被请求时被调用
    void init(ServletConfig var1) throws ServletException;
    // 返回由Servlet容器传给init()方法的ServletConfig对象。
    ServletConfig getServletConfig();
    // 每当请求Servlet时，Servlet容器就会调用这个方法
    void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;
    // 返回Servlet的一段描述，可以返回一段字符串。
    String getServletInfo();
    // 当要销毁Servlet时，Servlet容器就会调用这个方法，
    // 在卸载应用程序或者关闭Servlet容器时，就会发生这种情况，一般在这个方法中会写一些清除代码。
    void destroy();
}
```



## ServletRequest

**Servlet容器**对于接受到的每一个Http请求，都会创建一个`ServletRequest`对象，并把这个对象传递给`Servlet`的`Sevice()`方法。其中，`ServletRequest`对象内封装了这个请求的详细信息。

```java
public interface ServletRequest {
    Object getAttribute(String var1);

    Enumeration getAttributeNames();

    String getCharacterEncoding();

    void setCharacterEncoding(String var1) throws UnsupportedEncodingException;
    // 返回请求主体的字节数
    int getContentLength();
    // 返回主体的MIME类型
    String getContentType();

    ServletInputStream getInputStream() throws IOException;
    // 返回请求参数的值
    String getParameter(String var1);

    Enumeration getParameterNames();

    String[] getParameterValues(String var1);

    Map getParameterMap();

    String getProtocol();

    String getScheme();

    String getServerName();

    int getServerPort();

    BufferedReader getReader() throws IOException;

    String getRemoteAddr();

    String getRemoteHost();

    void setAttribute(String var1, Object var2);

    void removeAttribute(String var1);

    Locale getLocale();

    Enumeration getLocales();

    boolean isSecure();

    RequestDispatcher getRequestDispatcher(String var1);

    /** @deprecated */
    String getRealPath(String var1);

    int getRemotePort();

    String getLocalName();

    String getLocalAddr();

    int getLocalPort();
}
```

## ServletResponse

在调用`Servlet`的`Service()`方法前，**Servlet容器**会先创建一个`ServletResponse`对象，并把它作为第二个参数传给`Service()`方法。`ServletResponse`隐藏了向浏览器发送响应的复杂过程。

```java
public interface ServletResponse {
    String getCharacterEncoding();

    String getContentType();
    // 用向客户端发送字节流数据
    ServletOutputStream getOutputStream() throws IOException;
    // 返回了一个可以向客户端发送文本的的Java.io.PrintWriter对象。字符流对象
    // 默认情况下，PrintWriter对象使用ISO-8859-1编码（该编码在输入中文时会发生乱码）。
    PrintWriter getWriter() throws IOException;

    void setCharacterEncoding(String var1);

    void setContentLength(int var1);
    // 设置响应的内容类型,告诉浏览器响应的内容类型
    // 也可以加上“charset=UTF-8”改变响应的编码方式以防止发生中文乱码现象。
    void setContentType(String var1);

    void setBufferSize(int var1);

    int getBufferSize();

    void flushBuffer() throws IOException;

    void resetBuffer();

    boolean isCommitted();

    void reset();

    void setLocale(Locale var1);

    Locale getLocale();
}
```



## ServletConfig

当**Servlet容器**初始化`Servlet`时，**Servlet容器**会给`Servlet`的`init()`方式传入一个`ServletConfig`对象。

```Java
public interface ServletConfig {
    // 获取Servlet在web.xml中配置的name
    String getServletName();

    ServletContext getServletContext();
    // 获取Servlet的初始化参数
    String getInitParameter(String var1);
    // 获取Servlet所有的初始参数名
    Enumeration getInitParameterNames();
}
```



## ServletContext

`ServletContext`对象表示`Servlet`应用程序。`每个Web应用程序都只有一个ServletContext对象`。

**有了ServletContext对象，就可以共享从应用程序中的所有资料处访问到的信息，并且可以动态注册Web对象。前者将对象保存在ServletContext中的一个内部Map中。**保存在ServletContext中的对象被称作属性。

```java
public interface ServletContext {
    String getContextPath();

    ServletContext getContext(String var1);

    int getMajorVersion();

    int getMinorVersion();

    String getMimeType(String var1);

    Set getResourcePaths(String var1);

    URL getResource(String var1) throws MalformedURLException;

    InputStream getResourceAsStream(String var1);

    RequestDispatcher getRequestDispatcher(String var1);

    RequestDispatcher getNamedDispatcher(String var1);

    /** @deprecated */
    Servlet getServlet(String var1) throws ServletException;

    /** @deprecated */
    Enumeration getServlets();

    /** @deprecated */
    Enumeration getServletNames();

    void log(String var1);

    /** @deprecated */
    void log(Exception var1, String var2);

    void log(String var1, Throwable var2);

    String getRealPath(String var1);

    String getServerInfo();

    String getInitParameter(String var1);

    Enumeration getInitParameterNames();

    Object getAttribute(String var1);

    Enumeration getAttributeNames();

    void setAttribute(String var1, Object var2);

    void removeAttribute(String var1);

    String getServletContextName();
}
```



## GenericServlet

为Servlet接口中的所有方法提供了默认的实现

提供方法，包围ServletConfig对象中的方法。

```java
public abstract class GenericServlet implements Servlet, ServletConfig, Serializable {
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.LocalStrings");
    private transient ServletConfig config;

    public GenericServlet() {
    }

    public void destroy() {
    }

    public String getInitParameter(String name) {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getInitParameter(name);
        }
    }

    public Enumeration getInitParameterNames() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getInitParameterNames();
        }
    }

    public ServletConfig getServletConfig() {
        return this.config;
    }

    public ServletContext getServletContext() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getServletContext();
        }
    }

    public String getServletInfo() {
        return "";
    }
    // 将传入的的ServletConfig参数赋给了一个内部的ServletConfig引用从而来保存ServletConfig对象
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    public void init() throws ServletException {
    }

    public void log(String msg) {
        this.getServletContext().log(this.getServletName() + ": " + msg);
    }

    public void log(String message, Throwable t) {
        this.getServletContext().log(this.getServletName() + ": " + message, t);
    }

    public abstract void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    public String getServletName() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getServletName();
        }
    }
}
```

## HttpServlet

`HttpServlet`是由`GenericServlet`抽象类扩展而来

使用时，不用覆盖`service`方法，而是覆盖`doGet`或者`doPost`方法。在少数情况，还会覆盖其他的5个方法。

`service`方法使用的是`HttpServletRequest`和`HttpServletResponse`对象。

```Java

public abstract class HttpServlet extends GenericServlet implements Serializable {
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";
    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";
    private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");

    public HttpServlet() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }

    protected long getLastModified(HttpServletRequest req) {
        return -1L;
    }

    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoBodyResponse response = new NoBodyResponse(resp);
        this.doGet(req, response);
        response.setContentLength();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_post_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_put_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_delete_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }

    private Method[] getAllDeclaredMethods(Class c) {
        if (c.equals(HttpServlet.class)) {
            return null;
        } else {
            Method[] parentMethods = this.getAllDeclaredMethods(c.getSuperclass());
            Method[] thisMethods = c.getDeclaredMethods();
            if (parentMethods != null && parentMethods.length > 0) {
                Method[] allMethods = new Method[parentMethods.length + thisMethods.length];
                System.arraycopy(parentMethods, 0, allMethods, 0, parentMethods.length);
                System.arraycopy(thisMethods, 0, allMethods, parentMethods.length, thisMethods.length);
                thisMethods = allMethods;
            }

            return thisMethods;
        }
    }

    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Method[] methods = this.getAllDeclaredMethods(this.getClass());
        boolean ALLOW_GET = false;
        boolean ALLOW_HEAD = false;
        boolean ALLOW_POST = false;
        boolean ALLOW_PUT = false;
        boolean ALLOW_DELETE = false;
        boolean ALLOW_TRACE = true;
        boolean ALLOW_OPTIONS = true;

        for(int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            if (m.getName().equals("doGet")) {
                ALLOW_GET = true;
                ALLOW_HEAD = true;
            }

            if (m.getName().equals("doPost")) {
                ALLOW_POST = true;
            }

            if (m.getName().equals("doPut")) {
                ALLOW_PUT = true;
            }

            if (m.getName().equals("doDelete")) {
                ALLOW_DELETE = true;
            }
        }

        String allow = null;
        if (ALLOW_GET && allow == null) {
            allow = "GET";
        }

        if (ALLOW_HEAD) {
            if (allow == null) {
                allow = "HEAD";
            } else {
                allow = allow + ", HEAD";
            }
        }

        if (ALLOW_POST) {
            if (allow == null) {
                allow = "POST";
            } else {
                allow = allow + ", POST";
            }
        }

        if (ALLOW_PUT) {
            if (allow == null) {
                allow = "PUT";
            } else {
                allow = allow + ", PUT";
            }
        }

        if (ALLOW_DELETE) {
            if (allow == null) {
                allow = "DELETE";
            } else {
                allow = allow + ", DELETE";
            }
        }

        if (ALLOW_TRACE) {
            if (allow == null) {
                allow = "TRACE";
            } else {
                allow = allow + ", TRACE";
            }
        }

        if (ALLOW_OPTIONS) {
            if (allow == null) {
                allow = "OPTIONS";
            } else {
                allow = allow + ", OPTIONS";
            }
        }

        resp.setHeader("Allow", allow);
    }

    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CRLF = "\r\n";
        String responseString = "TRACE " + req.getRequestURI() + " " + req.getProtocol();

        String headerName;
        for(Enumeration reqHeaderEnum = req.getHeaderNames(); reqHeaderEnum.hasMoreElements(); responseString = responseString + CRLF + headerName + ": " + req.getHeader(headerName)) {
            headerName = (String)reqHeaderEnum.nextElement();
        }

        responseString = responseString + CRLF;
        int responseLength = responseString.length();
        resp.setContentType("message/http");
        resp.setContentLength(responseLength);
        ServletOutputStream out = resp.getOutputStream();
        out.print(responseString);
        out.close();
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        long lastModified;
        if (method.equals("GET")) {
            lastModified = this.getLastModified(req);
            if (lastModified == -1L) {
                this.doGet(req, resp);
            } else {
                long ifModifiedSince = req.getDateHeader("If-Modified-Since");
                if (ifModifiedSince < lastModified / 1000L * 1000L) {
                    this.maybeSetLastModified(resp, lastModified);
                    this.doGet(req, resp);
                } else {
                    resp.setStatus(304);
                }
            }
        } else if (method.equals("HEAD")) {
            lastModified = this.getLastModified(req);
            this.maybeSetLastModified(resp, lastModified);
            this.doHead(req, resp);
        } else if (method.equals("POST")) {
            this.doPost(req, resp);
        } else if (method.equals("PUT")) {
            this.doPut(req, resp);
        } else if (method.equals("DELETE")) {
            this.doDelete(req, resp);
        } else if (method.equals("OPTIONS")) {
            this.doOptions(req, resp);
        } else if (method.equals("TRACE")) {
            this.doTrace(req, resp);
        } else {
            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[]{method};
            errMsg = MessageFormat.format(errMsg, errArgs);
            resp.sendError(501, errMsg);
        }

    }

    private void maybeSetLastModified(HttpServletResponse resp, long lastModified) {
        if (!resp.containsHeader("Last-Modified")) {
            if (lastModified >= 0L) {
                resp.setDateHeader("Last-Modified", lastModified);
            }

        }
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;
        // 之所以能够这样强制的转换，是因为在调用Servlet的Service方法时，
        //Servlet容器总会传入一个HttpServletRequest对象和HttpServletResponse对象，
        // 预备使用HTTP。因此，转换类型当然不会出错了。
        try {
            request = (HttpServletRequest)req;
            response = (HttpServletResponse)res;
        } catch (ClassCastException var6) {
            throw new ServletException("non-HTTP request or response");
        }

        this.service(request, response);
    }
}
```



## HttpServletRequest

 `HttpServletRequest`表示`Http`环境中的`Servlet`请求。

我们可以通过该对象分别获得`HTTP`请求的请求行，请求头和请求体。

![img](\picture\70-16546750455162.png)

```java
public interface HttpServletRequest extends ServletRequest {
    String BASIC_AUTH = "BASIC";
    String FORM_AUTH = "FORM";
    String CLIENT_CERT_AUTH = "CLIENT_CERT";
    String DIGEST_AUTH = "DIGEST";

    String getAuthType();
    // 返回一个cookie对象数组
    Cookie[] getCookies();

    long getDateHeader(String var1);

    String getHeader(String var1);

    Enumeration getHeaders(String var1);

    Enumeration getHeaderNames();

    int getIntHeader(String var1);

    String getMethod();

    String getPathInfo();

    String getPathTranslated();
    // 返回请求上下文的请求URI部分
    String getContextPath();
    // 返回请求URL中的查询字符串
    String getQueryString();

    String getRemoteUser();

    boolean isUserInRole(String var1);

    Principal getUserPrincipal();

    String getRequestedSessionId();

    String getRequestURI();

    StringBuffer getRequestURL();

    String getServletPath();

    HttpSession getSession(boolean var1);
    // 返回与这个请求相关的会话对象
    HttpSession getSession();

    boolean isRequestedSessionIdValid();

    boolean isRequestedSessionIdFromCookie();

    boolean isRequestedSessionIdFromURL();

    /** @deprecated */
    boolean isRequestedSessionIdFromUrl();
}
```











## Request乱码问题的解决方法

  在前面我们讲过，***在service中使用的编码解码方式默认为：ISO-8859-1编码**，但此编码并不支持中文，因此会出现乱码问题，所以我们需要手动修改编码方式为`UTF-8`编码，才能解决中文乱码问题，下面是发生乱码的具体细节：

![img](\picture\70-16546750534954.png)**解决post提交方式的乱码：request.setCharacterEncoding("UTF-8");**

![image-20220622171023085](\picture\image-20220622171023085.png)

**解决get提交的方式的乱码：parameter = newString(parameter.getbytes("iso-8859-1"), "utf-8");或修改Tomcat配置**

原因：Tomcat默认编码是iso-8859-1

```xml
    <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
			   URIEncoding="utf-8"/>
```







## HttpServletResponse

它继承自`ServletResponse`接口，专门用来封装`HTTP`响应消息。  由于HTTP请求消息分为状态行，响应消息头，响应消息体三部分，因此，在`HttpServletResponse`接口中定义了向客户端发送响应状态码，响应消息头，响应消息体的方法。

![img](\picture\70-16546750628806.png)

**response对象的getOutSream（）和getWriter（）方法都可以发送响应消息体，但是他们之间相互排斥，不可以同时使用，否则会发生异常。**



```java
public interface HttpServletResponse extends ServletResponse {
    int SC_CONTINUE = 100;
    int SC_SWITCHING_PROTOCOLS = 101;
    int SC_OK = 200;
    int SC_CREATED = 201;
    int SC_ACCEPTED = 202;
    int SC_NON_AUTHORITATIVE_INFORMATION = 203;
    int SC_NO_CONTENT = 204;
    int SC_RESET_CONTENT = 205;
    int SC_PARTIAL_CONTENT = 206;
    int SC_MULTIPLE_CHOICES = 300;
    int SC_MOVED_PERMANENTLY = 301;
    int SC_MOVED_TEMPORARILY = 302;
    int SC_FOUND = 302;
    int SC_SEE_OTHER = 303;
    int SC_NOT_MODIFIED = 304;
    int SC_USE_PROXY = 305;
    int SC_TEMPORARY_REDIRECT = 307;
    int SC_BAD_REQUEST = 400;
    int SC_UNAUTHORIZED = 401;
    int SC_PAYMENT_REQUIRED = 402;
    int SC_FORBIDDEN = 403;
    int SC_NOT_FOUND = 404;
    int SC_METHOD_NOT_ALLOWED = 405;
    int SC_NOT_ACCEPTABLE = 406;
    int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
    int SC_REQUEST_TIMEOUT = 408;
    int SC_CONFLICT = 409;
    int SC_GONE = 410;
    int SC_LENGTH_REQUIRED = 411;
    int SC_PRECONDITION_FAILED = 412;
    int SC_REQUEST_ENTITY_TOO_LARGE = 413;
    int SC_REQUEST_URI_TOO_LONG = 414;
    int SC_UNSUPPORTED_MEDIA_TYPE = 415;
    int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    int SC_EXPECTATION_FAILED = 417;
    int SC_INTERNAL_SERVER_ERROR = 500;
    int SC_NOT_IMPLEMENTED = 501;
    int SC_BAD_GATEWAY = 502;
    int SC_SERVICE_UNAVAILABLE = 503;
    int SC_GATEWAY_TIMEOUT = 504;
    int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
    // 给这个响应添加一个cookie
    void addCookie(Cookie var1);

    boolean containsHeader(String var1);

    String encodeURL(String var1);

    String encodeRedirectURL(String var1);

    /** @deprecated */
    String encodeUrl(String var1);

    /** @deprecated */
    String encodeRedirectUrl(String var1);

    void sendError(int var1, String var2) throws IOException;

    void sendError(int var1) throws IOException;

    void sendRedirect(String var1) throws IOException;

    void setDateHeader(String var1, long var2);

    void addDateHeader(String var1, long var2);

    void setHeader(String var1, String var2);
    // 给这个请求添加一个响应头
    void addHeader(String var1, String var2);

    void setIntHeader(String var1, int var2);

    void addIntHeader(String var1, int var2);
    // 发送一条响应码，讲浏览器跳转到指定的位置
    void setStatus(int var1);

    /** @deprecated */
    void setStatus(int var1, String var2);
}
```



## Response的乱码问题

**response缓冲区的默认编码是iso8859-1，此码表中没有中文。所以需要更改response的编码方式**

![img](\picture\70-16546750752788.png)

 通过更改`response`的编码方式为`UTF-8`，任然无法解决乱码问题，因为发送端服务端虽然改变了编码方式为`UTF-8`，但是接收端浏览器端仍然使用`GB2312`编码方式解码，还是无法还原正常的中文，因此还需要告知浏览器端使用UTF-8编码去解码。

![img](\picture\70-165467508050110.png)

response.setContentType("text/html;charset=UTF-8")这个方法包含了上面的两个方法的调用，因此在实际的开发中，只需要调用一个response.setContentType("text/html;charset=UTF-8")方法即可。

![img](\picture\70-165467508593312.png)

![img](\picture\70-165467520378115.png)



## ServletContextListener

Servlet全局监听器，需要在web.xml中注册，注册后会自动调用



```java
public interface ServletContextListener extends EventListener {
    // 当应用启动时，ServletContext进行初始化，Servlet容器会自动调用
    void contextInitialized(ServletContextEvent var1);
    // 当应用停止时，ServletContext被销毁，此时Servlet容器也会自动地调用
    void contextDestroyed(ServletContextEvent var1);
}
```





## Tomcat工作机制动画演示

![img](\picture\2018120522281643.gif)