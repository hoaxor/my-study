package com.hyh.practice;

import com.hyh.dao.BaseDAO;
import com.hyh.dao.CachedBaseDAO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : huang.yaohua
 * @date : 2022/4/24 9:28
 */
@Slf4j(topic = "readWriteDAOTest")
public class ReadWriteLockDAOTest {
    public static void main(String[] args) {
        BaseDAO baseDAO = new BaseDAO();
        test1(baseDAO);
        BaseDAO cacheDAO = new CachedBaseDAO();
        test1(cacheDAO);

    }

    /**
     * 无缓存
     * 每次都从数据库读
     * 数据库压力大
     * 高性能、高并发
     * 数据库并发有限
     * 数据库读取数据速度低于读取缓存
     */
    public static void test1(BaseDAO regionDAO) {
//        Map<String, Object> param = new HashMap<>();
        String region = "-977737102";
//        param.put("communityId", region);
        log.debug("========================query========================");
        String sql = "select 1 from wfm_region where region_no = ?";
        Map<String, Object> map = regionDAO.query(sql, region);

        System.out.println(map);
        map = regionDAO.query(sql, region);
        System.out.println(map);
        map = regionDAO.query(sql, region);
        System.out.println(map);
        log.debug("========================update========================");

        regionDAO.update("update wfm_region set a.region_name = ? where region_no = ?", "hyh", region);
        map = regionDAO.query(sql, region);
        System.out.println(map);
    }
}


