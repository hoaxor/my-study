package com.hyh.aop.aspcect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author : huang.yaohua
 * @date : 2022/6/1 17:59
 */
@Aspect
public class CalculatorAspect {
    
    @Before("execution()")
    public void before(){
        
    }
}
