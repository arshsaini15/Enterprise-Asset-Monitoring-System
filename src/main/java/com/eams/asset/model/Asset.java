package com.eams.asset.model;

import com.eams.asset.enums.AssetType;
import com.eams.asset.enums.AssetStatus;
import com.eams.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus currentStatus = AssetStatus.UP;

    @NotBlank(message = "Location is mandatory")
    @Column(nullable = false, length = 150)
    private String location;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double thresholdTemp;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double thresholdPressure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

}
