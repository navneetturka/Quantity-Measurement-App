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
    @Test
    public void testEquality_YardWithNullUnit() {

        assertThrows(
                NullPointerException.class,
                () -> {
                    Length yard =
                            new Length(1.0, null);

                    yard.equals(
                            new Length(1.0, Length.LengthUnit.YARDS)
                    );
                }
        );
    }

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
    @Test
    public void testEquality_CentimetersWithNullUnit() {

        assertThrows(
                NullPointerException.class,
                () -> {
                    Length cm =
                            new Length(1.0, null);

                    cm.equals(
                            new Length(
                                    1.0,
                                    Length.LengthUnit.CENTIMETERS
                            )
                    );
                }
        );
    }

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

}