package com.eams.sensor.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDataRequestDTO {

    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotNull(message = "Temperature is required")
    private Double temperature;

    @Positive(message = "Pressure should be positive")
    private Double pressure;
}