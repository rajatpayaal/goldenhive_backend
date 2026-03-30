package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.HomeBanner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeBannerRepository extends MongoRepository<HomeBanner, String> {
    List<HomeBanner> findAllByOrderByDisplayOrderAsc();
    List<HomeBanner> findByActiveTrueOrderByDisplayOrderAsc();
    Optional<HomeBanner> findByDisplayOrder(int displayOrder);
}
