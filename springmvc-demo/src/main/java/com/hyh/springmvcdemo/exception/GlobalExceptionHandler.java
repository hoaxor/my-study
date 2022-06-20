package com.hyh.springmvcdemo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 集中处理所有异常
 * 使用ControllerAdvice，表明是异常处理类
 * @author : huang.yaohua
 * @date : 2022/6/16 17:47
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 
     * 属性value限定可以处理的异常的类型
     * 方法Exception类型入参用来接收异常信息
     * 可以返回ModelAndView类型
     *  如果有多个异常处理方法，精确匹配
     *  全局异常处理器与本类异常处理器同时存在时，本类优先
     */
//    @ExceptionHandler(value = {Exception.class})
    public String globalExceptionHandler(Exception e) {
        System.out.println("global exception handler" + e);
        // 交由视图解析器处理
        return "hello";
    }


}
