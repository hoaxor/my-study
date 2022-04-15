package com.hyh.jmm.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AccumulatorTest {
    public static void main(String[] args) {
        demo(() -> new AtomicLong(0), AtomicLong::getAndIncrement);
        demo(LongAdder::new, LongAdder::increment);
    }

    /**
     * @param adderSupplier 提供累加器
     * @param action        执行累加操作
     */
    public static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T accumulator = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(accumulator);
                }
            }));
        }
        long start = System.nanoTime();
        //启动所有线程
        ts.forEach(Thread::start);
        //等待所有线程执行完毕
        ts.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(accumulator + " cost:" + (System.nanoTime() - start) / 1000_000 + "ms");
    }
}
