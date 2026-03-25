package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.PackageDTO;
import com.goldenhive.backend.dto.CreatePackageRequest;
import com.goldenhive.backend.dto.ImageDTO;
import com.goldenhive.backend.dto.ItineraryDayDTO;
import com.goldenhive.backend.dto.MetaDTO;
import com.goldenhive.backend.entity.Package;
import com.goldenhive.backend.entity.Image;
import com.goldenhive.backend.entity.ItineraryDay;
import com.goldenhive.backend.entity.Meta;
import com.goldenhive.backend.enums.PackageStatus;
import com.goldenhive.backend.iservice.IPackageService;
import com.goldenhive.backend.repository.PackageRepository;
import com.goldenhive.backend.util.SlugUtil;
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
public class PackageServiceImpl implements IPackageService {
    
    private final PackageRepository packageRepository;
    
    @Override
    public PackageDTO createPackage(CreatePackageRequest request) {
        log.info("Creating package: {}", request.getName());
        
        String slug = SlugUtil.generateSlug(request.getName());
        
        Meta meta = new Meta();
        meta.setCreatedAt(LocalDateTime.now());
        meta.setUpdatedAt(LocalDateTime.now());
        meta.setStatus(PackageStatus.ACTIVE);
        
        Package pkg = new Package();
        pkg.setName(request.getName());
        pkg.setSlug(slug);
        pkg.setDestination(request.getDestination());
        pkg.setDuration(request.getDuration());
        pkg.setBasePrice(request.getBasePrice());
        pkg.setImages(mapImageDTOToEntity(request.getImages()));
        pkg.setInclusions(request.getInclusions());
        pkg.setExclusions(request.getExclusions());
        pkg.setItinerary(mapItineraryDTOToEntity(request.getItinerary()));
        pkg.setHighlights(request.getHighlights());
        pkg.setCustomizable(request.isCustomizable());
        pkg.setWhatsappContact(request.getWhatsappContact());
        pkg.setPopularTag(request.getPopularTag());
        pkg.setLimitedOffer(request.isLimitedOffer());
        pkg.setMeta(meta);
        
        Package savedPackage = packageRepository.save(pkg);
        log.info("Package created with slug: {}", slug);
        
        return mapToDTO(savedPackage);
    }
    
    @Override
    public Optional<PackageDTO> getPackageById(String packageId) {
        log.info("Fetching package with ID: {}", packageId);
        return packageRepository.findById(packageId).map(this::mapToDTO);
    }
    
    @Override
    public Optional<PackageDTO> getPackageBySlug(String slug) {
        log.info("Fetching package by slug: {}", slug);
        return packageRepository.findBySlug(slug).map(this::mapToDTO);
    }
    
    @Override
    public List<PackageDTO> getAllActivePackages() {
        log.info("Fetching all active packages");
        return packageRepository.findByMetaStatus(PackageStatus.ACTIVE)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageDTO> getAllPackages() {
        log.info("Fetching all packages (admin)");
        return packageRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageDTO> getPackagesByDestination(String destination) {
        log.info("Fetching packages by destination: {}", destination);
        return packageRepository.findByDestination(destination)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageDTO> searchPackages(String name) {
        log.info("Searching packages with name: {}", name);
        return packageRepository.searchByName(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageDTO> getLimitedOfferPackages() {
        log.info("Fetching limited offer packages");
        return packageRepository.findByLimitedOfferAndMetaStatus(true, PackageStatus.ACTIVE)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PackageDTO> getCustomizablePackages() {
        log.info("Fetching customizable packages");
        return packageRepository.findByCustomizableAndMetaStatus(true, PackageStatus.ACTIVE)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public PackageDTO updatePackage(String packageId, CreatePackageRequest request) {
        log.info("Updating package with ID: {}", packageId);
        
        Package pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found with ID: " + packageId));
        
        pkg.setName(request.getName());
        pkg.setDestination(request.getDestination());
        pkg.setDuration(request.getDuration());
        pkg.setBasePrice(request.getBasePrice());
        pkg.setImages(mapImageDTOToEntity(request.getImages()));
        pkg.setInclusions(request.getInclusions());
        pkg.setExclusions(request.getExclusions());
        pkg.setItinerary(mapItineraryDTOToEntity(request.getItinerary()));
        pkg.setHighlights(request.getHighlights());
        pkg.setCustomizable(request.isCustomizable());
        pkg.setWhatsappContact(request.getWhatsappContact());
        pkg.setPopularTag(request.getPopularTag());
        pkg.setLimitedOffer(request.isLimitedOffer());
        pkg.getMeta().setUpdatedAt(LocalDateTime.now());
        
        Package updatedPackage = packageRepository.save(pkg);
        log.info("Package updated with ID: {}", packageId);
        
        return mapToDTO(updatedPackage);
    }
    
    @Override
    public void updatePackageStatus(String packageId, boolean isActive) {
        log.info("Updating package status - ID: {}, Active: {}", packageId, isActive);
        
        Package pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found with ID: " + packageId));
        
        pkg.getMeta().setStatus(isActive ? PackageStatus.ACTIVE : PackageStatus.INACTIVE);
        pkg.getMeta().setUpdatedAt(LocalDateTime.now());
        
        packageRepository.save(pkg);
        log.info("Package status updated");
    }
    
    @Override
    public void deletePackage(String packageId) {
        log.info("Deleting package with ID: {}", packageId);
        
        if (!packageRepository.existsById(packageId)) {
            throw new RuntimeException("Package not found with ID: " + packageId);
        }
        
        packageRepository.deleteById(packageId);
        log.info("Package deleted with ID: {}", packageId);
    }
    
    /**
     * Convert Package entity to PackageDTO
     */
    private PackageDTO mapToDTO(Package pkg) {
        return new PackageDTO(
                pkg.getPackageId(),
                pkg.getName(),
                pkg.getSlug(),
                pkg.getDestination(),
                pkg.getDuration(),
                pkg.getBasePrice(),
                mapImageEntityToDTO(pkg.getImages()),
                pkg.getInclusions(),
                pkg.getExclusions(),
                mapItineraryEntityToDTO(pkg.getItinerary()),
                pkg.getHighlights(),
                pkg.isCustomizable(),
                pkg.getWhatsappContact(),
                pkg.getPopularTag(),
                pkg.isLimitedOffer(),
                mapMetaEntityToDTO(pkg.getMeta())
        );
    }
    
    private List<Image> mapImageDTOToEntity(List<ImageDTO> dtoList) {
        if (dtoList == null) return null;
        return dtoList.stream()
                .map(dto -> new Image(dto.getUrl(), dto.isPrimary()))
                .collect(Collectors.toList());
    }
    
    private List<ImageDTO> mapImageEntityToDTO(List<Image> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(entity -> new ImageDTO(entity.getUrl(), entity.isPrimary()))
                .collect(Collectors.toList());
    }
    
    private List<ItineraryDay> mapItineraryDTOToEntity(List<ItineraryDayDTO> dtoList) {
        if (dtoList == null) return null;
        return dtoList.stream()
                .map(dto -> new ItineraryDay(dto.getDay(), dto.getTitle(), dto.getDescription(), dto.getLocation()))
                .collect(Collectors.toList());
    }
    
    private List<ItineraryDayDTO> mapItineraryEntityToDTO(List<ItineraryDay> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(entity -> new ItineraryDayDTO(entity.getDay(), entity.getTitle(), entity.getDescription(), entity.getLocation()))
                .collect(Collectors.toList());
    }
    
    private MetaDTO mapMetaEntityToDTO(Meta meta) {
        if (meta == null) return null;
        return new MetaDTO(meta.getCreatedAt(), meta.getUpdatedAt(), meta.getStatus());
    }
}
