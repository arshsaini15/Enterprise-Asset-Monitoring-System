package com.eams.alert.dto;

import com.eams.alert.enums.AlertStatus;
import com.eams.alert.enums.AlertType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertResponseDTO {

    private Long id;

    private Long assetId;

    private String assetName;

    private AlertType type;

    private String message;

    private AlertStatus status;

    private LocalDateTime triggeredAt;
}