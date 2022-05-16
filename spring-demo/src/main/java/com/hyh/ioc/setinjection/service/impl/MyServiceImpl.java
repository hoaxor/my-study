package com.hyh.ioc.setinjection.service.impl;

import com.hyh.ioc.setinjection.service.MyService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : huang.yaohua
 * @date : 2022/5/14 23:15
 */
@Slf4j
public class MyServiceImpl implements MyService {


    /**
     * spring 默认使用无参构造方法
     */
    public MyServiceImpl() {

    }

    @Override
    public void print(String s) {
        log.debug(s);
    }
}
