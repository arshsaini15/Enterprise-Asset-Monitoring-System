package com.eams.alert.service.impl;

import com.eams.alert.dto.AlertResponseDTO;
import com.eams.alert.enums.AlertStatus;
import com.eams.alert.enums.AlertType;
import com.eams.alert.mapper.AlertMapper;
import com.eams.alert.model.Alert;
import com.eams.alert.repository.AlertRepository;
import com.eams.alert.service.AlertService;
import com.eams.asset.model.Asset;
import com.eams.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    @Override
    public void createAlert(Asset asset, AlertType type, String message) {

        Alert alert = new Alert();

        alert.setAsset(asset);
        alert.setType(type);
        alert.setMessage(message);
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setTriggeredAt(LocalDateTime.now());

        alertRepository.save(alert);

        log.warn("ALERT CREATED → Asset id {} ({}), Type: {}, Message: {}",
                asset.getId(),
                asset.getName(),
                type,
                message);
    }

    @Override
    public List<AlertResponseDTO> getAllAlerts() {

        log.info("Fetching all alerts");

        List<Alert> alerts = alertRepository.findAll();

        log.info("Total alerts found: {}", alerts.size());

        return alerts.stream()
                .map(alertMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void resolveAlert(Long alertId) {

        log.info("Resolving alert with id {}", alertId);

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Alert with id " + alertId + " not found"));

        alert.setStatus(AlertStatus.RESOLVED);
        alert.setMessage("Alert resolved with id{}" + alertId + " at " + LocalDateTime.now());
        alert.setTriggeredAt(LocalDateTime.now());

        alertRepository.save(alert);

        log.info("Alert with id {} marked as RESOLVED", alertId);
    }
}