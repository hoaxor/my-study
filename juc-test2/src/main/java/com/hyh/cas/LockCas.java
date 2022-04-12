package com.hyh.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "lockCas")
public class LockCas {

    /**
     * 0 表示无锁
     * 1 表示有锁
     */
    public final AtomicInteger lock = new AtomicInteger(0);

    public void lock() {
        do {
        } while (!lock.compareAndSet(0, 1));
    }

    public void unlock() {
        lock.set(0);
        log.debug("unlock...");
    }

    public static void main(String[] args) {
        LockCas lock = new LockCas();
        new Thread(() -> {
            log.debug("start...");
            lock.lock();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();

        new Thread(() -> {
            log.debug("start...");
            lock.lock();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();

    }
}
