package com.hyh.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @author : huang.yaohua
 * @date : 2022/4/24 10:08
 */
public class JDBCUtil {
    private static JdbcTemplate jdbcTemplate;

    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static {
        init();
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public static void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/spring-druid.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
