package com.hyh.ioc;

import com.hyh.ioc.setinjection.service.MyService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : huang.yaohua
 * @date : 2022/6/2 11:34
 */
public class IocTest {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //3. 获取对象
        MyService myService = (MyService) context.getBean("myService");
    }
}
