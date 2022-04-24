package com.hyh.practice;

import com.hyh.dao.RegionDAO;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : huang.yaohua
 * @date : 2022/4/24 9:28
 */
@Slf4j(topic = "readWriteDAOTest")
public class ReadWriteLockDAOTest {
    public static void main(String[] args) {
        RegionDAO regionDAO = new RegionDAO();
        test1(regionDAO);

    }

    /**
     * 无缓存
     * 每次都从数据库读
     */
    public static void test1(RegionDAO regionDAO) {
        Map<String, Object> param = new HashMap<>();
        param.put("communityId", "-977737102");
        log.debug("========================query========================");
        Map<String, Object> map = regionDAO.queryRegion(param);
        System.out.println(map);
        map = regionDAO.queryRegion(param);
        System.out.println(map);
        map = regionDAO.queryRegion(param);
        System.out.println(map);
        log.debug("========================update========================");

        regionDAO.update(param);
        map = regionDAO.queryRegion(param);
        System.out.println(map);
    }
    
    
}


