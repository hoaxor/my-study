package com.hyh.springbootdemo.model;

import com.hyh.springbootdemo.anotation.MyComponent;

import java.time.LocalDateTime;

/**
 * @author : huang.yaohua
 * @date : 2022/5/29 20:00
 */
@MyComponent("dnf")
public class Dungeon {

    public void plaly() {
        System.out.println("playing dnf");
    }
}
