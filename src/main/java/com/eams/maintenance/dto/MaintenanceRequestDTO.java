package com.eams.maintenance.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MaintenanceRequestDTO {

    @NotNull(message = "Asset id is required")
    private Long assetId;

    @NotNull(message = "Scheduled date is required")
    @Future(message = "Scheduled date must be in the future")
    private LocalDateTime scheduledDate;

    @NotBlank(message = "Remarks cannot be empty")
    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;
}