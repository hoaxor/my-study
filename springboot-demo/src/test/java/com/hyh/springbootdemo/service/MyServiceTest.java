package com.hyh.springbootdemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : huang.yaohua
 * @date : 2022/5/23 15:08
 */
@SpringBootTest
class MyServiceTest {
    @Autowired
    private MyService myService;

    @Test
    void print() {
        myService.print("test my service");
    }

    @Test
    void get() throws IOException {
        System.out.println(myService.get("name"));
    }
}