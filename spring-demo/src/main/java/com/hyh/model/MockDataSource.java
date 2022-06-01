package com.hyh.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : huang.yaohua
 * @date : 2022/5/31 16:09
 */
@Data
@AllArgsConstructor
public class MockDataSource {
    private String name;

    private boolean alive;

    public MockDataSource() {
        System.out.println("create datasource");
    }

    public void init() {
        System.out.println("init datasource");
    }

    public void destroy() {
        System.out.println("destroy datasource");
    }
}
