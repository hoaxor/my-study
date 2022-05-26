package com.hyh.springbootdemo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CarTest {

    @Autowired
    private Car car;

    @Test
    @DisplayName("print car")
    void test1() {
        System.out.println(car);
    }


}