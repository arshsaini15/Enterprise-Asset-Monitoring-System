package com.eams.maintenance.model;

import com.eams.asset.model.Asset;
import com.eams.maintenance.enums.MaintenanceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @NotNull(message = "Asset is required")
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus status = MaintenanceStatus.SCHEDULED;

    @Column(nullable = false)
    @NotNull(message = "Scheduled date is required")
    @Future(message = "Scheduled date must be in the future")
    private LocalDateTime scheduledDate;

    private LocalDateTime completedDate;

    @NotBlank(message = "Remarks cannot be empty")
    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    @Column(nullable = false, length = 500)
    private String remarks;
}