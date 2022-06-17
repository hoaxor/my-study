package com.hyh.springmvcdemo.mybatis.mapper;

import com.hyh.springmvcdemo.mybatis.model.OmOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author : huang.yaohua
 * @date : 2022/6/17 11:51
 */
//@Mapper()
public interface OmOrderMapper {

    //    @Select("select id, order_code orderCode from om_order where id = #{id}")
    OmOrder getOmOrder(Integer id);

    OmOrder getOmOrder2(Integer id, String orderState);

    OmOrder getOmOrder3(Map<String, Object> param);

    OmOrder getOmOrder4(OmOrder omOrder);

    OmOrder getOmOrder5(@Param("id") Integer id, @Param("order") OmOrder omOrder);

    OmOrder getOmOrder6(@Param("id") Integer id, @Param("orderState") String orderState);
}
