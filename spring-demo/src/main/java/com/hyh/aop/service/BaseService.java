package com.hyh.aop.service;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * @author : huang.yaohua
 * @date : 2022/6/2 10:01
 */
@Component
public class BaseService {
    public String getSome(String s) {
        return UUID.randomUUID().toString();
    }

    public String getSome(int s) {
        System.out.println("业务方法");
        double random = Math.random();
        return String.valueOf(random);
    }

    public String getSome(int a, int s) {
        System.out.println("业务方法");
        int i = a / s;
        return String.valueOf(i);
    }
}
