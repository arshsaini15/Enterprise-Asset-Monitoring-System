package com.eams.maintenance.dto;

import com.eams.maintenance.enums.MaintenanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MaintenanceResponseDTO {

    private Long maintenanceId;

    private Long assetId;

    private String assetName;

    private MaintenanceStatus status;

    private LocalDateTime scheduledDate;

    private LocalDateTime completedDate;

    private String remarks;
}