package com.eams.sensor.controller;

import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.dto.SensorDataResponseDTO;
import com.eams.sensor.service.SensorDataService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorDataController {

    private final SensorDataService sensorService;

    public SensorDataController(SensorDataService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/send-data")
    public ResponseEntity<?> sendData(@Valid @RequestBody SensorDataRequestDTO dto) {
        sensorService.sendSensorData(dto);
        return ResponseEntity.ok("Sensor data processed");
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataByAssetId(@PathVariable Long assetId) {
        return ResponseEntity.ok(sensorService.getSensorDataByAsset(assetId));
    }

}
