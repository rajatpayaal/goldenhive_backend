package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.PackageDTO;
import com.goldenhive.backend.dto.CreatePackageFormRequest;
import com.goldenhive.backend.dto.CreatePackageRequest;
import java.util.List;
import java.util.Optional;

public interface IPackageService {
    
    /**
     * Create a new package
     */
    PackageDTO createPackage(CreatePackageRequest request);

    /**
     * Create a new package with multipart/form-data
     */
    PackageDTO createPackage(CreatePackageFormRequest request);
    
    /**
     * Get package by ID
     */
    Optional<PackageDTO> getPackageById(String packageId);
    
    /**
     * Get package by slug (SEO-friendly)
     */
    Optional<PackageDTO> getPackageBySlug(String slug);
    
    /**
     * Get all active packages
     */
    List<PackageDTO> getAllActivePackages();
    
    /**
     * Get all packages (admin)
     */
    List<PackageDTO> getAllPackages();
    
    /**
     * Get packages by destination
     */
    List<PackageDTO> getPackagesByDestination(String destination);
    
    /**
     * Search packages by name
     */
    List<PackageDTO> searchPackages(String name);
    
    /**
     * Get limited offer packages
     */
    List<PackageDTO> getLimitedOfferPackages();
    
    /**
     * Get customizable packages
     */
    List<PackageDTO> getCustomizablePackages();
    
    /**
     * Update package
     */
    PackageDTO updatePackage(String packageId, CreatePackageRequest request);

    /**
     * Update package with multipart/form-data
     */
    PackageDTO updatePackage(String packageId, CreatePackageFormRequest request);
    
    /**
     * Activate/Deactivate package
     */
    void updatePackageStatus(String packageId, boolean isActive);
    
    /**
     * Delete package
     */
    void deletePackage(String packageId);
}
