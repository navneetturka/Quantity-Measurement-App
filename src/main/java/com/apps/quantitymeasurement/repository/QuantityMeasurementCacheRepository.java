package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementCacheRepository.class);

    private static QuantityMeasurementCacheRepository instance;
    private final List<QuantityMeasurementEntity> cache;

    private QuantityMeasurementCacheRepository() {
        this.cache = new ArrayList<>();
        logger.info("QuantityMeasurementCacheRepository initialized.");
    }

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        cache.add(entity);
        logger.debug("Saved to cache. Total: {}", cache.size());
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getAllMeasurements() {
        return Collections.unmodifiableList(new ArrayList<>(cache));
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getMeasurementsByOperation(
            String operation) {
        if (operation == null) throw new IllegalArgumentException("Operation cannot be null");
        return Collections.unmodifiableList(
                cache.stream()
                        .filter(e -> operation.equalsIgnoreCase(e.getOperation()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getMeasurementsByType(
            String measurementType) {
        if (measurementType == null)
            throw new IllegalArgumentException("Measurement type cannot be null");
        return Collections.unmodifiableList(
                cache.stream()
                        .filter(e -> measurementType.equalsIgnoreCase(
                                e.getThisMeasurementType()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public synchronized int getTotalCount() {
        return cache.size();
    }

    @Override
    public synchronized void deleteAll() {
        int count = cache.size();
        cache.clear();
        logger.info("Deleted {} entities from cache.", count);
    }

    /** Kept for UC15 test backward compatibility */
    public synchronized void clear() {
        deleteAll();
    }

    @Override
    public String getPoolStatistics() {
        return "CacheRepository[size=" + cache.size() + ", type=in-memory]";
    }
}