package com.hyh.springmvcdemo.mybatis.service;

import com.hyh.springmvcdemo.mybatis.mapper.OmOrderMapper;
import com.hyh.springmvcdemo.mybatis.model.OmOrder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : huang.yaohua
 * @date : 2022/6/17 11:56
 */
public class OmOrderService {
    private OmOrderMapper omOrderMapper;

    public OmOrder getOmOrder(int id) {
        return omOrderMapper.getOmOrder(id);
    }

}
