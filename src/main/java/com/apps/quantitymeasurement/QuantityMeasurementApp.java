package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.util.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuantityMeasurementApp {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementApp.class);

    private static QuantityMeasurementApp instance;

    public final QuantityMeasurementController controller;
    public final IQuantityMeasurementRepository repository;

    private QuantityMeasurementApp() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        logger.info("Initializing app [repository.type={}]",
                config.getRepositoryType());

        this.repository = createRepository(config);
        IQuantityMeasurementService service = createService(repository);
        this.controller = createController(service);

        logger.info("App initialized. Repository: {}",
                repository.getClass().getSimpleName());
    }

    public static synchronized QuantityMeasurementApp getInstance() {
        if (instance == null) instance = new QuantityMeasurementApp();
        return instance;
    }

    public static synchronized void resetInstance() {
        if (instance != null) instance.closeResources();
        instance = null;
        ApplicationConfig.reset();
    }

    private IQuantityMeasurementRepository createRepository(
            ApplicationConfig config) {
        if (config.isDatabaseRepository()) {
            logger.info("Using QuantityMeasurementDatabaseRepository.");
            return new QuantityMeasurementDatabaseRepository();
        }
        logger.info("Using QuantityMeasurementCacheRepository.");
        return QuantityMeasurementCacheRepository.getInstance();
    }

    private IQuantityMeasurementService createService(
            IQuantityMeasurementRepository repo) {
        return new QuantityMeasurementServiceImpl(repo);
    }

    private QuantityMeasurementController createController(
            IQuantityMeasurementService svc) {
        return new QuantityMeasurementController(svc);
    }

    public QuantityMeasurementController getController() { return controller; }
    public IQuantityMeasurementRepository getRepository() { return repository; }

    public void closeResources() {
        logger.info("Closing application resources...");
        if (repository != null) repository.releaseResources();
    }

    public void deleteAllMeasurements() {
        logger.info("Deleting all measurements...");
        repository.deleteAll();
    }

    public static void main(String[] args) {
        logger.info("=== Quantity Measurement App UC16 Starting ===");

        QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();
        QuantityMeasurementController ctrl = app.getController();

        // Compare
        boolean eq = ctrl.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        logger.info("1 FEET == 12 INCHES? {}", eq);

        // Convert
        QuantityDTO converted = ctrl.performConversion(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));
        logger.info("1 FEET in INCHES = {}", converted);

        // Add
        QuantityDTO added = ctrl.performAddition(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));
        logger.info("1 FEET + 12 INCHES in FEET = {}", added);

        // Temperature
        QuantityDTO fahr = ctrl.performConversion(
                new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
        logger.info("100 CELSIUS = {} FAHRENHEIT", fahr);

        // Subtract
        QuantityDTO diff = ctrl.performSubtraction(
                new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM));
        logger.info("5 KG - 2 KG = {}", diff);

        // Divide
        double ratio = ctrl.performDivision(
                new QuantityDTO(10.0, QuantityDTO.VolumeUnit.LITRE),
                new QuantityDTO(5.0,  QuantityDTO.VolumeUnit.LITRE));
        logger.info("10 L / 5 L = {}", ratio);

        // Report
        List<QuantityMeasurementEntity> all = app.getRepository().getAllMeasurements();
        logger.info("Total measurements stored: {}", all.size());
        logger.info("Pool stats: {}", app.getRepository().getPoolStatistics());

        // Cleanup
        app.deleteAllMeasurements();
        logger.info("After deleteAll, count = {}",
                app.getRepository().getTotalCount());

        app.closeResources();
        logger.info("=== App Shutdown Complete ===");
    }
}