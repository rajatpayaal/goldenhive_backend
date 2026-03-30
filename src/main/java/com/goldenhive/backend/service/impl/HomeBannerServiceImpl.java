package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.CreateHomeBannerRequest;
import com.goldenhive.backend.dto.HomeBannerDTO;
import com.goldenhive.backend.entity.HomeBanner;
import com.goldenhive.backend.exception.BadRequestException;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.IHomeBannerService;
import com.goldenhive.backend.repository.HomeBannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeBannerServiceImpl implements IHomeBannerService {

    private final HomeBannerRepository homeBannerRepository;

    @Override
    public HomeBannerDTO createHomeBanner(CreateHomeBannerRequest request) {
        log.info("Creating home banner with display order: {}", request.getDisplayOrder());
        validateDisplayOrder(request.getDisplayOrder(), null);

        LocalDateTime now = LocalDateTime.now();
        HomeBanner banner = new HomeBanner();
        applyRequest(banner, request);
        banner.setCreatedAt(now);
        banner.setUpdatedAt(now);

        return mapToDTO(homeBannerRepository.save(banner));
    }

    @Override
    public List<HomeBannerDTO> getAllHomeBanners() {
        log.info("Fetching all home banners");
        return homeBannerRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<HomeBannerDTO> getActiveHomeBanners() {
        log.info("Fetching active home banners");
        return homeBannerRepository.findByActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .limit(4)
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Optional<HomeBannerDTO> getHomeBannerById(String bannerId) {
        log.info("Fetching home banner by ID: {}", bannerId);
        return homeBannerRepository.findById(bannerId).map(this::mapToDTO);
    }

    @Override
    public HomeBannerDTO updateHomeBanner(String bannerId, CreateHomeBannerRequest request) {
        log.info("Updating home banner by ID: {}", bannerId);
        validateDisplayOrder(request.getDisplayOrder(), bannerId);

        HomeBanner banner = homeBannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException("Home banner not found"));

        applyRequest(banner, request);
        banner.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(homeBannerRepository.save(banner));
    }

    @Override
    public void deleteHomeBanner(String bannerId) {
        log.info("Deleting home banner by ID: {}", bannerId);
        if (!homeBannerRepository.existsById(bannerId)) {
            throw new NotFoundException("Home banner not found");
        }
        homeBannerRepository.deleteById(bannerId);
    }

    private void validateDisplayOrder(Integer displayOrder, String currentBannerId) {
        homeBannerRepository.findByDisplayOrder(displayOrder)
                .filter(existing -> !existing.getBannerId().equals(currentBannerId))
                .ifPresent(existing -> {
                    throw new BadRequestException("Display order already exists: " + displayOrder);
                });
    }

    private void applyRequest(HomeBanner banner, CreateHomeBannerRequest request) {
        banner.setTitle(request.getTitle());
        banner.setDescription(request.getDescription());
        banner.setImageUrl(request.getImageUrl());
        banner.setAltText(request.getAltText());
        banner.setCtaLabel(request.getCtaLabel());
        banner.setCtaLink(request.getCtaLink());
        banner.setSeoTitle(request.getSeoTitle());
        banner.setSeoDescription(request.getSeoDescription());
        banner.setDisplayOrder(request.getDisplayOrder());
        banner.setActive(request.getActive());
    }

    private HomeBannerDTO mapToDTO(HomeBanner banner) {
        return new HomeBannerDTO(
                banner.getBannerId(),
                banner.getTitle(),
                banner.getDescription(),
                banner.getImageUrl(),
                banner.getAltText(),
                banner.getCtaLabel(),
                banner.getCtaLink(),
                banner.getSeoTitle(),
                banner.getSeoDescription(),
                banner.getDisplayOrder(),
                banner.isActive()
        );
    }
}
