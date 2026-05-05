package com.eams.sensor.service;

import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.dto.SensorDataResponseDTO;

import java.util.List;

public interface SensorDataService {

    void sendSensorData(SensorDataRequestDTO dto);

    List<SensorDataResponseDTO> getSensorDataByAsset(Long assetId);
}
