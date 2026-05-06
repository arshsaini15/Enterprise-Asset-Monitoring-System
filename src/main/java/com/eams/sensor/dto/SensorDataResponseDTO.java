package com.eams.sensor.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SensorDataResponseDTO {

    private Long id;

    private Long assetId;

    private String assetName;

    private Double temperature;

    private Double pressure;

    private LocalDateTime timestamp;
}