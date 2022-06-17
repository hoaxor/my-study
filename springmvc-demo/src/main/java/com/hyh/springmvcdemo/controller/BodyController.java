package com.hyh.springmvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : huang.yaohua
 * @date : 2022/6/15 16:34
 */
@Controller
public class BodyController {
    /**
     * RequestBody接收请求body, POST类型才有请求体
     */
    @RequestMapping("/test1")
    public String test1(@RequestBody String body) {
        System.out.println(body);
        return "test1";

    }
}
