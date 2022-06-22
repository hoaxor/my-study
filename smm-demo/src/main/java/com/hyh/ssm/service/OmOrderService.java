package com.hyh.ssm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyh.ssm.dao.OmOrderDAO;
import com.hyh.ssm.model.OmOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : huang.yaohua
 * @date : 2022/6/21 15:57
 */
@Service
@Slf4j(topic = "orderService")
public class OmOrderService {

    @Autowired
    private OmOrderDAO omOrderDAO;

    public OmOrder getOmOrder(Integer id) {
        return omOrderDAO.getOmOrder(id);
    }

    public PageInfo<OmOrder> getOrders(Integer pageSize, Integer pageIndex) {
        // 方式一
        PageHelper.offsetPage((pageIndex - 1) * pageSize, pageSize);
        // 方式二
        // PageHelper.startPage(1, 10);
        // 紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页。
        List<OmOrder> orders = omOrderDAO.getOrders();
        log.debug("orders={}", orders);

        return new PageInfo<>(orders);
    }

    public List<OmOrder> getOrders2(Integer pageSize, Integer pageIndex) {
        // 方式三
        List<OmOrder> orders = omOrderDAO.getOrders2(pageIndex, pageSize);
        log.debug("orders={}", orders);
        return orders;
    }

    /**
     * 使用ISelect接口
     */
    public PageInfo<OmOrder> getOrders3(Integer pageSize, Integer pageIndex) {
        //对应的lambda用法
        PageInfo<OmOrder> pageInfo = PageHelper.offsetPage((pageIndex - 1) * pageSize, pageSize)
                .doSelectPageInfo(() -> omOrderDAO.getOrders());
        log.debug("pageInfo={}", pageInfo);
        return pageInfo;
    }


}
