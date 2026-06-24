package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.0001;

    private QuantityMeasurementCacheRepository repository;
    private QuantityMeasurementController controller;

    @BeforeEach
    public void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        repository.clear();
        controller = new QuantityMeasurementController(
                new QuantityMeasurementServiceImpl(repository)
        );
    }

    @Test
    public void testQuantityEntitySingleOperandConstruction() {
        QuantityDTO input =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO result =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        input,
                        "CONVERT",
                        result
                );

        assertEquals(1.0, entity.getThisValue(), EPSILON);
        assertEquals("FEET", entity.getThisUnit());
        assertEquals("LengthUnit", entity.getThisMeasurementType());
        assertEquals("CONVERT", entity.getOperation());
        assertEquals(12.0, entity.getResultValue(), EPSILON);
        assertEquals("INCHES", entity.getResultUnit());
        assertFalse(entity.hasError());
    }

    @Test
    public void testQuantityEntityBinaryOperandConstruction() {
        QuantityDTO first =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO second =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO result =
                new QuantityDTO(
                        2.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        first,
                        second,
                        "ADD",
                        result
                );

        assertEquals(1.0, entity.getThisValue(), EPSILON);
        assertEquals("FEET", entity.getThisUnit());
        assertEquals(12.0, entity.getThatValue(), EPSILON);
        assertEquals("INCHES", entity.getThatUnit());
        assertEquals("ADD", entity.getOperation());
        assertEquals(2.0, entity.getResultValue(), EPSILON);
        assertEquals("FEET", entity.getResultUnit());
        assertFalse(entity.isError());
    }

    @Test
    public void testQuantityEntityErrorConstruction() {
        QuantityDTO first =
                new QuantityDTO(
                        25.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO second =
                new QuantityDTO(
                        77.0,
                        QuantityDTO.TemperatureUnit.FAHRENHEIT
                );

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        first,
                        second,
                        "ADD",
                        "Temperature does not support ADD operation.",
                        true
                );

        assertTrue(entity.hasError());
        assertEquals("ADD", entity.getOperation());
        assertEquals(
                "Temperature does not support ADD operation.",
                entity.getErrorMessage()
        );
    }

    @Test
    public void testQuantityEntityToStringSuccess() {
        QuantityDTO input =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO result =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        input,
                        "CONVERT",
                        result
                );

        assertTrue(entity.toString().contains("CONVERT"));
        assertTrue(entity.toString().contains("result"));
        assertTrue(entity.toString().contains("INCHES"));
    }

    @Test
    public void testQuantityEntityToStringError() {
        QuantityDTO first =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO second =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        first,
                        second,
                        "ADD",
                        "Incompatible unit types",
                        true
                );

        assertTrue(entity.toString().contains("ADD"));
        assertTrue(entity.toString().contains("failed"));
        assertTrue(entity.toString().contains("Incompatible unit types"));
    }

    @Test
    public void testServiceCompareEqualitySameUnitSuccess() {
        QuantityDTO first =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO second =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        assertTrue(controller.performComparison(first, second));
    }

    @Test
    public void testServiceCompareEqualityDifferentUnitSuccess() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        assertTrue(controller.performComparison(feet, inches));
    }

    @Test
    public void testServiceCompareEqualityCrossCategoryReturnsFalse() {
        QuantityDTO length =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO weight =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        assertFalse(controller.performComparison(length, weight));
    }

    @Test
    public void testServiceConvertSuccess() {
        QuantityDTO feet =
                new QuantityDTO(
                        2.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO target =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO result =
                controller.performConversion(feet, target);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    public void testServiceAddSuccess() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO result =
                controller.performAddition(feet, inches);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    public void testServiceAddWithTargetUnitSuccess() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO target =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO result =
                controller.performAddition(feet, inches, target);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    public void testServiceAddUnsupportedOperationError() {
        QuantityDTO first =
                new QuantityDTO(
                        25.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO second =
                new QuantityDTO(
                        77.0,
                        QuantityDTO.TemperatureUnit.FAHRENHEIT
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performAddition(first, second)
        );
    }

    @Test
    public void testServiceSubtractSuccess() {
        QuantityDTO kilogram =
                new QuantityDTO(
                        5.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityDTO gram =
                new QuantityDTO(
                        2000.0,
                        QuantityDTO.WeightUnit.GRAM
                );

        QuantityDTO result =
                controller.performSubtraction(kilogram, gram);

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals("KILOGRAM", result.getUnit());
    }

    @Test
    public void testServiceSubtractWithTargetUnitSuccess() {
        QuantityDTO kilogram =
                new QuantityDTO(
                        5.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityDTO gram =
                new QuantityDTO(
                        2000.0,
                        QuantityDTO.WeightUnit.GRAM
                );

        QuantityDTO target =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.WeightUnit.GRAM
                );

        QuantityDTO result =
                controller.performSubtraction(kilogram, gram, target);

        assertEquals(3000.0, result.getValue(), EPSILON);
        assertEquals("GRAM", result.getUnit());
    }

    @Test
    public void testServiceDivideSuccess() {
        QuantityDTO first =
                new QuantityDTO(
                        10.0,
                        QuantityDTO.VolumeUnit.LITRE
                );

        QuantityDTO second =
                new QuantityDTO(
                        5.0,
                        QuantityDTO.VolumeUnit.LITRE
                );

        double result =
                controller.performDivision(first, second);

        assertEquals(2.0, result, EPSILON);
    }

    @Test
    public void testServiceDivideByZeroError() {
        QuantityDTO first =
                new QuantityDTO(
                        10.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO zero =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.LengthUnit.FEET
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performDivision(first, zero)
        );
    }

    @Test
    public void testControllerDemonstrateEqualitySuccess() {
        QuantityDTO yard =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.YARDS
                );

        QuantityDTO feet =
                new QuantityDTO(
                        3.0,
                        QuantityDTO.LengthUnit.FEET
                );

        assertTrue(controller.performComparison(yard, feet));
    }

    @Test
    public void testControllerDemonstrateConversionSuccess() {
        QuantityDTO kilogram =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityDTO target =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.WeightUnit.GRAM
                );

        QuantityDTO result =
                controller.performConversion(kilogram, target);

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    public void testControllerDemonstrateAdditionSuccess() {
        QuantityDTO litre =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.VolumeUnit.LITRE
                );

        QuantityDTO millilitre =
                new QuantityDTO(
                        1000.0,
                        QuantityDTO.VolumeUnit.MILLILITRE
                );

        QuantityDTO result =
                controller.performAddition(litre, millilitre);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals("LITRE", result.getUnit());
    }

    @Test
    public void testControllerDemonstrateAdditionError() {
        QuantityDTO length =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO weight =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performAddition(length, weight)
        );
    }

    @Test
    public void testLayerSeparationServiceIndependence() {
        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        assertTrue(service.compare(feet, inches));
    }

    @Test
    public void testLayerSeparationControllerIndependence() {
        QuantityMeasurementController localController =
                new QuantityMeasurementController(
                        new QuantityMeasurementServiceImpl(repository)
                );

        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        assertTrue(localController.performComparison(feet, inches));
    }

    @Test
    public void testDataFlowControllerToServiceAndRepository() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        controller.performComparison(feet, inches);

        List<QuantityMeasurementEntity> measurements =
                repository.getAllMeasurements();

        assertEquals(1, measurements.size());
        assertEquals("COMPARE", measurements.get(0).getOperation());
        assertEquals("Equal", measurements.get(0).getResultString());
    }

    @Test
    public void testBackwardCompatibilityAllUC1ToUC14LengthEquality() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        assertTrue(controller.performComparison(feet, inches));
    }

    @Test
    public void testBackwardCompatibilityAllUC1ToUC14WeightEquality() {
        QuantityDTO kilogram =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityDTO gram =
                new QuantityDTO(
                        1000.0,
                        QuantityDTO.WeightUnit.GRAM
                );

        assertTrue(controller.performComparison(kilogram, gram));
    }

    @Test
    public void testBackwardCompatibilityAllUC1ToUC14VolumeEquality() {
        QuantityDTO litre =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.VolumeUnit.LITRE
                );

        QuantityDTO millilitre =
                new QuantityDTO(
                        1000.0,
                        QuantityDTO.VolumeUnit.MILLILITRE
                );

        assertTrue(controller.performComparison(litre, millilitre));
    }

    @Test
    public void testServiceAllMeasurementCategories() {
        assertTrue(
                controller.performComparison(
                        new QuantityDTO(
                                1.0,
                                QuantityDTO.LengthUnit.FEET
                        ),
                        new QuantityDTO(
                                12.0,
                                QuantityDTO.LengthUnit.INCHES
                        )
                )
        );

        assertTrue(
                controller.performComparison(
                        new QuantityDTO(
                                1.0,
                                QuantityDTO.WeightUnit.KILOGRAM
                        ),
                        new QuantityDTO(
                                1000.0,
                                QuantityDTO.WeightUnit.GRAM
                        )
                )
        );

        assertTrue(
                controller.performComparison(
                        new QuantityDTO(
                                1.0,
                                QuantityDTO.VolumeUnit.LITRE
                        ),
                        new QuantityDTO(
                                1000.0,
                                QuantityDTO.VolumeUnit.MILLILITRE
                        )
                )
        );

        assertTrue(
                controller.performComparison(
                        new QuantityDTO(
                                25.0,
                                QuantityDTO.TemperatureUnit.CELSIUS
                        ),
                        new QuantityDTO(
                                77.0,
                                QuantityDTO.TemperatureUnit.FAHRENHEIT
                        )
                )
        );
    }

    @Test
    public void testControllerAllOperations() {
        QuantityDTO feet =
                new QuantityDTO(
                        10.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        24.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO target =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        assertFalse(controller.performComparison(feet, inches));
        assertEquals(
                120.0,
                controller.performConversion(feet, target).getValue(),
                EPSILON
        );
        assertEquals(
                12.0,
                controller.performAddition(feet, inches).getValue(),
                EPSILON
        );
        assertEquals(
                8.0,
                controller.performSubtraction(feet, inches).getValue(),
                EPSILON
        );
        assertEquals(
                5.0,
                controller.performDivision(feet, inches),
                EPSILON
        );
    }

    @Test
    public void testServiceValidationConsistencyForNullInput() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performAddition(feet, null)
        );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performSubtraction(feet, null)
        );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performDivision(feet, null)
        );
    }

    @Test
    public void testEntityImmutabilityByNoSetterMethods() {
        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        new QuantityDTO(
                                1.0,
                                QuantityDTO.LengthUnit.FEET
                        ),
                        "CONVERT",
                        new QuantityDTO(
                                12.0,
                                QuantityDTO.LengthUnit.INCHES
                        )
                );

        assertEquals(1.0, entity.getThisValue(), EPSILON);
        assertEquals(12.0, entity.getResultValue(), EPSILON);

        for (java.lang.reflect.Method method : entity.getClass().getMethods()) {
            assertFalse(method.getName().startsWith("set"));
        }
    }

    @Test
    public void testServiceExceptionHandlingAllOperations() {
        QuantityDTO temperature =
                new QuantityDTO(
                        25.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO anotherTemperature =
                new QuantityDTO(
                        77.0,
                        QuantityDTO.TemperatureUnit.FAHRENHEIT
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performAddition(
                        temperature,
                        anotherTemperature
                )
        );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performSubtraction(
                        temperature,
                        anotherTemperature
                )
        );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performDivision(
                        temperature,
                        anotherTemperature
                )
        );
    }

    @Test
    public void testIntegrationEndToEndLengthAddition() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO result =
                controller.performAddition(feet, inches);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals("FEET", result.getUnit());
        assertEquals(1, repository.getAllMeasurements().size());
        assertEquals("ADD", repository.getAllMeasurements().get(0).getOperation());
    }

    @Test
    public void testIntegrationEndToEndTemperatureUnsupported() {
        QuantityDTO first =
                new QuantityDTO(
                        10.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO second =
                new QuantityDTO(
                        20.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performAddition(first, second)
        );

        assertEquals(1, repository.getAllMeasurements().size());
        assertTrue(repository.getAllMeasurements().get(0).hasError());
    }

    @Test
    public void testServiceNullEntityRejection() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performComparison(null, feet)
        );
    }

    @Test
    public void testControllerNullServicePrevention() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new QuantityMeasurementController(null)
        );
    }

    @Test
    public void testServiceAllUnitImplementations() {
        assertEquals(
                12.0,
                controller.performConversion(
                        new QuantityDTO(
                                1.0,
                                QuantityDTO.LengthUnit.FEET
                        ),
                        new QuantityDTO(
                                0.0,
                                QuantityDTO.LengthUnit.INCHES
                        )
                ).getValue(),
                EPSILON
        );

        assertEquals(
                1000.0,
                controller.performConversion(
                        new QuantityDTO(
                                1.0,
                                QuantityDTO.WeightUnit.KILOGRAM
                        ),
                        new QuantityDTO(
                                0.0,
                                QuantityDTO.WeightUnit.GRAM
                        )
                ).getValue(),
                EPSILON
        );

        assertEquals(
                1000.0,
                controller.performConversion(
                        new QuantityDTO(
                                1.0,
                                QuantityDTO.VolumeUnit.LITRE
                        ),
                        new QuantityDTO(
                                0.0,
                                QuantityDTO.VolumeUnit.MILLILITRE
                        )
                ).getValue(),
                EPSILON
        );

        assertEquals(
                212.0,
                controller.performConversion(
                        new QuantityDTO(
                                100.0,
                                QuantityDTO.TemperatureUnit.CELSIUS
                        ),
                        new QuantityDTO(
                                0.0,
                                QuantityDTO.TemperatureUnit.FAHRENHEIT
                        )
                ).getValue(),
                EPSILON
        );
    }

    @Test
    public void testEntityOperationTypeTracking() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        controller.performAddition(feet, inches);

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);

        assertEquals("ADD", entity.getOperation());
        assertEquals("FEET", entity.getThisUnit());
        assertEquals("INCHES", entity.getThatUnit());
    }

    @Test
    public void testLayerDecouplingServiceChange() {
        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController localController =
                new QuantityMeasurementController(service);

        QuantityDTO feet =
                new QuantityDTO(
                        3.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO yard =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.YARDS
                );

        assertTrue(localController.performComparison(feet, yard));
    }

    @Test
    public void testLayerDecouplingEntityChangeDoesNotBreakService() {
        QuantityDTO litre =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.VolumeUnit.LITRE
                );

        QuantityDTO millilitre =
                new QuantityDTO(
                        1000.0,
                        QuantityDTO.VolumeUnit.MILLILITRE
                );

        assertTrue(controller.performComparison(litre, millilitre));
        assertEquals(1, repository.getAllMeasurements().size());
    }

    @Test
    public void testScalabilityNewOperationAddition() {
        QuantityDTO tonne =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.TONNE
                );

        QuantityDTO kilogram =
                new QuantityDTO(
                        1000.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        assertTrue(controller.performComparison(tonne, kilogram));
    }

    @Test
    public void testConvertLengthYardsToInches() {
        QuantityDTO yard =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.YARDS
                );

        QuantityDTO target =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        QuantityDTO result =
                controller.performConversion(yard, target);

        assertEquals(36.0, result.getValue(), EPSILON);
    }

    @Test
    public void testAddWeightKilogramsAndPounds() {
        QuantityDTO kilogram =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityDTO pound =
                new QuantityDTO(
                        2.20462,
                        QuantityDTO.WeightUnit.POUND
                );

        QuantityDTO result =
                controller.performAddition(kilogram, pound);

        assertEquals(2.0, result.getValue(), 0.001);
    }

    @Test
    public void testVolumeGallonToLitreConversion() {
        QuantityDTO gallon =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.VolumeUnit.GALLON
                );

        QuantityDTO litre =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.VolumeUnit.LITRE
                );

        QuantityDTO result =
                controller.performConversion(gallon, litre);

        assertEquals(3.78541, result.getValue(), EPSILON);
    }

    @Test
    public void testTemperatureUnitComparison() {
        QuantityDTO celsius =
                new QuantityDTO(
                        25.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO fahrenheit =
                new QuantityDTO(
                        77.0,
                        QuantityDTO.TemperatureUnit.FAHRENHEIT
                );

        assertTrue(controller.performComparison(celsius, fahrenheit));
    }

    @Test
    public void testTemperatureUnitConversion() {
        QuantityDTO celsius =
                new QuantityDTO(
                        100.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO fahrenheit =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.TemperatureUnit.FAHRENHEIT
                );

        QuantityDTO result =
                controller.performConversion(celsius, fahrenheit);

        assertEquals(212.0, result.getValue(), EPSILON);
        assertEquals("FAHRENHEIT", result.getUnit());
    }

    @Test
    public void testRepositoryStoresSuccessfulOperation() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        controller.performAddition(feet, inches);

        assertEquals(1, repository.getAllMeasurements().size());
        assertFalse(repository.getAllMeasurements().get(0).hasError());
    }

    @Test
    public void testRepositoryStoresErrorOperation() {
        QuantityDTO first =
                new QuantityDTO(
                        25.0,
                        QuantityDTO.TemperatureUnit.CELSIUS
                );

        QuantityDTO second =
                new QuantityDTO(
                        77.0,
                        QuantityDTO.TemperatureUnit.FAHRENHEIT
                );

        assertThrows(
                QuantityMeasurementException.class,
                () -> controller.performAddition(first, second)
        );

        assertEquals(1, repository.getAllMeasurements().size());
        assertTrue(repository.getAllMeasurements().get(0).hasError());
        assertEquals("ADD", repository.getAllMeasurements().get(0).getOperation());
    }

    @Test
    public void testRepositoryReturnsUnmodifiableMeasurements() {
        QuantityDTO feet =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.LengthUnit.FEET
                );

        QuantityDTO inches =
                new QuantityDTO(
                        12.0,
                        QuantityDTO.LengthUnit.INCHES
                );

        controller.performComparison(feet, inches);

        List<QuantityMeasurementEntity> measurements =
                repository.getAllMeasurements();

        assertThrows(
                UnsupportedOperationException.class,
                () -> measurements.add(
                        new QuantityMeasurementEntity(
                                feet,
                                inches,
                                "COMPARE",
                                "Equal"
                        )
                )
        );
    }
}
