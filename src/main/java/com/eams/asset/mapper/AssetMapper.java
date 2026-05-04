package com.eams.asset.mapper;

import com.eams.asset.dto.AssetRequestDTO;
import com.eams.asset.dto.AssetResponseDTO;
import com.eams.asset.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    @Mapping(target = "assignedTo", ignore = true)
    Asset toEntity(AssetRequestDTO dto);

    @Mapping(source = "assignedTo.id", target = "assignedUserId")
    @Mapping(source = "assignedTo.name", target = "assignedUserName")
    AssetResponseDTO toResponseDto(Asset asset);
}