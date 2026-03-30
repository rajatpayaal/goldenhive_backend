package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.CreateHomeBannerRequest;
import com.goldenhive.backend.dto.HomeBannerDTO;

import java.util.List;
import java.util.Optional;

public interface IHomeBannerService {
    HomeBannerDTO createHomeBanner(CreateHomeBannerRequest request);
    List<HomeBannerDTO> getAllHomeBanners();
    List<HomeBannerDTO> getActiveHomeBanners();
    Optional<HomeBannerDTO> getHomeBannerById(String bannerId);
    HomeBannerDTO updateHomeBanner(String bannerId, CreateHomeBannerRequest request);
    void deleteHomeBanner(String bannerId);
}
