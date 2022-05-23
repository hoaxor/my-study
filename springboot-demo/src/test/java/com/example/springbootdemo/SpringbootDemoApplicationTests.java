package com.example.springbootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class SpringbootDemoApplicationTests {

    @Autowired
    public LocalDate localDate;


    @Test
    void contextLoads() {
    }

    @Test
    void testProxyBeanMethods() {
        System.out.println(localDate);
    }

}
