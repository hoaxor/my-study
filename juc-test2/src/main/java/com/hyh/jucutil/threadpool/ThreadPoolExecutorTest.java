package com.hyh.jucutil.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : huang.yaohua
 * @date : 2022/4/15 17:47
 */
@Slf4j(topic = "executor")
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }

    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //提交Callable任务，如果任务内部出现异常，会在future.get()时抛出
        Future<Boolean> fu = executorService.submit(() -> {
            log.debug("{}", 1 / 0);
            return true;
        });
        log.debug("future.get()={}", fu.get());
    }

    public static void test2() throws ExecutionException, InterruptedException {
        //        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//        executorService.allowCoreThreadTimeOut(true);
        log.debug("submit start");

        Future<String> future = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(1);
            return "test";
        });

        //保护性暂停模式
        log.debug(future.get());

        log.debug("invokeAll start");

        List<Future<Object>> futures = executorService.invokeAll(Arrays.asList(() -> {
            TimeUnit.SECONDS.sleep(1);
            return "1";
        }, () -> {
            TimeUnit.SECONDS.sleep(2);
            return "2";
        }));

        futures.forEach(f -> {
            try {
                log.debug("{}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        log.debug("invokeAll start");

        String s = executorService.invokeAny(Arrays.asList(() -> {
            TimeUnit.SECONDS.sleep(1);
            return "3";
        }, () -> {
            TimeUnit.SECONDS.sleep(2);
            return "4";
        }));

        log.debug(s);


        log.debug("shutdown now");
        executorService.shutdownNow();

//        executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        // 装饰器模式 ？ 委托模式 ？
//        Executors.newSingleThreadExecutor();
    }
}
