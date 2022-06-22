package com.hyh.ssm.service.impl;

import com.hyh.ssm.service.MyService;

/**
 * @author : huang.yaohua
 * @date : 2022/6/21 15:55
 */
public class MyServiceImpl implements MyService {
    @Override
    public String getMsg(Integer integer) {
        return "hello " + integer;
    }
}
