package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;

    private final List<QuantityMeasurementEntity> quantityMeasurementEntityCache;

    private QuantityMeasurementCacheRepository() {
        this.quantityMeasurementEntityCache = new ArrayList<>();
    }

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }

        return instance;
    }

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        quantityMeasurementEntityCache.add(entity);
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getAllMeasurements() {
        return Collections.unmodifiableList(
                new ArrayList<>(quantityMeasurementEntityCache)
        );
    }

    public synchronized void clear() {
        quantityMeasurementEntityCache.clear();
    }
}