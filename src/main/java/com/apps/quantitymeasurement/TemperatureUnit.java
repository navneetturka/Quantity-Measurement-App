package com.apps.quantitymeasurement;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    private final SupportsArithmetic supportsArithmetic =
            () -> false;

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(
            double value
    ) {

        switch (this) {

            case CELSIUS:
                return value;

            case FAHRENHEIT:
                return (value - 32) * 5 / 9;

            case KELVIN:
                return value - 273.15;

            default:
                throw new IllegalArgumentException(
                        "Unsupported temperature unit"
                );
        }
    }

    @Override
    public double convertFromBaseUnit(
            double baseValue
    ) {

        switch (this) {

            case CELSIUS:
                return baseValue;

            case FAHRENHEIT:
                return (baseValue * 9 / 5) + 32;

            case KELVIN:
                return baseValue + 273.15;

            default:
                throw new IllegalArgumentException(
                        "Unsupported temperature unit"
                );
        }
    }

    public double convertTo(
            double value,
            TemperatureUnit targetUnit
    ) {

        double celsiusValue =
                convertToBaseUnit(value);

        return targetUnit.convertFromBaseUnit(
                celsiusValue
        );
    }

    @Override
    public boolean supportsArithmetic() {

        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(
            String operation
    ) {

        if (!supportsArithmetic()) {

            throw new UnsupportedOperationException(
                    "Temperature does not support "
                            + operation
                            + " operation."
            );
        }
    }

    @Override
    public String getUnitName() {

        return name();
    }
}