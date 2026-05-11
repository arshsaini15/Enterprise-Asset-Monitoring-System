package com.eams.maintenance.repository;

import com.eams.maintenance.model.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<MaintenanceLog, Long> {

    List<MaintenanceLog> findByAssetAssetId(Long assetId);
}