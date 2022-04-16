package com.hyh.jucutil.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : huang.yaohua
 * @date : 2022/4/15 17:47
 */
@Slf4j(topic = "executor")
public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//        executorService.allowCoreThreadTimeOut(true);
        executorService.execute(() -> {
            log.debug("1");
        });
        executorService.execute(() -> {
            log.debug("2");
        });

        executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        // 装饰器模式 ？ 委托模式
        Executors.newSingleThreadExecutor();
    }
}
