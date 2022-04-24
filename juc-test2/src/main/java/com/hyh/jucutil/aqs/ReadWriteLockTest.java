package com.hyh.jucutil.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : huang.yaohua
 * @date : 2022/4/23 19:38
 */
@Slf4j(topic = "readWriteLockTest")
public class ReadWriteLockTest {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    /**
     * 读-读不互斥
     */
    public static void test1() {
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            log.debug("{}", dataContainer.read());
        }).start();

        new Thread(() -> {
            log.debug("{}", dataContainer.read());
        }).start();
    }

    /**
     * 读-写互斥
     */
    public static void test2() throws InterruptedException {
        DataContainer dataContainer = new DataContainer();

        new Thread(dataContainer::write).start();

        TimeUnit.MILLISECONDS.sleep(100);

        new Thread(() -> {
            log.debug("{}", dataContainer.read());
        }).start();
    }
}

@Slf4j(topic = "dataContainer")
class DataContainer {
    private Object data;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    public Object read() {
        log.debug("获取读锁");
        readLock.lock();
        try {
            log.debug("read");
            return data;
        } finally {
            log.debug("释放读锁");
            readLock.unlock();
        }
    }

    public void write() {
        log.debug("获取写锁");
        writeLock.lock();
        try {
            log.debug("write");
            this.data = "1";
        } finally {
            log.debug("释放写锁");
            writeLock.unlock();
        }
    }
}

class ObjectSon{
    @Override
    public String toString() {
        return super.toString();
    }
}
