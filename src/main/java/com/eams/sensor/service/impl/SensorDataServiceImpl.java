package com.eams.sensor.service.impl;

import com.eams.alert.enums.AlertStatus;
import com.eams.alert.enums.AlertType;
import com.eams.alert.model.Alert;
import com.eams.alert.repository.AlertRepository;
import com.eams.alert.service.AlertService;
import com.eams.asset.model.Asset;
import com.eams.asset.repository.AssetRepository;
import com.eams.common.exception.AssetNotFoundException;
import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.dto.SensorDataResponseDTO;
import com.eams.sensor.mapper.SensorDataMapper;
import com.eams.sensor.model.SensorData;
import com.eams.sensor.repository.SensorRepository;
import com.eams.sensor.service.SensorDataService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorDataServiceImpl implements SensorDataService {

    private static final Logger log = LoggerFactory.getLogger(SensorDataServiceImpl.class);

    private final SensorRepository sensorRepository;
    private final AssetRepository assetRepository;
    private final SensorDataMapper sensorMapper;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    public SensorDataServiceImpl(SensorRepository sensorRepository, AssetRepository assetRepository, SensorDataMapper sensorMapper, AlertRepository alertRepository, AlertService alertService) {
        this.sensorRepository = sensorRepository;
        this.assetRepository = assetRepository;
        this.sensorMapper = sensorMapper;
        this.alertRepository = alertRepository;
        this.alertService = alertService;
    }

    @Override
    @Transactional
    public void sendSensorData(SensorDataRequestDTO dto) {

        Asset asset = getAssetOrThrow(dto.getAssetId());

        SensorData data = sensorMapper.toEntity(dto);

        data.setAsset(asset);
        data.setTimestamp(LocalDateTime.now());

        sensorRepository.save(data);

        log.info("Sensor data saved for asset id {}", asset.getId());

        checkThresholdAndTriggerAlert(data);
    }

    private Asset getAssetOrThrow(Long assetId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new AssetNotFoundException("Asset with id " + assetId + " not found"));

        log.info("Asset found for asset id {}", asset.getId());
        return asset;
    }

    private void checkThresholdAndTriggerAlert(SensorData data) {

        Asset asset = data.getAsset();

        // =========================
        // TEMPERATURE ALERT LOGIC
        // =========================

        boolean tempExceeded =
                data.getTemperature() != null
                        && asset.getThresholdTemp() != null
                        && data.getTemperature() > asset.getThresholdTemp();

        Optional<Alert> activeTempAlert =
                alertRepository.findByAssetAndTypeAndStatus(
                        asset,
                        AlertType.TEMP_HIGH,
                        AlertStatus.ACTIVE
                );

        if (tempExceeded) {

            log.warn("Temperature threshold exceeded for asset id {} ({}): {} > {}",
                    asset.getId(),
                    asset.getName(),
                    data.getTemperature(),
                    asset.getThresholdTemp());

            // Create alert ONLY if no active alert exists
            if (activeTempAlert.isEmpty()) {

                alertService.createAlert(
                        asset,
                        AlertType.TEMP_HIGH,
                        "Temperature exceeded threshold"
                );
            }

        } else {

            // Resolve existing active alert if temperature becomes normal
            activeTempAlert.ifPresent(alertService::resolveAlert);
        }


        // ======================
        // PRESSURE ALERT LOGIC
        // ======================

        boolean pressureExceeded =
                data.getPressure() != null
                        && asset.getThresholdPressure() != null
                        && data.getPressure() > asset.getThresholdPressure();

        Optional<Alert> activePressureAlert =
                alertRepository.findByAssetAndTypeAndStatus(
                        asset,
                        AlertType.PRESSURE_HIGH,
                        AlertStatus.ACTIVE
                );

        if (pressureExceeded) {

            log.warn("Pressure threshold exceeded for asset id {} ({}): {} > {}",
                    asset.getId(),
                    asset.getName(),
                    data.getPressure(),
                    asset.getThresholdPressure());

            // Create alert ONLY if no active alert exists
            if (activePressureAlert.isEmpty()) {

                alertService.createAlert(
                        asset,
                        AlertType.PRESSURE_HIGH,
                        "Pressure exceeded threshold"
                );
            }

        } else {

            // Resolve existing active alert if pressure becomes normal
            activePressureAlert.ifPresent(alertService::resolveAlert);
        }
    }

    @Override
    public List<SensorDataResponseDTO> getSensorDataByAsset(Long assetId) {

        log.info("Fetching sensor data for asset id {}", assetId);

        Asset asset = getAssetOrThrow(assetId);

        List<SensorData> dataList = sensorRepository.findByAsset(asset);

        log.info("Found {} sensor records for asset id {}", dataList.size(), assetId);

        return dataList.stream()
                .map(sensorMapper::toResponseDto)
                .toList();
    }

    private void createAlert(Asset asset, String message, String type) {

        Alert alert = new Alert();
        alert.setAsset(asset);
        alert.setMessage(message);
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setType(AlertType.valueOf(type));
        alert.setTriggeredAt(LocalDateTime.now());

        alertRepository.save(alert);

        log.info("Alert saved for asset id {}", asset.getId());
    }

}