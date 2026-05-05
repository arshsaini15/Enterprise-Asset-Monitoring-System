package com.eams.sensor.model;

import com.eams.asset.model.Asset;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Asset is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @NotNull(message = "Temperature is required")
    private Double temperature;

    @Positive(message = "Pressure should be positive")
    private Double pressure;

    private LocalDateTime timestamp;
}