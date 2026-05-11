package com.eams.maintenance.service;

import com.eams.maintenance.dto.MaintenanceRequestDTO;
import com.eams.maintenance.dto.MaintenanceResponseDTO;

import java.util.List;

public interface MaintenanceService {

    MaintenanceResponseDTO scheduleMaintenance(
            MaintenanceRequestDTO dto);

    MaintenanceResponseDTO startMaintenance(
            Long maintenanceId);

    MaintenanceResponseDTO completeMaintenance(
            Long maintenanceId);

    MaintenanceResponseDTO cancelMaintenance(
            Long maintenanceId);

    List<MaintenanceResponseDTO> getMaintenanceHistory(
            Long assetId);
}