package com.hyh.springmvcdemo.mybatis.mapper;

import com.hyh.springmvcdemo.mybatis.model.OmOrder;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : huang.yaohua
 * @date : 2022/6/17 11:51
 */
//@Mapper()
public interface OmOrderMapper {

    /**
     * 一个参数不需要标注Param注解
     */
    //    @Select("select id, order_code orderCode from om_order where id = #{id}")
    OmOrder getOmOrder(Integer id);
    
    OmOrder getOmOrder11(Integer id);

    /**
     * 传入多个参数，且未指定Param注解时使用默认名arg0、arg1...获取参数值
     */
    OmOrder getOmOrder2(Integer id, String orderState);

    /**
     * 传入Map使用key获取参数值
     */
    OmOrder getOmOrder3(Map<String, Object> param);

    /**
     * 传入自定义POJO使用属性名获取参数值
     */
    OmOrder getOmOrder4(OmOrder omOrder);

    /**
     * 传入多个参数，指定Param注解时使用注解值获取参数值，简单类型直接使用注解值获取参数 POJO类型使用注解值+属性名获取参数值
     */
    OmOrder getOmOrder5(@Param("id") Integer id, @Param("order") OmOrder omOrder);

    OmOrder getOmOrder6(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * 返回值为Map，列名全大写作为Key，,mybatis使用HashMap封装结果
     */
    Map<String, Object> getOmOrderMap(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * 使用MapKey注解指定列名作为key
     */
    @MapKey("id")
    Map<Integer, OmOrder> getOmOrderMaps(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * 返回结合类型
     */
    List<OmOrder> getOmOrders(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * 使用级联属性
     */
    List<OmOrder> getOmOrderAndUser(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * association 的用法二： 分步查询
     */
    List<OmOrder> getOmOrderAndUser2(@Param("id") Integer id, @Param("orderState") String orderState);

    /**
     * collection用法：POJO包含集合类型使用 collection
     */
    List<OmOrder> getOmOrderAndProductAttrs(@Param("id") Integer id, @Param("orderState") String orderState);
}
