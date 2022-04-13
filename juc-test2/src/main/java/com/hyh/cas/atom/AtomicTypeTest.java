package com.hyh.cas.atom;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "atomic")
public class AtomicTypeTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        log.debug(atomicInteger.incrementAndGet() + "");

        System.out.println(atomicInteger.updateAndGet(x -> x * 10));

        System.out.println(atomicInteger.updateAndGet(x ->
                x / 10
        ));
    }
}
