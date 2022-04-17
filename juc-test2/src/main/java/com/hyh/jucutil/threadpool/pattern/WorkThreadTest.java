package com.hyh.jucutil.threadpool.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : huang.yaohua
 * @date : 2022/4/17 17:23
 */
@Slf4j(topic = "workThread")
public class WorkThreadTest {
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    /**
     * 点餐任务：接受客户点餐、上菜
     * 做菜任务：做菜
     * 点餐任务和做菜任务 共用了一个线程池，当工作线程全被点餐任务占据时会出现做菜任务无线等待执行的情况
     * 做菜任务进入饥饿状态
     * 因此，不同任务应该使用不同线程池
     */
    public static void test1() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        executor.submit(() -> {
            log.debug("开始点餐");
            Future<String> future = executor.submit(() -> {
                log.debug("开始做菜");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "红烧鱼";
            });

            try {
                log.debug("上菜：{}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();

        do {
        } while (executor.awaitTermination(1, TimeUnit.SECONDS));


    }
}
