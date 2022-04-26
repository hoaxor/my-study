package com.hyh.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CachedBaseDAO extends BaseDAO {

    //sql语句+参数作为key
    //HashMap线程不安全
    private Map<SqlPair, Map<String, Object>> cache = new HashMap<>();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    @Override
    public Map<String, Object> query(String sql, Object... args) {
        SqlPair sqlPair = new SqlPair(sql, args);
        Map<String, Object> map;
        readLock.lock();
        try {
            map = cache.get(sqlPair);
        } finally {
            readLock.unlock();
        }
        if (null != map) {
            return map;
        }


        writeLock.lock();
        try {
            //要使用双重检测
            // 若多个线程同时来查数据，
            // 首个线程读取成功后缓存会被更新，可以直接从缓存查询数据
            map = cache.get(sqlPair);
            if (null != map) {
                return map;
            }
            map = super.query(sql, args);
            cache.put(sqlPair, map);
            return map;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int update(String sql, Object... args) {
        //先更新后清除缓存
        //先清除缓存后更新数据容易造成更大的数据不一致问题
        writeLock.lock();
        try {
            int update = super.update(sql, args);
            cache.clear();
            return update;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 缓存key
     */
    class SqlPair {
        private String sql;

        private Object[] args;

        public SqlPair(String sql, Object[] args) {
            this.sql = sql;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SqlPair sqlPair = (SqlPair) o;
            return sql.equals(sqlPair.sql) && Arrays.equals(args, sqlPair.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(sql);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }
}
