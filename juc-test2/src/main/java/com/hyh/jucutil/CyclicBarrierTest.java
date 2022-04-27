package com.hyh.jucutil;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j(topic = "cyclicBarrier")
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        testCyclicBarrier(3);
//        testCountDownLatch(3);
    }

    public static void testCyclicBarrier(int loopCount) {
        //CyclicBarrier 相比 countDownLatch ,可以重用，计数减为零后，计数重置为初始值
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            //计数减为零后会回调此方法
            log.debug("all task complete");
        });

        //要确保线程池大小和 cyclicBarrier计数大小相等
        //若不相等
        // 假如线程池大小大于CyclicBarrier计数
        //可能会出现这种情况： task1(执行1s)->task1(执行1s)->task2(执行2s)
        //没有按预想的task1->task2顺序执行
        ExecutorService ser = Executors.newFixedThreadPool(2);

        for (int i = 0; i < loopCount; i++) {
            ser.submit(() -> {
                log.debug("task1 start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cyclicBarrier.await();//计数减一 : 2 - 1
                    log.debug("task1 end");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            ser.submit(() -> {
                log.debug("task2 start");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cyclicBarrier.await();//计数减一 : 1 - 1
                    log.debug("task2 end");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

        }

        ser.shutdown();
    }

    public static void testCountDownLatch(int loopCount) throws InterruptedException {

        ExecutorService ser = Executors.newFixedThreadPool(2);
        for (int i = 0; i < loopCount; i++) {
            //CountDownLatch 不能重用，计数减为零后countDownLatch失效
            CountDownLatch countDownLatch = new CountDownLatch(2);

            ser.submit(() -> {
                log.debug("task1 start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                log.debug("task1 end");
            });

            ser.submit(() -> {
                log.debug("task2 start");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                log.debug("task2 end");
            });

            countDownLatch.await();
            log.debug("all task complete");
        }

        ser.shutdown();
    }
}
