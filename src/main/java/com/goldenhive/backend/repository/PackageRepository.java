package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.Package;
import com.goldenhive.backend.enums.PackageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends MongoRepository<Package, String> {
    
    /**
     * Find package by SEO slug
     */
    Optional<Package> findBySlug(String slug);
    
    /**
     * Find all active packages
     */
    List<Package> findByMetaStatus(PackageStatus status);
    
    /**
     * Find packages by destination
     */
    List<Package> findByDestination(String destination);
    
    /**
     * Search packages by name
     */
    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Package> searchByName(String name);
    
    /**
     * Find all active packages with pagination
     */
    Page<Package> findByMetaStatus(PackageStatus status, Pageable pageable);
    
    /**
     * Find active packages by destination
     */
    List<Package> findByDestinationAndMetaStatus(String destination, PackageStatus status);
    
    /**
     * Find packages with limited offer
     */
    List<Package> findByLimitedOfferAndMetaStatus(boolean limitedOffer, PackageStatus status);
    
    /**
     * Find customizable packages
     */
    List<Package> findByCustomizableAndMetaStatus(boolean customizable, PackageStatus status);
}
