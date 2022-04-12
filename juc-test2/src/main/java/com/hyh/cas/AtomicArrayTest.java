package com.hyh.cas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AtomicArrayTest {


    public static void main(String[] args) {
        demo(() -> new int[10],
                array -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array)));

        demo(() -> new AtomicIntegerArray(10),
                AtomicIntegerArray::length,
                AtomicIntegerArray::getAndIncrement,
                array -> System.out.println(array.toString()));
    }


    /**
     * 函数式接口
     * 1. supplier 无参有返回值
     * 2. function 一个参数有返回值  (x)->返回值； BiFunction: (x,y)->返回值；
     * 3. consumer 一个参数有返回值  (x)->void；  BiConsumer: (x,y)->void；
     *
     * @param arraySupplier  提供一个数组
     * @param lengthFunction 获取数组长度
     * @param putConsumer    操作数组元素，入参：数组和下标
     * @param printConsumer  操作数组元素，入参：数组
     * @param <T>            tt
     */

    public static <T> void demo(Supplier<T> arraySupplier,
                                Function<T, Integer> lengthFunction,
                                BiConsumer<T, Integer> putConsumer,
                                Consumer<T> printConsumer) {
        List<Thread> threadList = new ArrayList<>();

        T t = arraySupplier.get();

        Integer length = lengthFunction.apply(t);

        for (int i = 0; i < length; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(t, j % length);
                }
            }));
        }

        threadList.forEach(Thread::start);
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printConsumer.accept(t);
    }
}
