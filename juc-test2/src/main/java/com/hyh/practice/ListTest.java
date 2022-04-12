package com.hyh.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : huang.yaohua
 * @date : 2022/4/11 15:16
 */
public class ListTest {
    public static final List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            list.add("sfdasfdasdfasdfdsafdsafdsafdsafdsfafdsfsdafdasfsad" + i);
        }
        while (true){
            
        }
    }
}
