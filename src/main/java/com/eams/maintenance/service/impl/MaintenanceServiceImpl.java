package com.eams.maintenance.service.impl;

import com.eams.asset.model.Asset;
import com.eams.asset.repository.AssetRepository;
import com.eams.common.exception.ResourceNotFoundException;
import com.eams.maintenance.dto.MaintenanceRequestDTO;
import com.eams.maintenance.dto.MaintenanceResponseDTO;
import com.eams.asset.enums.AssetStatus;
import com.eams.uptime.service.UptimeService;
import com.eams.maintenance.enums.MaintenanceStatus;
import com.eams.maintenance.mapper.MaintenanceMapper;
import com.eams.maintenance.model.MaintenanceLog;
import com.eams.maintenance.repository.MaintenanceRepository;
import com.eams.maintenance.service.MaintenanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final AssetRepository assetRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final UptimeService uptimeService;

    @Override
    public MaintenanceResponseDTO scheduleMaintenance(MaintenanceRequestDTO dto) {

        Asset asset = assetRepository
                .findById(dto.getAssetId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Asset not found with id : "
                                        + dto.getAssetId()));

        MaintenanceLog maintenanceLog = new MaintenanceLog();

        maintenanceLog.setAsset(asset);
        maintenanceLog.setStatus(MaintenanceStatus.SCHEDULED);
        maintenanceLog.setScheduledDate(dto.getScheduledDate());
        maintenanceLog.setRemarks(dto.getRemarks());
        MaintenanceLog savedLog = maintenanceRepository.save(maintenanceLog);

        return maintenanceMapper.toDto(savedLog);
    }

    @Override
    public MaintenanceResponseDTO
    startMaintenance(Long maintenanceId) {

        MaintenanceLog maintenanceLog = getMaintenanceById(maintenanceId);

        if (maintenanceLog.getStatus() == MaintenanceStatus.COMPLETED) {
            throw new IllegalStateException("Completed maintenance cannot be restarted");
        }

        if (maintenanceLog.getStatus() == MaintenanceStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled maintenance cannot be started");
        }

        maintenanceLog.setStatus(MaintenanceStatus.IN_PROGRESS);
        MaintenanceLog updatedLog = maintenanceRepository.save(maintenanceLog);

        return maintenanceMapper.toDto(updatedLog);
    }

    @Override
    public MaintenanceResponseDTO completeMaintenance(Long maintenanceId) {

        MaintenanceLog maintenanceLog = getMaintenanceById(maintenanceId);

        if (maintenanceLog.getStatus() == MaintenanceStatus.CANCELLED) {

            throw new IllegalStateException("Cancelled maintenance cannot be completed");
        }

        if (maintenanceLog.getStatus() == MaintenanceStatus.COMPLETED) {

            throw new IllegalStateException("Maintenance already completed");
        }

        maintenanceLog.setStatus(MaintenanceStatus.COMPLETED);

        maintenanceLog.setCompletedDate(LocalDateTime.now());

        uptimeService.changeAssetStatus(maintenanceLog.getAsset(), AssetStatus.UP);

        MaintenanceLog updatedLog = maintenanceRepository.save(maintenanceLog);

        return maintenanceMapper.toDto(updatedLog);
    }

    @Override
    public MaintenanceResponseDTO cancelMaintenance(Long maintenanceId) {

        MaintenanceLog maintenanceLog = getMaintenanceById(maintenanceId);

        if (maintenanceLog.getStatus() == MaintenanceStatus.COMPLETED) {

            throw new IllegalStateException("Completed maintenance cannot be cancelled");
        }

        maintenanceLog.setStatus(MaintenanceStatus.CANCELLED);

        MaintenanceLog updatedLog = maintenanceRepository.save(maintenanceLog);

        return maintenanceMapper.toDto(updatedLog);
    }

    @Override
    public List<MaintenanceResponseDTO> getMaintenanceHistory(Long assetId) {

        List<MaintenanceLog> maintenanceLogs = maintenanceRepository.findByAssetAssetId(assetId);

        return maintenanceLogs.stream()
                .map(maintenanceMapper::toDto)
                .collect(Collectors.toList());
    }

    private MaintenanceLog
    getMaintenanceById(Long maintenanceId) {

        return maintenanceRepository
                .findById(maintenanceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Maintenance not found with id : "
                                        + maintenanceId));
    }
}