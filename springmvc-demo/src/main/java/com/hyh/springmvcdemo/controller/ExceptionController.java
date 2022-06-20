package com.hyh.springmvcdemo.controller;

import com.hyh.springmvcdemo.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : huang.yaohua
 * @date : 2022/6/16 17:24
 */
@Controller
public class ExceptionController {

    @RequestMapping("/cal")
    public String calc(Integer i, ModelMap map) {
        int i1 = 10 / i;
        map.put("msg", i1);
        return "hello";
    }

    @RequestMapping("/login")
    public String login(String username, ModelMap map) {
        if (!"admin".equals(username)) {
            throw new MyException();
        }
        map.addAttribute("msg", "登陆成功");
        return "hello";
    }

    @RequestMapping("/nullPoint")
    public String nullPoint() {
        System.out.println(((String) null).indexOf("1"));
        return "hello";
    }

    /**
     * 告诉SpringMVC这个方法专门处理这个类中其它发生的异常
     */
    @ExceptionHandler(value = {ArithmeticException.class})
    public ModelAndView exceptionHandler2(Exception e) {
        System.out.println("controller exceptionHandler" + e);
        // 交由视图解析器处理
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ex", e);
        modelAndView.addObject("msg", "发生了异常");
        modelAndView.setViewName("hello");

        return modelAndView;
    }


}
