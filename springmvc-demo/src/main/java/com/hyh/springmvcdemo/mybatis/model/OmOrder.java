package com.hyh.springmvcdemo.mybatis.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : huang.yaohua
 * @date : 2022/6/17 11:52
 */
@Data
public class OmOrder {
    private Integer id;

    private String orderCode;

    private LocalDateTime createDate;

    private String orderState;

}
