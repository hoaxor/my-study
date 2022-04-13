package com.hyh.cas;

import sun.misc.Unsafe;

public class MyAtomicInteger implements Account {
    private volatile int value;

    private static final long OFFSET;

    static final Unsafe UNSAFE;

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            OFFSET = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean compareAndSet(int expected, int next) {
        return UNSAFE.compareAndSwapInt(this, OFFSET, expected, next);
    }


    public void decrement(int delta) {
        do {

        } while (!compareAndSet(value, value - delta));
    }


    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }


}
