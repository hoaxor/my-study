package com.hyh.springmvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : huang.yaohua
 * @date : 2022/6/16 11:28
 */
@Controller
public class InterceptorTestController {

    @RequestMapping("/interceptor1")
//    @ResponseBody
    public String interceptor1(ModelMap map) {
        System.out.println("interceptor1.....");
        map.addAttribute("msg", "test Interceptor");
        return "hello";
    }
}
