package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.CreateCustomRequestDTO;
import com.goldenhive.backend.dto.CustomRequestDTO;
import com.goldenhive.backend.iservice.ICustomRequestService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "CUSTOM PACKAGE APIs (6)", description = "Custom package request endpoints")
public class CustomRequestController {
    private final ICustomRequestService customRequestService;

    @PostMapping("/api/user/custom-package/request")
    @Operation(summary = "[CUSTOM PACKAGE] Create custom package request")
    public ApiResponse<CustomRequestDTO> create(@Valid @RequestBody CreateCustomRequestDTO request) {
        return ApiResponse.<CustomRequestDTO>builder().success(true).message("Custom request submitted").data(customRequestService.createRequest(request)).build();
    }

    @GetMapping("/api/user/custom-package/my-requests")
    @Operation(summary = "[CUSTOM PACKAGE] List my custom package requests")
    public ApiResponse<List<CustomRequestDTO>> myRequests(@RequestParam String userId) {
        return ApiResponse.<List<CustomRequestDTO>>builder().success(true).message("Custom requests fetched").data(customRequestService.getMyRequests(userId)).build();
    }

    @GetMapping("/api/admin/custom-requests")
    @Operation(summary = "[CUSTOM PACKAGE] List all custom requests")
    public ApiResponse<List<CustomRequestDTO>> all() {
        return ApiResponse.<List<CustomRequestDTO>>builder().success(true).message("Custom requests fetched").data(customRequestService.getAllRequests()).build();
    }

    @GetMapping("/api/admin/custom-requests/{id}")
    @Operation(summary = "[CUSTOM PACKAGE] Get custom request by ID")
    public ApiResponse<CustomRequestDTO> byId(@PathVariable String id) {
        return ApiResponse.<CustomRequestDTO>builder().success(true).message("Custom request fetched").data(customRequestService.getById(id)).build();
    }

    @PutMapping("/api/admin/custom-requests/{id}/approve")
    @Operation(summary = "[CUSTOM PACKAGE] Approve custom request")
    public ApiResponse<CustomRequestDTO> approve(@PathVariable String id) {
        return ApiResponse.<CustomRequestDTO>builder().success(true).message("Custom request approved").data(customRequestService.approve(id)).build();
    }

    @PutMapping("/api/admin/custom-requests/{id}/reject")
    @Operation(summary = "[CUSTOM PACKAGE] Reject custom request")
    public ApiResponse<CustomRequestDTO> reject(@PathVariable String id) {
        return ApiResponse.<CustomRequestDTO>builder().success(true).message("Custom request rejected").data(customRequestService.reject(id)).build();
    }
}


