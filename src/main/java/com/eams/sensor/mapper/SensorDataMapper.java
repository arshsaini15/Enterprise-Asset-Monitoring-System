package com.eams.sensor.mapper;

import com.eams.sensor.dto.SensorDataRequestDTO;
import com.eams.sensor.dto.SensorDataResponseDTO;
import com.eams.sensor.model.SensorData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SensorDataMapper {

    SensorData toEntity(SensorDataRequestDTO dto);

    @Mapping(source = "asset.id", target = "assetId")
    @Mapping(source = "asset.name", target = "assetName")
    SensorDataResponseDTO toResponseDto(SensorData sensorData);
}
