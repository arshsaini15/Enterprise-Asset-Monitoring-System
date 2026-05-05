package com.eams.sensor.dto;

import java.time.LocalDateTime;

public class SensorDataResponseDTO {
    private Long id;
    private Long assetId;
    private Double temperature;
    private Double pressure;
    private LocalDateTime timestamp;
}