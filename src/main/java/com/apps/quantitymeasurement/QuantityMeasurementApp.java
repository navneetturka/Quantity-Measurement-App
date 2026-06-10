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
    public static <U extends IMeasurable> Quantity<U>
    demonstrateSubtraction(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        return quantity1.subtract(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U>
    demonstrateSubtraction(
            Quantity<U> quantity1,
            Quantity<U> quantity2,
            U targetUnit
    ) {

        return quantity1.subtract(
                quantity2,
                targetUnit
        );
    }

    public static <U extends IMeasurable> double
    demonstrateDivision(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        return quantity1.divide(quantity2);
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
        Quantity<LengthUnit> lengthDifference =
                demonstrateSubtraction(
                        new Quantity<>(
                                10.0,
                                LengthUnit.FEET
                        ),
                        new Quantity<>(
                                6.0,
                                LengthUnit.INCHES
                        )
                );

        System.out.println(
                "10 FEET - 6 INCHES = "
                        + lengthDifference
        );

        double lengthRatio =
                demonstrateDivision(
                        new Quantity<>(
                                10.0,
                                LengthUnit.FEET
                        ),
                        new Quantity<>(
                                2.0,
                                LengthUnit.FEET
                        )
                );

        System.out.println(
                "10 FEET / 2 FEET = "
                        + lengthRatio
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
        Quantity<WeightUnit> weightDifference =
                demonstrateSubtraction(
                        new Quantity<>(
                                10.0,
                                WeightUnit.KILOGRAM
                        ),
                        new Quantity<>(
                                5000.0,
                                WeightUnit.GRAM
                        )
                );

        System.out.println(
                "10 KG - 5000 GRAM = "
                        + weightDifference
        );

        double weightRatio =
                demonstrateDivision(
                        new Quantity<>(
                                10.0,
                                WeightUnit.KILOGRAM
                        ),
                        new Quantity<>(
                                5.0,
                                WeightUnit.KILOGRAM
                        )
                );

        System.out.println(
                "10 KG / 5 KG = "
                        + weightRatio
        );

        Quantity<VolumeUnit> litre =
                new Quantity<>(
                        1.0,
                        VolumeUnit.LITRE
                );

        Quantity<VolumeUnit> millilitre =
                new Quantity<>(
                        1000.0,
                        VolumeUnit.MILLILITRE
                );

        boolean areVolumesEqual =
                demonstrateEquality(
                        litre,
                        millilitre
                );

        System.out.println(
                "1 LITRE and 1000 MILLILITRE are equal: "
                        + areVolumesEqual
        );

        Quantity<VolumeUnit> convertedVolume =
                demonstrateConversion(
                        litre,
                        VolumeUnit.MILLILITRE
                );

        System.out.println(
                "1 LITRE in MILLILITRE = "
                        + convertedVolume
        );

        Quantity<VolumeUnit> totalVolume =
                demonstrateAddition(
                        litre,
                        millilitre,
                        VolumeUnit.LITRE
                );

        System.out.println(
                "1 LITRE + 1000 MILLILITRE in LITRE = "
                        + totalVolume
        );

        Quantity<VolumeUnit> gallon =
                new Quantity<>(
                        1.0,
                        VolumeUnit.GALLON
                );

        Quantity<VolumeUnit> gallonToLitre =
                demonstrateConversion(
                        gallon,
                        VolumeUnit.LITRE
                );

        System.out.println(
                "1 GALLON in LITRE = "
                        + gallonToLitre
        );
        Quantity<VolumeUnit> volumeDifference =
                demonstrateSubtraction(
                        new Quantity<>(
                                5.0,
                                VolumeUnit.LITRE
                        ),
                        new Quantity<>(
                                500.0,
                                VolumeUnit.MILLILITRE
                        )
                );

        System.out.println(
                "5 LITRE - 500 MILLILITRE = "
                        + volumeDifference
        );

        double volumeRatio =
                demonstrateDivision(
                        new Quantity<>(
                                5.0,
                                VolumeUnit.LITRE
                        ),
                        new Quantity<>(
                                10.0,
                                VolumeUnit.LITRE
                        )
                );

        System.out.println(
                "5 LITRE / 10 LITRE = "
                        + volumeRatio
        );

    }
}