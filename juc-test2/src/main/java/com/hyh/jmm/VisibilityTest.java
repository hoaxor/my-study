package com.hyh.jmm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


@Slf4j(topic = "visibilityTest")
public class VisibilityTest {
    public static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test1() throws InterruptedException {
        new Thread(() -> {
            while (flag) {

            }

        }, "t").start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("set flag = false");
        flag = false;

    }

    static final Object lock = new Object();

    public static void test2() throws InterruptedException {
        new Thread(() -> {
            while (flag) {

            }

        }, "t").start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("set flag = false");
        synchronized (lock) {
            flag = false;
        }

    }


}
