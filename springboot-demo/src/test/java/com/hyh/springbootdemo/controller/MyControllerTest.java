package com.hyh.springbootdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author : huang.yaohua
 * @date : 2022/5/23 14:50
 */
@SpringBootTest
public class MyControllerTest {

    @Test
    public void hello() {
        Set<String> set = new TreeSet<>();
        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();
    }
    
}