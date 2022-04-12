package com.hyh.cas;

/**
 * @author : huang.yaohua
 * @date : 2022/4/10 21:05
 */
public class SynchronizedAccount implements Account {
    private Integer balance;

    public SynchronizedAccount(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }
}
