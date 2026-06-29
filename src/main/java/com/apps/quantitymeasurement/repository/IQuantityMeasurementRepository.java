package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);
    List<QuantityMeasurementEntity> getAllMeasurements();

    // ── UC16 new methods
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);
    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);
    int getTotalCount();
    void deleteAll();

    // ── Default extension points
    default String getPoolStatistics() {
        return "No connection pool in use.";
    }

    default void releaseResources() {
        // No-op; overridden by database implementation
    }
}