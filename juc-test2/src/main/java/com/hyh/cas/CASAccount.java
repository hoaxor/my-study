package com.hyh.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : huang.yaohua
 * @date : 2022/4/10 21:08
 */
public class CASAccount implements Account {
    private AtomicInteger balance;

    public CASAccount(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
//        balance.addAndGet(amount);
        while (true) {
            int prev = balance.get();

            int next = prev - amount;

            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}
