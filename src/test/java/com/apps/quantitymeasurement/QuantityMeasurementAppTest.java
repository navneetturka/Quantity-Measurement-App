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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
                QuantityMeasurementApp
                        .demonstrateEquality(
                                feet,
                                inches
                        )
        );
    }

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {

        Quantity<WeightUnit> kilogram =
                new Quantity<>(
                        1.0,
                        WeightUnit.KILOGRAM
                );

        Quantity<WeightUnit> result =
                QuantityMeasurementApp
                        .demonstrateConversion(
                                kilogram,
                                WeightUnit.GRAM
                        );

        assertEquals(
                1000.0,
                result.getValue(),
                EPSILON
        );
    }

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {

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
                QuantityMeasurementApp
                        .demonstrateAddition(
                                kilogram,
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
}