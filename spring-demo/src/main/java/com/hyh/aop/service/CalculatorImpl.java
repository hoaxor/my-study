package com.hyh.aop.service;


import com.hyh.ioc.setinjection.Student;
import org.springframework.stereotype.Component;

/**
 * @author : huang.yaohua
 * @date : 2022/6/1 16:32
 */
@Component
public class CalculatorImpl implements Calculator, OrderService {
    @Override
    public int add(int i, int j) {
        System.out.println("add");
        return i + j;
    }

    @Override
    public int sub(int i, int j) {
        System.out.println("sub");
        return i - j;
    }

    @Override
    public int mul(int i, int j) {
        System.out.println("mul");
        return i * j;
    }

    @Override
    public int div(int i, int j) {
        System.out.println("div");
        return i / j;
    }

    @Override
    public String createOrder() {
        System.out.println("createOrder");
        return "order";
    }

    @Override
    public Student createStudent() {
        System.out.println("createStudent");
        return new Student();
    }
}
