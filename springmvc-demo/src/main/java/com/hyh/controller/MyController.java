package com.hyh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : huang.yaohua
 * @date : 2022/6/6 17:51
 */
@RestController
public class MyController {

    @GetMapping("hello")
    public String hello() {
        return "hello world!";
    }
}
