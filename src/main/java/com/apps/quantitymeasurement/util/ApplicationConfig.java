package com.apps.quantitymeasurement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final Logger logger =
            LoggerFactory.getLogger(ApplicationConfig.class);

    private static final String PROPERTIES_FILE = "application.properties";
    private static ApplicationConfig instance;
    private final Properties properties;

    private ApplicationConfig() {
        this.properties = new Properties();
        loadProperties();
        logger.info("ApplicationConfig initialized. Environment: {}",
                getEnvironment());
    }

    public static synchronized ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = null;
    }

    private void loadProperties() {
        try (InputStream input =
                     getClass().getClassLoader()
                             .getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                logger.warn("'{}' not found. Using defaults.", PROPERTIES_FILE);
                return;
            }
            properties.load(input);
            logger.info("'{}' loaded successfully.", PROPERTIES_FILE);
        } catch (IOException e) {
            logger.error("Failed to load '{}': {}", PROPERTIES_FILE, e.getMessage());
        }
    }

    private String get(String key, String defaultValue) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.trim().isEmpty()) {
            return sysProp.trim();
        }
        return properties.getProperty(key, defaultValue).trim();
    }

    private int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getEnvironment()      { return get("app.environment", "development"); }
    public String getRepositoryType()   { return get("repository.type", "cache"); }
    public boolean isDatabaseRepository() {
        return "database".equalsIgnoreCase(getRepositoryType());
    }
    public String getDbUrl()            { return get("db.url",
            "jdbc:h2:mem:quantitydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MySQL"); }
    public String getDbUsername()       { return get("db.username", "sa"); }
    public String getDbPassword()       { return get("db.password", ""); }
    public String getDbDriver()         { return get("db.driver", "org.h2.Driver"); }
    public int    getPoolSize()         { return getInt("db.pool.size", 10); }
    public int    getPoolTimeoutMs()    { return getInt("db.pool.timeout.ms", 5000); }
    public int    getIdleTimeoutMs()    { return getInt("db.pool.idle.timeout.ms", 60000); }
    public boolean isSchemaAutoCreate() { return Boolean.parseBoolean(
            get("db.schema.auto", "true")); }
    public String getSchemaFile()       { return get("db.schema.file", "db/schema.sql"); }

    @Override
    public String toString() {
        return "ApplicationConfig{env='" + getEnvironment()
                + "', repoType='" + getRepositoryType()
                + "', dbUrl='" + getDbUrl() + "'}";
    }
}