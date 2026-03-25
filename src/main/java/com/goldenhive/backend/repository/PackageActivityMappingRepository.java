package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.PackageActivityMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageActivityMappingRepository extends MongoRepository<PackageActivityMapping, String> {
    
    /**
     * Find all activities mapped to a package
     */
    List<PackageActivityMapping> findByPackageId(String packageId);
    
    /**
     * Find specific package-activity mapping
     */
    Optional<PackageActivityMapping> findByPackageIdAndActivityId(String packageId, String activityId);
    
    /**
     * Find all packages that include a specific activity
     */
    List<PackageActivityMapping> findByActivityId(String activityId);
    
    /**
     * Find included activities for a package
     */
    List<PackageActivityMapping> findByPackageIdAndIsIncluded(String packageId, boolean isIncluded);
    
    /**
     * Find activities with discount for a package
     */
    @Query("{'packageId': ?0, 'discountedPrice': {$lt: ?1}}")
    List<PackageActivityMapping> findDiscountedActivities(String packageId, double originalPrice);
    
    /**
     * Delete mappings for a package
     */
    void deleteByPackageId(String packageId);
    
    /**
     * Delete mappings for an activity across all packages
     */
    void deleteByActivityId(String activityId);
}
