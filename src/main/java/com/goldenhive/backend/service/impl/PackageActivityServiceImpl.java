package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.PackageActivityMappingDTO;
import com.goldenhive.backend.dto.CreatePackageActivityMappingRequest;
import com.goldenhive.backend.entity.PackageActivityMapping;
import com.goldenhive.backend.iservice.IPackageActivityService;
import com.goldenhive.backend.repository.PackageActivityMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageActivityServiceImpl implements IPackageActivityService {
    
    private final PackageActivityMappingRepository mappingRepository;
    
    @Override
    public PackageActivityMappingDTO createMapping(CreatePackageActivityMappingRequest request) {
        log.info("Creating mapping - Package: {}, Activity: {}", request.getPackageId(), request.getActivityId());
        
        // Check if mapping already exists
        if (mappingRepository.findByPackageIdAndActivityId(request.getPackageId(), request.getActivityId()).isPresent()) {
            throw new RuntimeException("Mapping already exists for Package: " + request.getPackageId() + 
                    ", Activity: " + request.getActivityId());
        }
        
        PackageActivityMapping mapping = new PackageActivityMapping();
        mapping.setPackageId(request.getPackageId());
        mapping.setActivityId(request.getActivityId());
        mapping.setDiscountedPrice(request.getDiscountedPrice());
        mapping.setIncluded(request.isIncluded());
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setUpdatedAt(LocalDateTime.now());
        
        PackageActivityMapping savedMapping = mappingRepository.save(mapping);
        log.info("Mapping created with ID: {}", savedMapping.getId());
        
        return mapToDTO(savedMapping);
    }
    
    @Override
    public Optional<PackageActivityMappingDTO> getMappingById(String mappingId) {
        log.info("Fetching mapping with ID: {}", mappingId);
        return mappingRepository.findById(mappingId).map(this::mapToDTO);
    }
    
    @Override
    public List<PackageActivityMappingDTO> getActivitiesForPackage(String packageId) {
        log.info("Fetching all activities for Package: {}", packageId);
        return mappingRepository.findByPackageId(packageId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageActivityMappingDTO> getIncludedActivities(String packageId) {
        log.info("Fetching included activities for Package: {}", packageId);
        return mappingRepository.findByPackageIdAndIsIncluded(packageId, true)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageActivityMappingDTO> getOptionalActivities(String packageId) {
        log.info("Fetching optional activities for Package: {}", packageId);
        return mappingRepository.findByPackageIdAndIsIncluded(packageId, false)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageActivityMappingDTO> getPackagesWithActivity(String activityId) {
        log.info("Fetching packages containing Activity: {}", activityId);
        return mappingRepository.findByActivityId(activityId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Double> getDiscountedPrice(String packageId, String activityId) {
        log.info("Getting discounted price - Package: {}, Activity: {}", packageId, activityId);
        return mappingRepository.findByPackageIdAndActivityId(packageId, activityId)
                .map(PackageActivityMapping::getDiscountedPrice);
    }
    
    @Override
    public PackageActivityMappingDTO updateMapping(String mappingId, CreatePackageActivityMappingRequest request) {
        log.info("Updating mapping with ID: {}", mappingId);
        
        PackageActivityMapping mapping = mappingRepository.findById(mappingId)
                .orElseThrow(() -> new RuntimeException("Mapping not found with ID: " + mappingId));
        
        mapping.setDiscountedPrice(request.getDiscountedPrice());
        mapping.setIncluded(request.isIncluded());
        mapping.setUpdatedAt(LocalDateTime.now());
        
        PackageActivityMapping updatedMapping = mappingRepository.save(mapping);
        log.info("Mapping updated with ID: {}", mappingId);
        
        return mapToDTO(updatedMapping);
    }
    
    @Override
    public void deleteMapping(String mappingId) {
        log.info("Deleting mapping with ID: {}", mappingId);
        
        if (!mappingRepository.existsById(mappingId)) {
            throw new RuntimeException("Mapping not found with ID: " + mappingId);
        }
        
        mappingRepository.deleteById(mappingId);
        log.info("Mapping deleted");
    }
    
    @Override
    public void deleteMappingsForPackage(String packageId) {
        log.info("Deleting all mappings for Package: {}", packageId);
        mappingRepository.deleteByPackageId(packageId);
        log.info("All mappings deleted for package");
    }
    
    /**
     * Convert PackageActivityMapping entity to DTO
     */
    private PackageActivityMappingDTO mapToDTO(PackageActivityMapping mapping) {
        return new PackageActivityMappingDTO(
                mapping.getId(),
                mapping.getPackageId(),
                mapping.getActivityId(),
                mapping.getDiscountedPrice(),
                mapping.isIncluded(),
                mapping.getCreatedAt(),
                mapping.getUpdatedAt()
        );
    }
}
