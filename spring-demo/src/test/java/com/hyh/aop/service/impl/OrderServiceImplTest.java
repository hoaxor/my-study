package com.hyh.aop.service.impl;

import com.hyh.aop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : huang.yaohua
 * @date : 2022/5/15 23:16
 */
class OrderServiceImplTest {

    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        OrderService orderService = (OrderService) context.getBean("orderService");
        System.out.println(orderService.getClass().getName());
        orderService.createOrder();
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        OrderService orderService = (OrderService) context.getBean("orderService");
        System.out.println(orderService.getClass().getName());
        System.out.println("test " + orderService.createStudent());
    }
}