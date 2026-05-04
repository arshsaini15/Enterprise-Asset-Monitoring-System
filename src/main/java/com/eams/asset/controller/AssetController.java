package com.eams.asset.controller;

import com.eams.asset.dto.AssetRequestDTO;
import com.eams.asset.dto.AssetResponseDTO;
import com.eams.asset.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<AssetResponseDTO>> getAllAssets() {
        return ResponseEntity.ok(service.getAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAssetById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetResponseDTO>> getAssetsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getAssetsByUser(userId));
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