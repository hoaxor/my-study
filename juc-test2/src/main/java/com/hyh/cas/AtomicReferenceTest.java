package com.hyh.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : huang.yaohua
 * @date : 2022/4/12 17:50
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<String> atomicReference = new AtomicReference<>("a");

        System.out.println(atomicReference.get());

        atomicReference.compareAndSet("a", "b");

        System.out.println(atomicReference.get());

    }
}
