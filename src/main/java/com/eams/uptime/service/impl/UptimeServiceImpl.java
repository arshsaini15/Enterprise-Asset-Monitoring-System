package com.eams.uptime.service.impl;

import com.eams.asset.model.Asset;
import com.eams.asset.repository.AssetRepository;
import com.eams.asset.enums.AssetStatus;
import com.eams.uptime.model.UptimeLog;
import com.eams.uptime.repository.UptimeLogRepository;
import com.eams.uptime.service.UptimeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UptimeServiceImpl implements UptimeService {

    private final UptimeLogRepository uptimeLogRepository;

    private final AssetRepository assetRepository;

    @Override
    public void changeAssetStatus(Asset asset, AssetStatus newStatus) {

        if (asset.getCurrentStatus() == newStatus) {
            return;
        }

        uptimeLogRepository
                .findByAssetAndEndTimeIsNull(asset)
                .ifPresent(activeLog -> {

                    activeLog.setEndTime(
                            LocalDateTime.now());

                    uptimeLogRepository
                            .save(activeLog);
                });

        asset.setCurrentStatus(newStatus);

        assetRepository.save(asset);

        UptimeLog newLog = new UptimeLog();

        newLog.setAsset(asset);

        newLog.setStatus(newStatus);

        newLog.setStartTime(
                LocalDateTime.now());

        uptimeLogRepository.save(newLog);
    }
}