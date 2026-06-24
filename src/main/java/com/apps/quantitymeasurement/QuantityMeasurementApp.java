package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    private static QuantityMeasurementApp instance;

    public final QuantityMeasurementController controller;
    public final IQuantityMeasurementRepository repository;

    private QuantityMeasurementApp() {
        this.repository = createRepository();

        IQuantityMeasurementService service =
                createService(repository);

        this.controller =
                createController(service);
    }

    public static synchronized QuantityMeasurementApp getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementApp();
        }

        return instance;
    }

    private IQuantityMeasurementRepository createRepository() {
        return QuantityMeasurementCacheRepository.getInstance();
    }

    private IQuantityMeasurementService createService(
            IQuantityMeasurementRepository repository
    ) {
        return new QuantityMeasurementServiceImpl(repository);
    }

    private QuantityMeasurementController createController(
            IQuantityMeasurementService service
    ) {
        return new QuantityMeasurementController(service);
    }

    public QuantityMeasurementController getController() {
        return controller;
    }

    public IQuantityMeasurementRepository getRepository() {
        return repository;
    }

    public static void main(String[] args) {
        QuantityMeasurementApp app =
                QuantityMeasurementApp.getInstance();

        QuantityMeasurementController controller =
                app.getController();

        QuantityDTO oneFoot =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO twelveInches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        System.out.println(
                "1 FEET and 12 INCHES are equal: "
                        + controller.performComparison(
                        oneFoot,
                        twelveInches
                )
        );

        QuantityDTO convertedLength =
                controller.performConversion(
                        oneFoot,
                        new QuantityDTO(
                                0.0,
                                QuantityDTO.LengthUnit.INCHES
                        )
                );

        System.out.println(
                "1 FEET in INCHES = "
                        + convertedLength
        );

        QuantityDTO totalLength =
                controller.performAddition(
                        oneFoot,
                        twelveInches,
                        new QuantityDTO(
                                0.0,
                                QuantityDTO.LengthUnit.FEET
                        )
                );

        System.out.println(
                "1 FEET + 12 INCHES in FEET = "
                        + totalLength
        );

        QuantityDTO celsius =
                new QuantityDTO(
                        100.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO fahrenheit =
                controller.performConversion(
                        celsius,
                        new QuantityDTO(
                                0.0,
                                QuantityDTO.TemperatureUnit.FAHRENHEIT
                        )
                );

        System.out.println(
                "100 CELSIUS in FAHRENHEIT = "
                        + fahrenheit
        );

        System.out.println(
                "Saved measurements: "
                        + app.getRepository()
                        .getAllMeasurements()
                        .size()
        );
    }
}