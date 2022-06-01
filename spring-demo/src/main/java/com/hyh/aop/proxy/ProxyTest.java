package com.hyh.aop.proxy;

import com.hyh.aop.service.Calculator;
import com.hyh.aop.service.CalculatorImpl;
import com.hyh.aop.service.OrderService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author : huang.yaohua
 * @date : 2022/6/1 16:37
 */
public class ProxyTest {
    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        // 创建动态代理对象
        final Object proxyInstance = Proxy.newProxyInstance(calculator.getClass().getClassLoader(),
                calculator.getClass().getInterfaces(), ((proxy, method, args1) -> {
                    System.out.println("args=" + Arrays.toString(args1));
//                    System.out.println(proxy);
                    return method.invoke(calculator, args1);
//                    return null;
                })
        );
        System.out.println(proxyInstance.getClass());
//        System.out.println(((Calculator) proxyInstance).add(1, 1));
        System.out.println(((OrderService) proxyInstance).createOrder());

    }
}
