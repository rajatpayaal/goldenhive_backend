package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.CreatePackageFormRequest;
import com.goldenhive.backend.dto.CreatePackageRequest;
import com.goldenhive.backend.dto.PackageDTO;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.IPackageService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "PACKAGE APIs (7)", description = "Package management endpoints")
public class PackageController {
    private final IPackageService packageService;

    @PostMapping(value = "/api/admin/packages", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "[PACKAGE] Create package")
    public ApiResponse<PackageDTO> create(@Valid @RequestBody CreatePackageRequest request) {
        return ApiResponse.<PackageDTO>builder().success(true).message("Package created").data(packageService.createPackage(request)).build();
    }

    @PostMapping(value = "/api/admin/packages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "[PACKAGE] Create package with multipart/form-data")
    public ApiResponse<PackageDTO> createWithFormData(@Valid @ModelAttribute CreatePackageFormRequest request) {
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

    @PutMapping(value = "/api/admin/packages/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "[PACKAGE] Update package")
    public ApiResponse<PackageDTO> update(@PathVariable String id, @Valid @RequestBody CreatePackageRequest request) {
        return ApiResponse.<PackageDTO>builder().success(true).message("Package updated").data(packageService.updatePackage(id, request)).build();
    }

    @PutMapping(value = "/api/admin/packages/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "[PACKAGE] Update package with multipart/form-data")
    public ApiResponse<PackageDTO> updateWithFormData(@PathVariable String id, @Valid @ModelAttribute CreatePackageFormRequest request) {
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
