package com.hyh.springbootdemo.consts;

/**
 * @author : huang.yaohua
 * @date : 2022/5/31 14:50
 */
public enum HttpMethod {
    GET,
    DELETE,
    POST,
    PUT;

    public static void main(String[] args) {
        System.out.println(HttpMethod.PUT.name());
        System.out.println(HttpMethod.PUT.ordinal());
    }
}
