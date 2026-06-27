//package com.apps.quantitymeasurement;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.apps.quantitymeasurement.QuantityMeasurementApp.Feet;
//import com.apps.quantitymeasurement.QuantityMeasurementApp.Inches;
//
//public class QuantityMeasurementAppTest {
//
//    @Test
//    public void testFeetEquality_SameValue() {
//        Feet f1 = new Feet(1.0);
//        Feet f2 = new Feet(1.0);
//
//        assertTrue(f1.equals(f2));
//    }
//    @Test
//    public void testFeetEquality_DifferentValue() {
//        Feet f1 = new Feet(1.0);
//        Feet f2 = new Feet(2.0);
//
//        assertFalse(f1.equals(f2));
//    }
//
//    @Test
//    public void testFeetEquality_NullComparison() {
//        Feet f1 = new Feet(1.0);
//
//        assertFalse(f1.equals(null));
//    }
//
//    @Test
//    public void testFeetEquality_DifferentClass() {
//        Feet f1 = new Feet(1.0);
//        String str = "test";
//
//        assertFalse(f1.equals(str));
//    }
//
//    @Test
//    public void testFeetEquality_SameReference() {
//        Feet f1 = new Feet(1.0);
//
//        assertTrue(f1.equals(f1));
//    }
//    @Test
//    public void testInchesEquality_SameValue() {
//        Inches i1 = new Inches(1.0);
//        Inches i2 = new Inches(1.0);
//
//        assertTrue(i1.equals(i2));
//    }
//
//    @Test
//    public void testInchesEquality_DifferentValue() {
//        Inches i1 = new Inches(1.0);
//        Inches i2 = new Inches(2.0);
//
//        assertFalse(i1.equals(i2));
//    }
//
//    @Test
//    public void testInchesEquality_NullComparison() {
//        Inches i1 = new Inches(1.0);
//
//        assertFalse(i1.equals(null));
//    }
//
//    @Test
//    public void testInchesEquality_DifferentClass() {
//        Inches i1 = new Inches(1.0);
//
//        assertFalse(i1.equals("test"));
//    }
//
//    @Test
//    public void testInchesEquality_SameReference() {
//        Inches i1 = new Inches(1.0);
//
//        assertTrue(i1.equals(i1));
//    }
//}

package com.apps.quantitymeasurement;
import com.apps.quantitymeasurement.unit.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.quantity.Quantity;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;


public class QuantityMeasurementAppTest {

    @Test
    public void testFeetToFeet_SameValue() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(1.0, LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testInchToInch_SameValue() {
        Length l1 = new Length(1.0, LengthUnit.INCHES);
        Length l2 = new Length(1.0, LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testFeetToInch_Equal() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testFeetToFeet_Different() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(2.0, LengthUnit.FEET);

        assertFalse(l1.equals(l2));
    }

    @Test
    public void testCrossUnit_Different() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(10.0, LengthUnit.INCHES);

        assertFalse(l1.equals(l2));
    }

    @Test
    public void testNullComparison() {
        Length l1 = new Length(1.0, LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }

    @Test
    public void testSameReference() {
        Length l1 = new Length(1.0, LengthUnit.FEET);

        assertTrue(l1.equals(l1));
    }

    @Test
    public void testEquality_YardToYard_SameValue() {

        Length yard1 =
                new Length(1.0, LengthUnit.YARDS);

        Length yard2 =
                new Length(1.0, LengthUnit.YARDS);

        assertTrue(yard1.equals(yard2));
    }

    @Test
    public void testEquality_YardToYard_DifferentValue() {

        Length yard1 =
                new Length(1.0, LengthUnit.YARDS);

        Length yard2 =
                new Length(2.0, LengthUnit.YARDS);

        assertFalse(yard1.equals(yard2));
    }

    @Test
    public void testEquality_YardToFeet_EquivalentValue() {

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        Length feet =
                new Length(3.0, LengthUnit.FEET);

        assertTrue(yard.equals(feet));
    }

    @Test
    public void testEquality_FeetToYard_EquivalentValue() {

        Length feet =
                new Length(3.0, LengthUnit.FEET);

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        assertTrue(feet.equals(yard));
    }

    @Test
    public void testEquality_YardToInches_EquivalentValue() {

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        Length inches =
                new Length(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(inches));
    }

    @Test
    public void testEquality_InchesToYard_EquivalentValue() {

        Length inches =
                new Length(36.0, LengthUnit.INCHES);

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        assertTrue(inches.equals(yard));
    }

    @Test
    public void testEquality_YardToFeet_NonEquivalentValue() {

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        Length feet =
                new Length(2.0, LengthUnit.FEET);

        assertFalse(yard.equals(feet));
    }

    @Test
    public void testEquality_CentimetersToInches_EquivalentValue() {

        Length cm =
                new Length(1.0, LengthUnit.CENTIMETERS);

        Length inches =
                new Length(0.393700787, LengthUnit.INCHES);

        assertTrue(cm.equals(inches));
    }

    @Test
    public void testEquality_CentimetersToFeet_NonEquivalentValue() {

        Length cm =
                new Length(1.0, LengthUnit.CENTIMETERS);

        Length feet =
                new Length(1.0, LengthUnit.FEET);

        assertFalse(cm.equals(feet));
    }

    @Test
    public void testEquality_MultiUnit_TransitiveProperty() {

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        Length feet =
                new Length(3.0, LengthUnit.FEET);

        Length inches =
                new Length(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yard.equals(inches));
    }
//    @Test
//    public void testEquality_YardWithNullUnit() {
//
//        assertThrows(
//                NullPointerException.class,
//                () -> {
//                    Length yard =
//                            new Length(1.0, null);
//
//                    yard.equals(
//                            new Length(1.0, LengthUnit.YARDS)
//                    );
//                }
//        );
//    }

    @Test
    public void testEquality_YardSameReference() {

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        assertTrue(yard.equals(yard));
    }

    @Test
    public void testEquality_YardNullComparison() {

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        assertFalse(yard.equals(null));
    }
//    @Test
//    public void testEquality_CentimetersWithNullUnit() {
//
//        assertThrows(
//                NullPointerException.class,
//                () -> {
//                    Length cm =
//                            new Length(1.0, null);
//
//                    cm.equals(
//                            new Length(
//                                    1.0,
//                                    LengthUnit.CENTIMETERS
//                            )
//                    );
//                }
//        );
//    }

    @Test
    public void testEquality_CentimetersSameReference() {

        Length cm =
                new Length(1.0, LengthUnit.CENTIMETERS);

        assertTrue(cm.equals(cm));
    }

    @Test
    public void testEquality_CentimetersNullComparison() {

        Length cm =
                new Length(1.0, LengthUnit.CENTIMETERS);

        assertFalse(cm.equals(null));
    }

    @Test
    public void testEquality_AllUnits_ComplexScenario() {

        Length yards =
                new Length(2.0, LengthUnit.YARDS);

        Length feet =
                new Length(6.0, LengthUnit.FEET);

        Length inches =
                new Length(72.0, LengthUnit.INCHES);

        assertTrue(yards.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yards.equals(inches));
    }

    @Test
    public void testConversion_FeetToInches() {

        Length feet =
                new Length(1.0, LengthUnit.FEET);

        Length inches =
                feet.convertTo(LengthUnit.INCHES);

        assertEquals(
                12.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_InchesToFeet() {

        Length inches =
                new Length(24.0, LengthUnit.INCHES);

        Length feet =
                inches.convertTo(LengthUnit.FEET);

        assertEquals(
                2.0,
                feet.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_YardsToInches() {

        Length yards =
                new Length(1.0, LengthUnit.YARDS);

        Length inches =
                yards.convertTo(LengthUnit.INCHES);

        assertEquals(
                36.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_InchesToYards() {

        Length inches =
                new Length(72.0, LengthUnit.INCHES);

        Length yards =
                inches.convertTo(LengthUnit.YARDS);

        assertEquals(
                2.0,
                yards.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_CentimetersToInches() {

        Length cm =
                new Length(2.54, LengthUnit.CENTIMETERS);

        Length inches =
                cm.convertTo(LengthUnit.INCHES);

        assertEquals(
                1.0,
                inches.getValue(),
                0.0001
        );
    }

    @Test
    public void testConversion_FeetToYard() {

        Length feet =
                new Length(6.0, LengthUnit.FEET);

        Length yards =
                feet.convertTo(LengthUnit.YARDS);

        assertEquals(
                2.0,
                yards.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {

        Length original =
                new Length(5.0, LengthUnit.FEET);

        Length inches =
                original.convertTo(LengthUnit.INCHES);

        Length convertedBack =
                inches.convertTo(LengthUnit.FEET);

        assertEquals(
                original.getValue(),
                convertedBack.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_ZeroValue() {

        Length feet =
                new Length(0.0, LengthUnit.FEET);

        Length inches =
                feet.convertTo(LengthUnit.INCHES);

        assertEquals(
                0.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_NegativeValue() {

        Length feet =
                new Length(-1.0, LengthUnit.FEET);

        Length inches =
                feet.convertTo(LengthUnit.INCHES);

        assertEquals(
                -12.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_InvalidUnit_Throws() {

        Length feet =
                new Length(1.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> feet.convertTo(null)
        );
    }

    @Test
    public void testConversion_NaNOrInfinite_Throws() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(
                        Double.NaN,
                        LengthUnit.FEET
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(
                        Double.POSITIVE_INFINITY,
                        LengthUnit.FEET
                )
        );
    }

    @Test
    public void testConversion_PrecisionTolerance() {

        Length cm =
                new Length(2.54, LengthUnit.CENTIMETERS);

        Length inches =
                cm.convertTo(LengthUnit.INCHES);

        assertEquals(
                1.0,
                inches.getValue(),
                0.0001
        );
    }

    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {

        Length l1 =
                new Length(1.0, LengthUnit.FEET);

        Length l2 =
                new Length(2.0, LengthUnit.FEET);

        Length result = l1.add(l2);

        assertEquals(
                3.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {

        Length l1 =
                new Length(6.0, LengthUnit.INCHES);

        Length l2 =
                new Length(6.0, LengthUnit.INCHES);

        Length result = l1.add(l2);

        assertEquals(
                12.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {

        Length feet =
                new Length(1.0, LengthUnit.FEET);

        Length inches =
                new Length(12.0, LengthUnit.INCHES);

        Length result = feet.add(inches);

        assertEquals(
                2.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {

        Length inches =
                new Length(12.0, LengthUnit.INCHES);

        Length feet =
                new Length(1.0, LengthUnit.FEET);

        Length result = inches.add(feet);

        assertEquals(
                24.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {

        Length yard =
                new Length(1.0, LengthUnit.YARDS);

        Length feet =
                new Length(3.0, LengthUnit.FEET);

        Length result = yard.add(feet);

        assertEquals(
                2.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {

        Length cm =
                new Length(2.54, LengthUnit.CENTIMETERS);

        Length inch =
                new Length(1.0, LengthUnit.INCHES);

        Length result = cm.add(inch);

        assertEquals(
                5.08,
                result.getValue(),
                0.01
        );

        assertEquals(
                LengthUnit.CENTIMETERS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_Commutativity() {

        Length feet =
                new Length(1.0, LengthUnit.FEET);

        Length inches =
                new Length(12.0, LengthUnit.INCHES);

        Length result1 = feet.add(inches);

        Length result2 = inches.add(feet);

        assertTrue(result1.equals(result2));
    }

    @Test
    public void testAddition_WithZero() {

        Length feet =
                new Length(5.0, LengthUnit.FEET);

        Length inches =
                new Length(0.0, LengthUnit.INCHES);

        Length result = feet.add(inches);

        assertEquals(
                5.0,
                result.getValue(),
                0.000001
        );
    }

    @Test
    public void testAddition_NegativeValues() {

        Length l1 =
                new Length(5.0, LengthUnit.FEET);

        Length l2 =
                new Length(-2.0, LengthUnit.FEET);

        Length result = l1.add(l2);

        assertEquals(
                3.0,
                result.getValue(),
                0.000001
        );
    }

    @Test
    public void testAddition_NullSecondOperand() {

        Length feet =
                new Length(1.0, LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> feet.add(null)
        );
    }

    @Test
    public void testAddition_LargeValues() {

        Length l1 =
                new Length(1e6, LengthUnit.FEET);

        Length l2 =
                new Length(1e6, LengthUnit.FEET);

        Length result = l1.add(l2);

        assertEquals(
                2e6,
                result.getValue(),
                0.000001
        );
    }

    @Test
    public void testAddition_SmallValues() {

        Length l1 =
                new Length(0.001, LengthUnit.FEET);

        Length l2 =
                new Length(0.002, LengthUnit.FEET);

        Length result = l1.add(l2);

        assertEquals(
                0.003,
                result.getValue(),
                0.000001
        );
    }

    private static final double EPSILON = 0.01;

    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {

        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.FEET
                        );

        assertEquals(2.0, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {

        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.INCHES
                        );

        assertEquals(24.0, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {

        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.YARDS
                        );

        assertEquals(0.667, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Centimeters() {

        Length result =
                new Length(1.0, LengthUnit.INCHES)
                        .add(
                                new Length(
                                        1.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.CENTIMETERS
                        );

        assertEquals(5.08, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.CENTIMETERS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {

        Length result =
                new Length(2.0, LengthUnit.YARDS)
                        .add(
                                new Length(
                                        3.0,
                                        LengthUnit.FEET
                                ),
                                LengthUnit.YARDS
                        );

        assertEquals(3.0, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {

        Length result =
                new Length(2.0, LengthUnit.YARDS)
                        .add(
                                new Length(
                                        3.0,
                                        LengthUnit.FEET
                                ),
                                LengthUnit.FEET
                        );

        assertEquals(9.0, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Commutativity() {

        Length result1 =
                new Length(1.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.YARDS
                        );

        Length result2 =
                new Length(12.0, LengthUnit.INCHES)
                        .add(
                                new Length(
                                        1.0,
                                        LengthUnit.FEET
                                ),
                                LengthUnit.YARDS
                        );

        assertEquals(
                result1.getValue(),
                result2.getValue(),
                EPSILON
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {

        Length result =
                new Length(5.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        0.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.YARDS
                        );

        assertEquals(1.667, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {

        Length result =
                new Length(5.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        -2.0,
                                        LengthUnit.FEET
                                ),
                                LengthUnit.INCHES
                        );

        assertEquals(36.0, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(
                        1.0,
                        LengthUnit.FEET
                ).add(
                        new Length(
                                12.0,
                                LengthUnit.INCHES
                        ),
                        null
                )
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {

        Length result =
                new Length(1000.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        500.0,
                                        LengthUnit.FEET
                                ),
                                LengthUnit.INCHES
                        );

        assertEquals(18000.0, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {

        Length result =
                new Length(12.0, LengthUnit.INCHES)
                        .add(
                                new Length(
                                        12.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.YARDS
                        );

        assertEquals(0.667, result.getValue(), EPSILON);

        assertEquals(
                LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_AllUnitCombinations() {

        LengthUnit[] units =
                LengthUnit.values();

        for (LengthUnit unit1 : units) {

            for (LengthUnit unit2 : units) {

                for (LengthUnit targetUnit : units) {

                    Length length1 =
                            new Length(1.0, unit1);

                    Length length2 =
                            new Length(1.0, unit2);

                    Length result =
                            length1.add(
                                    length2,
                                    targetUnit
                            );

                    assertEquals(
                            targetUnit,
                            result.getUnit()
                    );
                }
            }
        }
    }

    @Test
    public void testAddition_ExplicitTargetUnit_PrecisionTolerance() {

        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(
                                new Length(
                                        1.0,
                                        LengthUnit.INCHES
                                ),
                                LengthUnit.CENTIMETERS
                        );

        assertEquals(33.02, result.getValue(), EPSILON);
    }

    @Test
    void testLengthUnitEnum_FeetConstant() {

        assertEquals(
                1.0,
                LengthUnit.FEET.getConversionFactor()
        );
    }

    @Test
    void testLengthUnitEnum_InchesConstant() {

        assertEquals(
                1.0 / 12.0,
                LengthUnit.INCHES.getConversionFactor()
        );
    }

    @Test
    void testLengthUnitEnum_YardsConstant() {

        assertEquals(
                3.0,
                LengthUnit.YARDS.getConversionFactor()
        );
    }

    @Test
    void testLengthUnitEnum_CentimetersConstant() {

        assertEquals(
                1.0 / 30.48,
                LengthUnit.CENTIMETERS.getConversionFactor(),
                EPSILON
        );
    }

    @Test
    void testConvertToBaseUnit_FeetToFeet() {

        double result =
                LengthUnit.FEET.convertToBaseUnit(5.0);

        assertEquals(
                5.0,
                result
        );
    }

    @Test
    void testConvertToBaseUnit_InchesToFeet() {

        double result =
                LengthUnit.INCHES.convertToBaseUnit(12.0);

        assertEquals(
                1.0,
                result
        );
    }

    @Test
    void testConvertToBaseUnit_YardsToFeet() {

        double result =
                LengthUnit.YARDS.convertToBaseUnit(1.0);

        assertEquals(
                3.0,
                result
        );
    }

    @Test
    void testConvertToBaseUnit_CentimetersToFeet() {

        double result =
                LengthUnit.CENTIMETERS.convertToBaseUnit(30.48);

        assertEquals(
                1.0,
                result,
                EPSILON
        );
    }

    @Test
    void testConvertFromBaseUnit_FeetToFeet() {

        double result =
                LengthUnit.FEET.convertFromBaseUnit(2.0);

        assertEquals(
                2.0,
                result
        );
    }

    @Test
    void testConvertFromBaseUnit_FeetToInches() {

        double result =
                LengthUnit.INCHES.convertFromBaseUnit(1.0);

        assertEquals(
                12.0,
                result
        );
    }

    @Test
    void testConvertFromBaseUnit_FeetToYards() {

        double result =
                LengthUnit.YARDS.convertFromBaseUnit(3.0);

        assertEquals(
                1.0,
                result
        );
    }

    @Test
    void testConvertFromBaseUnit_FeetToCentimeters() {

        double result =
                LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0);

        assertEquals(
                30.48,
                result,
                EPSILON
        );
    }


    @Test
    void testQuantityLengthRefactored_Equality() {

        Length length1 =
                new Length(1.0, LengthUnit.FEET);

        Length length2 =
                new Length(12.0, LengthUnit.INCHES);

        assertEquals(
                length1,
                length2
        );
    }

    @Test
    void testQuantityLengthRefactored_ConvertTo() {

        Length length =
                new Length(1.0, LengthUnit.FEET);

        Length converted =
                length.convertTo(LengthUnit.INCHES);

        assertEquals(
                new Length(12.0, LengthUnit.INCHES),
                converted
        );
    }

    @Test
    void testQuantityLengthRefactored_Add() {

        Length length1 =
                new Length(1.0, LengthUnit.FEET);

        Length length2 =
                new Length(12.0, LengthUnit.INCHES);

        Length result =
                length1.add(length2);

        assertEquals(
                new Length(2.0, LengthUnit.FEET),
                result
        );
    }

    @Test
    void testQuantityLengthRefactored_AddWithTargetUnit() {

        Length length1 =
                new Length(1.0, LengthUnit.FEET);

        Length length2 =
                new Length(12.0, LengthUnit.INCHES);

        Length result =
                length1.add(
                        length2,
                        LengthUnit.YARDS
                );

        assertEquals(
                new Length(
                        0.6666666666666666,
                        LengthUnit.YARDS
                ),
                result
        );
    }

    @Test
    void testQuantityLengthRefactored_NullUnit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(1.0, null)
        );
    }

    @Test
    void testQuantityLengthRefactored_InvalidValue() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(
                        Double.NaN,
                        LengthUnit.FEET
                )
        );
    }

    @Test
    void testBackwardCompatibility_UC1EqualityTests() {

        assertEquals(
                new Length(1.0, LengthUnit.FEET),
                new Length(1.0, LengthUnit.FEET)
        );

        assertNotEquals(
                new Length(1.0, LengthUnit.FEET),
                new Length(2.0, LengthUnit.FEET)
        );
    }

    @Test
    void testBackwardCompatibility_UC5ConversionTests() {

        Length result =
                new Length(
                        1.0,
                        LengthUnit.FEET
                ).convertTo(LengthUnit.INCHES);

        assertEquals(
                new Length(
                        12.0,
                        LengthUnit.INCHES
                ),
                result
        );
    }

    @Test
    void testBackwardCompatibility_UC6AdditionTests() {

        Length result =
                new Length(
                        1.0,
                        LengthUnit.FEET
                ).add(
                        new Length(
                                12.0,
                                LengthUnit.INCHES
                        )
                );

        assertEquals(
                new Length(
                        2.0,
                        LengthUnit.FEET
                ),
                result
        );
    }

    @Test
    void testBackwardCompatibility_UC7AdditionWithTargetUnitTests() {

        Length result =
                new Length(
                        1.0,
                        LengthUnit.FEET
                ).add(
                        new Length(
                                12.0,
                                LengthUnit.INCHES
                        ),
                        LengthUnit.INCHES
                );

        assertEquals(
                new Length(
                        24.0,
                        LengthUnit.INCHES
                ),
                result
        );
    }

    @Test
    public void testArchitecturalScalability_MultipleCategories() {

        assertNotNull(
                LengthUnit.FEET
        );

        assertNotNull(
                LengthUnit.INCHES
        );

        assertNotNull(
                LengthUnit.YARDS
        );

        assertNotNull(
                LengthUnit.CENTIMETERS
        );
    }

    @Test
    void testRoundTripConversion_RefactoredDesign() {

        Length original =
                new Length(
                        5.0,
                        LengthUnit.FEET
                );

        Length converted =
                original.convertTo(
                        LengthUnit.INCHES
                );

        Length roundTrip =
                converted.convertTo(
                        LengthUnit.FEET
                );

        assertEquals(
                original.getValue(),
                roundTrip.getValue(),
                EPSILON
        );
    }

    @Test
    void testUnitImmutability() {

        assertNotNull(LengthUnit.FEET);
        assertNotNull(LengthUnit.INCHES);
        assertNotNull(LengthUnit.YARDS);
        assertNotNull(LengthUnit.CENTIMETERS);
    }

    @Test
    public void testEquality_KilogramToKilogram_SameValue() {

        Weight w1 =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight w2 =
                new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    public void testEquality_KilogramToKilogram_DifferentValue() {

        Weight w1 =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight w2 =
                new Weight(2.0, WeightUnit.KILOGRAM);

        assertFalse(w1.equals(w2));
    }

    @Test
    public void testEquality_KilogramToGram_EquivalentValue() {

        Weight kg =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight gram =
                new Weight(1000.0, WeightUnit.GRAM);

        assertTrue(kg.equals(gram));
    }

    @Test
    public void testEquality_GramToKilogram_EquivalentValue() {

        Weight gram =
                new Weight(1000.0, WeightUnit.GRAM);

        Weight kg =
                new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(gram.equals(kg));
    }

    @Test
    public void testEquality_WeightVsLength_Incompatible() {

        Weight weight =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Length length =
                new Length(1.0, LengthUnit.FEET);

        assertFalse(weight.equals(length));
    }

    @Test
    public void testEquality_NullComparison() {

        Weight weight =
                new Weight(1.0, WeightUnit.KILOGRAM);

        assertFalse(weight.equals(null));
    }

    @Test
    public void testEquality_SameReference() {

        Weight weight =
                new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(weight.equals(weight));
    }

    @Test
    public void testEquality_NullUnit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Weight(1.0, null)
        );
    }

    @Test
    public void testEquality_TransitiveProperty() {

        Weight kg =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight gram =
                new Weight(1000.0, WeightUnit.GRAM);

        Weight pound =
                new Weight(2.20462, WeightUnit.POUND);

        assertTrue(kg.equals(gram));
        assertTrue(gram.equals(pound));
        assertTrue(kg.equals(pound));
    }

    @Test
    public void testEquality_ZeroValue() {

        Weight kg =
                new Weight(0.0, WeightUnit.KILOGRAM);

        Weight gram =
                new Weight(0.0, WeightUnit.GRAM);

        assertTrue(kg.equals(gram));
    }

    @Test
    public void testEquality_NegativeWeight() {

        Weight kg =
                new Weight(-1.0, WeightUnit.KILOGRAM);

        Weight gram =
                new Weight(-1000.0, WeightUnit.GRAM);

        assertTrue(kg.equals(gram));
    }

    @Test
    public void testEquality_LargeWeightValue() {

        Weight gram =
                new Weight(1000000.0, WeightUnit.GRAM);

        Weight kg =
                new Weight(1000.0, WeightUnit.KILOGRAM);

        assertTrue(gram.equals(kg));
    }

    @Test
    public void testEquality_SmallWeightValue() {

        Weight kg =
                new Weight(0.001, WeightUnit.KILOGRAM);

        Weight gram =
                new Weight(1.0, WeightUnit.GRAM);

        assertTrue(kg.equals(gram));
    }

    @Test
    public void testConversion_PoundToKilogram() {

        Weight pound =
                new Weight(2.20462, WeightUnit.POUND);

        Weight kg =
                pound.convertTo(WeightUnit.KILOGRAM);

        assertEquals(
                1.0,
                kg.getValue(),
                EPSILON
        );
    }

    @Test
    public void testConversion_KilogramToPound() {

        Weight kg =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight pound =
                kg.convertTo(WeightUnit.POUND);

        assertEquals(
                2.20462,
                pound.getValue(),
                0.001
        );
    }

    @Test
    public void testConversion_SameUnit() {

        Weight kg =
                new Weight(5.0, WeightUnit.KILOGRAM);

        Weight result =
                kg.convertTo(WeightUnit.KILOGRAM);

        assertEquals(
                5.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    public void testWeightConversion_ZeroValue() {

        Weight kg =
                new Weight(0.0, WeightUnit.KILOGRAM);

        Weight gram =
                kg.convertTo(WeightUnit.GRAM);

        assertEquals(
                0.0,
                gram.getValue(),
                EPSILON
        );
    }

    @Test
    public void testWeightConversion_NegativeValue() {

        Weight kg =
                new Weight(-1.0, WeightUnit.KILOGRAM);

        Weight gram =
                kg.convertTo(WeightUnit.GRAM);

        assertEquals(
                -1000.0,
                gram.getValue(),
                EPSILON
        );
    }

    @Test
    public void testConversion_RoundTrip() {

        Weight original =
                new Weight(1.5, WeightUnit.KILOGRAM);

        Weight gram =
                original.convertTo(WeightUnit.GRAM);

        Weight convertedBack =
                gram.convertTo(WeightUnit.KILOGRAM);

        assertEquals(
                original.getValue(),
                convertedBack.getValue(),
                EPSILON
        );
    }

    @Test
    public void testAddition_SameUnit_KilogramPlusKilogram() {

        Weight w1 =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight w2 =
                new Weight(2.0, WeightUnit.KILOGRAM);

        Weight result =
                w1.add(w2);

        assertEquals(
                3.0,
                result.getValue(),
                EPSILON
        );

        assertEquals(
                WeightUnit.KILOGRAM,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_KilogramPlusGram() {

        Weight kg =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight gram =
                new Weight(1000.0, WeightUnit.GRAM);

        Weight result =
                kg.add(gram);

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );

        assertEquals(
                WeightUnit.KILOGRAM,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_PoundPlusKilogram() {

        Weight pound =
                new Weight(2.20462, WeightUnit.POUND);

        Weight kg =
                new Weight(1.0, WeightUnit.KILOGRAM);

        Weight result =
                pound.add(kg);

        assertEquals(
                4.40924,
                result.getValue(),
                0.01
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Kilogram() {

        Weight result =
                new Weight(1.0, WeightUnit.KILOGRAM)
                        .add(
                                new Weight(
                                        1000.0,
                                        WeightUnit.GRAM
                                ),
                                WeightUnit.GRAM
                        );

        assertEquals(
                2000.0,
                result.getValue(),
                EPSILON
        );

        assertEquals(
                WeightUnit.GRAM,
                result.getUnit()
        );
    }

    @Test
    public void testWeightAddition_Commutativity() {

        Weight result1 =
                new Weight(1.0, WeightUnit.KILOGRAM)
                        .add(
                                new Weight(
                                        1000.0,
                                        WeightUnit.GRAM
                                )
                        );

        Weight result2 =
                new Weight(1000.0, WeightUnit.GRAM)
                        .add(
                                new Weight(
                                        1.0,
                                        WeightUnit.KILOGRAM
                                )
                        );

        assertTrue(result1.equals(result2));
    }

    @Test
    public void testWeightAddition_WithZero() {

        Weight result =
                new Weight(5.0, WeightUnit.KILOGRAM)
                        .add(
                                new Weight(
                                        0.0,
                                        WeightUnit.GRAM
                                )
                        );

        assertEquals(
                5.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    public void testWeightAddition_NegativeValues() {

        Weight result =
                new Weight(5.0, WeightUnit.KILOGRAM)
                        .add(
                                new Weight(
                                        -2000.0,
                                        WeightUnit.GRAM
                                )
                        );

        assertEquals(
                3.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    public void testWeightAddition_LargeValues() {

        Weight w1 =
                new Weight(1e6, WeightUnit.KILOGRAM);

        Weight w2 =
                new Weight(1e6, WeightUnit.KILOGRAM);

        Weight result =
                w1.add(w2);

        assertEquals(
                2e6,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testIMeasurableInterface_LengthUnitImplementation() {

        IMeasurable unit = LengthUnit.FEET;

        assertEquals(
                1.0,
                unit.getConversionFactor()
        );

        assertEquals(
                5.0,
                unit.convertToBaseUnit(5.0)
        );

        assertEquals(
                5.0,
                unit.convertFromBaseUnit(5.0)
        );

        assertEquals(
                "FEET",
                unit.getUnitName()
        );
    }

    @Test
    void testIMeasurableInterface_WeightUnitImplementation() {

        IMeasurable unit = WeightUnit.KILOGRAM;

        assertEquals(
                1.0,
                unit.getConversionFactor()
        );

        assertEquals(
                5.0,
                unit.convertToBaseUnit(5.0)
        );

        assertEquals(
                5.0,
                unit.convertFromBaseUnit(5.0)
        );

        assertEquals(
                "KILOGRAM",
                unit.getUnitName()
        );
    }

    @Test
    void testIMeasurableInterface_ConsistentBehavior() {

        IMeasurable length =
                LengthUnit.FEET;

        IMeasurable weight =
                WeightUnit.KILOGRAM;

        assertNotNull(
                length.getUnitName()
        );

        assertNotNull(
                weight.getUnitName()
        );
    }

    @Test
    void testGenericQuantity_LengthOperations_Equality() {

        Quantity<LengthUnit> feet =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<LengthUnit> inches =
                new Quantity<>(
                        12.0,
                        LengthUnit.INCHES
                );

        assertTrue(
                feet.equals(inches)
        );
    }

    @Test
    void testGenericQuantity_WeightOperations_Equality() {

        Quantity<WeightUnit> kilogram =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        Quantity<WeightUnit> gram =
                new Quantity<>(
                        1000.0,
                        WeightUnit.GRAM
                );

        assertTrue(
                kilogram.equals(gram)
        );
    }

    @Test
    void testGenericQuantity_LengthOperations_Conversion() {

        Quantity<LengthUnit> feet =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<LengthUnit> result =
                feet.convertTo(
                        LengthUnit.INCHES
                );

        assertEquals(
                12.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testGenericQuantity_WeightOperations_Conversion() {

        Quantity<WeightUnit> kilogram =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        Quantity<WeightUnit> result =
                kilogram.convertTo(
                        WeightUnit.GRAM
                );

        assertEquals(
                1000.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testGenericQuantity_LengthOperations_Addition() {

        Quantity<LengthUnit> feet =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<LengthUnit> inches =
                new Quantity<>(
                        12.0,
                        LengthUnit.INCHES
                );

        Quantity<LengthUnit> result =
                feet.add(
                        inches,
                        LengthUnit.FEET
                );

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testGenericQuantity_WeightOperations_Addition() {

        Quantity<WeightUnit> kilogram =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        Quantity<WeightUnit> gram =
                new Quantity<>(
                        1000.0,
                        WeightUnit.GRAM
                );

        Quantity<WeightUnit> result =
                kilogram.add(
                        gram,
                        WeightUnit.KILOGRAM
                );

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testCrossCategoryPrevention_LengthVsWeight() {

        Quantity<LengthUnit> length =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<WeightUnit> weight =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        assertFalse(
                length.equals(weight)
        );
    }

    @Test
    void testGenericQuantity_ConstructorValidation_NullUnit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity<>(
                        1.0,
                        null
                )
        );
    }

    @Test
    void testGenericQuantity_ConstructorValidation_InvalidValue() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity<>(
                        Double.NaN,
                        LengthUnit.FEET
                )
        );
    }

    @Test
    void testGenericQuantity_Conversion_AllUnitCombinations() {

        Quantity<LengthUnit> feet =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        assertEquals(
                12.0,
                feet.convertTo(
                        LengthUnit.INCHES
                ).getValue(),
                EPSILON
        );

        Quantity<WeightUnit> kilogram =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        assertEquals(
                1000.0,
                kilogram.convertTo(
                        WeightUnit.GRAM
                ).getValue(),
                EPSILON
        );
    }

    @Test
    void testGenericQuantity_Addition_AllUnitCombinations() {

        Quantity<LengthUnit> length1 =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<LengthUnit> length2 =
                new Quantity<>(
                        12.0,
                        LengthUnit.INCHES
                );

        Quantity<LengthUnit> result =
                length1.add(
                        length2,
                        LengthUnit.FEET
                );

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {

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

        assertTrue(
                controller.performComparison(
                        feet,
                        inches
                )
        );
    }

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {

        QuantityDTO kilogram =
                new QuantityDTO(
                        1.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityDTO gram =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.WeightUnit.GRAM
                );

        QuantityDTO result =
                controller.performConversion(
                        kilogram,
                        gram
                );

        assertEquals(
                1000.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {

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

        QuantityDTO target =
                new QuantityDTO(
                        0.0,
                        QuantityDTO.WeightUnit.KILOGRAM
                );

        QuantityDTO result =
                controller.performAddition(
                        kilogram,
                        gram,
                        target
                );

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testTypeWildcard_FlexibleSignatures() {

        Quantity<?> length =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<?> weight =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        assertNotNull(length);
        assertNotNull(weight);
    }

    @Test
    void testHashCode_GenericQuantity_Consistency() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<LengthUnit> q2 =
                new Quantity<>(
                        12.0,
                        LengthUnit.INCHES
                );

        assertEquals(
                q1.hashCode(),
                q2.hashCode()
        );
    }

    @Test
    void testEquals_GenericQuantity_ContractPreservation() {

        Quantity<WeightUnit> q1 =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        Quantity<WeightUnit> q2 =
                new Quantity<>(
                        1000.0,
                        WeightUnit.GRAM
                );

        assertTrue(q1.equals(q2));
        assertTrue(q2.equals(q1));
    }

    @Test
    void testEnumAsUnitCarrier_BehaviorEncapsulation() {

        IMeasurable unit =
                WeightUnit.POUND;

        double result =
                unit.convertToBaseUnit(1.0);

        assertEquals(
                0.453592,
                result,
                EPSILON
        );
    }

    @Test
    void testTypeErasure_RuntimeSafety() {

        Quantity<?> length =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                );

        Quantity<?> weight =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        assertFalse(
                length.equals(weight)
        );
    }

    @Test
    void testCompositionOverInheritance_Flexibility() {

        Quantity<LengthUnit> quantity =
                new Quantity<>(
                        5.0,
                        LengthUnit.FEET
                );

        assertEquals(
                5.0,
                quantity.getValue()
        );
    }

    @Test
    void testImmutability_GenericQuantity() {

        Quantity<LengthUnit> quantity =
                new Quantity<>(
                        5.0,
                        LengthUnit.FEET
                );

        Quantity<LengthUnit> converted =
                quantity.convertTo(
                        LengthUnit.INCHES
                );

        assertNotSame(
                quantity,
                converted
        );

    }

    @Test
    void testEquality_LitreToLitre_SameValue() {

        Quantity<VolumeUnit> litre1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> litre2 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(litre1.equals(litre2));
    }

    @Test
    void testEquality_LitreToLitre_DifferentValue() {

        Quantity<VolumeUnit> litre1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> litre2 =
                new Quantity<>(2.0, VolumeUnit.LITRE);

        assertFalse(litre1.equals(litre2));
    }

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(litre.equals(millilitre));
    }

    @Test
    void testEquality_MillilitreToLitre_EquivalentValue() {

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(millilitre.equals(litre));
    }

    @Test
    void testEquality_LitreToGallon_EquivalentValue() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> gallon =
                new Quantity<>(0.264172, VolumeUnit.GALLON);

        assertTrue(litre.equals(gallon));
    }

    @Test
    void testEquality_GallonToLitre_EquivalentValue() {

        Quantity<VolumeUnit> gallon =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> litre =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        assertTrue(gallon.equals(litre));
    }

    @Test
    void testEquality_VolumeVsLength_Incompatible() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(litre.equals(feet));
    }

    @Test
    void testEquality_VolumeVsWeight_Incompatible() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<WeightUnit> kilogram =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(litre.equals(kilogram));
    }

    @Test
    void testVolumeEquality_NullComparison() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertFalse(litre.equals(null));
    }

    @Test
    void testVolumeEquality_SameReference() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(litre.equals(litre));
    }

    @Test
    void testVolumeEquality_NullUnit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null)
        );
    }

    @Test
    void testVolumeEquality_TransitiveProperty() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> litreAgain =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(litre.equals(millilitre));
        assertTrue(millilitre.equals(litreAgain));
        assertTrue(litre.equals(litreAgain));
    }

    @Test
    void testVolumeEquality_ZeroValue() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(0.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(0.0, VolumeUnit.MILLILITRE);

        assertTrue(litre.equals(millilitre));
    }

    @Test
    void testEquality_NegativeVolume() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(-1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(-1000.0, VolumeUnit.MILLILITRE);

        assertTrue(litre.equals(millilitre));
    }

    @Test
    void testEquality_LargeVolumeValue() {

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> litre =
                new Quantity<>(1000.0, VolumeUnit.LITRE);

        assertTrue(millilitre.equals(litre));
    }

    @Test
    void testEquality_SmallVolumeValue() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(0.001, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1.0, VolumeUnit.MILLILITRE);

        assertTrue(litre.equals(millilitre));
    }

    // CONVERSION TESTS

    @Test
    void testConversion_LitreToMillilitre() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(
                1000.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testConversion_MillilitreToLitre() {

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                millilitre.convertTo(VolumeUnit.LITRE);

        assertEquals(
                1.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testConversion_GallonToLitre() {

        Quantity<VolumeUnit> gallon =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> result =
                gallon.convertTo(VolumeUnit.LITRE);

        assertEquals(
                3.78541,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testConversion_LitreToGallon() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.convertTo(VolumeUnit.GALLON);

        assertEquals(
                1.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testConversion_MillilitreToGallon() {

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                millilitre.convertTo(VolumeUnit.GALLON);

        assertEquals(
                0.264172,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testVolumeConversion_SameUnit() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.convertTo(VolumeUnit.LITRE);

        assertEquals(
                5.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testVolumeConversion_ZeroValue() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(0.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(
                0.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testVolumeConversion_NegativeValue() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(-1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(
                -1000.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testVolumeConversion_RoundTrip() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.5, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.convertTo(VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(
                1.5,
                result.getValue(),
                EPSILON
        );
    }

    // ADDITION TESTS

    @Test
    void testAddition_SameUnit_LitrePlusLitre() {

        Quantity<VolumeUnit> litre1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> litre2 =
                new Quantity<>(2.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre1.add(litre2);

        assertEquals(
                3.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testAddition_CrossUnit_LitrePlusMillilitre() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                litre.add(millilitre);

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testAddition_ExplicitTargetUnit_Millilitre() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                litre.add(
                        millilitre,
                        VolumeUnit.MILLILITRE
                );

        assertEquals(
                2000.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testAddition_ExplicitTargetUnit_Gallon() {

        Quantity<VolumeUnit> litre1 =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> litre2 =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre1.add(
                        litre2,
                        VolumeUnit.GALLON
                );

        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testVolumeAddition_WithZero() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> zero =
                new Quantity<>(0.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                litre.add(zero);

        assertEquals(
                5.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testVolumeAddition_NegativeValues() {

        Quantity<VolumeUnit> litre =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> negative =
                new Quantity<>(-2000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                litre.add(negative);

        assertEquals(
                3.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testVolumeUnitEnum_LitreConstant() {

        assertEquals(
                1.0,
                VolumeUnit.LITRE.getConversionFactor(),
                EPSILON
        );
    }

    @Test
    void testVolumeUnitEnum_MillilitreConstant() {

        assertEquals(
                0.001,
                VolumeUnit.MILLILITRE.getConversionFactor(),
                EPSILON
        );
    }

    @Test
    void testVolumeUnitEnum_GallonConstant() {

        assertEquals(
                3.78541,
                VolumeUnit.GALLON.getConversionFactor(),
                EPSILON
        );
    }

    @Test
    void testConvertToBaseUnit_MillilitreToLitre() {

        double result =
                VolumeUnit.MILLILITRE
                        .convertToBaseUnit(1000.0);

        assertEquals(
                1.0,
                result,
                EPSILON
        );
    }

    @Test
    void testConvertToBaseUnit_GallonToLitre() {

        double result =
                VolumeUnit.GALLON
                        .convertToBaseUnit(1.0);

        assertEquals(
                3.78541,
                result,
                EPSILON
        );
    }

    @Test
    void testConvertFromBaseUnit_LitreToMillilitre() {

        double result =
                VolumeUnit.MILLILITRE
                        .convertFromBaseUnit(1.0);

        assertEquals(
                1000.0,
                result,
                EPSILON
        );
    }

    @Test
    void testConvertFromBaseUnit_LitreToGallon() {

        double result =
                VolumeUnit.GALLON
                        .convertFromBaseUnit(3.78541);

        assertEquals(
                1.0,
                result,
                EPSILON
        );

    }
    //Substraction test cases
    @Test
    void testSubtraction_SameUnit_FeetMinusFeet() {

        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(5.0,
                                        LengthUnit.FEET));

        assertEquals(5.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_SameUnit_LitreMinusLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(10.0,
                        VolumeUnit.LITRE)
                        .subtract(
                                new Quantity<>(3.0,
                                        VolumeUnit.LITRE));

        assertEquals(7.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_CrossUnit_FeetMinusInches() {

        Quantity<LengthUnit> result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(6.0,
                                        LengthUnit.INCHES));

        assertEquals(9.5,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_CrossUnit_InchesMinusFeet() {

        Quantity<LengthUnit> result =
                new Quantity<>(120.0,
                        LengthUnit.INCHES)
                        .subtract(
                                new Quantity<>(5.0,
                                        LengthUnit.FEET));

        assertEquals(60.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Feet() {

        Quantity<LengthUnit> result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(6.0,
                                        LengthUnit.INCHES),
                                LengthUnit.FEET);

        assertEquals(9.5,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Inches() {

        Quantity<LengthUnit> result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(6.0,
                                        LengthUnit.INCHES),
                                LengthUnit.INCHES);

        assertEquals(114.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Millilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(5.0,
                        VolumeUnit.LITRE)
                        .subtract(
                                new Quantity<>(2.0,
                                        VolumeUnit.LITRE),
                                VolumeUnit.MILLILITRE);

        assertEquals(3000.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_ResultingInNegative() {

        Quantity<LengthUnit> result =
                new Quantity<>(5.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(10.0,
                                        LengthUnit.FEET));

        assertEquals(-5.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_ResultingInZero() {

        Quantity<LengthUnit> result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(120.0,
                                        LengthUnit.INCHES));

        assertEquals(0.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_WithZeroOperand() {

        Quantity<LengthUnit> result =
                new Quantity<>(5.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(0.0,
                                        LengthUnit.INCHES));

        assertEquals(5.0,
                result.getValue(),
                EPSILON);
    }
    @Test
    void testSubtraction_WithNegativeValues() {

        Quantity<LengthUnit> result =
                new Quantity<>(5.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(-2.0,
                                        LengthUnit.FEET));

        assertEquals(7.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_NonCommutative() {

        Quantity<LengthUnit> result1 =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(5.0,
                                        LengthUnit.FEET));

        Quantity<LengthUnit> result2 =
                new Quantity<>(5.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(10.0,
                                        LengthUnit.FEET));

        assertEquals(5.0,
                result1.getValue(),
                EPSILON);

        assertEquals(-5.0,
                result2.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_WithLargeValues() {

        Quantity<WeightUnit> result =
                new Quantity<>(1e6,
                        WeightUnit.KILOGRAM)
                        .subtract(
                                new Quantity<>(5e5,
                                        WeightUnit.KILOGRAM));

        assertEquals(5e5,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_WithSmallValues() {

        Quantity<LengthUnit> result =
                new Quantity<>(0.001,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(0.0005,
                                        LengthUnit.FEET));

        assertEquals(0.0005,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_NullOperand() {

        Quantity<LengthUnit> feet =
                new Quantity<>(10.0,
                        LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> feet.subtract(null)
        );
    }

    @Test
    void testSubtraction_NullTargetUnit() {

        Quantity<LengthUnit> feet =
                new Quantity<>(10.0,
                        LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> feet.subtract(
                        new Quantity<>(5.0,
                                LengthUnit.FEET),
                        null)
        );
    }

    @Test
    void testSubtraction_AllMeasurementCategories() {

        Quantity<LengthUnit> lengthResult =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(5.0,
                                        LengthUnit.FEET));

        Quantity<WeightUnit> weightResult =
                new Quantity<>(10.0,
                        WeightUnit.KILOGRAM)
                        .subtract(
                                new Quantity<>(5.0,
                                        WeightUnit.KILOGRAM));

        Quantity<VolumeUnit> volumeResult =
                new Quantity<>(10.0,
                        VolumeUnit.LITRE)
                        .subtract(
                                new Quantity<>(5.0,
                                        VolumeUnit.LITRE));

        assertEquals(5.0,
                lengthResult.getValue(),
                EPSILON);

        assertEquals(5.0,
                weightResult.getValue(),
                EPSILON);

        assertEquals(5.0,
                volumeResult.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_ChainedOperations() {

        Quantity<LengthUnit> result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(2.0,
                                        LengthUnit.FEET))
                        .subtract(
                                new Quantity<>(1.0,
                                        LengthUnit.FEET));

        assertEquals(7.0,
                result.getValue(),
                EPSILON);
    }

    @Test
    void testSubtraction_Immutability() {

        Quantity<LengthUnit> original =
                new Quantity<>(10.0,
                        LengthUnit.FEET);

        Quantity<LengthUnit> other =
                new Quantity<>(5.0,
                        LengthUnit.FEET);

        Quantity<LengthUnit> result =
                original.subtract(other);

        assertEquals(10.0,
                original.getValue(),
                EPSILON);

        assertEquals(5.0,
                other.getValue(),
                EPSILON);

        assertEquals(5.0,
                result.getValue(),
                EPSILON);
    }
    //Division test cases
    @Test
    void testDivision_SameUnit_FeetDividedByFeet() {

        double result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(2.0,
                                        LengthUnit.FEET));

        assertEquals(5.0,
                result,
                EPSILON);
    }

    @Test
    void testDivision_SameUnit_LitreDividedByLitre() {

        double result =
                new Quantity<>(10.0,
                        VolumeUnit.LITRE)
                        .divide(
                                new Quantity<>(5.0,
                                        VolumeUnit.LITRE));

        assertEquals(2.0,
                result,
                EPSILON);
    }

    @Test
    void testDivision_CrossUnit_FeetDividedByInches() {

        double result =
                new Quantity<>(24.0,
                        LengthUnit.INCHES)
                        .divide(
                                new Quantity<>(2.0,
                                        LengthUnit.FEET));

        assertEquals(1.0,
                result,
                EPSILON);
    }

    @Test
    void testDivision_CrossUnit_KilogramDividedByGram() {

        double result =
                new Quantity<>(2.0,
                        WeightUnit.KILOGRAM)
                        .divide(
                                new Quantity<>(2000.0,
                                        WeightUnit.GRAM));

        assertEquals(1.0,
                result,
                EPSILON);
    }

    @Test
    void testDivision_RatioGreaterThanOne() {

        double result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(2.0,
                                        LengthUnit.FEET));

        assertEquals(5.0,
                result,
                EPSILON);
    }

    @Test
    void testDivision_RatioLessThanOne() {

        double result =
                new Quantity<>(5.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(10.0,
                                        LengthUnit.FEET));

        assertEquals(0.5,
                result,
                EPSILON);
    }

    @Test
    void testDivision_RatioEqualToOne() {

        double result =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(10.0,
                                        LengthUnit.FEET));

        assertEquals(1.0,
                result,
                EPSILON);
    }

    @Test
    void testDivision_NonCommutative() {

        double result1 =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(5.0,
                                        LengthUnit.FEET));

        double result2 =
                new Quantity<>(5.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(10.0,
                                        LengthUnit.FEET));

        assertEquals(2.0,
                result1,
                EPSILON);

        assertEquals(0.5,
                result2,
                EPSILON);
    }

    @Test
    void testDivision_ByZero() {

        Quantity<LengthUnit> feet =
                new Quantity<>(10.0,
                        LengthUnit.FEET);

        assertThrows(
                ArithmeticException.class,
                () -> feet.divide(
                        new Quantity<>(0.0,
                                LengthUnit.FEET))
        );
    }

    @Test
    void testDivision_WithLargeRatio() {

        double result =
                new Quantity<>(1e6,
                        WeightUnit.KILOGRAM)
                        .divide(
                                new Quantity<>(1.0,
                                        WeightUnit.KILOGRAM));

        assertEquals(1e6,
                result,
                EPSILON);
    }

    @Test
    void testDivision_WithSmallRatio() {

        double result =
                new Quantity<>(1.0,
                        WeightUnit.KILOGRAM)
                        .divide(
                                new Quantity<>(1e6,
                                        WeightUnit.KILOGRAM));

        assertEquals(1e-6,
                result,
                EPSILON);
    }

    @Test
    void testDivision_NullOperand() {

        Quantity<LengthUnit> feet =
                new Quantity<>(10.0,
                        LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> feet.divide(null)
        );
    }

    @Test
    void testDivision_AllMeasurementCategories() {

        double lengthResult =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(5.0,
                                        LengthUnit.FEET));

        double weightResult =
                new Quantity<>(10.0,
                        WeightUnit.KILOGRAM)
                        .divide(
                                new Quantity<>(5.0,
                                        WeightUnit.KILOGRAM));

        double volumeResult =
                new Quantity<>(10.0,
                        VolumeUnit.LITRE)
                        .divide(
                                new Quantity<>(5.0,
                                        VolumeUnit.LITRE));

        assertEquals(2.0,
                lengthResult,
                EPSILON);

        assertEquals(2.0,
                weightResult,
                EPSILON);

        assertEquals(2.0,
                volumeResult,
                EPSILON);
    }

    @Test
    void testDivision_Immutability() {

        Quantity<LengthUnit> original =
                new Quantity<>(10.0,
                        LengthUnit.FEET);

        Quantity<LengthUnit> divisor =
                new Quantity<>(5.0,
                        LengthUnit.FEET);

        double result =
                original.divide(divisor);

        assertEquals(10.0,
                original.getValue(),
                EPSILON);

        assertEquals(5.0,
                divisor.getValue(),
                EPSILON);

        assertEquals(2.0,
                result,
                EPSILON);
    }


    @Test
    void testSubtractionAndDivision_Integration() {

        Quantity<LengthUnit> difference =
                new Quantity<>(10.0,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(2.0,
                                        LengthUnit.FEET));

        double result =
                difference.divide(
                        new Quantity<>(4.0,
                                LengthUnit.FEET));

        assertEquals(
                2.0,
                result,
                EPSILON
        );
    }

    @Test
    void testSubtractionAddition_Inverse() {

        Quantity<LengthUnit> original =
                new Quantity<>(10.0,
                        LengthUnit.FEET);

        Quantity<LengthUnit> added =
                original.add(
                        new Quantity<>(5.0,
                                LengthUnit.FEET));

        Quantity<LengthUnit> result =
                added.subtract(
                        new Quantity<>(5.0,
                                LengthUnit.FEET));

        assertEquals(
                original.getValue(),
                result.getValue(),
                EPSILON
        );

        assertEquals(
                original.getUnit(),
                result.getUnit()
        );
    }

    @Test
    void testSubtraction_PrecisionAndRounding() {

        Quantity<LengthUnit> result =
                new Quantity<>(1.1111,
                        LengthUnit.FEET)
                        .subtract(
                                new Quantity<>(0.1111,
                                        LengthUnit.FEET));

        assertEquals(
                1.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testDivision_PrecisionHandling() {

        double result =
                new Quantity<>(1.0,
                        LengthUnit.FEET)
                        .divide(
                                new Quantity<>(3.0,
                                        LengthUnit.FEET));

        assertEquals(
                0.3333,
                result,
                0.0001
        );
    }

    @Test
    void testUC13_NullOperandValidationConsistency() {

        Quantity<LengthUnit> feet =
                new Quantity<>(
                        10.0,
                        LengthUnit.FEET
                );


        Exception addException =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> feet.add(null)
                );


        Exception subtractException =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> feet.subtract(null)
                );


        Exception divideException =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> feet.divide(null)
                );


        assertEquals(
                addException.getMessage(),
                subtractException.getMessage()
        );


        assertEquals(
                subtractException.getMessage(),
                divideException.getMessage()
        );
    }



    @Test
    void testUC13_CrossCategoryValidationConsistency() {

        Quantity<LengthUnit> length =
                new Quantity<>(
                        10.0,
                        LengthUnit.FEET
                );


        Quantity<WeightUnit> weight =
                new Quantity<>(
                        5.0,
                        WeightUnit.KILOGRAM
                );


        assertThrows(
                IllegalArgumentException.class,
                () -> length.add(
                        (Quantity) weight
                )
        );


        assertThrows(
                IllegalArgumentException.class,
                () -> length.subtract(
                        (Quantity) weight
                )
        );


        assertThrows(
                IllegalArgumentException.class,
                () -> length.divide(
                        (Quantity) weight
                )
        );
    }



    @Test
    void testUC13_DivisionByZeroHandledByArithmeticOperation() {

        Quantity<LengthUnit> feet =
                new Quantity<>(
                        10.0,
                        LengthUnit.FEET
                );


        Quantity<LengthUnit> zero =
                new Quantity<>(
                        0.0,
                        LengthUnit.FEET
                );


        assertThrows(
                ArithmeticException.class,
                () -> feet.divide(zero)
        );
    }



    @Test
    void testUC13_AdditionBehaviorUnchanged() {

        Quantity<LengthUnit> result =
                new Quantity<>(
                        1.0,
                        LengthUnit.FEET
                )
                        .add(
                                new Quantity<>(
                                        12.0,
                                        LengthUnit.INCHES
                                )
                        );


        assertEquals(
                2.0,
                result.getValue(),
                EPSILON
        );


        assertEquals(
                LengthUnit.FEET,
                result.getUnit()
        );
    }



    @Test
    void testUC13_SubtractionBehaviorUnchanged() {

        Quantity<LengthUnit> result =
                new Quantity<>(
                        10.0,
                        LengthUnit.FEET
                )
                        .subtract(
                                new Quantity<>(
                                        6.0,
                                        LengthUnit.INCHES
                                )
                        );


        assertEquals(
                9.5,
                result.getValue(),
                EPSILON
        );
    }



    @Test
    void testUC13_DivisionBehaviorUnchanged() {

        double result =
                new Quantity<>(
                        24.0,
                        LengthUnit.INCHES
                )
                        .divide(
                                new Quantity<>(
                                        2.0,
                                        LengthUnit.FEET
                                )
                        );


        assertEquals(
                1.0,
                result,
                EPSILON
        );
    }



    @Test
    void testUC13_OperationsMaintainImmutability() {

        Quantity<LengthUnit> original =
                new Quantity<>(
                        10.0,
                        LengthUnit.FEET
                );


        Quantity<LengthUnit> other =
                new Quantity<>(
                        5.0,
                        LengthUnit.FEET
                );


        Quantity<LengthUnit> result =
                original.subtract(other);


        assertEquals(
                10.0,
                original.getValue(),
                EPSILON
        );


        assertEquals(
                5.0,
                other.getValue(),
                EPSILON
        );


        assertEquals(
                5.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void shouldCompareCelsiusAndFahrenheit() {

        Quantity<TemperatureUnit> celsius =
                new Quantity<>(
                        0.0,
                        TemperatureUnit.CELSIUS
                );

        Quantity<TemperatureUnit> fahrenheit =
                new Quantity<>(
                        32.0,
                        TemperatureUnit.FAHRENHEIT
                );

        assertEquals(
                celsius,
                fahrenheit
        );
    }

    @Test
    void shouldConvertCelsiusToFahrenheit() {

        Quantity<TemperatureUnit> celsius =
                new Quantity<>(
                        100.0,
                        TemperatureUnit.CELSIUS
                );

        Quantity<TemperatureUnit> fahrenheit =
                celsius.convertTo(
                        TemperatureUnit.FAHRENHEIT
                );

        assertEquals(
                212.0,
                fahrenheit.getValue(),
                EPSILON
        );
    }

    @Test
    void shouldConvertKelvinToCelsius() {

        Quantity<TemperatureUnit> kelvin =
                new Quantity<>(
                        273.15,
                        TemperatureUnit.KELVIN
                );

        Quantity<TemperatureUnit> celsius =
                kelvin.convertTo(
                        TemperatureUnit.CELSIUS
                );

        assertEquals(
                0.0,
                celsius.getValue(),
                EPSILON
        );
    }

    @Test
    void shouldNotAllowTemperatureAddition() {

        Quantity<TemperatureUnit> first =
                new Quantity<>(
                        10.0,
                        TemperatureUnit.CELSIUS
                );

        Quantity<TemperatureUnit> second =
                new Quantity<>(
                        20.0,
                        TemperatureUnit.CELSIUS
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> first.add(second)
        );
    }

    @Test
    void shouldNotAllowTemperatureSubtraction() {

        Quantity<TemperatureUnit> first =
                new Quantity<>(
                        10.0,
                        TemperatureUnit.CELSIUS
                );

        Quantity<TemperatureUnit> second =
                new Quantity<>(
                        20.0,
                        TemperatureUnit.CELSIUS
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> first.subtract(second)
        );
    }

    @Test
    void shouldNotAllowTemperatureDivision() {

        Quantity<TemperatureUnit> first =
                new Quantity<>(
                        10.0,
                        TemperatureUnit.CELSIUS
                );

        Quantity<TemperatureUnit> second =
                new Quantity<>(
                        20.0,
                        TemperatureUnit.CELSIUS
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> first.divide(second)
        );
    }




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