package com.hyh.ssm.dao;

import com.hyh.ssm.model.OmOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huang.yaohua
 * @date : 2022/6/21 15:58
 */
public interface OmOrderDAO {

    OmOrder getOmOrder(Integer id);


    int insertOmOrderBatch(List<OmOrder> orders);

    List<OmOrder> getOrders();

    /**
     * xml中不需要处理这两个参数pageNum、pageSize
     */
    List<OmOrder> getOrders2(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
