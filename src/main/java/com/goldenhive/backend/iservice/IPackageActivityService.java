package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.PackageActivityMappingDTO;
import com.goldenhive.backend.dto.CreatePackageActivityMappingRequest;
import java.util.List;
import java.util.Optional;

public interface IPackageActivityService {
    
    /**
     * Create package-activity mapping
     */
    PackageActivityMappingDTO createMapping(CreatePackageActivityMappingRequest request);
    
    /**
     * Get mapping by ID
     */
    Optional<PackageActivityMappingDTO> getMappingById(String mappingId);
    
    /**
     * Get all activities for a package
     */
    List<PackageActivityMappingDTO> getActivitiesForPackage(String packageId);
    
    /**
     * Get included activities for a package
     */
    List<PackageActivityMappingDTO> getIncludedActivities(String packageId);
    
    /**
     * Get optional activities for a package
     */
    List<PackageActivityMappingDTO> getOptionalActivities(String packageId);
    
    /**
     * Get packages containing an activity
     */
    List<PackageActivityMappingDTO> getPackagesWithActivity(String activityId);
    
    /**
     * Get discounted price for activity in package
     */
    Optional<Double> getDiscountedPrice(String packageId, String activityId);
    
    /**
     * Update mapping with new discount
     */
    PackageActivityMappingDTO updateMapping(String mappingId, CreatePackageActivityMappingRequest request);
    
    /**
     * Delete mapping
     */
    void deleteMapping(String mappingId);
    
    /**
     * Delete all mappings for package (when deleting package)
     */
    void deleteMappingsForPackage(String packageId);
}
