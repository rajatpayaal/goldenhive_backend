package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.CreatePackageRequest;
import com.goldenhive.backend.dto.PackageDTO;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.IPackageService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "PACKAGE APIs (7)", description = "Package management endpoints")
public class PackageController {
    private final IPackageService packageService;

    @PostMapping("/api/admin/packages")
    @Operation(summary = "[PACKAGE] Create package")
    public ApiResponse<PackageDTO> create(@Valid @RequestBody CreatePackageRequest request) {
        return ApiResponse.<PackageDTO>builder().success(true).message("Package created").data(packageService.createPackage(request)).build();
    }

    @GetMapping("/api/admin/packages")
    @Operation(summary = "[PACKAGE] List admin packages")
    public ApiResponse<List<PackageDTO>> adminList() {
        return ApiResponse.<List<PackageDTO>>builder().success(true).message("Packages fetched").data(packageService.getAllPackages()).build();
    }

    @GetMapping("/api/admin/packages/{id}")
    @Operation(summary = "[PACKAGE] Get admin package by ID")
    public ApiResponse<PackageDTO> adminGet(@PathVariable String id) {
        return ApiResponse.<PackageDTO>builder().success(true).message("Package fetched").data(packageService.getPackageById(id).orElseThrow(() -> new NotFoundException("Package not found"))).build();
    }

    @PutMapping("/api/admin/packages/{id}")
    @Operation(summary = "[PACKAGE] Update package")
    public ApiResponse<PackageDTO> update(@PathVariable String id, @Valid @RequestBody CreatePackageRequest request) {
        return ApiResponse.<PackageDTO>builder().success(true).message("Package updated").data(packageService.updatePackage(id, request)).build();
    }

    @DeleteMapping("/api/admin/packages/{id}")
    @Operation(summary = "[PACKAGE] Delete package")
    public ApiResponse<Void> delete(@PathVariable String id) {
        packageService.deletePackage(id);
        return ApiResponse.<Void>builder().success(true).message("Package deleted").data(null).build();
    }

    @GetMapping("/api/user/packages")
    @Operation(summary = "[PACKAGE] List user packages")
    public ApiResponse<List<PackageDTO>> userList() {
        return ApiResponse.<List<PackageDTO>>builder().success(true).message("Packages fetched").data(packageService.getAllActivePackages()).build();
    }

    @GetMapping("/api/user/packages/{id}")
    @Operation(summary = "[PACKAGE] Get user package by ID")
    public ApiResponse<PackageDTO> userGet(@PathVariable String id) {
        return ApiResponse.<PackageDTO>builder().success(true).message("Package fetched").data(packageService.getPackageById(id).orElseThrow(() -> new NotFoundException("Package not found"))).build();
    }
}


