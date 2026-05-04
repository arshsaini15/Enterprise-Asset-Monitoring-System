package com.eams.asset.dto;

import com.eams.asset.enums.AssetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDTO {

    private Long id;

    private String name;

    private AssetType type;

    private String location;

    private Double thresholdTemp;

    private Double thresholdPressure;

    private Long assignedUserId;

    private String assignedUserName;
}