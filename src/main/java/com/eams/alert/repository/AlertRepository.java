package com.eams.alert.repository;

import com.eams.alert.enums.AlertStatus;
import com.eams.alert.enums.AlertType;
import com.eams.alert.model.Alert;
import com.eams.asset.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByAssetAndTypeAndStatus(
            Asset asset,
            AlertType type,
            AlertStatus status
    );
}