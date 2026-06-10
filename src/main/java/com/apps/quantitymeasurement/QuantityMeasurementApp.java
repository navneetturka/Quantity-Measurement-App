package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable> boolean
    demonstrateEquality(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        return quantity1.equals(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U>
    demonstrateConversion(
            Quantity<U> quantity,
            U targetUnit
    ) {

        return quantity.convertTo(targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U>
    demonstrateAddition(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        return quantity1.add(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U>
    demonstrateAddition(
            Quantity<U> quantity1,
            Quantity<U> quantity2,
            U targetUnit
    ) {

        return quantity1.add(quantity2, targetUnit);
    }

    public static void main(String[] args) {

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

        boolean areLengthsEqual =
                demonstrateEquality(
                        feet,
                        inches
                );

        System.out.println(
                "1 FEET and 12 INCHES are equal: "
                        + areLengthsEqual
        );

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

        boolean areWeightsEqual =
                demonstrateEquality(
                        kilogram,
                        gram
                );

        System.out.println(
                "1 KG and 1000 GRAM are equal: "
                        + areWeightsEqual
        );

        Quantity<LengthUnit> convertedLength =
                demonstrateConversion(
                        feet,
                        LengthUnit.INCHES
                );

        System.out.println(
                "1 FEET in INCHES = "
                        + convertedLength
        );

        Quantity<WeightUnit> convertedWeight =
                demonstrateConversion(
                        kilogram,
                        WeightUnit.GRAM
                );

        System.out.println(
                "1 KG in GRAM = "
                        + convertedWeight
        );

        Quantity<LengthUnit> totalLength =
                demonstrateAddition(
                        feet,
                        inches,
                        LengthUnit.FEET
                );

        System.out.println(
                "1 FEET + 12 INCHES in FEET = "
                        + totalLength
        );

        Quantity<WeightUnit> totalWeight =
                demonstrateAddition(
                        kilogram,
                        gram,
                        WeightUnit.KILOGRAM
                );

        System.out.println(
                "1 KG + 1000 GRAM in KG = "
                        + totalWeight
        );

        Quantity<WeightUnit> pounds =
                new Quantity<>(
                        2.20462,
                        WeightUnit.POUND
                );

        Quantity<WeightUnit> mixedWeight =
                demonstrateAddition(
                        kilogram,
                        pounds,
                        WeightUnit.KILOGRAM
                );

        System.out.println(
                "1 KG + 2.20462 POUND in KG = "
                        + mixedWeight
        );
    }
}