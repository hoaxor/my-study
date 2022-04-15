package com.hyh.jmm.cas;

/**
 * 存在线程安全问题
 *
 * @author : huang.yaohua
 * @date : 2022/4/10 21:01
 */
public class UnsafeAccount implements Account {

    private Integer balance;

    public UnsafeAccount(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(Integer amount) {
        balance -= amount;
    }
}
