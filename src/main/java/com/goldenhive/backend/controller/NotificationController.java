package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.NotificationDTO;
import com.goldenhive.backend.iservice.INotificationQueryService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "NOTIFICATION APIs (1)", description = "Notification endpoints")
public class NotificationController {
    private final INotificationQueryService notificationQueryService;

    @GetMapping("/api/admin/notifications")
    @Operation(summary = "[NOTIFICATION] Admin notifications")
    public ApiResponse<List<NotificationDTO>> list() {
        return ApiResponse.<List<NotificationDTO>>builder().success(true).message("Notifications fetched").data(notificationQueryService.getAdminNotifications()).build();
    }
}


