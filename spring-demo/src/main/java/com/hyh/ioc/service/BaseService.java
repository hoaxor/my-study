package com.hyh.ioc.service;

import com.hyh.ioc.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : huang.yaohua
 * @date : 2022/6/23 11:44
 */
public class BaseService<T> {
    @Autowired
    private BaseDAO<T> baseDAO;

    public void save(T t) {
        baseDAO.save(t);
    }
}
