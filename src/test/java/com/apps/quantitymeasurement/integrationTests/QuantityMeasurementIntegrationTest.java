package com.apps.quantitymeasurement.integrationTests;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.util.ApplicationConfig;
import com.apps.quantitymeasurement.util.ConnectionPool;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class QuantityMeasurementIntegrationTest {

    private static ApplicationConfig config;
    private static ConnectionPool pool;

    private IQuantityMeasurementRepository repository;
    private QuantityMeasurementController controller;

    private static final double EPSILON = 0.001;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("db.url",
                "jdbc:h2:mem:integrationdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        System.setProperty("db.username", "sa");
        System.setProperty("db.password", "");
        System.setProperty("db.driver", "org.h2.Driver");
        System.setProperty("db.pool.size", "5");
        System.setProperty("repository.type", "database");
        System.setProperty("db.schema.auto", "true");

        ApplicationConfig.reset();
        ConnectionPool.reset();

        config = ApplicationConfig.getInstance();
        pool = ConnectionPool.getInstance();
    }

    @AfterClass
    public static void tearDownClass() {
        ConnectionPool.reset();
        ApplicationConfig.reset();
        System.clearProperty("db.url");
        System.clearProperty("db.username");
        System.clearProperty("db.password");
        System.clearProperty("db.driver");
        System.clearProperty("db.pool.size");
        System.clearProperty("repository.type");
        System.clearProperty("db.schema.auto");
    }

    @Before
    public void setUp() {
        repository = new QuantityMeasurementDatabaseRepository(config, pool);
        repository.deleteAll();
        controller = new QuantityMeasurementController(
                new QuantityMeasurementServiceImpl(repository));
    }

    @Test
    public void testIntegration_Compare_FeetAndInches_Equal_PersistedToDB() {
        boolean result = controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

        assertTrue(result);
        assertEquals(1, repository.getTotalCount());

        QuantityMeasurementEntity saved = repository.getAllMeasurements().get(0);
        assertEquals("COMPARE", saved.getOperation());
        assertFalse(saved.hasError());
        assertEquals("Equal", saved.getResultString());
    }

    @Test
    public void testIntegration_Compare_IncompatibleTypes_PersistedAsNotEqual() {
        boolean result = controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM));

        assertFalse(result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testIntegration_Convert_FeetToInches_PersistedToDB() {
        QuantityDTO result = controller.performConversion(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

        assertEquals(12.0, result.getValue(), EPSILON);
        assertEquals(1, repository.getTotalCount());

        QuantityMeasurementEntity saved = repository.getAllMeasurements().get(0);
        assertEquals("CONVERT", saved.getOperation());
    }

    @Test
    public void testIntegration_Convert_CelsiusToFahrenheit_212() {
        QuantityDTO result = controller.performConversion(
                new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));

        assertEquals(212.0, result.getValue(), EPSILON);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testIntegration_Add_FeetAndInches_PersistedToDB() {
        QuantityDTO result = controller.performAddition(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(1, repository.getTotalCount());

        QuantityMeasurementEntity saved = repository.getAllMeasurements().get(0);
        assertEquals("ADD", saved.getOperation());
        assertFalse(saved.hasError());
    }

    @Test
    public void testIntegration_Add_Temperature_ErrorPersistedToDB() {
        try {
            controller.performAddition(
                    new QuantityDTO(10.0, QuantityDTO.TemperatureUnit.CELSIUS),
                    new QuantityDTO(20.0, QuantityDTO.TemperatureUnit.CELSIUS));
            fail("Expected QuantityMeasurementException");
        } catch (QuantityMeasurementException e) {
            assertEquals(1, repository.getTotalCount());
            assertTrue(repository.getAllMeasurements().get(0).hasError());
        }
    }

    @Test
    public void testIntegration_Subtract_KgAndGram_PersistedToDB() {
        QuantityDTO result = controller.performSubtraction(
                new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(2000.0, QuantityDTO.WeightUnit.GRAM));

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(1, repository.getTotalCount());
        assertEquals("SUBTRACT",
                repository.getAllMeasurements().get(0).getOperation());
    }

    @Test
    public void testIntegration_Divide_LitreByLitre_PersistedToDB() {
        double result = controller.performDivision(
                new QuantityDTO(10.0, QuantityDTO.VolumeUnit.LITRE),
                new QuantityDTO(5.0, QuantityDTO.VolumeUnit.LITRE));

        assertEquals(2.0, result, EPSILON);
        assertEquals(1, repository.getTotalCount());
        assertEquals("DIVIDE",
                repository.getAllMeasurements().get(0).getOperation());
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testIntegration_Divide_ByZero_ThrowsAndPersistsError() {
        try {
            controller.performDivision(
                    new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
                    new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));
        } finally {
            assertEquals(1, repository.getTotalCount());
            assertTrue(repository.getAllMeasurements().get(0).hasError());
        }
    }

    @Test
    public void testIntegration_QueryByOperation_FiltersCorrectly() {
        controller.performAddition(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performAddition(
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM));

        List<QuantityMeasurementEntity> adds =
                repository.getMeasurementsByOperation("ADD");
        assertEquals(2, adds.size());

        List<QuantityMeasurementEntity> compares =
                repository.getMeasurementsByOperation("COMPARE");
        assertEquals(1, compares.size());
    }

    @Test
    public void testIntegration_QueryByType_FiltersCorrectly() {
        controller.performAddition(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performConversion(
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM));

        List<QuantityMeasurementEntity> lengths =
                repository.getMeasurementsByType("LengthUnit");
        assertEquals(1, lengths.size());
        assertEquals("LengthUnit", lengths.get(0).getThisMeasurementType());
    }

    @Test
    public void testIntegration_DeleteAll_ClearsDatabase() {
        controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        assertEquals(1, repository.getTotalCount());

        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    public void testIntegration_DatabaseIsolation_BetweenTests() {
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    public void testIntegration_PoolStatistics_NonEmpty() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
    }

    @Test
    public void testIntegration_MultipleOperations_AllPersisted() {
        controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        controller.performConversion(
                new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
        controller.performAddition(
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM));
        controller.performSubtraction(
                new QuantityDTO(5.0, QuantityDTO.VolumeUnit.LITRE),
                new QuantityDTO(2.0, QuantityDTO.VolumeUnit.LITRE));
        controller.performDivision(
                new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));

        assertEquals(5, repository.getTotalCount());
    }

    @Test
    public void testBackwardCompat_FeetEqualsInches() {
        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)));
    }

    @Test
    public void testBackwardCompat_KilogramEqualsGram() {
        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)));
    }

    @Test
    public void testBackwardCompat_LitreEqualsMillilitre() {
        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE),
                new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testBackwardCompat_TemperatureConversion() {
        QuantityDTO result = controller.performConversion(
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
        assertEquals(32.0, result.getValue(), EPSILON);
    }
}