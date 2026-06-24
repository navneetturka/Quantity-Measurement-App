package com.apps.quantitymeasurement.entity;

public class QuantityDTO {

    public interface IMeasurableUnit {
        String getUnitName();

        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS, METER, METERS;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }

    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAM, GRAM, POUND, OUNCE, TONNE;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, LITER, MILLILITRE, MILLILITER, GALLON;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }

    private final double value;
    private final String unit;
    private final String measurementType;

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this(value, unit.getUnitName(), unit.getMeasurementType());
    }

    public QuantityDTO(double value, String unit, String measurementType) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit cannot be null or empty");
        }

        if (measurementType == null || measurementType.trim().isEmpty()) {
            throw new IllegalArgumentException("Measurement type cannot be null or empty");
        }

        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    @Override
    public String toString() {
        return value + " " + unit + " (" + measurementType + ")";
    }
}