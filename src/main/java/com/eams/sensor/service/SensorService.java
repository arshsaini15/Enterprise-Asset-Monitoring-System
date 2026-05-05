package com.eams.sensor.service;

import com.eams.asset.model.Asset;
import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.dto.SensorDataResponseDTO;
import com.eams.sensor.model.SensorData;

import java.util.List;

public interface SensorService {

    void sendSensorData(SensorDataRequestDTO dto);

    List<SensorDataResponseDTO> getSensorDataByAsset(Long assetId);
}
