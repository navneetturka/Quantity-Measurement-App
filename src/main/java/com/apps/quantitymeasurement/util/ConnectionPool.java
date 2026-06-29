package com.apps.quantitymeasurement.util;

import com.apps.quantitymeasurement.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {

    private static final Logger logger =
            LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;

    private final BlockingQueue<Connection> pool;
    private final ApplicationConfig config;
    private final AtomicInteger totalCreated  = new AtomicInteger(0);
    private final AtomicInteger totalAcquired = new AtomicInteger(0);
    private final AtomicInteger totalReleased = new AtomicInteger(0);
    private final AtomicInteger totalFailed   = new AtomicInteger(0);
    private volatile boolean closed = false;

    private ConnectionPool(ApplicationConfig config) {
        this.config = config;
        this.pool   = new ArrayBlockingQueue<>(config.getPoolSize());
        initializePool();
        logger.info("ConnectionPool ready. Size={}, URL={}",
                config.getPoolSize(), config.getDbUrl());
    }

    public static synchronized ConnectionPool getInstance() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        if (instance == null || instance.closed) {
            instance = new ConnectionPool(config);
        }
        return instance;
    }

    public static synchronized void reset() {
        if (instance != null) {
            instance.closeAllConnections();
        }
        instance = null;
    }

    private void initializePool() {
        loadDriver();
        for (int i = 0; i < config.getPoolSize(); i++) {
            try {
                pool.offer(createConnection());
                totalCreated.incrementAndGet();
            } catch (SQLException e) {
                logger.error("Failed to create connection #{}: {}", i + 1, e.getMessage());
                totalFailed.incrementAndGet();
            }
        }
        logger.info("Pool initialized: {}/{} connections available.",
                pool.size(), config.getPoolSize());
    }

    private void loadDriver() {
        try {
            Class.forName(config.getDbDriver());
        } catch (ClassNotFoundException e) {
            throw new DatabaseException(
                    "JDBC driver not found: " + config.getDbDriver(), e);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                config.getDbUrl(),
                config.getDbUsername(),
                config.getDbPassword());
    }

    public Connection acquireConnection() {
        if (closed) throw new DatabaseException("ConnectionPool is closed.");
        try {
            Connection conn = pool.poll(
                    config.getPoolTimeoutMs(), TimeUnit.MILLISECONDS);
            if (conn == null) {
                totalFailed.incrementAndGet();
                throw new DatabaseException(
                        "Pool exhausted — no connection available within "
                                + config.getPoolTimeoutMs() + "ms.");
            }
            if (!isValid(conn)) {
                closeQuietly(conn);
                conn = createConnection();
                totalCreated.incrementAndGet();
            }
            totalAcquired.incrementAndGet();
            return conn;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DatabaseException("Interrupted waiting for connection.", e);
        } catch (SQLException e) {
            totalFailed.incrementAndGet();
            throw new DatabaseException("Failed to create connection.", e);
        }
    }

    public void releaseConnection(Connection conn) {
        if (conn == null) return;
        if (closed) { closeQuietly(conn); return; }
        try {
            if (!conn.getAutoCommit()) conn.setAutoCommit(true);
            pool.offer(conn);
            totalReleased.incrementAndGet();
        } catch (SQLException e) {
            closeQuietly(conn);
        }
    }

    public synchronized void closeAllConnections() {
        closed = true;
        Connection conn;
        while ((conn = pool.poll()) != null) closeQuietly(conn);
        logger.info("All connections closed.");
    }

    private boolean isValid(Connection conn) {
        try { return conn != null && !conn.isClosed() && conn.isValid(2); }
        catch (SQLException e) { return false; }
    }

    private void closeQuietly(Connection conn) {
        try { if (conn != null) conn.close(); }
        catch (SQLException ignored) {}
    }

    public String getPoolStatistics() {
        return String.format(
                "ConnectionPool[available=%d, created=%d, acquired=%d, " +
                        "released=%d, failed=%d, closed=%b]",
                pool.size(), totalCreated.get(), totalAcquired.get(),
                totalReleased.get(), totalFailed.get(), closed);
    }

    public int getAvailableConnections() { return pool.size(); }
    public int getTotalCreated()         { return totalCreated.get(); }
    public int getTotalAcquired()        { return totalAcquired.get(); }
    public int getTotalReleased()        { return totalReleased.get(); }
    public boolean isClosed()            { return closed; }
}