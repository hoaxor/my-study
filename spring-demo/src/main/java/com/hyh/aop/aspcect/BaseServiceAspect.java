package com.hyh.aop.aspcect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : huang.yaohua
 * @date : 2022/6/2 10:03
 */
@Component
@Aspect
public class BaseServiceAspect {

    /**
     * 方法执行前执行
     * joinPoint 可以省略
     */
    @Before("execution(public * com.hyh..BaseServadice.getSome(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println("before");
        System.out.println(joinPoint.getTarget());
    }

    /**
     * 通过 ProceedingJoinPoint  推进目标方法的执行
     */
    @Around("execution(public * com.hyh..BaseService.getSome(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println(joinPoint.getSignature());
        // 利用反射执行目标方法
        //前置通知
        Object proceed = null;
        try {
            // 可以修改入参
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            // 异常通知
        } finally {
            //最终通知
        }
        //返回通知
        // 可以修改目标方法的返回值
        return proceed;
    }

    /**
     * 方法异常后执行
     */
    @AfterThrowing(value = "execution(public * com.hyh..BaseService.getSome(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, RuntimeException e) {
        System.out.println("after throwing");
        System.out.println(joinPoint);
        System.out.println(e);
    }

    /**
     * 方法返回后执行
     * JoinPoint 必须是第一个或者省略
     * returning 用来就收方法返回值的 参数名，入参名
     */
    @AfterReturning(value = "execution(public * com.hyh..BaseService.getSome(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("after returning");
        System.out.println(joinPoint);
        System.out.println("result=" + result);
    }

    /**
     * 不管是正常执行完成，还是抛出异常，都会执行返回通知中的内容。
     */
    @After(value = "execution(public * com.hyh..BaseService.getSome(..))")
    public void after(JoinPoint joinPoint) {
        System.out.println("after");
        System.out.println(joinPoint);
    }
}
