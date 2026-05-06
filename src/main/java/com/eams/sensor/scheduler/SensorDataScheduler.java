package com.eams.sensor.scheduler;

import com.eams.asset.model.Asset;
import com.eams.asset.repository.AssetRepository;
import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.service.SensorDataService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class SensorDataScheduler {

    private static final Logger log = LoggerFactory.getLogger(SensorDataScheduler.class);

    private final AssetRepository assetRepository;
    private final SensorDataService sensorService;

    private final Random random = new Random();

    // Runs every 7 seconds
    @Scheduled(fixedRate = 7000)
    public void generateSensorData() {

        List<Asset> assets = assetRepository.findAll();

        if (assets.isEmpty()) {
            log.warn("No assets found. Skipping sensor simulation.");
            return;
        }

        // pick random asset
        Asset asset = assets.get(random.nextInt(assets.size()));

        // generate values around thresholds (so alerts sometimes trigger)
        double tempBase = asset.getThresholdTemp() != null ? asset.getThresholdTemp() : 80;
        double pressureBase = asset.getThresholdPressure() != null ? asset.getThresholdPressure() : 100;

        double temperature = tempBase + random.nextDouble() * 20 - 10;   // +/-10 swing
        double pressure = pressureBase + random.nextDouble() * 20 - 10;

        SensorDataRequestDTO dto = new SensorDataRequestDTO();
        dto.setAssetId(asset.getId());
        dto.setTemperature(temperature);
        dto.setPressure(pressure);

        sensorService.sendSensorData(dto);

        log.info("Simulated sensor data → assetId={}, temp={}, pressure={}",
                asset.getId(), temperature, pressure);
    }
}