package com.hyh.jucutil.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author : huang.yaohua
 * @date : 2022/4/17 18:10
 */
@Slf4j(topic = "timer")
public class TimerTest {
    public static void main(String[] args) {
        test4();
    }

    public static void test1() {
        Timer timer = new Timer();

        TimerTask timerTask1 = new TimerTask() {

            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("11111111");
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {

                log.debug("222222222");
            }
        };
        log.debug("start...");
        // 使用timer添加两个任务，希望它们在1s后执行
        timer.schedule(timerTask1, 1_000);
        // timer内部使用一个线程来顺序执行队列中的任务，因为timerTask1的延时影响到了timerTask2的执行，
        // 若前面的异常发生了异常会影响后面任务的执行
        timer.schedule(timerTask2, 1_000);
    }

    public static void test2() {
        //可以设置多个工作线程，使得多个任务同时执行
        //corePoolSize设为1时，多个任务串行执行
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1,
                r -> new Thread(null, r, "schedule thread"));

        scheduledExecutorService.schedule(() -> {
            log.debug("task 1");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //任务异常不会影响后续任务的执行
            log.debug("{}", 1 / 0);
        }, 1, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            log.debug("task 2");
        }, 1, TimeUnit.SECONDS);

//        scheduledExecutorService.scheduleAtFixedRate();
    }

    public static void test3() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        //延迟计算从任务开始时
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.debug("fix rate start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("fix rate end");

        }, 0, 2, TimeUnit.SECONDS);
    }

    public static void test4() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        //延迟计算从任务结束时
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            log.debug("fix rate start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("fix rate end");

        }, 0, 2, TimeUnit.SECONDS);
    }
}
