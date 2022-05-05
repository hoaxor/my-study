package com.hyh.jucutil.map;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 
 * 需使用jdk1.7
 *
 * @author : huang.yaohua
 * @date : 2022/5/1 21:38
 */
@Slf4j(topic = "hashMapTest")
public class HashMapTest {
    public static void main(String[] args) throws InterruptedException {
        // 什么情况下触发死链 infinite loop
        final HashMap<MyKey, String> map = new HashMap<>(1);
        MyKey key = new MyKey();
        map.put(key, "1");
        map.put(new MyKey(), "2");

        //map 初始为 2-1
        //使map达到扩容条件
        //需使用调试模式触发死链
        //t1、t2同时触发扩容操作
        //t1 停在transfer Entry<K,V> next = e.next;代码后 , 此时 e 为 2 next 为 1
        //t2 先完成扩容操作 map变为 4-1-2
        //t1 继续扩容，2.next指向 newTable[i] 把 2放到 newTable[i]链表首个位置，e 赋值为 1
        // e 此时变为为 1
        // 重复上一步操作，1.next指向2，1放到newTable[i]链表首个位置， e赋值为 next,由于t2已经修改了1.next指向2所以 e被赋值为2
        // 重复上一步操作，由于2.next=null所以扩容正常退出
        // table = newTable
        Thread t1 = new Thread(() -> map.put(new MyKey(), "3"), "t1");
        t1.start();

        Thread t2 = new Thread(() -> map.put(new MyKey(), "4"), "t2");
        t2.start();
        t1.join();
        t2.join();
        //由于map中已经形成死链，1-2-1，查找不存在的key会使查找进入死循环
        System.out.println(map.get(new MyKey()));
    }
}

class MyKey {
    /*hashCode 的常规协定是：
    在 Java 应用程序执行期间，在同一对象上多次调用 hashCode 方法时，必须一致地返回相同的整数，
    前提是对象上 equals 比较中所用的信息没有被修改。从某一应用程序的一次执行到同一应用程序的另一次执行，该整数无需保持一致。
    如果根据 equals(Object) 方法，两个对象是相等的，那么在两个对象中的每个对象上调用 hashCode 方法都必须生成相同的整数结果。
    以下情况不 是必需的：如果根据 equals(java.lang.Object) 方法，两个对象不相等，那么在两个对象中的任一对象上调用 hashCode 方法必定会生成不同的整数结果。但是，程序员应该知道，为不相等的对象生成不同整数结果可以提高哈希表的性能。
    实际上，由 Object 类定义的 hashCode 方法确实会针对不同的对象返回不同的整数。
    （这一般是通过将该对象的内部地址转换成一个整数来实现的，但是 JavaTM 编程语言不需要这种实现技巧。）
    当equals方法被重写时，通常有必要重写 hashCode 方法，以维护 hashCode 方法的常规协定，该协定声明相等对象必须具有相等的哈希码。*/
    @Override
    public int hashCode() {
        //使得所有key都落在同一桶中
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
