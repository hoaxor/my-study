package com.hyh.jmm.pattern;

import lombok.extern.slf4j.Slf4j;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author : huang.yaohua
 * @date : 2022/4/14 17:27
 */
public class FlyweightTest {
    public static void main(String[] args) {
        Pool pool = new Pool(3);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Connection connection = pool.getConnection();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pool.free(connection);
            }).start();
        }
    }
}

@Slf4j(topic = "pool")
class Pool {

    public static final int FREE = 0;

    public static final int BUSY = 1;
    /**
     * 连接池大小
     */
    private int poolSize;

    /**
     * 连接数组
     */
    private Connection[] connections;

    /**
     * 连接数组中每个连接的状态 1 忙 0 闲
     */
    private AtomicIntegerArray states;

    public Pool(int poolSize) {
        this.poolSize = poolSize;
        states = new AtomicIntegerArray(poolSize);
        connections = new MockConnection[poolSize];
        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnection("连接" + i);
        }
    }

    public Connection getConnection() {
        while (true) {
            for (int i = 0; i < connections.length; i++) {
                //获取空闲连接
                if (states.compareAndSet(i, FREE, BUSY)) {
                    Connection connection = connections[i];
                    log.debug("获取连接{}", connection);
                    return connection;
                }
            }
            log.debug("没有空闲连接，等待获取连接");
            //没有空闲连接进入等待
            //一直循环获取连接而不等待会占用CPU资源
            synchronized (this) {
                try {
                    //会出现 错过notify而陷入无限等待的情况
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void free(Connection conn) {
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == conn) {
                log.debug("释放连接{}", conn);
                states.set(i, FREE);
                synchronized (this) {
                    this.notifyAll();
                }
                break;
            }
        }
    }
}

@Slf4j(topic = "semaphorePool")
class SemaphorePool {

    public static final int FREE = 0;

    public static final int BUSY = 1;
    /**
     * 连接池大小
     */
    private int poolSize;

    /**
     * 连接数组
     */
    private Connection[] connections;

    /**
     * 连接数组中每个连接的状态 1 忙 0 闲
     */
    private AtomicIntegerArray states;

    private Semaphore semaphore;

    public SemaphorePool(int poolSize) {
        this.poolSize = poolSize;
        semaphore = new Semaphore(poolSize);
        states = new AtomicIntegerArray(poolSize);
        connections = new MockConnection[poolSize];
        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnection("连接" + i);
        }
    }

    public Connection getConnection() {
        try {
            // 获取资源，计数减一
            // `Semaphore`通过使用计数器来控制对共享资源的访问。
            // 如果计数器大于0，则允许访问。 如果为0，则拒绝访问，线程在此等待
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        for (int i = 0; i < connections.length; i++) {
            //获取空闲连接
            if (states.compareAndSet(i, FREE, BUSY)) {
                Connection connection = connections[i];
                log.debug("获取连接{}", connection);
                return connection;
            }
        }
        return null;
    }

    public void free(Connection conn) {
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == conn) {
                log.debug("释放连接{}", conn);
                states.set(i, FREE);
                // 释放资源，计数加一
                semaphore.release();
                break;
            }
        }
    }
}

/**
 * 假连接池
 */
class MockConnection implements Connection {

    private String name;

    public MockConnection(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MockConnection{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
