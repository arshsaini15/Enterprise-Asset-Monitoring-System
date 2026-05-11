package com.eams.uptime.service;

import com.eams.asset.enums.AssetStatus;
import com.eams.asset.model.Asset;

public interface UptimeService {

    void changeAssetStatus(Asset asset, AssetStatus newStatus);
}