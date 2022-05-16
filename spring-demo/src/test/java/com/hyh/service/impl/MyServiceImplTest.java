package com.hyh.service.impl;

import com.hyh.ioc.setinjection.service.MyService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : huang.yaohua
 * @date : 2022/5/14 23:34
 */
class MyServiceImplTest {

    @Test
    public void testPrint() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MyService ser = context.getBean(MyService.class);
        ser.print("test");
    }

}