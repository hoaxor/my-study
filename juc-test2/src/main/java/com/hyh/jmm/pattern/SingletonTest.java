package com.hyh.jmm.pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "singleton")
public class SingletonTest {
    private static SingletonTest INSTANTCE;

    /**
     * 尽可能缩小同步代码块范围
     */
    public static synchronized SingletonTest getSingleton() {
        if (INSTANTCE == null) {
            INSTANTCE = new SingletonTest();
        }
        return INSTANTCE;
    }

    /**
     * 相对上面的方法缩小了同步代码块，但是每次获取实例都需要加锁解锁
     */
    public static SingletonTest getSingleton2() {
        synchronized (SingletonTest.class) {
            if (INSTANTCE == null) {
                INSTANTCE = new SingletonTest();
            }
        }
        return INSTANTCE;
    }

    /**
     * 只有首次调用需要加锁，但因为第一个if判断在同步代码块外导致了一个非常隐蔽的问题，
     * 由于new SingletonTest()不是原子操作，执行时可能出现指令重排序的情况
     * 正常过程如下：
     * <p>
     * 1. 分配内存空间
     * 2. 初始化Singleton实例
     * 3. 赋值 instance 实例引用
     * <p>
     * 重排序以后可能会出现：
     * <p>
     * 1. 分配内存空间
     * 2. 赋值 instance 实例引用
     * 3. 初始化Singleton实例
     * 这样重排序并不影响单线程的执行结果，JVM是允许的。但是在多线程中就会出问题。
     * <p>
     * 当重排序以后，线程B 拿到了不为null 的instance实例引用，但是并没有被初始化，然后线程B使用了一个没有被初始化的对象引用，就出问题了。
     */
    public static SingletonTest getSingleton3() {
        if (INSTANTCE == null) {
            synchronized (SingletonTest.class) {
                if (INSTANTCE == null) {

                    INSTANTCE = new SingletonTest();
                }
            }
        }
        return INSTANTCE;
    }

    /**
     * 双重检测问题解决
     * 方案一
     * 内部类是懒加载，只有在第一次使用时才会初始化
     * 方案二
     * 添加volatile关键字
     */
    public static class InnerClass {
        public static final SingletonTest INSTANCE = new SingletonTest();
    }

    private SingletonTest() {

    }

}
