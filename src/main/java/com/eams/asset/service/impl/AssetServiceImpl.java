package com.eams.asset.service.impl;

import com.eams.asset.dto.AssetRequestDTO;
import com.eams.asset.dto.AssetResponseDTO;
import com.eams.asset.mapper.AssetMapper;
import com.eams.asset.model.Asset;
import com.eams.asset.repository.AssetRepository;
import com.eams.asset.service.AssetService;
import com.eams.common.exception.UserNotFoundException;
import com.eams.user.model.User;
import com.eams.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AssetMapper assetMapper;

    @Override
    public AssetResponseDTO createAsset(AssetRequestDTO dto) {

        Asset asset = assetMapper.toEntity(dto);

        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new UserNotFoundException("User with id " + dto.getAssignedUserId() + " not found"));

            asset.setAssignedTo(user);
        }

        return assetMapper.toResponseDto(assetRepository.save(asset));
    }

    @Override
    public List<AssetResponseDTO> getAllAssets() {
        return assetRepository.findAll()
                .stream()
                .map(assetMapper::toResponseDto)
                .toList();
    }

    @Override
    public AssetResponseDTO getAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException("Asset with id " + id + " not found"));

        return assetMapper.toResponseDto(asset);
    }

    @Override
    public List<AssetResponseDTO> getAssetsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return assetRepository.findByAssignedTo(user)
                .stream()
                .map(assetMapper::toResponseDto)
                .toList();
    }

    @Override
    public AssetResponseDTO updateAsset(Long id, AssetRequestDTO dto) {

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setLocation(dto.getLocation());
        asset.setThresholdTemp(dto.getThresholdTemp());
        asset.setThresholdPressure(dto.getThresholdPressure());

        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            asset.setAssignedTo(user);
        } else {
            asset.setAssignedTo(null);
        }

        return assetMapper.toResponseDto(assetRepository.save(asset));
    }

    @Override
    public void deleteAsset(Long id) {

        if (!assetRepository.existsById(id)) {
            throw new RuntimeException("Asset not found");
        }

        assetRepository.deleteById(id);
    }
}