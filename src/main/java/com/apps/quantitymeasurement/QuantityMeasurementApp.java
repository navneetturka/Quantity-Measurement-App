//package com.apps.quantitymeasurement;
//
//public class QuantityMeasurementApp {
//
//    public static class Feet {
//        private final double value;
//
//        public Feet(double value) {
//            this.value = value;
//        }
//
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) return true;
//            if (obj == null) return false;
//            if (this.getClass() != obj.getClass()) return false;
//
//            Feet other = (Feet) obj;
//
//            return Double.compare(this.value, other.value) == 0;
//        }
//    }
//    public static class Inches {
//        private final double value;
//
//        public Inches(double value) {
//            this.value = value;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//
//            if (this == obj) return true;
//
//            if (obj == null) return false;
//
//            if (this.getClass() != obj.getClass()) return false;
//
//            Inches other = (Inches) obj;
//
//            return Double.compare(this.value, other.value) == 0;
//        }
//    }
//    public static void demonstrateFeetEquality() {
//        Feet f1 = new Feet(1.0);
//        Feet f2 = new Feet(1.0);
//
//        System.out.println("Feet Equal: " + f1.equals(f2));
//    }
//
//    public static void demonstrateInchesEquality() {
//        Inches i1 = new Inches(1.0);
//        Inches i2 = new Inches(1.0);
//
//        System.out.println("Inches Equal: " + i1.equals(i2));
//    }
//
//    public static void main(String[] args) {
//        demonstrateFeetEquality();
//        demonstrateInchesEquality();
//    }
//}

package com.apps.quantitymeasurement;
public class QuantityMeasurementApp {

    public static void demonstrateLengthEquality(double value1,
                                                 Length.LengthUnit unit1,
                                                 double value2,
                                                 Length.LengthUnit unit2
    ) {

        Length length1 = new Length(value1, unit1);
        Length length2 = new Length(value2, unit2);

        System.out.println(  value1 + " " + unit1 +
                " and " +
                value2 + " " + unit2 +
                " are equal: " +
                length1.equals(length2)
        );
    }
    public static boolean demonstrateLengthComparison(
            double value1,
            Length.LengthUnit unit1,
            double value2,
            Length.LengthUnit unit2
    ) {

        Length length1 =
                new Length(value1, unit1);

        Length length2 =
                new Length(value2, unit2);

        return length1.equals(length2);
    }
    public static Length demonstrateLengthConversion(
            double value,
            Length.LengthUnit fromUnit,
            Length.LengthUnit toUnit
    ) {

        Length length =
                new Length(value, fromUnit);

        return length.convertTo(toUnit);
    }
    public static Length demonstrateLengthConversion(
            Length length,
            Length.LengthUnit toUnit
    ) {

        return length.convertTo(toUnit);
    }
    public static Length demonstrateLengthAddition(
            Length length1,
            Length length2
    ) {

        return length1.add(length2);
    }
    public static Length demonstrateLengthAddition(
            Length length1,
            Length length2,
            Length.LengthUnit targetUnit
    ) {

        return length1.add(length2, targetUnit);
    }


    public static void main(String[] args) {

        demonstrateLengthEquality(
                1.0,
                Length.LengthUnit.FEET,
                12.0,
                Length.LengthUnit.INCHES
        );


        demonstrateLengthEquality(
                1.0,
                Length.LengthUnit.YARDS,
                3.0,
                Length.LengthUnit.FEET
        );
        demonstrateLengthEquality(
                1.0,
                Length.LengthUnit.YARDS,
                36.0,
                Length.LengthUnit.INCHES
        );


        demonstrateLengthEquality(
                1.0,
                Length.LengthUnit.CENTIMETERS,
                0.393701,
                Length.LengthUnit.INCHES
        );


        demonstrateLengthEquality(
                30.48,
                Length.LengthUnit.CENTIMETERS,
                1.0,
                Length.LengthUnit.FEET
        );
        boolean comparisonResult =
                demonstrateLengthComparison(
                        1.0,
                        Length.LengthUnit.YARDS,
                        3.0,
                        Length.LengthUnit.FEET
                );

        System.out.println(
                "Comparison Result: " +
                        comparisonResult
        );

        Length feetToInches =
                demonstrateLengthConversion(
                        1.0,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES
                );

        System.out.println(
                "1 FEET in INCHES = " +
                        feetToInches
        );
        Length yardsToFeet =
                demonstrateLengthConversion(
                        3.0,
                        Length.LengthUnit.YARDS,
                        Length.LengthUnit.FEET
                );

        System.out.println(
                "3 YARDS in FEET = " +
                        yardsToFeet
        );
        Length inchesToYards =
                demonstrateLengthConversion(
                        72.0,
                        Length.LengthUnit.INCHES,
                        Length.LengthUnit.YARDS
                );

        System.out.println(
                "72 INCHES in YARDS = " +
                        inchesToYards
        );

        Length converted =
                demonstrateLengthConversion(
                        1.0,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES
                );

        System.out.println(
                "Converted Length: " + converted
        );
        Length yard =
                new Length(
                        2.0,
                        Length.LengthUnit.YARDS
                );

        Length convertedYard =
                demonstrateLengthConversion(
                        yard,
                        Length.LengthUnit.INCHES
                );

        System.out.println(
                "2 YARDS in INCHES = " +
                        convertedYard
        );
        Length result1 =
                demonstrateLengthAddition(
                        new Length(
                                1.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                12.0,
                                Length.LengthUnit.INCHES
                        )
                );

        System.out.println(
                "1 FEET + 12 INCHES = " +
                        result1
        );

        Length result2 =
                demonstrateLengthAddition(
                        new Length(
                                1.0,
                                Length.LengthUnit.YARDS
                        ),
                        new Length(
                                3.0,
                                Length.LengthUnit.FEET
                        )
                );

        System.out.println(
                "1 YARD + 3 FEET = " +
                        result2
        );

        Length result3 =
                demonstrateLengthAddition(
                        new Length(
                                36.0,
                                Length.LengthUnit.INCHES
                        ),
                        new Length(
                                1.0,
                                Length.LengthUnit.YARDS
                        )
                );

        System.out.println(
                "36 INCHES + 1 YARD = " +
                        result3
        );

        Length result4 =
                demonstrateLengthAddition(
                        new Length(
                                2.54,
                                Length.LengthUnit.CENTIMETERS
                        ),
                        new Length(
                                1.0,
                                Length.LengthUnit.INCHES
                        )
                );

        System.out.println(
                "2.54 CM + 1 INCH = " +
                        result4
        );

        Length result5 =
                demonstrateLengthAddition(
                        new Length(
                                5.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                0.0,
                                Length.LengthUnit.INCHES
                        )
                );

        System.out.println(
                "5 FEET + 0 INCHES = " +
                        result5
        );

        Length result6 =
                demonstrateLengthAddition(
                        new Length(
                                5.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                -2.0,
                                Length.LengthUnit.FEET
                        )
                );

        System.out.println(
                "5 FEET + (-2 FEET) = " +
                        result6
        );

        Length targetResult1 =
                demonstrateLengthAddition(
                        new Length(
                                1.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                12.0,
                                Length.LengthUnit.INCHES
                        ),
                        Length.LengthUnit.FEET
                );

        System.out.println(
                "1 FEET + 12 INCHES in FEET = " +
                        targetResult1
        );

        Length targetResult2 =
                demonstrateLengthAddition(
                        new Length(
                                1.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                12.0,
                                Length.LengthUnit.INCHES
                        ),
                        Length.LengthUnit.INCHES
                );

        System.out.println(
                "1 FEET + 12 INCHES in INCHES = " +
                        targetResult2
        );

        Length targetResult3 =
                demonstrateLengthAddition(
                        new Length(
                                1.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                12.0,
                                Length.LengthUnit.INCHES
                        ),
                        Length.LengthUnit.YARDS
                );

        System.out.println(
                "1 FEET + 12 INCHES in YARDS = " +
                        targetResult3
        );

        Length targetResult4 =
                demonstrateLengthAddition(
                        new Length(
                                1.0,
                                Length.LengthUnit.YARDS
                        ),
                        new Length(
                                3.0,
                                Length.LengthUnit.FEET
                        ),
                        Length.LengthUnit.YARDS
                );

        System.out.println(
                "1 YARD + 3 FEET in YARDS = " +
                        targetResult4
        );

        Length targetResult5 =
                demonstrateLengthAddition(
                        new Length(
                                36.0,
                                Length.LengthUnit.INCHES
                        ),
                        new Length(
                                1.0,
                                Length.LengthUnit.YARDS
                        ),
                        Length.LengthUnit.FEET
                );

        System.out.println(
                "36 INCHES + 1 YARD in FEET = " +
                        targetResult5
        );

        Length targetResult6 =
                demonstrateLengthAddition(
                        new Length(
                                2.54,
                                Length.LengthUnit.CENTIMETERS
                        ),
                        new Length(
                                1.0,
                                Length.LengthUnit.INCHES
                        ),
                        Length.LengthUnit.CENTIMETERS
                );

        System.out.println(
                "2.54 CM + 1 INCH in CM = " +
                        targetResult6
        );

        Length targetResult7 =
                demonstrateLengthAddition(
                        new Length(
                                5.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                0.0,
                                Length.LengthUnit.INCHES
                        ),
                        Length.LengthUnit.YARDS
                );

        System.out.println(
                "5 FEET + 0 INCHES in YARDS = " +
                        targetResult7
        );

        Length targetResult8 =
                demonstrateLengthAddition(
                        new Length(
                                5.0,
                                Length.LengthUnit.FEET
                        ),
                        new Length(
                                -2.0,
                                Length.LengthUnit.FEET
                        ),
                        Length.LengthUnit.INCHES
                );

        System.out.println(
                "5 FEET + (-2 FEET) in INCHES = " +
                        targetResult8
        );
    }
}