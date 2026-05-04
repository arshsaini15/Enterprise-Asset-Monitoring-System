package com.eams.asset.service.impl;

import com.eams.asset.dto.AssetRequestDTO;
import com.eams.asset.dto.AssetResponseDTO;
import com.eams.asset.mapper.AssetMapper;
import com.eams.asset.model.Asset;
import com.eams.asset.repository.AssetRepository;
import com.eams.asset.service.AssetService;
import com.eams.common.exception.AssetNotFoundException;
import com.eams.common.exception.UserNotFoundException;
import com.eams.user.model.User;
import com.eams.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private static final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AssetMapper assetMapper;

    @Override
    public AssetResponseDTO createAsset(AssetRequestDTO dto) {

        log.info("Creating asset with name: {}", dto.getName());

        Asset asset = assetMapper.toEntity(dto);

        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() ->
                            new UserNotFoundException("User with id " + dto.getAssignedUserId() + " not found"));

            asset.setAssignedTo(user);
            log.info("Assigning asset '{}' to userId: {}", dto.getName(), dto.getAssignedUserId());
        }

        Asset savedAsset = assetRepository.save(asset);

        log.info("Asset created successfully with id: {}", savedAsset.getId());

        return assetMapper.toResponseDto(savedAsset);
    }

    @Override
    public List<AssetResponseDTO> getAllAssets() {

        log.info("Fetching all assets");

        List<Asset> assets = assetRepository.findAll();

        log.info("Total assets fetched: {}", assets.size());

        return assets.stream()
                .map(assetMapper::toResponseDto)
                .toList();
    }

    @Override
    public AssetResponseDTO getAssetById(Long id) {

        log.info("Fetching asset with id: {}", id);

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() ->
                        new AssetNotFoundException("Asset with id " + id + " not found"));

        log.info("Asset found with id: {}", id);

        return assetMapper.toResponseDto(asset);
    }

    @Override
    public List<AssetResponseDTO> getAssetsByUser(Long userId) {

        log.info("Fetching assets for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + userId + " not found"));

        List<Asset> assets = assetRepository.findByAssignedTo(user);

        log.info("Found {} assets for userId: {}", assets.size(), userId);

        return assets.stream()
                .map(assetMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public AssetResponseDTO updateAsset(Long id, AssetRequestDTO dto) {

        log.info("Updating asset with id: {}", id);

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() ->
                        new AssetNotFoundException("Asset with id " + id + " not found"));

        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setLocation(dto.getLocation());
        asset.setThresholdTemp(dto.getThresholdTemp());
        asset.setThresholdPressure(dto.getThresholdPressure());

        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() ->
                            new UserNotFoundException("User with id " + dto.getAssignedUserId() + " not found"));

            asset.setAssignedTo(user);
            log.info("Reassigned asset id {} to userId {}", id, dto.getAssignedUserId());

        } else {
            asset.setAssignedTo(null);
            log.warn("Asset id {} has been unassigned from any user", id);
        }

        Asset updatedAsset = assetRepository.save(asset);

        log.info("Asset updated successfully with id: {}", id);

        return assetMapper.toResponseDto(updatedAsset);
    }

    @Override
    public void deleteAsset(Long id) {

        log.info("Deleting asset with id: {}", id);

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() ->
                        new AssetNotFoundException("Asset with id " + id + " not found"));

        assetRepository.delete(asset);

        log.info("Asset deleted successfully with id: {}", id);
    }
}