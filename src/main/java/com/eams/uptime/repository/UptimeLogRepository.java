package com.eams.uptime.repository;

import com.eams.asset.model.Asset;
import com.eams.uptime.model.UptimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UptimeLogRepository extends JpaRepository<UptimeLog, Long> {

    Optional<UptimeLog> findByAssetAndEndTimeIsNull(Asset asset);
    List<UptimeLog> findByAssetAssetId(Long assetId);

}