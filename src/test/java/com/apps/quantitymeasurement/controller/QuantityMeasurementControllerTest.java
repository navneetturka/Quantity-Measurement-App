package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QuantityMeasurementControllerTest {

    private IQuantityMeasurementService mockService;
    private QuantityMeasurementController controller;

    private static final double EPSILON = 0.001;

    private final QuantityDTO FEET_DTO =
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
    private final QuantityDTO INCHES_DTO =
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
    private final QuantityDTO RESULT_DTO =
            new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);

    @Before
    public void setUp() {
        mockService = mock(IQuantityMeasurementService.class);
        controller  = new QuantityMeasurementController(mockService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_NullService_ThrowsIllegalArgument() {
        new QuantityMeasurementController(null);
    }

    @Test
    public void testPerformComparison_DelegatesToService_ReturnsTrue() {
        when(mockService.compare(FEET_DTO, INCHES_DTO)).thenReturn(true);
        assertTrue(controller.performComparison(FEET_DTO, INCHES_DTO));
        verify(mockService).compare(FEET_DTO, INCHES_DTO);
    }

    @Test
    public void testPerformComparison_DelegatesToService_ReturnsFalse() {
        when(mockService.compare(FEET_DTO, INCHES_DTO)).thenReturn(false);
        assertFalse(controller.performComparison(FEET_DTO, INCHES_DTO));
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testPerformComparison_ServiceThrows_BubblesUp() {
        when(mockService.compare(any(), any()))
                .thenThrow(new QuantityMeasurementException("error"));
        controller.performComparison(FEET_DTO, INCHES_DTO);
    }

    @Test
    public void testPerformConversion_DelegatesToService() {
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO expected = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        when(mockService.convert(FEET_DTO, target)).thenReturn(expected);

        QuantityDTO result = controller.performConversion(FEET_DTO, target);
        assertEquals(12.0, result.getValue(), EPSILON);
        verify(mockService).convert(FEET_DTO, target);
    }

    @Test
    public void testPerformAddition_TwoArgs_DelegatesToService() {
        when(mockService.add(FEET_DTO, INCHES_DTO)).thenReturn(RESULT_DTO);
        QuantityDTO result = controller.performAddition(FEET_DTO, INCHES_DTO);
        assertEquals(2.0, result.getValue(), EPSILON);
        verify(mockService).add(FEET_DTO, INCHES_DTO);
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testPerformAddition_ServiceThrows_BubblesUp() {
        when(mockService.add(any(), any()))
                .thenThrow(new QuantityMeasurementException("unsupported"));
        controller.performAddition(FEET_DTO, INCHES_DTO);
    }

    @Test
    public void testPerformAddition_ThreeArgs_DelegatesToService() {
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO expected = new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES);
        when(mockService.add(FEET_DTO, INCHES_DTO, target)).thenReturn(expected);

        QuantityDTO result = controller.performAddition(FEET_DTO, INCHES_DTO, target);
        assertEquals(24.0, result.getValue(), EPSILON);
        verify(mockService).add(FEET_DTO, INCHES_DTO, target);
    }

    @Test
    public void testPerformSubtraction_TwoArgs_DelegatesToService() {
        QuantityDTO fiveKg = new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO twoKg  = new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO three  = new QuantityDTO(3.0, QuantityDTO.WeightUnit.KILOGRAM);

        when(mockService.subtract(fiveKg, twoKg)).thenReturn(three);
        QuantityDTO result = controller.performSubtraction(fiveKg, twoKg);
        assertEquals(3.0, result.getValue(), EPSILON);
        verify(mockService).subtract(fiveKg, twoKg);
    }

    @Test
    public void testPerformSubtraction_ThreeArgs_DelegatesToService() {
        QuantityDTO fiveKg  = new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO twoKg   = new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO targetG = new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM);
        QuantityDTO threeG  = new QuantityDTO(3000.0, QuantityDTO.WeightUnit.GRAM);

        when(mockService.subtract(fiveKg, twoKg, targetG)).thenReturn(threeG);
        QuantityDTO result = controller.performSubtraction(fiveKg, twoKg, targetG);
        assertEquals(3000.0, result.getValue(), EPSILON);
        verify(mockService).subtract(fiveKg, twoKg, targetG);
    }

    @Test
    public void testPerformDivision_DelegatesToService() {
        QuantityDTO ten  = new QuantityDTO(10.0, QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO five = new QuantityDTO(5.0,  QuantityDTO.VolumeUnit.LITRE);
        when(mockService.divide(ten, five)).thenReturn(2.0);

        double result = controller.performDivision(ten, five);
        assertEquals(2.0, result, EPSILON);
        verify(mockService).divide(ten, five);
    }

    @Test(expected = QuantityMeasurementException.class)
    public void testPerformDivision_ByZero_BubblesUp() {
        QuantityDTO ten  = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO zero = new QuantityDTO(0.0,  QuantityDTO.LengthUnit.FEET);
        when(mockService.divide(ten, zero))
                .thenThrow(new QuantityMeasurementException("divide by zero"));
        controller.performDivision(ten, zero);
    }

    @Test
    public void testMultipleOperations_CorrectDelegationCount() {
        when(mockService.compare(any(), any())).thenReturn(true);
        when(mockService.add(any(), any())).thenReturn(RESULT_DTO);

        controller.performComparison(FEET_DTO, INCHES_DTO);
        controller.performComparison(FEET_DTO, INCHES_DTO);
        controller.performAddition(FEET_DTO, INCHES_DTO);

        verify(mockService, times(2)).compare(any(), any());
        verify(mockService, times(1)).add(any(), any());
    }
}