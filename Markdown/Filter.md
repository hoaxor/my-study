Filter

简介

过滤请求

url-pattern写法，不能两两组合

![image-20220623100355727](\picture\image-20220623100355727.png)

Filter 顶级接口

```java
public interface Filter {
    // filter 初始化
    void init(FilterConfig var1) throws ServletException;
    // 过滤
    void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;
    // 销毁
    void destroy();
}
```



多个Filter执行顺序

![image-20220623100654027](\picture\image-20220623100654027.png)