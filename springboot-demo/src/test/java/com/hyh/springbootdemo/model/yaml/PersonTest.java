package com.hyh.springbootdemo.model.yaml;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : huang.yaohua
 * @date : 2022/5/30 16:50
 */
@SpringBootTest
class PersonTest {

    @Autowired
    private Person person;

    @Test
    void test() {
        System.out.println(person);
    }

}