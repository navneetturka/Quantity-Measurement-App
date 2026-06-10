package com.apps.quantitymeasurement;
import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 0.0001;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (unit == null) {
            throw new IllegalArgumentException(
                    "Unit cannot be null"
            );
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    "Invalid value"
            );
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {

        return unit.convertToBaseUnit(value);
    }

    public boolean compare(Quantity<U> other) {

        return Math.abs(
                this.convertToBaseUnit() -
                        other.convertToBaseUnit()
        ) < EPSILON;
    }

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }

        double baseValue =
                this.convertToBaseUnit();

        double convertedValue =
                targetUnit.convertFromBaseUnit(
                        baseValue
                );

        return new Quantity<>(
                convertedValue,
                targetUnit
        );
    }

    public Quantity<U> add(Quantity<U> other) {

        return add(
                other,
                this.unit
        );
    }

    public Quantity<U> add(
            Quantity<U> other,
            U targetUnit
    ) {

        if (other == null) {
            throw new IllegalArgumentException(
                    "Quantity cannot be null"
            );
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }

        double thisBaseValue =
                this.convertToBaseUnit();

        double otherBaseValue =
                other.convertToBaseUnit();

        double sumInBaseUnit =
                thisBaseValue + otherBaseValue;

        double resultValue =
                targetUnit.convertFromBaseUnit(
                        sumInBaseUnit
                );

        return new Quantity<>(
                resultValue,
                targetUnit
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null) return false;

        if (!(obj instanceof Quantity<?>))
            return false;

        Quantity<?> other =
                (Quantity<?>) obj;

        if (this.unit.getClass() !=
                other.unit.getClass()) {
            return false;
        }

        return Math.abs(
                this.convertToBaseUnit() -
                        other.unit.convertToBaseUnit(
                                other.value
                        )
        ) < EPSILON;
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                convertToBaseUnit(),
                unit.getClass()
        );
    }

    @Override
    public String toString() {

        return value + " " + unit;
    }
}