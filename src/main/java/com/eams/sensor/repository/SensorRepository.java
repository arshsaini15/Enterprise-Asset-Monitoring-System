package com.eams.sensor.repository;

import com.eams.asset.model.Asset;
import com.eams.sensor.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findByAsset(Asset asset);
}
