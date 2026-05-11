package com.eams.maintenance.controller;

import com.eams.maintenance.dto.MaintenanceRequestDTO;
import com.eams.maintenance.dto.MaintenanceResponseDTO;
import com.eams.maintenance.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO>
    scheduleMaintenance(
            @Valid
            @RequestBody
            MaintenanceRequestDTO dto) {

        MaintenanceResponseDTO response =
                maintenanceService
                        .scheduleMaintenance(dto);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @PutMapping("/{maintenanceId}/start")
    public ResponseEntity<MaintenanceResponseDTO>
    startMaintenance(
            @PathVariable
            Long maintenanceId) {

        MaintenanceResponseDTO response =
                maintenanceService
                        .startMaintenance(maintenanceId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{maintenanceId}/complete")
    public ResponseEntity<MaintenanceResponseDTO>
    completeMaintenance(
            @PathVariable
            Long maintenanceId) {

        MaintenanceResponseDTO response =
                maintenanceService
                        .completeMaintenance(maintenanceId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{maintenanceId}/cancel")
    public ResponseEntity<MaintenanceResponseDTO>
    cancelMaintenance(
            @PathVariable
            Long maintenanceId) {

        MaintenanceResponseDTO response =
                maintenanceService
                        .cancelMaintenance(maintenanceId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<MaintenanceResponseDTO>>
    getMaintenanceHistory(
            @PathVariable
            Long assetId) {

        List<MaintenanceResponseDTO> response =
                maintenanceService
                        .getMaintenanceHistory(assetId);

        return ResponseEntity.ok(response);
    }
}