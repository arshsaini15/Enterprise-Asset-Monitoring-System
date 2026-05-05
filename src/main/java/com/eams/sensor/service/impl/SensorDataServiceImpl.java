package com.eams.sensor.service.impl;

import com.eams.asset.model.Asset;
import com.eams.asset.repository.AssetRepository;
import com.eams.common.exception.AssetNotFoundException;
import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.dto.SensorDataResponseDTO;
import com.eams.sensor.mapper.SensorDataMapper;
import com.eams.sensor.model.SensorData;
import com.eams.sensor.repository.SensorRepository;
import com.eams.sensor.service.SensorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorDataServiceImpl implements SensorDataService {

    private static final Logger log = LoggerFactory.getLogger(SensorDataServiceImpl.class);

    private final SensorRepository sensorRepository;
    private final AssetRepository assetRepository;
    private final SensorDataMapper sensorMapper;

    public SensorDataServiceImpl(SensorRepository sensorRepository, AssetRepository assetRepository, SensorDataMapper sensorMapper) {
        this.sensorRepository = sensorRepository;
        this.assetRepository = assetRepository;
        this.sensorMapper = sensorMapper;
    }

    @Override
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

        if (data.getTemperature() != null && asset.getThresholdTemp() != null && data.getTemperature() > asset.getThresholdTemp()) {
            // trigger temperature alert
            log.warn("Temperature threshold exceeded for asset id {} ({}): {} > {}",
                    asset.getId(),
                    asset.getName(),
                    data.getTemperature(),
                    asset.getThresholdTemp());
        }


        if (data.getPressure() != null && asset.getThresholdPressure() != null && data.getPressure() > asset.getThresholdPressure()) {
            // trigger pressure alert
            log.warn("Pressure threshold exceeded for asset id {} ({}): {} > {}",
                    asset.getId(),
                    asset.getName(),
                    data.getPressure(),
                    asset.getThresholdPressure());
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
}