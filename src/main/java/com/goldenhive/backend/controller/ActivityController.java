package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.ActivityDTO;
import com.goldenhive.backend.dto.CreateActivityFormRequest;
import com.goldenhive.backend.dto.CreateActivityRequest;
import com.goldenhive.backend.dto.PackageActivityMappingDTO;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.IActivityService;
import com.goldenhive.backend.iservice.IPackageActivityService;
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
@Tag(name = "ACTIVITY APIs (6)", description = "Activity management endpoints")
public class ActivityController {
    private final IActivityService activityService;
    private final IPackageActivityService packageActivityService;

    @PostMapping(value = "/api/admin/activities", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "[ACTIVITY] Create activity")
    public ApiResponse<ActivityDTO> create(@Valid @RequestBody CreateActivityRequest request) {
        return ApiResponse.<ActivityDTO>builder().success(true).message("Activity created").data(activityService.createActivity(request)).build();
    }

    @PostMapping(value = "/api/admin/activities", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "[ACTIVITY] Create activity with multipart/form-data")
    public ApiResponse<ActivityDTO> createWithFormData(@Valid @ModelAttribute CreateActivityFormRequest request) {
        return ApiResponse.<ActivityDTO>builder().success(true).message("Activity created").data(activityService.createActivity(request)).build();
    }

    @GetMapping("/api/admin/activities")
    @Operation(summary = "[ACTIVITY] List admin activities")
    public ApiResponse<List<ActivityDTO>> adminList() {
        return ApiResponse.<List<ActivityDTO>>builder().success(true).message("Activities fetched").data(activityService.getAllActivities()).build();
    }

    @GetMapping("/api/admin/activities/{id}")
    @Operation(summary = "[ACTIVITY] Get admin activity by ID")
    public ApiResponse<ActivityDTO> get(@PathVariable String id) {
        return ApiResponse.<ActivityDTO>builder().success(true).message("Activity fetched").data(activityService.getActivityById(id).orElseThrow(() -> new NotFoundException("Activity not found"))).build();
    }

    @PutMapping(value = "/api/admin/activities/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "[ACTIVITY] Update activity")
    public ApiResponse<ActivityDTO> update(@PathVariable String id, @Valid @RequestBody CreateActivityRequest request) {
        return ApiResponse.<ActivityDTO>builder().success(true).message("Activity updated").data(activityService.updateActivity(id, request)).build();
    }

    @PutMapping(value = "/api/admin/activities/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "[ACTIVITY] Update activity with multipart/form-data")
    public ApiResponse<ActivityDTO> updateWithFormData(@PathVariable String id, @Valid @ModelAttribute CreateActivityFormRequest request) {
        return ApiResponse.<ActivityDTO>builder().success(true).message("Activity updated").data(activityService.updateActivity(id, request)).build();
    }

    @DeleteMapping("/api/admin/activities/{id}")
    @Operation(summary = "[ACTIVITY] Delete activity")
    public ApiResponse<Void> delete(@PathVariable String id) {
        activityService.deleteActivity(id);
        return ApiResponse.<Void>builder().success(true).message("Activity deleted").data(null).build();
    }

    @GetMapping("/api/user/packages/{id}/activities")
    @Operation(summary = "[ACTIVITY] List package activities for user")
    public ApiResponse<List<PackageActivityMappingDTO>> packageActivities(@PathVariable String id) {
        return ApiResponse.<List<PackageActivityMappingDTO>>builder().success(true).message("Package activities fetched").data(packageActivityService.getActivitiesForPackage(id)).build();
    }
}
