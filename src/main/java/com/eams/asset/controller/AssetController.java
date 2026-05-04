package com.eams.asset.controller;

import com.eams.asset.dto.AssetRequestDTO;
import com.eams.asset.dto.AssetResponseDTO;
import com.eams.asset.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AssetResponseDTO> createAsset(@RequestBody @Valid AssetRequestDTO dto) {
        return ResponseEntity.ok(service.createAsset(dto));
    }

    @GetMapping
    public ResponseEntity<Page<AssetResponseDTO>> getAllAssets(Pageable pageable) {
        return ResponseEntity.ok(service.getAllAssets(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAssetById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AssetResponseDTO>> getAssetsByUser(
            @PathVariable Long userId,
            Pageable pageable) {

        return ResponseEntity.ok(service.getAssetsByUser(userId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> updateAsset(@PathVariable Long id, @RequestBody @Valid AssetRequestDTO dto) {
        return ResponseEntity.ok(service.updateAsset(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        service.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }
}