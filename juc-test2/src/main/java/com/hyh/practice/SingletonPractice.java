package com.hyh.practice;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 1. 为什么加final 防止继承
 * 2. 实现了Serializable，如何防止反序列化
 *
 * @author : huang.yaohua
 * @date : 2022/4/10 19:50
 */
@Slf4j(topic = "singletonPractice")
public final class SingletonPractice implements Serializable {

    /**
     * 3. 为什么设置为私有，能否防止反射创建新的实例？
     */
    private SingletonPractice() {
    }

    /**
     * 4. 这样初始化能否保证对象创建时的线程安全？
     */
    private static final SingletonPractice INSTANCE = new SingletonPractice();

    /**
     * 5. 为什么提供静态方法而不是将INSTANCE设为public？
     */
    public static SingletonPractice getInstance() {
        return INSTANCE;
    }

    /**
     * 防止反序列化
     */
    public Object readResolve() {
        return INSTANCE;
    }
}
