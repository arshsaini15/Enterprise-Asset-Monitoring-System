package com.eams.sensor.mapper;

import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.dto.SensorDataResponseDTO;
import com.eams.sensor.model.SensorData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorMapper {

    SensorData toEntity(SensorDataRequestDTO dto);

    SensorDataResponseDTO toResponseDto(SensorData sensorData);
}
