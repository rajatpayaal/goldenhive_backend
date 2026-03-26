package com.goldenhive.backend.dto;

import com.goldenhive.backend.enums.CustomRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Custom package request")
public class CustomRequestDTO {
    private String requestId;
    private String userId;
    private List<String> destinations;
    private double budget;
    private int travelers;
    private String preferences;
    private CustomRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
