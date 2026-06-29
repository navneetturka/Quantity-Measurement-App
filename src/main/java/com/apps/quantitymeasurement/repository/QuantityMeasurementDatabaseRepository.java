package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import com.apps.quantitymeasurement.util.ApplicationConfig;
import com.apps.quantitymeasurement.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);

    // ── SQL
    private static final String SQL_INSERT =
            "INSERT INTO quantity_measurement_entity " +
                    "(this_value, this_unit, this_measurement_type, " +
                    " that_value, that_unit, that_measurement_type, " +
                    " operation, result_value, result_unit, result_measurement_type, " +
                    " result_string, is_error, error_message, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM quantity_measurement_entity ORDER BY id ASC";

    private static final String SQL_SELECT_BY_OPERATION =
            "SELECT * FROM quantity_measurement_entity " +
                    "WHERE UPPER(operation) = UPPER(?) ORDER BY id ASC";

    private static final String SQL_SELECT_BY_TYPE =
            "SELECT * FROM quantity_measurement_entity " +
                    "WHERE UPPER(this_measurement_type) = UPPER(?) ORDER BY id ASC";

    private static final String SQL_COUNT =
            "SELECT COUNT(*) FROM quantity_measurement_entity";

    private static final String SQL_DELETE_ALL =
            "DELETE FROM quantity_measurement_entity";

    // ── Fields
    private final ConnectionPool    connectionPool;
    private final ApplicationConfig config;

    // ── Constructors

    /** Default constructor — uses singleton ApplicationConfig + ConnectionPool. */
    public QuantityMeasurementDatabaseRepository() {
        this(ApplicationConfig.getInstance(), ConnectionPool.getInstance());
    }

    /** DI constructor for testing with isolated pool/config. */
    public QuantityMeasurementDatabaseRepository(
            ApplicationConfig config,
            ConnectionPool connectionPool) {
        this.config         = config;
        this.connectionPool = connectionPool;
        if (config.isSchemaAutoCreate()) {
            initializeSchema();
        }
        logger.info("QuantityMeasurementDatabaseRepository ready. URL={}",
                config.getDbUrl());
    }

    // ── Schema

    private void initializeSchema() {
        String sql = loadSqlFromClasspath(config.getSchemaFile());
        if (sql == null || sql.isBlank()) {
            logger.warn("Schema file '{}' empty/missing.", config.getSchemaFile());
            return;
        }
        Connection conn = connectionPool.acquireConnection();
        try {
            for (String stmt : sql.split(";")) {
                String trimmed = stmt.trim();
                if (!trimmed.isEmpty()) {
                    try (Statement s = conn.createStatement()) {
                        s.execute(trimmed);
                    }
                }
            }
            logger.info("Schema initialized from '{}'.", config.getSchemaFile());
        } catch (SQLException e) {
            throw new DatabaseException("Schema initialization failed.", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    private String loadSqlFromClasspath(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) return null;
            try (BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
                return r.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            logger.error("Cannot read '{}': {}", path, e.getMessage());
            return null;
        }
    }

    // ── save

    @Override
    public void save(QuantityMeasurementEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");

        Connection conn = connectionPool.acquireConnection();
        try (PreparedStatement ps =
                     conn.prepareStatement(SQL_INSERT,
                             Statement.RETURN_GENERATED_KEYS)) {

            bindInsert(ps, entity);
            int affected = ps.executeUpdate();
            if (affected == 0)
                throw new DatabaseException("Save failed: no rows inserted.");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    logger.debug("Saved entity id={}, op={}",
                            keys.getLong(1), entity.getOperation());
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save entity: " + e.getMessage(), e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    private void bindInsert(PreparedStatement ps,
                            QuantityMeasurementEntity e) throws SQLException {
        ps.setDouble(1,  e.getThisValue());
        ps.setString(2,  e.getThisUnit());
        ps.setString(3,  e.getThisMeasurementType());
        ps.setObject(4,  e.getThatUnit() != null ? e.getThatValue() : null, Types.DOUBLE);
        ps.setString(5,  e.getThatUnit());
        ps.setString(6,  e.getThatMeasurementType());
        ps.setString(7,  e.getOperation());
        ps.setObject(8,  !e.hasError() ? e.getResultValue() : null, Types.DOUBLE);
        ps.setString(9,  e.getResultUnit());
        ps.setString(10, e.getResultMeasurementType());
        ps.setString(11, e.getResultString());
        ps.setBoolean(12, e.hasError());
        ps.setString(13, e.getErrorMessage());
        ps.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
    }

    // ── getAllMeasurements

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        Connection conn = connectionPool.acquireConnection();
        try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            List<QuantityMeasurementEntity> list = mapRows(rs);
            logger.debug("getAllMeasurements(): {} records.", list.size());
            return Collections.unmodifiableList(list);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all measurements.", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    // ── getMeasurementsByOperation

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(
            String operation) {
        if (operation == null)
            throw new IllegalArgumentException("Operation cannot be null");
        Connection conn = connectionPool.acquireConnection();
        try (PreparedStatement ps =
                     conn.prepareStatement(SQL_SELECT_BY_OPERATION)) {
            ps.setString(1, operation);
            try (ResultSet rs = ps.executeQuery()) {
                List<QuantityMeasurementEntity> list = mapRows(rs);
                logger.debug("getMeasurementsByOperation('{}'): {} records.",
                        operation, list.size());
                return Collections.unmodifiableList(list);
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to query by operation '" + operation + "'.", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    // ── getMeasurementsByType

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(
            String measurementType) {
        if (measurementType == null)
            throw new IllegalArgumentException("Measurement type cannot be null");
        Connection conn = connectionPool.acquireConnection();
        try (PreparedStatement ps =
                     conn.prepareStatement(SQL_SELECT_BY_TYPE)) {
            ps.setString(1, measurementType);
            try (ResultSet rs = ps.executeQuery()) {
                List<QuantityMeasurementEntity> list = mapRows(rs);
                logger.debug("getMeasurementsByType('{}'): {} records.",
                        measurementType, list.size());
                return Collections.unmodifiableList(list);
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to query by type '" + measurementType + "'.", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    // ── getTotalCount
    @Override
    public int getTotalCount() {
        Connection conn = connectionPool.acquireConnection();
        try (PreparedStatement ps = conn.prepareStatement(SQL_COUNT);
             ResultSet rs = ps.executeQuery()) {
            int count = rs.next() ? rs.getInt(1) : 0;
            logger.debug("getTotalCount() = {}", count);
            return count;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to count measurements.", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    // ── deleteAll

    @Override
    public void deleteAll() {
        Connection conn = connectionPool.acquireConnection();
        try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_ALL)) {
            int deleted = ps.executeUpdate();
            logger.info("deleteAll(): {} records removed.", deleted);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete all measurements.", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    // ── Resource management

    @Override
    public String getPoolStatistics() {
        return connectionPool.getPoolStatistics();
    }

    @Override
    public void releaseResources() {
        logger.info("Releasing database repository resources...");
        ConnectionPool.reset();
    }

    // ── ResultSet → Entity mapping

    private List<QuantityMeasurementEntity> mapRows(ResultSet rs)
            throws SQLException {
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        while (rs.next()) list.add(mapRow(rs));
        return list;
    }

    private QuantityMeasurementEntity mapRow(ResultSet rs) throws SQLException {
        boolean isError   = rs.getBoolean("is_error");
        String  operation = rs.getString("operation");

        QuantityDTO thisDto = new QuantityDTO(
                rs.getDouble("this_value"),
                rs.getString("this_unit"),
                rs.getString("this_measurement_type"));

        String thatUnit = rs.getString("that_unit");
        QuantityDTO thatDto = thatUnit != null
                ? new QuantityDTO(rs.getDouble("that_value"),
                thatUnit, rs.getString("that_measurement_type"))
                : null;

        if (isError) {
            return new QuantityMeasurementEntity(
                    thisDto, thatDto, operation,
                    rs.getString("error_message"), true);
        }

        String resultUnit = rs.getString("result_unit");
        if (resultUnit != null) {
            QuantityDTO resultDto = new QuantityDTO(
                    rs.getDouble("result_value"),
                    resultUnit,
                    rs.getString("result_measurement_type"));
            return thatDto == null
                    ? new QuantityMeasurementEntity(thisDto, operation, resultDto)
                    : new QuantityMeasurementEntity(thisDto, thatDto, operation, resultDto);
        }

        String resultString = rs.getString("result_string");
        if (resultString != null) {
            try {
                double numericResult = Double.parseDouble(resultString);
                return new QuantityMeasurementEntity(
                        thisDto, thatDto, operation, numericResult);
            } catch (NumberFormatException ex) {
                return new QuantityMeasurementEntity(
                        thisDto, thatDto, operation, resultString);
            }
        }
        return new QuantityMeasurementEntity(thisDto, thatDto, operation, "");
    }
}