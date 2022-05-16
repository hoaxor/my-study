package com.hyh.aop.service.impl;

import com.hyh.aop.service.OrderService;

/**
 * @author : huang.yaohua
 * @date : 2022/5/15 22:55
 */
public class OrderServiceImpl implements OrderService {
    @Override
    public String createOrder() {
        System.out.println("创建order");
        return "new order";
    }

    @Override
    public int deleteOrder() {
        return 0;
    }
}
