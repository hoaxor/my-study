package com.hyh.aop.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : huang.yaohua
 * @date : 2022/6/2 9:24
 */
class CalculatorImplTest {
    private ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


    @Test
    void add() {
        // bean实现了接口，要通过接口类型获取代理类，不能直接通过实现类类型获取组件
        // 实现了接口时，底层通过jdk动态代理生成代理对象
        Calculator bean = context.getBean(Calculator.class);
//        Calculator bean = (Calculator) context.getBean("calculatorImpl");
        System.out.println(bean.getClass());
        System.out.println(bean.add(1, Integer.MAX_VALUE - 1));
    }

    @Test
    void sub() {
    }

    @Test
    void mul() {
    }

    @Test
    void div() {
    }

    @Test
    void createOrder() {
    }

    @Test
    void createStudent() {
    }
}