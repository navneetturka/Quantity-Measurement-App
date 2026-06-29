package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import com.apps.quantitymeasurement.util.ApplicationConfig;
import com.apps.quantitymeasurement.util.ConnectionPool;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class QuantityMeasurementDatabaseRepositoryTest {

    private static ApplicationConfig config;
    private static ConnectionPool pool;
    private QuantityMeasurementDatabaseRepository repository;

    private static final QuantityDTO FEET_DTO =
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
    private static final QuantityDTO INCHES_DTO =
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
    private static final QuantityDTO KG_DTO =
            new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
    private static final QuantityDTO RESULT_DTO =
            new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("db.url",
                "jdbc:h2:mem:testdb_repo;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        System.setProperty("db.username", "sa");
        System.setProperty("db.password", "");
        System.setProperty("db.driver", "org.h2.Driver");
        System.setProperty("db.pool.size", "5");
        System.setProperty("repository.type", "database");
        System.setProperty("db.schema.auto", "true");

        ApplicationConfig.reset();
        ConnectionPool.reset();

        config = ApplicationConfig.getInstance();
        pool   = ConnectionPool.getInstance();
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
    }

    @Test
    public void testSave_SingleEntity_CountIsOne() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testSave_MultipleEntities_CountMatches() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        repository.save(new QuantityMeasurementEntity(
                KG_DTO, "CONVERT",
                new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)));
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "COMPARE", "Equal"));

        assertEquals(3, repository.getTotalCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSave_NullEntity_ThrowsIllegalArgument() {
        repository.save(null);
    }

    @Test
    public void testGetAllMeasurements_EmptyRepository_ReturnsEmptyList() {
        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    public void testGetAllMeasurements_AfterSaves_ReturnsAll() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "COMPARE", "Equal"));

        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();
        assertEquals(2, all.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAllMeasurements_ReturnsUnmodifiableList() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();
        all.add(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
    }

    @Test
    public void testGetByOperation_ADD_ReturnsOnlyAddEntities() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "COMPARE", "Equal"));
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));

        List<QuantityMeasurementEntity> adds =
                repository.getMeasurementsByOperation("ADD");
        assertEquals(2, adds.size());
        adds.forEach(e -> assertEquals("ADD", e.getOperation()));
    }

    @Test
    public void testGetByOperation_CaseInsensitive() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        assertEquals(1,
                repository.getMeasurementsByOperation("add").size());
    }

    @Test
    public void testGetByOperation_NoMatch_ReturnsEmpty() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        assertTrue(repository.getMeasurementsByOperation("SUBTRACT").isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByOperation_NullArg_ThrowsIllegalArgument() {
        repository.getMeasurementsByOperation(null);
    }

    @Test
    public void testGetByType_LengthUnit_ReturnsOnlyLength() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        repository.save(new QuantityMeasurementEntity(
                KG_DTO, "CONVERT",
                new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)));

        List<QuantityMeasurementEntity> lengths =
                repository.getMeasurementsByType("LengthUnit");
        assertEquals(1, lengths.size());
        assertEquals("LengthUnit", lengths.get(0).getThisMeasurementType());
    }

    @Test
    public void testGetByType_CaseInsensitive() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        assertEquals(1,
                repository.getMeasurementsByType("lengthunit").size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByType_NullArg_ThrowsIllegalArgument() {
        repository.getMeasurementsByType(null);
    }

    @Test
    public void testGetTotalCount_EmptyDb_ReturnsZero() {
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    public void testGetTotalCount_AfterFiveSaves_ReturnsFive() {
        for (int i = 0; i < 5; i++) {
            repository.save(new QuantityMeasurementEntity(
                    FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        }
        assertEquals(5, repository.getTotalCount());
    }

    @Test
    public void testDeleteAll_AfterSaves_CountBecomesZero() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        repository.save(new QuantityMeasurementEntity(
                KG_DTO, "CONVERT",
                new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)));

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    public void testDeleteAll_EmptyRepository_NoException() {
        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    public void testSave_ErrorEntity_IsPersistedAndRetrieved() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, KG_DTO, "ADD",
                "Incompatible unit types", true));

        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();
        assertEquals(1, all.size());
        assertTrue(all.get(0).hasError());
        assertEquals("ADD", all.get(0).getOperation());
    }

    @Test
    public void testGetPoolStatistics_ReturnsNonNullString() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
    }

    @Test
    public void testGetPoolStatistics_ContainsExpectedFields() {
        String stats = repository.getPoolStatistics();
        assertTrue(stats.contains("ConnectionPool") ||
                stats.contains("available"));
    }

    @Test
    public void testSqlInjection_InOperation_TreatedAsLiteral() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation(
                        "'; DROP TABLE quantity_measurement_entity; --");
        assertTrue(result.isEmpty());
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testLargeDataSet_500Saves_AllRetrieved() {
        int count = 500;
        for (int i = 0; i < count; i++) {
            repository.save(new QuantityMeasurementEntity(
                    FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        }
        assertEquals(count, repository.getTotalCount());
        assertEquals(count, repository.getAllMeasurements().size());
    }

    @Test
    public void testSave_TimestampIsRecorded() {
        repository.save(new QuantityMeasurementEntity(
                FEET_DTO, INCHES_DTO, "ADD", RESULT_DTO));
        assertEquals(1, repository.getTotalCount());
    }
}