package com.eams.uptime.model;

import com.eams.asset.model.Asset;
import com.eams.asset.enums.AssetStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "uptime_logs")
@Getter
@Setter
@NoArgsConstructor
public class UptimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uptimeLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus status;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;
}