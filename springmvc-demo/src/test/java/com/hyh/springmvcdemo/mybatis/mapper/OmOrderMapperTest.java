package com.hyh.springmvcdemo.mybatis.mapper;

import com.hyh.springmvcdemo.mybatis.model.OmOrder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : huang.yaohua
 * @date : 2022/6/17 14:54
 */

class OmOrderMapperTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeAll
    public static void initSqlSessionFactory() throws IOException {
        // 从 XML 中构建 SqlSessionFactory
        // MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，使得从类路径或其它位置加载资源文件更加容易。
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test1() throws IOException {

        //sqlSessionFactory.openSession 获取SqlSession实例
        try (SqlSession session = sqlSessionFactory.openSession()) {
            //获取的是动态代理对象
            OmOrderMapper mapper = session.getMapper(OmOrderMapper.class);
            OmOrder omOrder = mapper.getOmOrder(352880);
            System.out.println(omOrder);
        }
    }

    @Test
    public void test2() throws IOException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OmOrderMapper mapper = session.getMapper(OmOrderMapper.class);
            OmOrder omOrder = mapper.getOmOrder2(352880, "10F");
            System.out.println(omOrder);
        }
    }

    @Test
    public void test3() throws IOException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OmOrderMapper mapper = session.getMapper(OmOrderMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("id", 352880);
            map.put("orderState", "10F");
            OmOrder omOrder = mapper.getOmOrder3(map);
            System.out.println(omOrder);
        }
    }

    @Test
    public void test4() throws IOException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OmOrderMapper mapper = session.getMapper(OmOrderMapper.class);
            OmOrder omOrder1 = new OmOrder();
            omOrder1.setId(352880);
            omOrder1.setOrderState("10F");
            OmOrder omOrder = mapper.getOmOrder4(omOrder1);
            System.out.println(omOrder);
        }
    }

    @Test
    public void test5() throws IOException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OmOrderMapper mapper = session.getMapper(OmOrderMapper.class);
            OmOrder omOrder1 = new OmOrder();
            int i = 352880;
            omOrder1.setId(i);
            omOrder1.setOrderState("10F");
            OmOrder omOrder = mapper.getOmOrder5(i, omOrder1);
            System.out.println(omOrder);
        }
    }

    @Test
    public void test6() throws IOException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OmOrderMapper mapper = session.getMapper(OmOrderMapper.class);
            Integer i = 352880;
            i = null;
            String orderState = "10F";
            OmOrder omOrder = mapper.getOmOrder6(i, orderState);
            System.out.println(omOrder);
        }
    }
}