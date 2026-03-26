package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.DashboardStatsDTO;
import com.goldenhive.backend.iservice.IDashboardService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "DASHBOARD APIs (1)", description = "Dashboard endpoints")
public class DashboardController {
    private final IDashboardService dashboardService;

    @GetMapping("/api/admin/dashboard/stats")
    @Operation(summary = "[DASHBOARD] Dashboard stats")
    public ApiResponse<DashboardStatsDTO> stats() {
        return ApiResponse.<DashboardStatsDTO>builder().success(true).message("Dashboard stats fetched").data(dashboardService.getStats()).build();
    }
}


