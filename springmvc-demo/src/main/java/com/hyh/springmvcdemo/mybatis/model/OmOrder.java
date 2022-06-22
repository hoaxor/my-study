package com.hyh.springmvcdemo.mybatis.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : huang.yaohua
 * @date : 2022/6/17 11:52
 */
@Data
public class OmOrder implements Serializable {
    private Integer id;

    private String orderCode;

    private LocalDateTime createDate;

    private String orderState;

    private User user;

    private List<OmProductAttr> attrs;
}
