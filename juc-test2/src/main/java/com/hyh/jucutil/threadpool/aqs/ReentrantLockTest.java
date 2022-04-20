package com.hyh.jucutil.threadpool.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : huang.yaohua
 * @date : 2022/4/19 21:53
 */
@Slf4j(topic = "reentrantLock")
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();

        Condition condition = reentrantLock.newCondition();
        condition.await();
        condition.signal();
    }
}
