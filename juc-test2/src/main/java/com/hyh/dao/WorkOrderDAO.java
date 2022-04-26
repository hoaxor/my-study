package com.hyh.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : huang.yaohua
 * @date : 2022/4/26 15:28
 */
public class WorkOrderDAO {
    private NamedParameterJdbcTemplate jdbcTemplate = JDBCUtil.getNamedParameterJdbcTemplate();

    public List<Map<String, Object>> getAuditWorkOrder(String extState, int count) {
        String sql = "SELECT ACC_NBR as accNbr FROM (SELECT DISTINCT A.ACC_NBR, A.STATE_DATE FROM WO_WORK_ORDER_ING A" +
                " WHERE A.EXT_STATE = :extState ORDER BY A.STATE_DATE) WHERE ROWNUM <= :count";
        Map<String, Object> map = new HashMap<>();
        map.put("extState", extState);
        map.put("count", count);
        List<String> list = jdbcTemplate.queryForList(sql, map, String.class);
        if (list.isEmpty()) {
            return null;
        }
        map.put("accounts", list);

        StringBuilder workOrderSql = new StringBuilder("SELECT a.acc_nbr accNbr,\n" +
                "       wm_concat(A.id) ids,\n" +
                "       wm_concat(A.BASE_ORDER_ID) AS orderIds\n" +
                "  FROM WO_WORK_ORDER_ING A\n" +
                " WHERE A.EXT_STATE = :extState\n" +
                "       AND A.ACC_NBR IN (:accounts)\n" +
                " GROUP BY a.acc_nbr");
        return jdbcTemplate.queryForList(workOrderSql.toString(), map);
    }

    public int updateWorkOrder(Long id, String partyName) {
        String sql = "update WO_WORK_ORDER_ING a set a.party_name = :partyName where a.id = :id";
        Map<String, Object> param = new HashMap<>();
        param.put("partyName", partyName);
        param.put("id", id);
        return jdbcTemplate.update(sql, param);
    }

    public List<Integer> lockWorkOrders(List<Long> ids)throws SQLException {
        String sqlStr = "SELECT ID FROM WO_WORK_ORDER WHERE ID IN (:ids) FOR UPDATE NOWAIT ";
        Map<String, Object> param = new HashMap<>();
        param.put("ids", ids);
        return jdbcTemplate.queryForList(sqlStr, param, Integer.class);
    }
}
