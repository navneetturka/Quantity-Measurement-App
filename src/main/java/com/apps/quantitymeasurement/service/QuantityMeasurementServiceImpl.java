package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.entity.QuantityModel;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.unit.IMeasurable;
import com.apps.quantitymeasurement.unit.LengthUnit;
import com.apps.quantitymeasurement.unit.TemperatureUnit;
import com.apps.quantitymeasurement.unit.VolumeUnit;
import com.apps.quantitymeasurement.unit.WeightUnit;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private static final double EPSILON = 0.0001;

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(
            IQuantityMeasurementRepository repository
    ) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }

        this.repository = repository;
    }

    @Override
    public boolean compare(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO
    ) {
        try {
            QuantityModel<IMeasurable> thisQuantity =
                    getQuantityModel(thisQuantityDTO);

            QuantityModel<IMeasurable> thatQuantity =
                    getQuantityModel(thatQuantityDTO);

            if (!isSameMeasurementType(
                    thisQuantity.getUnit(),
                    thatQuantity.getUnit()
            )) {
                repository.save(
                        new QuantityMeasurementEntity(
                                thisQuantityDTO,
                                thatQuantityDTO,
                                "COMPARE",
                                "Not Equal"
                        )
                );

                return false;
            }

            boolean result =
                    Math.abs(
                            thisQuantity.getUnit()
                                    .convertToBaseUnit(thisQuantity.getValue())
                                    -
                                    thatQuantity.getUnit()
                                            .convertToBaseUnit(thatQuantity.getValue())
                    ) < EPSILON;

            repository.save(
                    new QuantityMeasurementEntity(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            "COMPARE",
                            result ? "Equal" : "Not Equal"
                    )
            );

            return result;
        } catch (RuntimeException exception) {
            throw saveAndCreateException(
                    thisQuantityDTO,
                    thatQuantityDTO,
                    "COMPARE",
                    exception
            );
        }
    }

    @Override
    public QuantityDTO convert(
            QuantityDTO thisQuantityDTO,
            QuantityDTO targetUnitDTO
    ) {
        try {
            QuantityModel<IMeasurable> thisQuantity =
                    getQuantityModel(thisQuantityDTO);

            QuantityModel<IMeasurable> targetQuantity =
                    getQuantityModel(targetUnitDTO);

            validateSameMeasurementType(
                    thisQuantity.getUnit(),
                    targetQuantity.getUnit()
            );

            double baseValue =
                    thisQuantity.getUnit()
                            .convertToBaseUnit(thisQuantity.getValue());

            double convertedValue =
                    targetQuantity.getUnit()
                            .convertFromBaseUnit(baseValue);

            QuantityDTO result =
                    toDTO(convertedValue, targetQuantity.getUnit());

            repository.save(
                    new QuantityMeasurementEntity(
                            thisQuantityDTO,
                            "CONVERT",
                            result
                    )
            );

            return result;
        } catch (RuntimeException exception) {
            throw saveAndCreateException(
                    thisQuantityDTO,
                    targetUnitDTO,
                    "CONVERT",
                    exception
            );
        }
    }

    @Override
    public QuantityDTO add(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO
    ) {
        return add(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO);
    }

    @Override
    public QuantityDTO add(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO
    ) {
        try {
            QuantityDTO result =
                    performArithmetic(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            targetUnitDTO,
                            ArithmeticOperation.ADD
                    );

            repository.save(
                    new QuantityMeasurementEntity(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            "ADD",
                            result
                    )
            );

            return result;
        } catch (RuntimeException exception) {
            throw saveAndCreateException(
                    thisQuantityDTO,
                    thatQuantityDTO,
                    "ADD",
                    exception
            );
        }
    }

    @Override
    public QuantityDTO subtract(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO
    ) {
        return subtract(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO);
    }

    @Override
    public QuantityDTO subtract(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO
    ) {
        try {
            QuantityDTO result =
                    performArithmetic(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            targetUnitDTO,
                            ArithmeticOperation.SUBTRACT
                    );

            repository.save(
                    new QuantityMeasurementEntity(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            "SUBTRACT",
                            result
                    )
            );

            return result;
        } catch (RuntimeException exception) {
            throw saveAndCreateException(
                    thisQuantityDTO,
                    thatQuantityDTO,
                    "SUBTRACT",
                    exception
            );
        }
    }

    @Override
    public double divide(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO
    ) {
        try {
            QuantityModel<IMeasurable> thisQuantity =
                    getQuantityModel(thisQuantityDTO);

            QuantityModel<IMeasurable> thatQuantity =
                    getQuantityModel(thatQuantityDTO);

            validateArithmeticOperands(
                    thisQuantity,
                    thatQuantity,
                    null,
                    false,
                    "DIVIDE"
            );

            double thisBaseValue =
                    thisQuantity.getUnit()
                            .convertToBaseUnit(thisQuantity.getValue());

            double thatBaseValue =
                    thatQuantity.getUnit()
                            .convertToBaseUnit(thatQuantity.getValue());

            if (Math.abs(thatBaseValue) < EPSILON) {
                throw new ArithmeticException("Cannot divide by zero");
            }

            double result = thisBaseValue / thatBaseValue;

            repository.save(
                    new QuantityMeasurementEntity(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            "DIVIDE",
                            result
                    )
            );

            return result;
        } catch (RuntimeException exception) {
            throw saveAndCreateException(
                    thisQuantityDTO,
                    thatQuantityDTO,
                    "DIVIDE",
                    exception
            );
        }
    }

    private QuantityDTO performArithmetic(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO,
            ArithmeticOperation operation
    ) {
        QuantityModel<IMeasurable> thisQuantity =
                getQuantityModel(thisQuantityDTO);

        QuantityModel<IMeasurable> thatQuantity =
                getQuantityModel(thatQuantityDTO);

        QuantityModel<IMeasurable> targetQuantity =
                getQuantityModel(targetUnitDTO);

        validateArithmeticOperands(
                thisQuantity,
                thatQuantity,
                targetQuantity,
                true,
                operation.name()
        );

        double thisBaseValue =
                thisQuantity.getUnit()
                        .convertToBaseUnit(thisQuantity.getValue());

        double thatBaseValue =
                thatQuantity.getUnit()
                        .convertToBaseUnit(thatQuantity.getValue());

        double resultBaseValue =
                operation.compute(thisBaseValue, thatBaseValue);

        double resultValue =
                targetQuantity.getUnit()
                        .convertFromBaseUnit(resultBaseValue);

        return toDTO(resultValue, targetQuantity.getUnit());
    }

    private void validateArithmeticOperands(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            QuantityModel<IMeasurable> targetQuantity,
            boolean targetRequired,
            String operation
    ) {
        validateSameMeasurementType(
                thisQuantity.getUnit(),
                thatQuantity.getUnit()
        );

        if (targetRequired) {
            validateSameMeasurementType(
                    thisQuantity.getUnit(),
                    targetQuantity.getUnit()
            );
        }

        thisQuantity.getUnit().validateOperationSupport(operation);
        thatQuantity.getUnit().validateOperationSupport(operation);

        if (targetRequired) {
            targetQuantity.getUnit().validateOperationSupport(operation);
        }
    }

    private boolean isSameMeasurementType(
            IMeasurable thisUnit,
            IMeasurable thatUnit
    ) {
        return thisUnit.getClass() == thatUnit.getClass();
    }

    private void validateSameMeasurementType(
            IMeasurable thisUnit,
            IMeasurable thatUnit
    ) {
        if (!isSameMeasurementType(thisUnit, thatUnit)) {
            throw new IllegalArgumentException("Incompatible unit types");
        }
    }

    private QuantityModel<IMeasurable> getQuantityModel(
            QuantityDTO quantity
    ) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        IMeasurable unit = resolveUnit(
                quantity.getMeasurementType(),
                quantity.getUnit()
        );

        return new QuantityModel<>(
                quantity.getValue(),
                unit
        );
    }

    private IMeasurable resolveUnit(
            String measurementType,
            String unitName
    ) {
        String type =
                measurementType
                        .trim()
                        .toUpperCase();

        switch (type) {
            case "LENGTHUNIT":
            case "LENGTH":
                return LengthUnit.fromUnitName(unitName);

            case "WEIGHTUNIT":
            case "WEIGHT":
                return WeightUnit.fromUnitName(unitName);

            case "VOLUMEUNIT":
            case "VOLUME":
                return VolumeUnit.fromUnitName(unitName);

            case "TEMPERATUREUNIT":
            case "TEMPERATURE":
                return TemperatureUnit.fromUnitName(unitName);

            default:
                throw new IllegalArgumentException(
                        "Unsupported measurement type: " + measurementType
                );
        }
    }

    private QuantityDTO toDTO(
            double value,
            IMeasurable unit
    ) {
        return new QuantityDTO(
                value,
                unit.getUnitName(),
                unit.getMeasurementType()
        );
    }

    private QuantityMeasurementException saveAndCreateException(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            String operation,
            RuntimeException exception
    ) {
        QuantityMeasurementException measurementException;

        if (exception instanceof QuantityMeasurementException) {
            measurementException =
                    (QuantityMeasurementException) exception;
        } else {
            measurementException =
                    new QuantityMeasurementException(
                            exception.getMessage(),
                            exception
                    );
        }

        repository.save(
                new QuantityMeasurementEntity(
                        thisQuantityDTO,
                        thatQuantityDTO,
                        operation,
                        measurementException.getMessage(),
                        true
                )
        );

        return measurementException;
    }

    private enum ArithmeticOperation {

        ADD {
            @Override
            double compute(double first, double second) {
                return first + second;
            }
        },

        SUBTRACT {
            @Override
            double compute(double first, double second) {
                return first - second;
            }
        };

        abstract double compute(double first, double second);
    }
}