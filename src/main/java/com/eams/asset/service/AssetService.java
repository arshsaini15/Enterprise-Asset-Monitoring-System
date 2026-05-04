package com.eams.asset.service;

import com.eams.asset.dto.AssetRequestDTO;
import com.eams.asset.dto.AssetResponseDTO;

import java.util.List;

public interface AssetService {

    AssetResponseDTO createAsset(AssetRequestDTO dto);

    List<AssetResponseDTO> getAllAssets();

    AssetResponseDTO getAssetById(Long id);

    List<AssetResponseDTO> getAssetsByUser(Long userId);

    AssetResponseDTO updateAsset(Long id, AssetRequestDTO dto);

    void deleteAsset(Long id);
}