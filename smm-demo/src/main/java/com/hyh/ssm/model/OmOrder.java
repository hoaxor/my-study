package com.hyh.ssm.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private String orderState;

    private User user;

    private List<OmProductAttr> attrs;
}
