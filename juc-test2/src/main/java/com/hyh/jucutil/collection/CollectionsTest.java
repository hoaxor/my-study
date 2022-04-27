package com.hyh.jucutil.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Slf4j(topic = "collections")
public class CollectionsTest {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        //使用了装饰器模式
        List<String> synchronizedList = Collections.synchronizedList(strings);
        synchronizedList.add("3");
        synchronizedList.add("4");
        synchronizedList.add("5");
        synchronizedList.add("6");
        synchronizedList.add("7");
        log.debug("{}", synchronizedList);
        Map<String, Object> map = new HashMap<>();
        map.put("a", "1");
        Map<String, Object> unmodifiableMap = Collections.unmodifiableMap(map);
//        unmodifiableMap.put("b", "2");
        log.debug("{}", unmodifiableMap);
        //打乱list内元素顺序
        Collections.shuffle(synchronizedList);

        log.debug("{}", synchronizedList);

    }
}
