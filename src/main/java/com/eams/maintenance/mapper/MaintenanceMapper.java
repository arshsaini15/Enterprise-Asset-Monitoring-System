package com.eams.maintenance.mapper;

import com.eams.maintenance.dto.MaintenanceResponseDTO;
import com.eams.maintenance.model.MaintenanceLog;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceMapper {

    public MaintenanceResponseDTO toDto(MaintenanceLog log) {

        MaintenanceResponseDTO dto =
                new MaintenanceResponseDTO();

        dto.setMaintenanceId(log.getMaintenanceId());

        dto.setAssetId(log.getAsset().getId());

        dto.setAssetName(log.getAsset().getName());

        dto.setStatus(log.getStatus());

        dto.setScheduledDate(log.getScheduledDate());

        dto.setCompletedDate(log.getCompletedDate());

        dto.setRemarks(log.getRemarks());

        return dto;
    }
}