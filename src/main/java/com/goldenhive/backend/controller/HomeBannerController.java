package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.CreateHomeBannerRequest;
import com.goldenhive.backend.dto.HomeBannerDTO;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.IHomeBannerService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "HOME BANNER APIs (6)", description = "Admin CRUD and public homepage banner endpoints")
public class HomeBannerController {

    private final IHomeBannerService homeBannerService;

    @PostMapping("/api/admin/home-banners")
    @Operation(summary = "[HOME BANNER] Create home banner")
    public ApiResponse<HomeBannerDTO> create(@Valid @RequestBody CreateHomeBannerRequest request) {
        return ApiResponse.<HomeBannerDTO>builder()
                .success(true)
                .message("Home banner created")
                .data(homeBannerService.createHomeBanner(request))
                .build();
    }

    @GetMapping("/api/admin/home-banners")
    @Operation(summary = "[HOME BANNER] List all home banners")
    public ApiResponse<List<HomeBannerDTO>> adminList() {
        return ApiResponse.<List<HomeBannerDTO>>builder()
                .success(true)
                .message("Home banners fetched")
                .data(homeBannerService.getAllHomeBanners())
                .build();
    }

    @GetMapping("/api/admin/home-banners/{id}")
    @Operation(summary = "[HOME BANNER] Get home banner by ID")
    public ApiResponse<HomeBannerDTO> get(@PathVariable String id) {
        return ApiResponse.<HomeBannerDTO>builder()
                .success(true)
                .message("Home banner fetched")
                .data(homeBannerService.getHomeBannerById(id)
                        .orElseThrow(() -> new NotFoundException("Home banner not found")))
                .build();
    }

    @PutMapping("/api/admin/home-banners/{id}")
    @Operation(summary = "[HOME BANNER] Update home banner")
    public ApiResponse<HomeBannerDTO> update(@PathVariable String id, @Valid @RequestBody CreateHomeBannerRequest request) {
        return ApiResponse.<HomeBannerDTO>builder()
                .success(true)
                .message("Home banner updated")
                .data(homeBannerService.updateHomeBanner(id, request))
                .build();
    }

    @DeleteMapping("/api/admin/home-banners/{id}")
    @Operation(summary = "[HOME BANNER] Delete home banner")
    public ApiResponse<Void> delete(@PathVariable String id) {
        homeBannerService.deleteHomeBanner(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Home banner deleted")
                .data(null)
                .build();
    }

    @GetMapping("/api/public/home-banners")
    @Operation(summary = "[HOME BANNER] Get public homepage slider banners")
    public ApiResponse<List<HomeBannerDTO>> getHomeBanners() {
        return ApiResponse.<List<HomeBannerDTO>>builder()
                .success(true)
                .message("Home banners fetched")
                .data(homeBannerService.getActiveHomeBanners())
                .build();
    }
}
