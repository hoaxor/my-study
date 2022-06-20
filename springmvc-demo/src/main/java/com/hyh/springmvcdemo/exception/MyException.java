package com.hyh.springmvcdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : huang.yaohua
 * @date : 2022/6/16 20:51
 */
@ResponseStatus(reason = "自定义异常",code = HttpStatus.NOT_ACCEPTABLE)
public class MyException extends  RuntimeException{
    
}
