package com.apps.quantitymeasurement.entity;

import java.io.Serializable;
import java.util.Objects;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private final double thisValue;
    private final String thisUnit;
    private final String thisMeasurementType;

    private final double thatValue;
    private final String thatUnit;
    private final String thatMeasurementType;

    private final String operation;

    private final double resultValue;
    private final String resultUnit;
    private final String resultMeasurementType;
    private final String resultString;

    private final boolean error;
    private final String errorMessage;

    public QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String operation,
            String resultString
    ) {
        this(
                thisQuantity,
                thatQuantity,
                operation,
                0.0,
                null,
                null,
                resultString,
                false,
                null
        );
    }

    public QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            String operation,
            QuantityDTO result
    ) {
        this(
                thisQuantity,
                null,
                operation,
                result.getValue(),
                result.getUnit(),
                result.getMeasurementType(),
                result.toString(),
                false,
                null
        );
    }

    public QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String operation,
            QuantityDTO result
    ) {
        this(
                thisQuantity,
                thatQuantity,
                operation,
                result.getValue(),
                result.getUnit(),
                result.getMeasurementType(),
                result.toString(),
                false,
                null
        );
    }

    public QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String operation,
            double resultValue
    ) {
        this(
                thisQuantity,
                thatQuantity,
                operation,
                resultValue,
                null,
                null,
                String.valueOf(resultValue),
                false,
                null
        );
    }

    public QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String operation,
            String errorMessage,
            boolean error
    ) {
        this(
                thisQuantity,
                thatQuantity,
                operation,
                0.0,
                null,
                null,
                null,
                error,
                errorMessage
        );
    }

    private QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String operation,
            double resultValue,
            String resultUnit,
            String resultMeasurementType,
            String resultString,
            boolean error,
            String errorMessage
    ) {
        this.thisValue = valueOf(thisQuantity);
        this.thisUnit = unitOf(thisQuantity);
        this.thisMeasurementType = measurementTypeOf(thisQuantity);

        this.thatValue = valueOf(thatQuantity);
        this.thatUnit = unitOf(thatQuantity);
        this.thatMeasurementType = measurementTypeOf(thatQuantity);

        this.operation = operation;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;
        this.resultString = resultString;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    private static double valueOf(QuantityDTO quantity) {
        return quantity == null ? 0.0 : quantity.getValue();
    }

    private static String unitOf(QuantityDTO quantity) {
        return quantity == null ? null : quantity.getUnit();
    }

    private static String measurementTypeOf(QuantityDTO quantity) {
        return quantity == null ? null : quantity.getMeasurementType();
    }

    public double getThisValue() {
        return thisValue;
    }

    public String getThisUnit() {
        return thisUnit;
    }

    public String getThisMeasurementType() {
        return thisMeasurementType;
    }

    public double getThatValue() {
        return thatValue;
    }

    public String getThatUnit() {
        return thatUnit;
    }

    public String getThatMeasurementType() {
        return thatMeasurementType;
    }

    public String getOperation() {
        return operation;
    }

    public double getResultValue() {
        return resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public String getResultMeasurementType() {
        return resultMeasurementType;
    }

    public String getResultString() {
        return resultString;
    }

    public boolean hasError() {
        return error;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityMeasurementEntity)) return false;

        QuantityMeasurementEntity other = (QuantityMeasurementEntity) obj;

        return Double.compare(thisValue, other.thisValue) == 0
                && Double.compare(thatValue, other.thatValue) == 0
                && Double.compare(resultValue, other.resultValue) == 0
                && error == other.error
                && Objects.equals(thisUnit, other.thisUnit)
                && Objects.equals(thisMeasurementType, other.thisMeasurementType)
                && Objects.equals(thatUnit, other.thatUnit)
                && Objects.equals(thatMeasurementType, other.thatMeasurementType)
                && Objects.equals(operation, other.operation)
                && Objects.equals(resultUnit, other.resultUnit)
                && Objects.equals(resultMeasurementType, other.resultMeasurementType)
                && Objects.equals(resultString, other.resultString)
                && Objects.equals(errorMessage, other.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                thisValue,
                thisUnit,
                thisMeasurementType,
                thatValue,
                thatUnit,
                thatMeasurementType,
                operation,
                resultValue,
                resultUnit,
                resultMeasurementType,
                resultString,
                error,
                errorMessage
        );
    }

    @Override
    public String toString() {
        if (error) {
            return operation + " failed: " + errorMessage;
        }

        return operation + " result: " + resultString;
    }
}