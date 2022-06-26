package com.hyh.ioc.dao;

/**
 * @author : huang.yaohua
 * @date : 2022/6/23 11:45
 */
public interface BaseDAO<T> {

    default void save(T t) {
        System.out.println("save" + t);
    }
}
