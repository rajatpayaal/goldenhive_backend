package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.CreatePackageActivityMappingRequest;
import com.goldenhive.backend.dto.PackageActivityMappingDTO;
import com.goldenhive.backend.iservice.IPackageActivityService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "PACKAGE-ACTIVITY MAPPING APIs (3)", description = "Package to activity mapping endpoints")
public class PackageActivityController {
    private final IPackageActivityService packageActivityService;

    @PostMapping("/api/admin/package-activities")
    @Operation(summary = "[MAPPING] Create package-activity mapping")
    public ApiResponse<PackageActivityMappingDTO> create(@Valid @RequestBody CreatePackageActivityMappingRequest request) {
        return ApiResponse.<PackageActivityMappingDTO>builder().success(true).message("Mapping created").data(packageActivityService.createMapping(request)).build();
    }

    @GetMapping("/api/admin/package-activities")
    @Operation(summary = "[MAPPING] List package-activity mappings")
    public ApiResponse<List<PackageActivityMappingDTO>> list(@RequestParam(required = false) String packageId) {
        List<PackageActivityMappingDTO> data = packageId == null ? List.of() : packageActivityService.getActivitiesForPackage(packageId);
        return ApiResponse.<List<PackageActivityMappingDTO>>builder().success(true).message("Mappings fetched").data(data).build();
    }

    @DeleteMapping("/api/admin/package-activities/{id}")
    @Operation(summary = "[MAPPING] Delete package-activity mapping")
    public ApiResponse<Void> delete(@PathVariable String id) {
        packageActivityService.deleteMapping(id);
        return ApiResponse.<Void>builder().success(true).message("Mapping deleted").data(null).build();
    }
}


