package com.hyh.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : huang.yaohua
 * @date : 2022/5/16 17:57
 */
@RestController
public class MyController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello springboot";
    }
}
