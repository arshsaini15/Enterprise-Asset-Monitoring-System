package com.eams.asset.repository;

import com.eams.asset.model.Asset;
import com.eams.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Page<Asset> findAll(Pageable pageable);
    Page<Asset> findByAssignedTo(User user, Pageable pageable);
}