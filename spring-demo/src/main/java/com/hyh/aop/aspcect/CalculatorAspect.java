package com.hyh.aop.aspcect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面类必须加入到容器
 *
 * @author : huang.yaohua
 * @date : 2022/6/1 17:59
 */
@Aspect
@Component
public class CalculatorAspect {


    /**
     * 前置通知
     * 切入点：访问权限符 返回值类型 方法签名
     */
    @Before("execution(public com.hyh.aop.service.CalculatorImpl add(..))")
    public void before() {
        System.out.println("before");
    }
}
