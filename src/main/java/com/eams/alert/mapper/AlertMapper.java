package com.eams.alert.mapper;

import com.eams.alert.dto.AlertResponseDTO;
import com.eams.alert.model.Alert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlertMapper {

    @Mapping(source = "asset.id", target = "assetId")
    @Mapping(source = "asset.name", target = "assetName")
    AlertResponseDTO toResponseDTO(Alert alert);

}