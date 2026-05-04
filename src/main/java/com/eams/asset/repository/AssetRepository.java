package com.eams.asset.repository;

import com.eams.asset.model.Asset;
import com.eams.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByAssignedTo(User user);
}