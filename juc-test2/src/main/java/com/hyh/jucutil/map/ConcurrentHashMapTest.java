package com.hyh.jucutil.map;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Slf4j(topic = "concurrentMapTest")
public class ConcurrentHashMapTest {
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) throws IOException, InterruptedException {
//        makeFile();
//        System.out.println(readFile("tmp/1.txt"));
        // HashMap非线程安全，统计单词出现次数不准确
        count(() -> new HashMap<String, Integer>(), (map, s) -> {
            for (String k : s) {
                Integer integer = map.get(k);
                map.put(k, integer == null ? 1 : integer + 1);
            }
        });
        ConcurrentHashMap<Object, Object> m = new ConcurrentHashMap<>();
        m.put("a", "1");
        m.get("");
        // 虽然ConcurrentHashMap是线程安全集合，但是组合操作不是原子操作，统计出来的数据也不准确
        // ConcurrentHashMap的get或put是原子操作，但组合不是
        count(() -> new ConcurrentHashMap<String, Integer>(), (map, s) -> {
            for (String k : s) {
                Integer integer = map.get(k);
                map.put(k, integer == null ? 1 : integer + 1);
            }
        });
        count(() -> new ConcurrentHashMap<String, LongAdder>(), (map, s) -> {
            for (String k : s) {
//                Integer integer = map.get(k);
//                map.put(k, integer == null ? 1 : integer + 1);
                //computeIfAbsent 如果key不存在，则根据计算生成value,然后将key、value放入map中
                // 返回原来map中的value或计算生成的value
                LongAdder longAdder = map.computeIfAbsent(k, ke -> new LongAdder());
                longAdder.increment();
            }
        });
    }

    /**
     * 生成测试文件
     */
    public static void makeFile() throws FileNotFoundException {
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);
        char[] chars = ALPHA.toCharArray();
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(c));
            }
        }
        //打乱字母顺序
        Collections.shuffle(list);

        for (int i = 0; i < ALPHA.length(); i++) {
            try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream("tmp/" + (i + 1) + ".txt")))) {
                String collect = String.join("\n", list.subList(i * count, (i + 1) * count));
                printWriter.print(collect);
            }
        }


    }


    public static List<String> readFile(String path) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                list.add(s);
            }
        }
        return list;
    }

    public static <T> void count(Supplier<Map<String, T>> supplier,
                                 BiConsumer<Map<String, T>, List<String>> biConsumer) throws InterruptedException {
        Map<String, T> map = supplier.get();
        CountDownLatch countDownLatch = new CountDownLatch(ALPHA.length());
        for (int i = 0; i < ALPHA.length(); i++) {
            int j = i;
            new Thread(() -> {
                List<String> s = null;
                try {
                    s = readFile("tmp/" + (j + 1) + ".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                biConsumer.accept(map, s);
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println(map);

    }
}
