package com.hyh.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

/**
 * @author : huang.yaohua
 * @date : 2022/4/24 10:14
 */
public class RegionDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate = JDBCUtil.getNamedParameterJdbcTemplate();

    public Map<String, Object> queryRegion(Map<String, Object> param) {
        return jdbcTemplate.queryForMap("SELECT A.* FROM WFM_REGION_BAK_HYH0919 A WHERE A.COMMUNITYID = :communityId", param);
    }

    public int update(Map<String, Object> param) {
        return jdbcTemplate.update("UPDATE WFM_REGION_BAK_HYH0919 A SET A.CREATE_DATE = SYSDATE WHERE A.COMMUNITYID = :communityId", param);
    }
}
