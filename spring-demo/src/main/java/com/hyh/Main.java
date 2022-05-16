package com.hyh;

import com.hyh.ioc.setinjection.service.MyService;
import com.hyh.ioc.setinjection.service.impl.MyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : huang.yaohua
 * @date : 2022/5/14 23:17
 */
@Slf4j(topic = "main")
public class Main {

    public static void main(String[] args) {
        MyService myService = new MyServiceImpl();
        myService.print("hello world");
        log.debug("{}", myService);
        //1. 指定spring配置文件，从classpath之下开始的路径
        //2. 创建容器对象 ApplicationContext, 使用实现类 ClassPathXmlApplicationContext 从类路径中
        // spring 默认使用无参构造方法，创建容器时会读取配置文件，创建文件中声明的所有对象
        // 优点：获取对象速度快，缺点：占用内存
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //3. 获取对象
        myService = (MyService) context.getBean("myService");
        //4. 使用对象
        myService.print("hello spring");
        log.debug("{}", myService);

        MyService bean = context.getBean(MyService.class);

    }
}
