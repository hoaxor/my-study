package com.hyh.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * @author : huang.yaohua
 * @date : 2022/4/24 10:14
 */
@Slf4j(topic = "baseDao")
public class BaseDAO {
    private final JdbcTemplate jdbcTemplate = JDBCUtil.getJdbcTemplate();

    public Map<String, Object> query(String sql, Object... args) {
        log.debug("sql={},args={}", sql, args);
        return jdbcTemplate.queryForMap(sql, args);
    }

    public int update(String sql, Object... args) {
        log.debug("sql={},args={}", sql, args);
        return jdbcTemplate.update(sql, args);
    }
}
