package com.eams.alert.service;

import com.eams.alert.dto.AlertResponseDTO;
import com.eams.alert.enums.AlertType;
import com.eams.asset.model.Asset;

import java.util.List;

public interface AlertService {

    void createAlert(Asset asset, AlertType type, String message);

    List<AlertResponseDTO> getAllAlerts();

    void resolveAlert(Long alertId);
}