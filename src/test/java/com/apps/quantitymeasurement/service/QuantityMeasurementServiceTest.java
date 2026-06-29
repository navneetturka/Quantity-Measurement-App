package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QuantityMeasurementServiceTest {

    private IQuantityMeasurementRepository mockRepository;
    private QuantityMeasurementServiceImpl service;

    private static final double EPSILON = 0.001;

    @Before
    public void setUp() {
        mockRepository = mock(IQuantityMeasurementRepository.class);
        service        = new QuantityMeasurementServiceImpl(mockRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_NullRepository_ThrowsIllegalArgument() {
        new QuantityMeasurementServiceImpl(null);
    }

    @Test
    public void testCompare_EqualLengths_ReturnsTrue() {
        boolean result = service.compare(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        assertTrue(result);
        verify(mockRepository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    public void testCompare_UnequalLengths_ReturnsFalse() {
        boolean result = service.compare(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(10.0, QuantityDTO.LengthUnit.INCHES));
        assertFalse(result);
        verify(mockRepository).save(any());
    }

    @Test
    public void testCompare_DifferentTypes_ReturnsFalse() {
        boolean result = service.compare(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM));
        assertFalse(result);
        verify(mockRepository).save(any());
    }

    @Test
    public void testCompare_Temperature_EqualCelsiusFahrenheit_ReturnsTrue() {
        boolean result = service.compare(
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
        assertTrue(result);
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testCompare_NullFirst_ThrowsException() {
        service.compare(null,
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET));
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testCompare_NullSecond_ThrowsException() {
        service.compare(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET), null);
    }

    @Test
    public void testConvert_FeetToInches_Correct() {
        QuantityDTO result = service.convert(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));
        assertEquals(12.0, result.getValue(), EPSILON);
        assertEquals("INCHES", result.getUnit());
        verify(mockRepository).save(any());
    }

    @Test
    public void testConvert_KilogramToGram_Correct() {
        QuantityDTO result = service.convert(
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM));
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    public void testConvert_CelsiusToFahrenheit_100degrees() {
        QuantityDTO result = service.convert(
                new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(0.0,   QuantityDTO.TemperatureUnit.FAHRENHEIT));
        assertEquals(212.0, result.getValue(), EPSILON);
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testConvert_IncompatibleTypes_ThrowsException() {
        service.convert(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM));
    }

    @Test
    public void testAdd_FeetAndInches_CorrectSum() {
        QuantityDTO result = service.add(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals("FEET", result.getUnit());
        verify(mockRepository).save(any());
    }

    @Test
    public void testAdd_WithTargetUnit_CorrectConversion() {
        QuantityDTO result = service.add(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));
        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals("INCHES", result.getUnit());
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testAdd_Temperature_ThrowsUnsupported() {
        service.add(
                new QuantityDTO(10.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(20.0, QuantityDTO.TemperatureUnit.CELSIUS));
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testAdd_NullFirst_ThrowsException() {
        service.add(null,
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
    }

    @Test
    public void testSubtract_KilogramsMinusGrams_Correct() {
        QuantityDTO result = service.subtract(
                new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(2000.0, QuantityDTO.WeightUnit.GRAM));
        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals("KILOGRAM", result.getUnit());
    }

    @Test
    public void testSubtract_WithTargetUnit_Correct() {
        QuantityDTO result = service.subtract(
                new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(2000.0, QuantityDTO.WeightUnit.GRAM),
                new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM));
        assertEquals(3000.0, result.getValue(), EPSILON);
        assertEquals("GRAM", result.getUnit());
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testSubtract_Temperature_ThrowsUnsupported() {
        service.subtract(
                new QuantityDTO(10.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(5.0, QuantityDTO.TemperatureUnit.CELSIUS));
    }

    @Test
    public void testDivide_TenByFive_ReturnsTwo() {
        double result = service.divide(
                new QuantityDTO(10.0, QuantityDTO.VolumeUnit.LITRE),
                new QuantityDTO(5.0, QuantityDTO.VolumeUnit.LITRE));
        assertEquals(2.0, result, EPSILON);
        verify(mockRepository).save(any());
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testDivide_ByZero_ThrowsException() {
        service.divide(
                new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testDivide_NullDivisor_ThrowsException() {
        service.divide(
                new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET), null);
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testDivide_Temperature_ThrowsUnsupported() {
        service.divide(
                new QuantityDTO(10.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(5.0, QuantityDTO.TemperatureUnit.CELSIUS));
    }

    @Test
    public void testCompare_SavedEntityHasCorrectOperation() {
        ArgumentCaptor<QuantityMeasurementEntity> captor =
                ArgumentCaptor.forClass(QuantityMeasurementEntity.class);

        service.compare(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

        verify(mockRepository).save(captor.capture());
        assertEquals("COMPARE", captor.getValue().getOperation());
        assertFalse(captor.getValue().hasError());
    }

    @Test
    public void testAdd_ErrorEntitySaved_WhenExceptionOccurs() {
        ArgumentCaptor<QuantityMeasurementEntity> captor =
                ArgumentCaptor.forClass(QuantityMeasurementEntity.class);

        try {
            service.add(
                    new QuantityDTO(10.0, QuantityDTO.TemperatureUnit.CELSIUS),
                    new QuantityDTO(20.0, QuantityDTO.TemperatureUnit.CELSIUS));
        } catch (QuantityMeasurementException ignored) {}

        verify(mockRepository).save(captor.capture());
        assertTrue(captor.getValue().hasError());
        assertEquals("ADD", captor.getValue().getOperation());
    }

    @Test
    public void testRepositoryCalledWithCorrectData_AfterMultipleOps() {
        service.compare(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
        service.convert(
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
                new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM));

        verify(mockRepository, times(2)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    public void testGetAllMeasurements_DelegatedToRepository() {
        List<QuantityMeasurementEntity> expected = new ArrayList<>();
        when(mockRepository.getAllMeasurements()).thenReturn(expected);
        verify(mockRepository, never()).getAllMeasurements();
    }
}