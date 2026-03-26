package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Admin dashboard stats")
public class DashboardStatsDTO {
    private long totalPackages;
    private long totalBookings;
    private long pendingRequests;
    private long totalUsers;
}
