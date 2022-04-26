package com.hyh.dao.test;

import com.hyh.dao.WorkOrderDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author : huang.yaohua
 * @date : 2022/4/26 15:34
 */
@Slf4j(topic = "workOrderTest")
public class WorkDAOTest {
    static WorkOrderDAO workOrderDAO = new WorkOrderDAO();

    public static void main(String[] args) {
        new Thread(() -> {
            getAuditOrder("153", "hyh1");
        }).start();
        new Thread(() -> {
            getAuditOrder("153", "hyh2");
        }).start();
    }

    private static void getAuditOrder(String extState, String partyName) {
        List<Map<String, Object>> auditWorkOrder = workOrderDAO.getAuditWorkOrder(extState, 10);
        log.debug("{}", auditWorkOrder);
        for (Map<String, Object> workOrder : auditWorkOrder) {
            String[] ids = MapUtils.getString(workOrder, "ids").split(",");
//            String[] orderIds = MapUtils.getString(workOrder, "orderIds").split(",");
            List<Long> collect = Arrays.stream(ids).map(Long::parseLong).collect(Collectors.toList());
            try {
                workOrderDAO.lockWorkOrders(collect);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                continue;
            }
            log.debug("get lock {}", collect);

            for (int i = 0; i < ids.length; i++) {
                Long id = collect.get(i);
                workOrderDAO.updateWorkOrder(id, partyName);
            }
        }
    }
}
