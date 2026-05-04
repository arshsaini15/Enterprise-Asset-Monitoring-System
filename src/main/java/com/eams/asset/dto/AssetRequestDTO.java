package com.eams.asset.dto;

import com.eams.asset.enums.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequestDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Type is mandatory")
    private AssetType type;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotNull(message = "Temperature threshold is required")
    @Positive(message = "Temperature must be positive")
    private Double thresholdTemp;

    @NotNull(message = "Pressure threshold is required")
    @Positive(message = "Pressure must be positive")
    private Double thresholdPressure;

    private Long assignedUserId;
}