# Listener

## 概念

监听器是`Servlet`规范中定义的一种特殊类，

- 用来监听`ServletContext`、`HttpSession`和`ServletRequest`等域对象的创建和销毁事件
- 用来监听域对象的属性发生修改的事件
- 可以在事件发生前、发生后做一些必要的处理

![在这里插入图片描述](\picture\listener)



## 监听器的分类

`web`应用中就是`request`,`session`,`servletContext`等域对象，也被称为**事件源**。

在`web`应用中，方法调用，属性改变，状态改变等称为**事件**

`Servlet`规范中定义了9个监听器接口，可以用来监听`ServletContext`、`HttpSession` 和 `ServletRequest` 对象的生命周期和属性变化事件。



**监听器Listener按照监听的事件可以分成3大类**

- 监听对象（生命周期）创建和销毁的监听器

![image-20220622103639139](\picture\image-20220622103639139.png)

- 监听对象中属性变更的监听器

![image-20220622103658193](\picture\image-20220622103658193.png)

- 监听 `HttpSession` 中的对象状态改变的监听器

`Session` 中的对象可以有多种状态：绑定到 `Session` 中、从 `Session` 中解除绑定、（服务器停止时）随 `Session` 对象持久化到存储设备中(钝化)，（服务器启动时）随 `Session` 对象从存储设备中恢复（活化）。

`Servlet` 规范中定义了两个特殊的监听器接口，用来帮助对象了解自己在 `Session` 中的状态：`HttpSessionBindingListener` 接口和 `HttpSessionActivationListener` 接口 ，**实现这两个接口的类不需要进行注册**。

![image-20220622103805106](\picture\image-20220622103805106.png)



所有监听器的方法不需要我们去调用，服务器会进行调用。当某一个特殊事件发生的时候，服务器会自动调用

![在这里插入图片描述](\picture\watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASmF2YeeahOWtpuS5oOS5i-i3rw==,size_20,color_FFFFFF,t_70,g_se,x_16.png)



## 监听器的用途

- 统计在线人数和用户
- 系统启动的时候加载初始化信息
- 统计网站的访问量
- 和Spring相结合