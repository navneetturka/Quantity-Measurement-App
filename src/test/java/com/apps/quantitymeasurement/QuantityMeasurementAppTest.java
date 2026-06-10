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
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testInchToInch_SameValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testFeetToInch_Equal() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testFeetToFeet_Different() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(2.0, Length.LengthUnit.FEET);

        assertFalse(l1.equals(l2));
    }

    @Test
    public void testCrossUnit_Different() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(10.0, Length.LengthUnit.INCHES);

        assertFalse(l1.equals(l2));
    }

    @Test
    public void testNullComparison() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }

    @Test
    public void testSameReference() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertTrue(l1.equals(l1));
    }
    @Test
    public void testEquality_YardToYard_SameValue() {

        Length yard1 =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length yard2 =
                new Length(1.0, Length.LengthUnit.YARDS);

        assertTrue(yard1.equals(yard2));
    }

    @Test
    public void testEquality_YardToYard_DifferentValue() {

        Length yard1 =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length yard2 =
                new Length(2.0, Length.LengthUnit.YARDS);

        assertFalse(yard1.equals(yard2));
    }
    @Test
    public void testEquality_YardToFeet_EquivalentValue() {

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length feet =
                new Length(3.0, Length.LengthUnit.FEET);

        assertTrue(yard.equals(feet));
    }

    @Test
    public void testEquality_FeetToYard_EquivalentValue() {

        Length feet =
                new Length(3.0, Length.LengthUnit.FEET);

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        assertTrue(feet.equals(yard));
    }
    @Test
    public void testEquality_YardToInches_EquivalentValue() {

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length inches =
                new Length(36.0, Length.LengthUnit.INCHES);

        assertTrue(yard.equals(inches));
    }

    @Test
    public void testEquality_InchesToYard_EquivalentValue() {

        Length inches =
                new Length(36.0, Length.LengthUnit.INCHES);

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        assertTrue(inches.equals(yard));
    }
    @Test
    public void testEquality_YardToFeet_NonEquivalentValue() {

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length feet =
                new Length(2.0, Length.LengthUnit.FEET);

        assertFalse(yard.equals(feet));
    }

    @Test
    public void testEquality_CentimetersToInches_EquivalentValue() {

        Length cm =
                new Length(1.0, Length.LengthUnit.CENTIMETERS);

        Length inches =
                new Length(0.393701, Length.LengthUnit.INCHES);

        assertTrue(cm.equals(inches));
    }
    @Test
    public void testEquality_CentimetersToFeet_NonEquivalentValue() {

        Length cm =
                new Length(1.0, Length.LengthUnit.CENTIMETERS);

        Length feet =
                new Length(1.0, Length.LengthUnit.FEET);

        assertFalse(cm.equals(feet));
    }

    @Test
    public void testEquality_MultiUnit_TransitiveProperty() {

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length feet =
                new Length(3.0, Length.LengthUnit.FEET);

        Length inches =
                new Length(36.0, Length.LengthUnit.INCHES);

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
//                            new Length(1.0, Length.LengthUnit.YARDS)
//                    );
//                }
//        );
//    }

    @Test
    public void testEquality_YardSameReference() {

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        assertTrue(yard.equals(yard));
    }

    @Test
    public void testEquality_YardNullComparison() {

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

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
//                                    Length.LengthUnit.CENTIMETERS
//                            )
//                    );
//                }
//        );
//    }

    @Test
    public void testEquality_CentimetersSameReference() {

        Length cm =
                new Length(1.0, Length.LengthUnit.CENTIMETERS);

        assertTrue(cm.equals(cm));
    }

    @Test
    public void testEquality_CentimetersNullComparison() {

        Length cm =
                new Length(1.0, Length.LengthUnit.CENTIMETERS);

        assertFalse(cm.equals(null));
    }

    @Test
    public void testEquality_AllUnits_ComplexScenario() {

        Length yards =
                new Length(2.0, Length.LengthUnit.YARDS);

        Length feet =
                new Length(6.0, Length.LengthUnit.FEET);

        Length inches =
                new Length(72.0, Length.LengthUnit.INCHES);

        assertTrue(yards.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yards.equals(inches));
    }
    @Test
    public void testConversion_FeetToInches() {

        Length feet =
                new Length(1.0, Length.LengthUnit.FEET);

        Length inches =
                feet.convertTo(Length.LengthUnit.INCHES);

        assertEquals(
                12.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_InchesToFeet() {

        Length inches =
                new Length(24.0, Length.LengthUnit.INCHES);

        Length feet =
                inches.convertTo(Length.LengthUnit.FEET);

        assertEquals(
                2.0,
                feet.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_YardsToInches() {

        Length yards =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length inches =
                yards.convertTo(Length.LengthUnit.INCHES);

        assertEquals(
                36.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_InchesToYards() {

        Length inches =
                new Length(72.0, Length.LengthUnit.INCHES);

        Length yards =
                inches.convertTo(Length.LengthUnit.YARDS);

        assertEquals(
                2.0,
                yards.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_CentimetersToInches() {

        Length cm =
                new Length(2.54, Length.LengthUnit.CENTIMETERS);

        Length inches =
                cm.convertTo(Length.LengthUnit.INCHES);

        assertEquals(
                1.0,
                inches.getValue(),
                0.0001
        );
    }

    @Test
    public void testConversion_FeetToYard() {

        Length feet =
                new Length(6.0, Length.LengthUnit.FEET);

        Length yards =
                feet.convertTo(Length.LengthUnit.YARDS);

        assertEquals(
                2.0,
                yards.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {

        Length original =
                new Length(5.0, Length.LengthUnit.FEET);

        Length inches =
                original.convertTo(Length.LengthUnit.INCHES);

        Length convertedBack =
                inches.convertTo(Length.LengthUnit.FEET);

        assertEquals(
                original.getValue(),
                convertedBack.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_ZeroValue() {

        Length feet =
                new Length(0.0, Length.LengthUnit.FEET);

        Length inches =
                feet.convertTo(Length.LengthUnit.INCHES);

        assertEquals(
                0.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_NegativeValue() {

        Length feet =
                new Length(-1.0, Length.LengthUnit.FEET);

        Length inches =
                feet.convertTo(Length.LengthUnit.INCHES);

        assertEquals(
                -12.0,
                inches.getValue(),
                0.000001
        );
    }

    @Test
    public void testConversion_InvalidUnit_Throws() {

        Length feet =
                new Length(1.0, Length.LengthUnit.FEET);

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
                        Length.LengthUnit.FEET
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(
                        Double.POSITIVE_INFINITY,
                        Length.LengthUnit.FEET
                )
        );
    }

    @Test
    public void testConversion_PrecisionTolerance() {

        Length cm =
                new Length(2.54, Length.LengthUnit.CENTIMETERS);

        Length inches =
                cm.convertTo(Length.LengthUnit.INCHES);

        assertEquals(
                1.0,
                inches.getValue(),
                0.0001
        );
    }
    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {

        Length l1 =
                new Length(1.0, Length.LengthUnit.FEET);

        Length l2 =
                new Length(2.0, Length.LengthUnit.FEET);

        Length result = l1.add(l2);

        assertEquals(
                3.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                Length.LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {

        Length l1 =
                new Length(6.0, Length.LengthUnit.INCHES);

        Length l2 =
                new Length(6.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2);

        assertEquals(
                12.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                Length.LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {

        Length feet =
                new Length(1.0, Length.LengthUnit.FEET);

        Length inches =
                new Length(12.0, Length.LengthUnit.INCHES);

        Length result = feet.add(inches);

        assertEquals(
                2.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                Length.LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {

        Length inches =
                new Length(12.0, Length.LengthUnit.INCHES);

        Length feet =
                new Length(1.0, Length.LengthUnit.FEET);

        Length result = inches.add(feet);

        assertEquals(
                24.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                Length.LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {

        Length yard =
                new Length(1.0, Length.LengthUnit.YARDS);

        Length feet =
                new Length(3.0, Length.LengthUnit.FEET);

        Length result = yard.add(feet);

        assertEquals(
                2.0,
                result.getValue(),
                0.000001
        );

        assertEquals(
                Length.LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {

        Length cm =
                new Length(2.54, Length.LengthUnit.CENTIMETERS);

        Length inch =
                new Length(1.0, Length.LengthUnit.INCHES);

        Length result = cm.add(inch);

        assertEquals(
                5.08,
                result.getValue(),
                0.01
        );

        assertEquals(
                Length.LengthUnit.CENTIMETERS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_Commutativity() {

        Length feet =
                new Length(1.0, Length.LengthUnit.FEET);

        Length inches =
                new Length(12.0, Length.LengthUnit.INCHES);

        Length result1 = feet.add(inches);

        Length result2 = inches.add(feet);

        assertTrue(result1.equals(result2));
    }

    @Test
    public void testAddition_WithZero() {

        Length feet =
                new Length(5.0, Length.LengthUnit.FEET);

        Length inches =
                new Length(0.0, Length.LengthUnit.INCHES);

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
                new Length(5.0, Length.LengthUnit.FEET);

        Length l2 =
                new Length(-2.0, Length.LengthUnit.FEET);

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
                new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> feet.add(null)
        );
    }

    @Test
    public void testAddition_LargeValues() {

        Length l1 =
                new Length(1e6, Length.LengthUnit.FEET);

        Length l2 =
                new Length(1e6, Length.LengthUnit.FEET);

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
                new Length(0.001, Length.LengthUnit.FEET);

        Length l2 =
                new Length(0.002, Length.LengthUnit.FEET);

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
                new Length(1.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.FEET
                        );

        assertEquals(2.0, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {

        Length result =
                new Length(1.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.INCHES
                        );

        assertEquals(24.0, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {

        Length result =
                new Length(1.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.YARDS
                        );

        assertEquals(0.667, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Centimeters() {

        Length result =
                new Length(1.0, Length.LengthUnit.INCHES)
                        .add(
                                new Length(
                                        1.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.CENTIMETERS
                        );

        assertEquals(5.08, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.CENTIMETERS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {

        Length result =
                new Length(2.0, Length.LengthUnit.YARDS)
                        .add(
                                new Length(
                                        3.0,
                                        Length.LengthUnit.FEET
                                ),
                                Length.LengthUnit.YARDS
                        );

        assertEquals(3.0, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {

        Length result =
                new Length(2.0, Length.LengthUnit.YARDS)
                        .add(
                                new Length(
                                        3.0,
                                        Length.LengthUnit.FEET
                                ),
                                Length.LengthUnit.FEET
                        );

        assertEquals(9.0, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.FEET,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Commutativity() {

        Length result1 =
                new Length(1.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        12.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.YARDS
                        );

        Length result2 =
                new Length(12.0, Length.LengthUnit.INCHES)
                        .add(
                                new Length(
                                        1.0,
                                        Length.LengthUnit.FEET
                                ),
                                Length.LengthUnit.YARDS
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
                new Length(5.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        0.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.YARDS
                        );

        assertEquals(1.667, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {

        Length result =
                new Length(5.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        -2.0,
                                        Length.LengthUnit.FEET
                                ),
                                Length.LengthUnit.INCHES
                        );

        assertEquals(36.0, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(
                        1.0,
                        Length.LengthUnit.FEET
                ).add(
                        new Length(
                                12.0,
                                Length.LengthUnit.INCHES
                        ),
                        null
                )
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {

        Length result =
                new Length(1000.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        500.0,
                                        Length.LengthUnit.FEET
                                ),
                                Length.LengthUnit.INCHES
                        );

        assertEquals(18000.0, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.INCHES,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {

        Length result =
                new Length(12.0, Length.LengthUnit.INCHES)
                        .add(
                                new Length(
                                        12.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.YARDS
                        );

        assertEquals(0.667, result.getValue(), EPSILON);

        assertEquals(
                Length.LengthUnit.YARDS,
                result.getUnit()
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_AllUnitCombinations() {

        Length.LengthUnit[] units =
                Length.LengthUnit.values();

        for (Length.LengthUnit unit1 : units) {

            for (Length.LengthUnit unit2 : units) {

                for (Length.LengthUnit targetUnit : units) {

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
                new Length(1.0, Length.LengthUnit.FEET)
                        .add(
                                new Length(
                                        1.0,
                                        Length.LengthUnit.INCHES
                                ),
                                Length.LengthUnit.CENTIMETERS
                        );

        assertEquals(33.02, result.getValue(), EPSILON);
    }
}