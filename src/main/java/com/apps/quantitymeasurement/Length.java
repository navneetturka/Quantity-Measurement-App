package com.apps.quantitymeasurement;

public class Length {

    private final double value;
    private final LengthUnit unit;

    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);


        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    public Length(double value, LengthUnit unit) {

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {
        return this.value * this.unit.getConversionFactor();
    }
    public boolean compare(Length other) {
        return Double.compare(
                this.convertToBaseUnit(),
                other.convertToBaseUnit()
        ) == 0;
    }
    public Length convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue =
                this.convertToBaseUnit();

        double convertedValue =
                baseValue / targetUnit.getConversionFactor();

        return new Length(convertedValue, targetUnit);
    }
    public Length add(Length thatLength) {

        if (thatLength == null) {
            throw new IllegalArgumentException(
                    "Length cannot be null"
            );
        }

        if (thatLength.unit == null) {
            throw new IllegalArgumentException(
                    "Unit cannot be null"
            );
        }

        if (!Double.isFinite(thatLength.value)) {
            throw new IllegalArgumentException(
                    "Invalid value"
            );
        }

        double thisBaseValue =
                this.convertToBaseUnit();

        double thatBaseValue =
                thatLength.convertToBaseUnit();


        double sumInBaseUnit =
                thisBaseValue + thatBaseValue;

        // Convert back to first operand unit
        double resultValue =
                sumInBaseUnit /
                        this.unit.getConversionFactor();

        // Return new object
        return new Length(
                resultValue,
                this.unit
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Length other = (Length) obj;

        return this.compare(other);
    }
    @Override
    public String toString() {

        return value + " " + unit;
    }
}