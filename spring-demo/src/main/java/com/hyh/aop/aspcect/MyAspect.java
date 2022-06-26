package com.hyh.aop.aspcect;

import com.hyh.ioc.setinjection.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

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
        System.out.println("Before");
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
//        System.out.println(joinPoint.getSignature());
//        System.out.println(joinPoint.getKind());
//        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println("After");

    }

    /**
     * 在after后执行
     * 在目标方法后执行
     * 能获取到目标方法的执行结果
     * 不会影响目标方法的执行
     * 方法有参数，用于接收方法返回值
     * 属性：
     * value 切入点表达式
     * returning 自定义变量，表示目标方法的返回值
     * 自定义变量名必须和通知方法的形参名一样
     */
    @AfterReturning(value = "execution(* *..OrderServiceImpl.createStudent(..))", returning = "student")
    public void afterReturning(Student student) {
        //切面代码
        System.out.println("AfterReturning");
        System.out.println("After returning student=" + student);
        //引用类型的返回值，在这里修改会影响目标方法最终的返回值 
        student.setStudent("hyh aft");
    }

    @AfterReturning(value = "execution(* *..OrderServiceImpl.createOrder(..))", returning = "order")
    public void afterReturning2(Object order) {
        //切面代码
        System.out.println("AfterReturning");
        System.out.println("After returning order=" + order);
    }

    /**
     * 可以有返回值，返回值是目标方法的返回值，或通知方法内返回值
     * around:环绕通知(应用：十分强大，可以做任何事情)：可以阻止方法的执行，必须手动执行目标方法
     */
    @Around("execution(String com.hyh.aop.service.impl.OrderServiceImpl.createOrder(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //切面代码
        System.out.println("around before");
        // 不调用proceed不会调用目标方法，可以控制是否执行目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("around proceed=" + proceed);
        System.out.println("around after");
        // 可以修改目标方法的返回值
        if (null != proceed) {
            return "2";
        }
        return proceed;
    }

    /**
     * 异常通知
     * 目标方法发生异常后执行否则不会执行
     * 不是异常处理程序，可以得到发生异常的通知，目标是监控目标方法的异常
     * value 切入点表达式
     * throwing 自定义变量，表示目标方法抛出的异常，变量名必须和通知方法的形参名一样
     */
    @AfterThrowing(value = "studentPointcut()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        System.out.println("AfterThrowing ex=" + ex.getMessage());
        // 异常发生时，可以记录异常信息
        // 发送短信、邮件通知
    }

    /**
     * Pointcut定义和管理切入点，不是通知注解
     * 放在方法上面，这个方法相当于切入点表达式的别名
     * 其他通知注解可以使用这个方法名，表明使用了这个切入点表达式
     * 属性：
     * value 切入点表达式
     * 相当于
     */
    @Pointcut("execution(* *..OrderServiceImpl.createStudent(..))")
    private void studentPointcut() {
        //无需代码
    }
}
