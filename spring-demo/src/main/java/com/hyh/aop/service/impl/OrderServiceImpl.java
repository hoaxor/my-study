package com.hyh.aop.service.impl;

import com.hyh.aop.service.OrderService;
import com.hyh.ioc.setinjection.Student;

import java.util.Date;

/**
 * @author : huang.yaohua
 * @date : 2022/5/15 22:55
 */
public class OrderServiceImpl implements OrderService {
    @Override
    public String createOrder() {
        System.out.println("创建order");
        return "new order";
    }

    @Override
    public Student createStudent() {
        System.out.println("createStudent");
        int i = 1 / 0;
        return new Student(20, "hyh", new Date());
    }
}
