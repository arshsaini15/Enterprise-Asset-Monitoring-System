package com.eams.alert.controller;

import com.eams.alert.dto.AlertResponseDTO;
import com.eams.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<List<AlertResponseDTO>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<Void> resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return ResponseEntity.noContent().build();
    }
}