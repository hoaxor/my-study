package com.hyh.ioc.service;

import com.hyh.model.Computer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author : huang.yaohua
 * @date : 2022/6/23 11:50
 */
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:applicationContext.xml")
class BaseServiceTest {

    @Autowired
    private BaseService<Computer> baseService;

    @Test
    public void test1() {
        System.out.println("baseService=" + baseService);
        System.out.println(baseService.getClass().getGenericSuperclass());
    }
}