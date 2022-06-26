package com.hyh.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/hello/{path}")
    public String hello(@MatrixVariable("id") Integer id,
                        @MatrixVariable("name") String name,
                        @PathVariable("path") String path) {
        System.out.println(id);
        System.out.println(name);
        System.out.println(path);
        return "MatrixVariable";
    }
}
