package com.hyh.ioc.setinjection;

/**
 * @author : huang.yaohua
 * @date : 2022/5/15 20:05
 */
public class School {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                '}';
    }
}
