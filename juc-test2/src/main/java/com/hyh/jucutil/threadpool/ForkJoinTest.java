package com.hyh.jucutil.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author : huang.yaohua
 * @date : 2022/4/17 21:28
 */
@Slf4j(topic = "forkJoin")
public class ForkJoinTest {
    public static void main(String[] args) throws InterruptedException {
//        testMyTask(2000);
        testMyTask2(2000);
    }

    public static void testMyTask(int n) {
        // 1毫秒=100w纳秒
        long start = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        MyTask myTask = new MyTask(n);
        Integer compute = forkJoinPool.invoke(myTask);
        log.debug("{}", compute);
        log.debug("cost {}ms", (System.nanoTime() - start) / 1000_000);
    }

    public static void testMyTask2(int end) {
        // 1毫秒=100w纳秒
        long start = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        MyTask2 myTask = new MyTask2(1, end);
        Integer compute = forkJoinPool.invoke(myTask);
        log.debug("{}", compute);
        log.debug("cost {}ms", (System.nanoTime() - start) / 1000_000);
    }

}

/**
 * 计算1到n的和
 * 方式一
 * RecursiveAction 无返回值
 * RecursiveTask 有返回值
 */
@Slf4j(topic = "myTask")
class MyTask extends RecursiveTask<Integer> {

    private final int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            log.debug("if {}", n);
            return n;
        }
        // 求 1 到 n 的和 此任务拆分为
        // new MyTask(n) + new MyTask(n - 1) + new MyTask(n - 2)...new MyTask(1)
        MyTask task = new MyTask(n - 1);
        task.fork();
        log.debug("fork {},{}", n, task);

        int i = n + task.join();
        log.debug("join {},{}", n, task);
        return i;
    }
}

/**
 * 计算1到n的和
 * 方式二，优化使用二分法
 */
@Slf4j(topic = "myTask2")
class MyTask2 extends RecursiveTask<Integer> {

    private final int begin;

    private final int end;

    public MyTask2(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + ',' + end + '}';
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            log.debug("begin=end,{}", end);
            return end;
        }

        if (end - begin == 1) {
            log.debug("{}-{}=1,begin+end={}", end, begin, begin + end);
            return begin + end;
        }


        // 求 1 到 n 的和 此任务拆分为两部分
        int mid = (begin + end) / 2;
        MyTask2 task1 = new MyTask2(begin, mid);
        task1.fork();

        MyTask2 task2 = new MyTask2(mid + 1, end);
        task2.fork();

        log.debug("fork {} + {} = ?", task1, task2);

        int i = task1.join() + task2.join();
        log.debug("join {} + {} = {}", task1, task2, i);

        return i;
    }
}



