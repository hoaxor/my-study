package com.hyh.jucutil.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : huang.yaohua
 * @date : 2022/4/26 22:46
 */
@Slf4j(topic = "countDown")
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        log.debug("count {}", countDownLatch.getCount());
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("count {}", countDownLatch.getCount());
            countDownLatch.countDown();
        }).start();


        //计数变为0之前会一直阻塞在此
        countDownLatch.await();
        log.debug("countDown release");
    }

    public static void test2() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(10,
                10, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        String[] s = new String[10];
        Random random = new Random();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int j = 0; j < s.length; j++) {
            int k = j;
            service.submit(() -> {
                for (int i = 0; i <= 100; i++) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s[k] = i + "%";
                    //这种方式打印，前面打印的结果会覆盖后面的
                    System.out.print("\r" + Arrays.toString(s));
                }
                countDownLatch.countDown();
            });
        }

        service.shutdown();
        countDownLatch.await();
        System.out.println("\ngame start");
    }
}
