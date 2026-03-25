package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Package Activity Mapping Data Transfer Object")
public class PackageActivityMappingDTO {
    
    @Schema(description = "Mapping ID", example = "60d5ec49c1234567890abc01")
    private String id;
    
    @Schema(description = "Package ID", example = "60d5ec49c1234567890abc02")
    private String packageId;
    
    @Schema(description = "Activity ID", example = "60d5ec49c1234567890abc03")
    private String activityId;
    
    @Schema(description = "Discounted price for this package", example = "3500")
    private double discountedPrice;
    
    @Schema(description = "Is activity included in base package", example = "true")
    private boolean isIncluded;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}
