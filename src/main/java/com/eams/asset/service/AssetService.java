package com.eams.asset.service;

import com.eams.asset.dto.AssetRequestDTO;
import com.eams.asset.dto.AssetResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {

    AssetResponseDTO createAsset(AssetRequestDTO dto);

    Page<AssetResponseDTO> getAllAssets(Pageable pageable);

    AssetResponseDTO getAssetById(Long id);

    Page<AssetResponseDTO> getAssetsByUser(Long userId, Pageable pageable);

    AssetResponseDTO updateAsset(Long id, AssetRequestDTO dto);

    void deleteAsset(Long id);
}