package com.hyh.aop.aspcect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;
import java.util.Date;

/**
 * 切面类注解，表明当前类是切面类
 *
 * @author : huang.yaohua
 * @date : 2022/5/15 22:56
 */
@Aspect
public class MyAspect {


    /**
     * Before注解：
     * 属性value切入点表达式，表示切面的执行位置
     * 前置通知方法的定义
     * 方法可以有参数，如果有是JoinPoint，也可以没有
     * 在目标方法之前执行
     * 不会影响目标方法的执行
     * 不会修改目标方法的执行结果
     * before:前置通知(应用：各种校验)：在方法执行前执行，如果通知抛出异常，阻止方法运行
     */
    @Before("execution(public String com.hyh.aop.service.impl.OrderServiceImpl.createOrder())")
    public void before() {
        //切面代码
        System.out.println("前置通知，在目标方法执行前执行切面的功能，" + new Date());

    }

    /**
     * after:最终通知(应用：清理现场)：方法执行完毕后执行，无论方法中是否出现异常
     * joinPoint 必须是第一个参数
     * 获取被执行方法的信息
     *
     * @param joinPoint 必须是第一个参数
     */
    @After("execution(String com.hyh.aop.service.impl.OrderServiceImpl.createOrder(..))")
    public void after(JoinPoint joinPoint) {
        //切面代码
        System.out.println(joinPoint.getSignature());
        System.out.println(joinPoint.getKind());
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println("-后置通知，在目标方法执行后执行切面的功能，");

    }

    /**
     * around:环绕通知(应用：十分强大，可以做任何事情)：方法执行前后分别执行，可以阻止方法的执行，必须手动执行目标方法
     */
    @Around("execution(String com.hyh.aop.service.impl.OrderServiceImpl.createOrder(..))")
    public void around(JoinPoint joinPoint) {
        //切面代码
        System.out.println("around，在目标方法执行后执行切面的功能，");

    }
}
