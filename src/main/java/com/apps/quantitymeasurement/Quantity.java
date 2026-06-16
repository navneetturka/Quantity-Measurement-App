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


    private enum ArithmeticOperation {


        ADD {

            @Override
            public double compute(
                    double thisBase,
                    double otherBase
            ) {

                return thisBase + otherBase;
            }
        },


        SUBTRACT {

            @Override
            public double compute(
                    double thisBase,
                    double otherBase
            ) {

                return thisBase - otherBase;
            }
        },


        DIVIDE {

            @Override
            public double compute(
                    double thisBase,
                    double otherBase
            ) {

                if (Math.abs(otherBase) < EPSILON) {

                    throw new ArithmeticException(
                            "Cannot divide by zero"
                    );
                }

                return thisBase / otherBase;
            }
        };


        public abstract double compute(
                double thisBase,
                double otherBase
        );
    }


    private void validateArithmeticOperands(
            Quantity<U> other,
            U targetUnit,
            boolean targetUnitRequired
    ) {


        if (other == null) {

            throw new IllegalArgumentException(
                    "Quantity cannot be null"
            );
        }


        if (targetUnitRequired &&
                targetUnit == null) {

            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }


        if (this.unit.getClass()
                != other.unit.getClass()) {

            throw new IllegalArgumentException(
                    "Incompatible unit types"
            );
        }


        if (!Double.isFinite(this.value)
                ||
                !Double.isFinite(other.value)) {

            throw new IllegalArgumentException(
                    "Invalid value"
            );
        }
    }


    private double performBaseArithmetic(
            Quantity<U> other,
            ArithmeticOperation operation
    ) {

        unit.validateOperationSupport(
                operation.name()
        );

        other.unit.validateOperationSupport(
                operation.name()
        );

        double thisBaseValue =
                this.convertToBaseUnit();

        double otherBaseValue =
                other.convertToBaseUnit();

        return operation.compute(
                thisBaseValue,
                otherBaseValue
        );
    }

    public boolean compare(
            Quantity<U> other
    ) {


        return Math.abs(
                this.convertToBaseUnit()
                        -
                        other.convertToBaseUnit()
        ) < EPSILON;
    }



    public Quantity<U> convertTo(
            U targetUnit
    ) {

        if (targetUnit == null) {

            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }

        if (unit instanceof TemperatureUnit
                &&
                targetUnit instanceof TemperatureUnit) {

            TemperatureUnit source =
                    (TemperatureUnit) unit;

            TemperatureUnit target =
                    (TemperatureUnit) targetUnit;

            double convertedValue =
                    source.convertTo(
                            value,
                            target
                    );

            return new Quantity<>(
                    convertedValue,
                    targetUnit
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
    public Quantity<U> add(
            Quantity<U> other
    ) {

        return add(
                other,
                this.unit
        );
    }




    public Quantity<U> add(
            Quantity<U> other,
            U targetUnit
    ) {


        validateArithmeticOperands(
                other,
                targetUnit,
                true
        );


        double resultBaseValue =
                performBaseArithmetic(
                        other,
                        ArithmeticOperation.ADD
                );


        double resultValue =
                targetUnit.convertFromBaseUnit(
                        resultBaseValue
                );


        return new Quantity<>(
                resultValue,
                targetUnit
        );
    }





    public Quantity<U> subtract(
            Quantity<U> other
    ) {

        return subtract(
                other,
                this.unit
        );
    }





    public Quantity<U> subtract(
            Quantity<U> other,
            U targetUnit
    ) {


        validateArithmeticOperands(
                other,
                targetUnit,
                true
        );


        double resultBaseValue =
                performBaseArithmetic(
                        other,
                        ArithmeticOperation.SUBTRACT
                );


        double resultValue =
                targetUnit.convertFromBaseUnit(
                        resultBaseValue
                );


        return new Quantity<>(
                resultValue,
                targetUnit
        );
    }





    public double divide(
            Quantity<U> other
    ) {


        validateArithmeticOperands(
                other,
                null,
                false
        );


        return performBaseArithmetic(
                other,
                ArithmeticOperation.DIVIDE
        );
    }





    @Override
    public boolean equals(
            Object obj
    ) {


        if (this == obj)
            return true;


        if (obj == null)
            return false;


        if (!(obj instanceof Quantity<?>))
            return false;


        Quantity<?> other =
                (Quantity<?>) obj;



        if (this.unit.getClass()
                !=
                other.unit.getClass()) {

            return false;
        }



        return Math.abs(
                this.convertToBaseUnit()
                        -
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