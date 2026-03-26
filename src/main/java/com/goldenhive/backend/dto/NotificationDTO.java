package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Notification summary")
public class NotificationDTO {
    private String type;
    private String title;
    private String recipient;
    private String message;
    private LocalDateTime createdAt;
}
