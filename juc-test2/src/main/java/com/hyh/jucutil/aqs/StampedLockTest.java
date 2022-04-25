package com.hyh.jucutil.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

@Slf4j(topic = "stampedLock")
public class StampedLockTest {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test1() {
        StampedDataContainer dataContainer = new StampedDataContainer(0);
        new Thread(() -> {
            try {
                dataContainer.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                dataContainer.read(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void test2() throws InterruptedException {
        StampedDataContainer dataContainer = new StampedDataContainer(1);
        new Thread(() -> {
            try {
                dataContainer.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(500);
        new Thread(() -> {
            dataContainer.write(0);
        }).start();

    }
}

@Slf4j(topic = "container")
class StampedDataContainer {
    private int data;

    private final StampedLock lock = new StampedLock();

    public StampedDataContainer(int data) {
        this.data = data;
    }

    public int read(int readTime) throws InterruptedException {
        // 乐观锁 获取戳
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read lock {}", stamp);

        TimeUnit.SECONDS.sleep(1);

        //验证戳成功表明没有写操作，可以安全使用，否则要升级成读锁保证数据安全
        if (lock.validate(stamp)) {
            log.debug("optimistic lock finish {}", stamp);
            return data;
        }

        log.debug("upgrade to read lock");
        long readStamp = lock.readLock();
        log.debug("read lock {}", readStamp);
        try {
            TimeUnit.SECONDS.sleep(readTime);
            log.debug("read finish {}", readStamp);
            return data;
        } finally {
            log.debug("read unlock {}", readStamp);
            lock.unlockRead(readStamp);
        }
    }

    public void write(int data) {
        log.debug("write start");
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);

        try {
            this.data = data;
            log.debug("write finish {}", stamp);
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}
