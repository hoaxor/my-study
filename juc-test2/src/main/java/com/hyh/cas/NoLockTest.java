package com.hyh.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 用不加锁的方式实现余额的正确管理
 *
 * @author : huang.yaohua
 * @date : 2022/4/10 20:50
 */
@Slf4j(topic = "nolock")
public class NoLockTest {
    public static void main(String[] args) {
        UnsafeAccount unsafeAccount = new UnsafeAccount(10000);
        SynchronizedAccount synchronizedAccount = new SynchronizedAccount(10000);
        CASAccount casAccount = new CASAccount(10000);
        Account.demo(unsafeAccount);

        Account.demo(synchronizedAccount);

        Account.demo(casAccount);

        Account.demo(new MyAtomicInteger(10000));
    }
}

interface Account {
    /**
     * 获取余额
     */
    Integer getBalance();

    /**
     * 取钱
     */
    void withdraw(Integer amount);

    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }

        long start = System.nanoTime();
        //启动每一个线程 每个线程 余额 - 10
        //若初始金额为 10000 那么最终输出0 才是正确的结果
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance() + " cost=" + (System.nanoTime() - start) / 1000_000 + "ms");
    }
}
